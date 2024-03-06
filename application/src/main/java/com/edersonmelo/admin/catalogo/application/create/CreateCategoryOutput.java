package com.edersonmelo.admin.catalogo.application.create;

import com.edersonmelo.admin.catalogo.domain.category.Category;
import com.edersonmelo.admin.catalogo.domain.category.CategoryID;

public record CreateCategoryOutput(
        CategoryID id

) {
    public static CreateCategoryOutput from(final Category aCategory){
        return new CreateCategoryOutput(aCategory.getId());
    }
}
