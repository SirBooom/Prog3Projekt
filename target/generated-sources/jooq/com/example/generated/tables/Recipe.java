/*
 * This file is generated by jOOQ.
 */
package com.example.generated.tables;


import com.example.generated.DefaultSchema;
import com.example.generated.Keys;
import com.example.generated.tables.IngredientDgTmp.IngredientDgTmpPath;
import com.example.generated.tables.Shop.ShopPath;
import com.example.generated.tables.records.RecipeRecord;

import java.util.Collection;

import org.jooq.Condition;
import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Identity;
import org.jooq.InverseForeignKey;
import org.jooq.Name;
import org.jooq.Path;
import org.jooq.PlainSQL;
import org.jooq.QueryPart;
import org.jooq.Record;
import org.jooq.SQL;
import org.jooq.Schema;
import org.jooq.Select;
import org.jooq.Stringly;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableOptions;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes", "this-escape" })
public class Recipe extends TableImpl<RecipeRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>Recipe</code>
     */
    public static final Recipe RECIPE = new Recipe();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<RecipeRecord> getRecordType() {
        return RecipeRecord.class;
    }

    /**
     * The column <code>Recipe.ID</code>.
     */
    public final TableField<RecipeRecord, Integer> ID = createField(DSL.name("ID"), SQLDataType.INTEGER.identity(true), this, "");

    /**
     * The column <code>Recipe.name</code>.
     */
    public final TableField<RecipeRecord, String> NAME = createField(DSL.name("name"), SQLDataType.VARCHAR(25), this, "");

    /**
     * The column <code>Recipe.cuisine</code>.
     */
    public final TableField<RecipeRecord, String> CUISINE = createField(DSL.name("cuisine"), SQLDataType.CLOB, this, "");

    /**
     * The column <code>Recipe.category</code>.
     */
    public final TableField<RecipeRecord, String> CATEGORY = createField(DSL.name("category"), SQLDataType.CLOB, this, "");

    /**
     * The column <code>Recipe.instructions</code>.
     */
    public final TableField<RecipeRecord, String> INSTRUCTIONS = createField(DSL.name("instructions"), SQLDataType.CLOB, this, "");

    /**
     * The column <code>Recipe.nutrition</code>.
     */
    public final TableField<RecipeRecord, Integer> NUTRITION = createField(DSL.name("nutrition"), SQLDataType.INTEGER, this, "");

    /**
     * The column <code>Recipe.cookingTime</code>.
     */
    public final TableField<RecipeRecord, String> COOKINGTIME = createField(DSL.name("cookingTime"), SQLDataType.CLOB, this, "");

    /**
     * The column <code>Recipe.ingredient</code>.
     */
    public final TableField<RecipeRecord, String> INGREDIENT = createField(DSL.name("ingredient"), SQLDataType.CLOB, this, "");

    private Recipe(Name alias, Table<RecipeRecord> aliased) {
        this(alias, aliased, (Field<?>[]) null, null);
    }

    private Recipe(Name alias, Table<RecipeRecord> aliased, Field<?>[] parameters, Condition where) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table(), where);
    }

    /**
     * Create an aliased <code>Recipe</code> table reference
     */
    public Recipe(String alias) {
        this(DSL.name(alias), RECIPE);
    }

    /**
     * Create an aliased <code>Recipe</code> table reference
     */
    public Recipe(Name alias) {
        this(alias, RECIPE);
    }

    /**
     * Create a <code>Recipe</code> table reference
     */
    public Recipe() {
        this(DSL.name("Recipe"), null);
    }

    public <O extends Record> Recipe(Table<O> path, ForeignKey<O, RecipeRecord> childPath, InverseForeignKey<O, RecipeRecord> parentPath) {
        super(path, childPath, parentPath, RECIPE);
    }

    /**
     * A subtype implementing {@link Path} for simplified path-based joins.
     */
    public static class RecipePath extends Recipe implements Path<RecipeRecord> {

        private static final long serialVersionUID = 1L;
        public <O extends Record> RecipePath(Table<O> path, ForeignKey<O, RecipeRecord> childPath, InverseForeignKey<O, RecipeRecord> parentPath) {
            super(path, childPath, parentPath);
        }
        private RecipePath(Name alias, Table<RecipeRecord> aliased) {
            super(alias, aliased);
        }

        @Override
        public RecipePath as(String alias) {
            return new RecipePath(DSL.name(alias), this);
        }

        @Override
        public RecipePath as(Name alias) {
            return new RecipePath(alias, this);
        }

        @Override
        public RecipePath as(Table<?> alias) {
            return new RecipePath(alias.getQualifiedName(), this);
        }
    }

    @Override
    public Schema getSchema() {
        return aliased() ? null : DefaultSchema.DEFAULT_SCHEMA;
    }

    @Override
    public Identity<RecipeRecord, Integer> getIdentity() {
        return (Identity<RecipeRecord, Integer>) super.getIdentity();
    }

    @Override
    public UniqueKey<RecipeRecord> getPrimaryKey() {
        return Keys.RECIPE__PK_RECIPE;
    }

    private transient IngredientDgTmpPath _ingredientDgTmp;

    /**
     * Get the implicit to-many join path to the <code>Ingredient_dg_tmp</code>
     * table
     */
    public IngredientDgTmpPath ingredientDgTmp() {
        if (_ingredientDgTmp == null)
            _ingredientDgTmp = new IngredientDgTmpPath(this, null, Keys.INGREDIENT_DG_TMP__FK_INGREDIENT_DG_TMP_PK_RECIPE.getInverseKey());

        return _ingredientDgTmp;
    }

    private transient ShopPath _shop;

    /**
     * Get the implicit to-many join path to the <code>Shop</code> table
     */
    public ShopPath shop() {
        if (_shop == null)
            _shop = new ShopPath(this, null, Keys.SHOP__FK_SHOP_PK_RECIPE.getInverseKey());

        return _shop;
    }

    @Override
    public Recipe as(String alias) {
        return new Recipe(DSL.name(alias), this);
    }

    @Override
    public Recipe as(Name alias) {
        return new Recipe(alias, this);
    }

    @Override
    public Recipe as(Table<?> alias) {
        return new Recipe(alias.getQualifiedName(), this);
    }

    /**
     * Rename this table
     */
    @Override
    public Recipe rename(String name) {
        return new Recipe(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Recipe rename(Name name) {
        return new Recipe(name, null);
    }

    /**
     * Rename this table
     */
    @Override
    public Recipe rename(Table<?> name) {
        return new Recipe(name.getQualifiedName(), null);
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public Recipe where(Condition condition) {
        return new Recipe(getQualifiedName(), aliased() ? this : null, null, condition);
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public Recipe where(Collection<? extends Condition> conditions) {
        return where(DSL.and(conditions));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public Recipe where(Condition... conditions) {
        return where(DSL.and(conditions));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public Recipe where(Field<Boolean> condition) {
        return where(DSL.condition(condition));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public Recipe where(SQL condition) {
        return where(DSL.condition(condition));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public Recipe where(@Stringly.SQL String condition) {
        return where(DSL.condition(condition));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public Recipe where(@Stringly.SQL String condition, Object... binds) {
        return where(DSL.condition(condition, binds));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public Recipe where(@Stringly.SQL String condition, QueryPart... parts) {
        return where(DSL.condition(condition, parts));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public Recipe whereExists(Select<?> select) {
        return where(DSL.exists(select));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public Recipe whereNotExists(Select<?> select) {
        return where(DSL.notExists(select));
    }
}
