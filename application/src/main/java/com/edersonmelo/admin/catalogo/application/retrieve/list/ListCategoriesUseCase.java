package com.edersonmelo.admin.catalogo.application.retrieve.list;

import com.edersonmelo.admin.catalogo.application.UseCase;
import com.edersonmelo.admin.catalogo.domain.category.CategorySearchQuery;
import com.edersonmelo.admin.catalogo.domain.pagination.Pagination;

public abstract class ListCategoriesUseCase extends UseCase<CategorySearchQuery, Pagination<CategoryListOutput>> {
}
