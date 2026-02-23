package com.example.budgetapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "budget.db";
    private static final int DATABASE_VERSION = 1;

    // Table Catégories
    public static final String TABLE_CATEGORIES = "categories";
    public static final String COL_CATEGORY_ID = "id";
    public static final String COL_CATEGORY_NAME = "name";
    public static final String COL_CATEGORY_COLOR = "color";
    public static final String COL_CATEGORY_ICON = "icon";

    // Table Dépenses
    public static final String TABLE_EXPENSES = "expenses";
    public static final String COL_EXPENSE_ID = "id";
    public static final String COL_EXPENSE_AMOUNT = "amount";
    public static final String COL_EXPENSE_CATEGORY = "category_id";
    public static final String COL_EXPENSE_DATE = "date";
    public static final String COL_EXPENSE_DESCRIPTION = "description";
    public static final String COL_EXPENSE_PAYMENT = "payment_method";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createCategories = "CREATE TABLE " + TABLE_CATEGORIES + "("
                + COL_CATEGORY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COL_CATEGORY_NAME + " TEXT NOT NULL,"
                + COL_CATEGORY_COLOR + " TEXT,"
                + COL_CATEGORY_ICON + " TEXT)";
        db.execSQL(createCategories);

        String createExpenses = "CREATE TABLE " + TABLE_EXPENSES + "("
                + COL_EXPENSE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COL_EXPENSE_AMOUNT + " REAL NOT NULL,"
                + COL_EXPENSE_CATEGORY + " INTEGER,"
                + COL_EXPENSE_DATE + " TEXT NOT NULL,"
                + COL_EXPENSE_DESCRIPTION + " TEXT,"
                + COL_EXPENSE_PAYMENT + " TEXT,"
                + "FOREIGN KEY(" + COL_EXPENSE_CATEGORY + ") REFERENCES "
                + TABLE_CATEGORIES + "(" + COL_CATEGORY_ID + "))";
        db.execSQL(createExpenses);

        insertDefaultCategories(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EXPENSES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORIES);
        onCreate(db);
    }

    private void insertDefaultCategories(SQLiteDatabase db) {
        String[] categories = {"Alimentation","Transport","Loisirs","Logement",
                "Santé","Shopping","Éducation","Autres"};
        String[] colors = {"#FF5252","#FF9800","#E040FB","#4CAF50",
                "#2196F3","#FFEB3B","#795548","#9E9E9E"};

        for (int i = 0; i < categories.length; i++) {
            ContentValues values = new ContentValues();
            values.put(COL_CATEGORY_NAME, categories[i]);
            values.put(COL_CATEGORY_COLOR, colors[i]);
            db.insert(TABLE_CATEGORIES, null, values);
        }
    }
}