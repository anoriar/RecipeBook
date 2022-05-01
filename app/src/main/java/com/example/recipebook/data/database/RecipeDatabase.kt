package com.example.recipebook.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.recipebook.data.database.dao.CategoryDao
import com.example.recipebook.data.database.dao.RecipeDao
import com.example.recipebook.data.database.entity.CategoryDbEntity
import com.example.recipebook.data.database.entity.RecipeDbEntity

@Database(entities = [RecipeDbEntity::class, CategoryDbEntity::class], version = 2)
abstract class RecipeDatabase: RoomDatabase () {

    abstract fun getRecipeDao():RecipeDao

    abstract fun getCategoryDao(): CategoryDao

    companion object {
        const val DB_NAME = "recipes.db"
    }
}

// Migration path definition from version 2 to version 3.
val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("DROP INDEX IF EXISTS index_recipes_categoryId")
        database.execSQL("ALTER TABLE recipes RENAME TO recipes_old;")
        database.execSQL("CREATE TABLE recipes (\n" +
                "id INTEGER NOT NULL,\n" +
                "name TEXT NOT NULL,\n" +
                "categoryId INTEGER NOT NULL,\n" +
                "text TEXT NOT NULL,\n" +
                "ingredients TEXT NOT NULL,\n" +
                "portions INTEGER NOT NULL,\n" +
                "image TEXT,\n" +
                "isFavourite INTEGER NOT NULL,\n" +
                "FOREIGN KEY('categoryId') REFERENCES 'categories'('id') ON UPDATE NO ACTION ON DELETE SET NULL,\n" + "PRIMARY KEY('id' AUTOINCREMENT)\n" + ");"
        )


        database.execSQL("CREATE INDEX IF NOT EXISTS index_recipes_categoryId ON recipes (categoryId)")
        database.execSQL("INSERT INTO recipes (categoryId, id, image, ingredients, isFavourite, name, portions, text) SELECT categoryId, id, image, ingredients, isFavourite, name, portions, text FROM recipes_old;")
        database.execSQL("DROP TABLE recipes_old;")
    }
}