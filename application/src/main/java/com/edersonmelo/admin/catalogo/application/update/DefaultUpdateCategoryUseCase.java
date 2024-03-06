package com.edersonmelo.admin.catalogo.application.update;

import com.edersonmelo.admin.catalogo.application.UseCase;
import com.edersonmelo.admin.catalogo.domain.category.Category;
import com.edersonmelo.admin.catalogo.domain.category.CategoryGateway;
import com.edersonmelo.admin.catalogo.domain.category.CategoryID;
import com.edersonmelo.admin.catalogo.domain.exceptions.DomainException;
import com.edersonmelo.admin.catalogo.domain.validation.Error;
import com.edersonmelo.admin.catalogo.domain.validation.handler.Notification;
import io.vavr.API;
import io.vavr.control.Either;

import java.util.Objects;
import java.util.function.Supplier;

public class DefaultUpdateCategoryUseCase  extends UpdateCategoryUseCase{

    private final CategoryGateway categoryGateway;

    public DefaultUpdateCategoryUseCase(CategoryGateway categoryGateway) {
        this.categoryGateway = Objects.requireNonNull(categoryGateway);
    }

    @Override
    public Either<Notification, UpdateCategoryOutput> execute(UpdateCategoryCommand aCommand) {
        final var anId = CategoryID.from(aCommand.id());
        final var aName = aCommand.name();
        final var aDescription = aCommand.description();
        final var isActive = aCommand.isActive();

        final var aCategory = this.categoryGateway.findById(anId)
                .orElseThrow(notFound(anId));
        
        final var notification = Notification.create();
        aCategory
                .update(aName, aDescription, isActive)
                .validate(notification);
        
        return notification.hasError() ? API.Left(notification) : update(aCategory);
    }

    private Either<Notification, UpdateCategoryOutput> update(Category aCategory) {
        return API.Try(()-> this.categoryGateway.update(aCategory))
                .toEither()
                .bimap(Notification::create, UpdateCategoryOutput::from);
    }

    private Supplier<DomainException> notFound(CategoryID anId){
        return () -> DomainException.with(new Error("Category with ID %s was not found".formatted(anId.getValue())));
    }
}
