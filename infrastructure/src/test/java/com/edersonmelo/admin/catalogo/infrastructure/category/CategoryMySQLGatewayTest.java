package com.edersonmelo.admin.catalogo.infrastructure.category;

import com.edersonmelo.admin.catalogo.domain.category.Category;
import com.edersonmelo.admin.catalogo.infrastructure.MySQLGatewayTest;
import com.edersonmelo.admin.catalogo.infrastructure.category.persistence.CategoryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@MySQLGatewayTest
public class CategoryMySQLGatewayTest {

    @Autowired
    private CategoryMySQLGateway categoryGateway;

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    public void givenAValidCategory_whenCallsCreate_shouldReturnANewCategory(){
        final var expectedName = "Filmes";
        final var expectedDescription =  "A categoria mais assistida";
        final var expectedIsActive = true;

        final var aCategory = Category.newCategory(expectedName, expectedDescription, expectedIsActive);

        Assertions.assertEquals(0, categoryRepository.count());

        final var actualCategory = categoryGateway.create(aCategory);

        Assertions.assertEquals(1, categoryRepository.count());

        Assertions.assertEquals(aCategory.getId(), actualCategory.getId());
        Assertions.assertEquals(expectedName, actualCategory.getName());
        Assertions.assertEquals(expectedDescription, actualCategory.getDescription());
        Assertions.assertEquals(expectedIsActive, actualCategory.isActive());
        Assertions.assertEquals(aCategory.getCreatedAt(), actualCategory.getCreatedAt());
        Assertions.assertEquals(aCategory.getUpdateAt(), actualCategory.getUpdateAt());
        Assertions.assertEquals(aCategory.getDeletedAt(), actualCategory.getDeletedAt());
        Assertions.assertNull(actualCategory.getDeletedAt());

        final var actualEntity = categoryRepository.findById(aCategory.getId().getValue()).get();

        Assertions.assertEquals(aCategory.getId(), actualEntity.getId());
        Assertions.assertEquals(expectedName, actualEntity.getName());
        Assertions.assertEquals(expectedDescription, actualEntity.getDescription());
        Assertions.assertEquals(expectedIsActive, actualEntity.getActive());
        Assertions.assertEquals(aCategory.getCreatedAt(), actualEntity.getCreatedAt());
        Assertions.assertEquals(aCategory.getUpdateAt(), actualEntity.getUpdatedAt());
        Assertions.assertEquals(aCategory.getDeletedAt(), actualEntity.getDeletedAt());
        Assertions.assertNull(actualCategory.getDeletedAt());
    }

}
