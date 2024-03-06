package com.edersonmelo.admin.catalogo.application.create;

import com.edersonmelo.admin.catalogo.application.UseCase;
import com.edersonmelo.admin.catalogo.domain.validation.handler.Notification;
import io.vavr.control.Either;

public abstract class CreateCategoryUseCase extends UseCase<CreateCategoryCommand, Either<Notification, CreateCategoryOutput> > {
}
