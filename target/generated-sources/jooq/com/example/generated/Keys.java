/*
 * This file is generated by jOOQ.
 */
package com.example.generated;


import com.example.generated.tables.IngredientDgTmp;
import com.example.generated.tables.Recipe;
import com.example.generated.tables.Shop;
import com.example.generated.tables.records.IngredientDgTmpRecord;
import com.example.generated.tables.records.RecipeRecord;
import com.example.generated.tables.records.ShopRecord;

import org.jooq.ForeignKey;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.Internal;


/**
 * A class modelling foreign key relationships and constraints of tables in the
 * default schema.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes", "this-escape" })
public class Keys {

    // -------------------------------------------------------------------------
    // UNIQUE and PRIMARY KEY definitions
    // -------------------------------------------------------------------------

    public static final UniqueKey<IngredientDgTmpRecord> INGREDIENT_DG_TMP__PK_INGREDIENT_DG_TMP = Internal.createUniqueKey(IngredientDgTmp.INGREDIENT_DG_TMP, DSL.name("pk_Ingredient_dg_tmp"), new TableField[] { IngredientDgTmp.INGREDIENT_DG_TMP.ID }, true);
    public static final UniqueKey<RecipeRecord> RECIPE__PK_RECIPE = Internal.createUniqueKey(Recipe.RECIPE, DSL.name("pk_Recipe"), new TableField[] { Recipe.RECIPE.ID }, true);
    public static final UniqueKey<ShopRecord> SHOP__PK_SHOP = Internal.createUniqueKey(Shop.SHOP, DSL.name("pk_Shop"), new TableField[] { Shop.SHOP.ID }, true);

    // -------------------------------------------------------------------------
    // FOREIGN KEY definitions
    // -------------------------------------------------------------------------

    public static final ForeignKey<IngredientDgTmpRecord, RecipeRecord> INGREDIENT_DG_TMP__FK_INGREDIENT_DG_TMP_PK_RECIPE = Internal.createForeignKey(IngredientDgTmp.INGREDIENT_DG_TMP, DSL.name("fk_Ingredient_dg_tmp_pk_Recipe"), new TableField[] { IngredientDgTmp.INGREDIENT_DG_TMP.AMOUNT_AVAILABLE }, Keys.RECIPE__PK_RECIPE, new TableField[] { Recipe.RECIPE.ID }, true);
    public static final ForeignKey<ShopRecord, RecipeRecord> SHOP__FK_SHOP_PK_RECIPE = Internal.createForeignKey(Shop.SHOP, DSL.name("fk_Shop_pk_Recipe"), new TableField[] { Shop.SHOP.AMOUNT_AVAILABLE }, Keys.RECIPE__PK_RECIPE, new TableField[] { Recipe.RECIPE.ID }, true);
}
