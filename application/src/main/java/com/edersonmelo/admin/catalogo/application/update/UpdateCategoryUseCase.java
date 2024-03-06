package com.edersonmelo.admin.catalogo.application.update;

import com.edersonmelo.admin.catalogo.application.UseCase;
import com.edersonmelo.admin.catalogo.domain.validation.handler.Notification;
import io.vavr.control.Either;

public abstract class UpdateCategoryUseCase extends UseCase<UpdateCategoryCommand, Either<Notification, UpdateCategoryOutput>> {
}
