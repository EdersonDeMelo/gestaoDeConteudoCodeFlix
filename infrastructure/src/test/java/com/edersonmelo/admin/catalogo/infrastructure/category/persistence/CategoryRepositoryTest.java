package com.edersonmelo.admin.catalogo.infrastructure.category.persistence;

import com.edersonmelo.admin.catalogo.domain.category.Category;
import com.edersonmelo.admin.catalogo.infrastructure.MySQLGatewayTest;
import org.hibernate.PropertyValueException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;

@MySQLGatewayTest
public class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    public void givenAnInvalidName_whenCallsSave_shouldReturnError(){
        final var expectedPropertyName = "name";
        final var expectedMessage = "not-null property references";

        final var aCategory = Category.newCategory("Filmes", "A categoria mais assistida", true);

        final var anEntity= CategoryJpaEntity.from(aCategory);
        anEntity.setName(null);

        final var actualException =
                Assertions.assertThrows(DataIntegrityViolationException.class, ()->categoryRepository.save(anEntity));

        final var actualCase =
                Assertions.assertInstanceOf(PropertyValueException.class, actualException.getCause());

        Assertions.assertEquals(expectedPropertyName, actualCase.getPropertyName());
        Assertions.assertEquals(expectedMessage, actualCase.getMessage());

    }

    @Test
    public void givenAnInvalidNullCreatedAt_whenCallsSave_shouldReturnError(){
        final var expectedPropertyName = "createAt";
        final var expectedMessage = "not-null property references";

        final var aCategory = Category.newCategory("Filmes", "A categoria mais assistida", true);

        final var anEntity= CategoryJpaEntity.from(aCategory);
        anEntity.setCreatedAt(null);

        final var actualException =
                Assertions.assertThrows(DataIntegrityViolationException.class, ()->categoryRepository.save(anEntity));

        final var actualCase =
                Assertions.assertInstanceOf(PropertyValueException.class, actualException.getCause());

        Assertions.assertEquals(expectedPropertyName, actualCase.getPropertyName());
        Assertions.assertEquals(expectedMessage, actualCase.getMessage());

    }
    @Test
    public void givenAnInvalidNullUpdateAt_whenCallsSave_shouldReturnError(){
        final var expectedPropertyName = "updatedAt";
        final var expectedMessage = "not-null property references";

        final var aCategory = Category.newCategory("Filmes", "A categoria mais assistida", true);

        final var anEntity= CategoryJpaEntity.from(aCategory);
        anEntity.setUpdatedAt(null);

        final var actualException =
                Assertions.assertThrows(DataIntegrityViolationException.class, ()->categoryRepository.save(anEntity));

        final var actualCase =
                Assertions.assertInstanceOf(PropertyValueException.class, actualException.getCause());

        Assertions.assertEquals(expectedPropertyName, actualCase.getPropertyName());
        Assertions.assertEquals(expectedMessage, actualCase.getMessage());

    }

}
