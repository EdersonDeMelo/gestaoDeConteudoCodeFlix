package com.edersonmelo.admin.catalogo.domain.category;

import com.edersonmelo.admin.catalogo.domain.AggregateRoot;
import com.edersonmelo.admin.catalogo.domain.validation.ValidationHandler;

import java.time.Instant;
import java.util.Objects;

public class Category extends AggregateRoot<CategoryID> implements Cloneable{
    private String name;
    private String description;
    private boolean active;
    private Instant createdAt;
    private Instant updateAt;
    private Instant deletedAt;

    public Category(
            final CategoryID anId,
            final String aName,
            final String aDescription,
            final boolean isActive,
            final Instant aCreationDate,
            final Instant aUpdateDate,
            final Instant aDeleteDate
    ) {
        super(anId);
        this.name = aName;
        this.description = aDescription;
        this.active = isActive;
        this.createdAt = Objects.requireNonNull(aCreationDate, "'createdAt' should not be null");
        this.updateAt = Objects.requireNonNull(aUpdateDate, "'updateAt' should not be null");
        this.deletedAt = aDeleteDate;
    }

    public Category() {
        super(CategoryID.unique());
    }

    public static Category newCategory(final String aName, final String aDescription, final boolean isActive){
        final var id = CategoryID.unique();
        final var now = Instant.now();
        final var deletedAt = isActive ? null : now;
        return new Category(id, aName, aDescription, isActive, now, now, deletedAt);
    }
    public static Category with(final Category aCategory){
        return with(
                aCategory.getId(),
                aCategory.getName(),
                aCategory.getDescription(),
                aCategory.isActive(),
                aCategory.getCreatedAt(),
                aCategory.getUpdateAt(),
                aCategory.getDeletedAt()
        );
    }

    public static Category with(
            final CategoryID anId,
            final String name,
            final String description,
            final Boolean active,
            final Instant createdAt,
            final Instant updatedAt,
            final Instant deletedAt) {
        return new Category(
                anId,
                name,
                description,
                active,
                createdAt,
                updatedAt,
                deletedAt
        );
    }

    @Override
    public void validate(final ValidationHandler handler) {
        new CategoryValidator(this, handler).validate();
    }

    public Category activate(){
        this.deletedAt  = null;
        this.active = true;
        this.updateAt = Instant.now();
        return this;
    }

    public Category deactivate(){
        if (getDeletedAt() == null){
            this.deletedAt  =Instant.now();
        }
        this.active = false;
        this.updateAt = Instant.now();
        return this;
    }

    public Category update(final String aNAme, final String aDescription, final boolean isActive){

        if(isActive){
            activate();
        }else{
            deactivate();
        }
        this.name = aNAme;
        this.description = aDescription;
        this.updateAt = Instant.now();
        return this;
    }

    public CategoryID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public boolean isActive() {
        return active;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdateAt() {
        return updateAt;
    }

    public Instant getDeletedAt() {
        return deletedAt;
    }

    @Override
    public Category clone(){
        try {
            Category clone = (Category) super.clone();

            return clone;
        }catch (CloneNotSupportedException e){
            throw new AssertionError();
        }
    }
}
