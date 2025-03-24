/*
 * This file is generated by jOOQ.
 */
package com.example.generated.tables;


import com.example.generated.DefaultSchema;
import com.example.generated.Keys;
import com.example.generated.tables.Recipe.RecipePath;
import com.example.generated.tables.records.ShopRecord;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

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
public class Shop extends TableImpl<ShopRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>Shop</code>
     */
    public static final Shop SHOP = new Shop();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<ShopRecord> getRecordType() {
        return ShopRecord.class;
    }

    /**
     * The column <code>Shop.ID</code>.
     */
    public final TableField<ShopRecord, Integer> ID = createField(DSL.name("ID"), SQLDataType.INTEGER.identity(true), this, "");

    /**
     * The column <code>Shop.NAME</code>.
     */
    public final TableField<ShopRecord, String> NAME = createField(DSL.name("NAME"), SQLDataType.CLOB.nullable(false), this, "");

    /**
     * The column <code>Shop.Amount_Available</code>.
     */
    public final TableField<ShopRecord, Integer> AMOUNT_AVAILABLE = createField(DSL.name("Amount_Available"), SQLDataType.INTEGER, this, "");

    /**
     * The column <code>Shop.price</code>.
     */
    public final TableField<ShopRecord, Float> PRICE = createField(DSL.name("price"), SQLDataType.REAL, this, "");

    /**
     * The column <code>Shop.nutrition</code>.
     */
    public final TableField<ShopRecord, Integer> NUTRITION = createField(DSL.name("nutrition"), SQLDataType.INTEGER, this, "");

    private Shop(Name alias, Table<ShopRecord> aliased) {
        this(alias, aliased, (Field<?>[]) null, null);
    }

    private Shop(Name alias, Table<ShopRecord> aliased, Field<?>[] parameters, Condition where) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table(), where);
    }

    /**
     * Create an aliased <code>Shop</code> table reference
     */
    public Shop(String alias) {
        this(DSL.name(alias), SHOP);
    }

    /**
     * Create an aliased <code>Shop</code> table reference
     */
    public Shop(Name alias) {
        this(alias, SHOP);
    }

    /**
     * Create a <code>Shop</code> table reference
     */
    public Shop() {
        this(DSL.name("Shop"), null);
    }

    public <O extends Record> Shop(Table<O> path, ForeignKey<O, ShopRecord> childPath, InverseForeignKey<O, ShopRecord> parentPath) {
        super(path, childPath, parentPath, SHOP);
    }

    /**
     * A subtype implementing {@link Path} for simplified path-based joins.
     */
    public static class ShopPath extends Shop implements Path<ShopRecord> {

        private static final long serialVersionUID = 1L;
        public <O extends Record> ShopPath(Table<O> path, ForeignKey<O, ShopRecord> childPath, InverseForeignKey<O, ShopRecord> parentPath) {
            super(path, childPath, parentPath);
        }
        private ShopPath(Name alias, Table<ShopRecord> aliased) {
            super(alias, aliased);
        }

        @Override
        public ShopPath as(String alias) {
            return new ShopPath(DSL.name(alias), this);
        }

        @Override
        public ShopPath as(Name alias) {
            return new ShopPath(alias, this);
        }

        @Override
        public ShopPath as(Table<?> alias) {
            return new ShopPath(alias.getQualifiedName(), this);
        }
    }

    @Override
    public Schema getSchema() {
        return aliased() ? null : DefaultSchema.DEFAULT_SCHEMA;
    }

    @Override
    public Identity<ShopRecord, Integer> getIdentity() {
        return (Identity<ShopRecord, Integer>) super.getIdentity();
    }

    @Override
    public UniqueKey<ShopRecord> getPrimaryKey() {
        return Keys.SHOP__PK_SHOP;
    }

    @Override
    public List<ForeignKey<ShopRecord, ?>> getReferences() {
        return Arrays.asList(Keys.SHOP__FK_SHOP_PK_RECIPE);
    }

    private transient RecipePath _recipe;

    /**
     * Get the implicit join path to the <code>Recipe</code> table.
     */
    public RecipePath recipe() {
        if (_recipe == null)
            _recipe = new RecipePath(this, Keys.SHOP__FK_SHOP_PK_RECIPE, null);

        return _recipe;
    }

    @Override
    public Shop as(String alias) {
        return new Shop(DSL.name(alias), this);
    }

    @Override
    public Shop as(Name alias) {
        return new Shop(alias, this);
    }

    @Override
    public Shop as(Table<?> alias) {
        return new Shop(alias.getQualifiedName(), this);
    }

    /**
     * Rename this table
     */
    @Override
    public Shop rename(String name) {
        return new Shop(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Shop rename(Name name) {
        return new Shop(name, null);
    }

    /**
     * Rename this table
     */
    @Override
    public Shop rename(Table<?> name) {
        return new Shop(name.getQualifiedName(), null);
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public Shop where(Condition condition) {
        return new Shop(getQualifiedName(), aliased() ? this : null, null, condition);
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public Shop where(Collection<? extends Condition> conditions) {
        return where(DSL.and(conditions));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public Shop where(Condition... conditions) {
        return where(DSL.and(conditions));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public Shop where(Field<Boolean> condition) {
        return where(DSL.condition(condition));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public Shop where(SQL condition) {
        return where(DSL.condition(condition));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public Shop where(@Stringly.SQL String condition) {
        return where(DSL.condition(condition));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public Shop where(@Stringly.SQL String condition, Object... binds) {
        return where(DSL.condition(condition, binds));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public Shop where(@Stringly.SQL String condition, QueryPart... parts) {
        return where(DSL.condition(condition, parts));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public Shop whereExists(Select<?> select) {
        return where(DSL.exists(select));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public Shop whereNotExists(Select<?> select) {
        return where(DSL.notExists(select));
    }
}
