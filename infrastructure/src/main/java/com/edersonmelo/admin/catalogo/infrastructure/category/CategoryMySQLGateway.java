package com.edersonmelo.admin.catalogo.infrastructure.category;

import com.edersonmelo.admin.catalogo.domain.category.Category;
import com.edersonmelo.admin.catalogo.domain.category.CategoryGateway;
import com.edersonmelo.admin.catalogo.domain.category.CategoryID;
import com.edersonmelo.admin.catalogo.domain.category.CategorySearchQuery;
import com.edersonmelo.admin.catalogo.domain.pagination.Pagination;
import com.edersonmelo.admin.catalogo.infrastructure.category.persistence.CategoryJpaEntity;
import com.edersonmelo.admin.catalogo.infrastructure.category.persistence.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoryMySQLGateway implements CategoryGateway {

    private final CategoryRepository repository;

    public CategoryMySQLGateway(final CategoryRepository repository) {
        this.repository = repository;
    }

    @Override
    public Category create(final Category category) {
        return this.repository.save(CategoryJpaEntity.from(category)).toAggregate();
    }

    @Override
    public void deleteById(CategoryID anId) {

    }

    @Override
    public Optional<Category> findById(CategoryID anId) {
        return Optional.empty();
    }

    @Override
    public Category update(Category aCategory) {
        return null;
    }

    @Override
    public Pagination<Category> findAll(CategorySearchQuery aQuery) {
        return null;
    }
}
