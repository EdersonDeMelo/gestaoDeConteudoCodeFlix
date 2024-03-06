package com.edersonmelo.admin.catalogo.application.retrieve.list;

import com.edersonmelo.admin.catalogo.domain.category.CategoryGateway;
import com.edersonmelo.admin.catalogo.domain.category.CategorySearchQuery;
import com.edersonmelo.admin.catalogo.domain.pagination.Pagination;

import java.util.Objects;

public class DefaultListCategoriesUseCase extends ListCategoriesUseCase{

    private final CategoryGateway categoryGateway;

    public DefaultListCategoriesUseCase(CategoryGateway categoryGateway) {
        this.categoryGateway = Objects.requireNonNull(categoryGateway);
    }

    @Override
    public Pagination<CategoryListOutput> execute(final CategorySearchQuery aQuery) {
        return this.categoryGateway.findAll(aQuery)
                .map(CategoryListOutput::from);
    }
}
