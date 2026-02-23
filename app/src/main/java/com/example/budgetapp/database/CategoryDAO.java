package com.example.budgetapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.budgetapp.models.Category;

import java.util.ArrayList;
import java.util.List;

public class CategoryDAO {

    private DatabaseHelper dbHelper;

    public CategoryDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public List<Category> getAllCategories() {
        List<Category> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.query(
                DatabaseHelper.TABLE_CATEGORIES,
                null, null, null, null, null,
                DatabaseHelper.COL_CATEGORY_NAME
        );

        if (cursor.moveToFirst()) {
            do {
                Category cat = new Category();
                cat.setId(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_CATEGORY_ID)));
                cat.setName(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_CATEGORY_NAME)));
                cat.setColor(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_CATEGORY_COLOR)));
                cat.setIconName(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_CATEGORY_ICON)));
                list.add(cat);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return list;
    }
}