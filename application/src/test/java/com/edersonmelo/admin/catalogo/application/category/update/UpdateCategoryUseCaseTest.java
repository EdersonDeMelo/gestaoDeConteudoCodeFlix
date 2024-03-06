package com.edersonmelo.admin.catalogo.application.category.update;

import com.edersonmelo.admin.catalogo.application.create.CreateCategoryCommand;
import com.edersonmelo.admin.catalogo.application.update.DefaultUpdateCategoryUseCase;
import com.edersonmelo.admin.catalogo.application.update.UpdateCategoryCommand;
import com.edersonmelo.admin.catalogo.domain.category.Category;
import com.edersonmelo.admin.catalogo.domain.category.CategoryGateway;
import com.edersonmelo.admin.catalogo.domain.category.CategoryID;
import com.edersonmelo.admin.catalogo.domain.exceptions.DomainException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Objects;
import java.util.Optional;

import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UpdateCategoryUseCaseTest {

    @InjectMocks
    private DefaultUpdateCategoryUseCase useCase;

    @Mock
    private CategoryGateway categoryGateway;

    @BeforeEach
    void cleanUp(){
        Mockito.reset(categoryGateway);
    }

    @Test
    public void giveAValidCommand_whenCallsUpdateCategory_shouldReturnCategoryId() {
        final var aCategory = Category.newCategory("film", null, true);

        final var expectedName = "Filmes";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = true;

        final var expectedId = aCategory.getId();

        final var aCommand = UpdateCategoryCommand.with(
                aCategory.getId().getValue(),
                expectedName,
                expectedDescription,
                expectedIsActive
        );

        Mockito.when(categoryGateway.findById(Mockito.eq(expectedId)))
                .thenReturn(Optional.of(aCategory.clone()));

        Mockito.when(categoryGateway.update(Mockito.any()))
                .thenAnswer(returnsFirstArg());

        final var actualOutput = useCase.execute(aCommand).get();

        Assertions.assertNotNull(actualOutput);
        Assertions.assertNotNull(actualOutput.id());

        Mockito.verify(categoryGateway, Mockito.times(1)).findById(Mockito.eq(expectedId));

        Mockito.verify(categoryGateway, Mockito.times(1)).update(Mockito.argThat(
                aUpdatecategory -> Objects.equals(expectedName, aUpdatecategory.getName())
                        && Objects.equals(expectedDescription, aUpdatecategory.getDescription())
                        && Objects.equals(expectedIsActive, aUpdatecategory.isActive())
                        && Objects.equals(expectedId, aUpdatecategory.getId())
                        && Objects.equals(aCategory.getCreatedAt(), aUpdatecategory.getCreatedAt())
                        && aCategory.getUpdateAt().isBefore(aUpdatecategory.getUpdateAt())
                        && Objects.isNull(aUpdatecategory.getDeletedAt())
        ));
    }

    @Test
    public void givenAInvalidName_whenCallsUpdateCategory_thenShouldReturnDomainException() {
        final var aCategory = Category.newCategory("film", null, true);

        final String expectedName = null;
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = true;
        final var expectedId = aCategory.getId();
        final var expectedErrorMessage = "'name' should not be null";
        final var expectedErrorCount = 1;

        final var aCommand =
                UpdateCategoryCommand.with(expectedId.getValue(), expectedName, expectedDescription, expectedIsActive);

        Mockito.when(categoryGateway.findById(Mockito.eq(expectedId)))
                .thenReturn(Optional.of(aCategory.clone()));

        final var notification = useCase.execute(aCommand).getLeft();

        Assertions.assertEquals(expectedErrorCount, notification.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, notification.firstError().message());

        Mockito.verify(categoryGateway, times(0)).create(any());
    }

    @Test
    public void giveAValidInactivateCommand_whenCallsUpdateCategory_shouldReturnInactiveCategoryId() {
        final var aCategory = Category.newCategory("film", null, true);

        final var expectedName = "Filmes";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = false;

        final var expectedId = aCategory.getId();

        final var aCommand = UpdateCategoryCommand.with(
                aCategory.getId().getValue(),
                expectedName,
                expectedDescription,
                expectedIsActive
        );

        Mockito.when(categoryGateway.findById(Mockito.eq(expectedId)))
                .thenReturn(Optional.of(aCategory.clone()));

        Mockito.when(categoryGateway.update(Mockito.any()))
                .thenAnswer(returnsFirstArg());

        Assertions.assertTrue(aCategory.isActive());
        Assertions.assertNull(aCategory.getDeletedAt());

        final var actualOutput = useCase.execute(aCommand).get();

        Assertions.assertNotNull(actualOutput);
        Assertions.assertNotNull(actualOutput.id());

        Mockito.verify(categoryGateway, Mockito.times(1)).findById(Mockito.eq(expectedId));

        Mockito.verify(categoryGateway, Mockito.times(1)).update(Mockito.argThat(
                aUpdatecategory -> Objects.equals(expectedName, aUpdatecategory.getName())
                        && Objects.equals(expectedDescription, aUpdatecategory.getDescription())
                        && Objects.equals(expectedIsActive, aUpdatecategory.isActive())
                        && Objects.equals(expectedId, aUpdatecategory.getId())
                        && Objects.equals(aCategory.getCreatedAt(), aUpdatecategory.getCreatedAt())
                        && aCategory.getUpdateAt().isBefore(aUpdatecategory.getUpdateAt())
                        && Objects.nonNull(aUpdatecategory.getDeletedAt())
        ));
    }

    @Test
    public void giveAValidCommand_whenCreateCategoryThrowsRandomException_shouldReturnException() {
        final var aCategory = Category.newCategory("film", null, true);

        final var expectedName = "Filmes";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = true;
        final var expectedId = aCategory.getId();
        final var expectedErrorMessage = "Gateway error";
        final var expectedErrorCount = 1;

        final var aCommand = UpdateCategoryCommand.with(
                expectedId.getValue(),
                expectedName,
                expectedDescription,
                expectedIsActive
        );

        Mockito.when(categoryGateway.findById(Mockito.eq(expectedId)))
                .thenReturn(Optional.of(aCategory.clone()));

        when(categoryGateway.update(any()))
                .thenThrow(new IllegalStateException("Gateway error"));

        final var notification = useCase.execute(aCommand).getLeft();

        Assertions.assertEquals(expectedErrorCount, notification.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, notification.firstError().message());

        Mockito.verify(categoryGateway, Mockito.times(1)).update(Mockito.argThat(
                aUpdatecategory -> Objects.equals(expectedName, aUpdatecategory.getName())
                        && Objects.equals(expectedDescription, aUpdatecategory.getDescription())
                        && Objects.equals(expectedIsActive, aUpdatecategory.isActive())
                        && Objects.equals(expectedId, aUpdatecategory.getId())
                        && Objects.equals(aCategory.getCreatedAt(), aUpdatecategory.getCreatedAt())
                        && aCategory.getUpdateAt().isBefore(aUpdatecategory.getUpdateAt())
                        && Objects.isNull(aUpdatecategory.getDeletedAt())
        ));
    }

    @Test
    public void giveACommandWithInvalidID_whenCallsUpdateCategory_shouldReturnNotFoundException() {
        final var aCategory = Category.newCategory("film", null, true);

        final var expectedName = "Filmes";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = false;
        final var expectedId = "123";
        final var expectedErrorMessage = "Category with ID 123 was not found";
        final var expectedErrorCount = 1;

        final var aCommand = UpdateCategoryCommand.with(
                expectedId,
                expectedName,
                expectedDescription,
                expectedIsActive
        );

        Mockito.when(categoryGateway.findById(CategoryID.from(expectedId)))
                .thenReturn(Optional.empty());

        final var actualException = Assertions.assertThrows(DomainException.class, ()-> useCase.execute(aCommand));

        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());

        Mockito.verify(categoryGateway, Mockito.times(1)).findById(Mockito.eq(CategoryID.from(expectedId)));

        Mockito.verify(categoryGateway, Mockito.times(0)).update(any());
    }

}
