package com.example.budgetapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.budgetapp.models.Expense;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExpenseDAO {

    private DatabaseHelper dbHelper;

    public ExpenseDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    // CREATE
    public long addExpense(Expense expense) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COL_EXPENSE_AMOUNT, expense.getAmount());
        values.put(DatabaseHelper.COL_EXPENSE_CATEGORY, expense.getCategoryId());
        values.put(DatabaseHelper.COL_EXPENSE_DATE, expense.getDate());
        values.put(DatabaseHelper.COL_EXPENSE_DESCRIPTION, expense.getDescription());
        values.put(DatabaseHelper.COL_EXPENSE_PAYMENT, expense.getPaymentMethod());

        long id = db.insert(DatabaseHelper.TABLE_EXPENSES, null, values);
        db.close();
        return id;
    }

    // READ ALL
    public List<Expense> getAllExpenses() {
        List<Expense> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String query = "SELECT e.*, c." + DatabaseHelper.COL_CATEGORY_NAME +
                " FROM " + DatabaseHelper.TABLE_EXPENSES + " e" +
                " LEFT JOIN " + DatabaseHelper.TABLE_CATEGORIES + " c" +
                " ON e." + DatabaseHelper.COL_EXPENSE_CATEGORY +
                " = c." + DatabaseHelper.COL_CATEGORY_ID +
                " ORDER BY e." + DatabaseHelper.COL_EXPENSE_DATE + " DESC";

        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                Expense e = new Expense();
                e.setId(cursor.getInt(0));
                e.setAmount(cursor.getDouble(1));
                e.setCategoryId(cursor.getInt(2));
                e.setDate(cursor.getString(3));
                e.setDescription(cursor.getString(4));
                e.setPaymentMethod(cursor.getString(5));
                e.setCategoryName(cursor.getString(6));
                list.add(e);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return list;
    }

    // READ ONE
    public Expense getExpenseById(int id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Expense expense = null;

        Cursor cursor = db.query(
                DatabaseHelper.TABLE_EXPENSES,
                null,
                DatabaseHelper.COL_EXPENSE_ID + " = ?",
                new String[]{String.valueOf(id)},
                null, null, null
        );

        if (cursor.moveToFirst()) {
            expense = new Expense();
            expense.setId(cursor.getInt(0));
            expense.setAmount(cursor.getDouble(1));
            expense.setCategoryId(cursor.getInt(2));
            expense.setDate(cursor.getString(3));
            expense.setDescription(cursor.getString(4));
            expense.setPaymentMethod(cursor.getString(5));
        }

        cursor.close();
        db.close();
        return expense;
    }

    // UPDATE
    public int updateExpense(Expense expense) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COL_EXPENSE_AMOUNT, expense.getAmount());
        values.put(DatabaseHelper.COL_EXPENSE_CATEGORY, expense.getCategoryId());
        values.put(DatabaseHelper.COL_EXPENSE_DATE, expense.getDate());
        values.put(DatabaseHelper.COL_EXPENSE_DESCRIPTION, expense.getDescription());
        values.put(DatabaseHelper.COL_EXPENSE_PAYMENT, expense.getPaymentMethod());

        int rows = db.update(
                DatabaseHelper.TABLE_EXPENSES,
                values,
                DatabaseHelper.COL_EXPENSE_ID + " = ?",
                new String[]{String.valueOf(expense.getId())}
        );

        db.close();
        return rows;
    }

    // DELETE
    public int deleteExpense(int expenseId) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int rows = db.delete(
                DatabaseHelper.TABLE_EXPENSES,
                DatabaseHelper.COL_EXPENSE_ID + " = ?",
                new String[]{String.valueOf(expenseId)}
        );
        db.close();
        return rows;
    }

    // STATISTIQUES
    public double getTotalExpenses() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT SUM(" + DatabaseHelper.COL_EXPENSE_AMOUNT + ") FROM " +
                        DatabaseHelper.TABLE_EXPENSES, null);

        double total = 0;
        if (cursor.moveToFirst()) total = cursor.getDouble(0);
        cursor.close();
        db.close();
        return total;
    }

    public Map<String, Double> getExpensesByCategory() {
        Map<String, Double> map = new HashMap<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String query = "SELECT c." + DatabaseHelper.COL_CATEGORY_NAME +
                ", SUM(e." + DatabaseHelper.COL_EXPENSE_AMOUNT + ")" +
                " FROM " + DatabaseHelper.TABLE_EXPENSES + " e" +
                " JOIN " + DatabaseHelper.TABLE_CATEGORIES + " c" +
                " ON e." + DatabaseHelper.COL_EXPENSE_CATEGORY +
                " = c." + DatabaseHelper.COL_CATEGORY_ID +
                " GROUP BY c." + DatabaseHelper.COL_CATEGORY_NAME;

        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                map.put(cursor.getString(0), cursor.getDouble(1));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return map;
    }
}