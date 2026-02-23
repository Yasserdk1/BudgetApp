package com.example.budgetapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.budgetapp.R;
import com.example.budgetapp.adapters.ExpenseAdapter;  // ← IMPORT AJOUTÉ !
import com.example.budgetapp.database.ExpenseDAO;
import com.example.budgetapp.models.Expense;
import com.example.budgetapp.utils.PreferencesManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_ADD_EXPENSE = 100;
    private static final int REQUEST_EDIT_EXPENSE = 101;

    private TextView totalTextView;
    private TextView budgetTextView;
    private TextView remainingTextView;
    private RecyclerView recyclerView;
    private FloatingActionButton fabAdd;

    private ExpenseAdapter adapter;  // ← Maintenant reconnu !
    private List<Expense> expenseList;
    private ExpenseDAO expenseDAO;
    private PreferencesManager preferencesManager;
    private NumberFormat currencyFormat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        preferencesManager = PreferencesManager.getInstance(this);
        preferencesManager.applyTheme(this);

        setContentView(R.layout.activity_main);

        expenseDAO = new ExpenseDAO(this);
        currencyFormat = NumberFormat.getCurrencyInstance(Locale.FRANCE);

        initViews();
        setupRecyclerView();
        setupListeners();
        loadData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshData();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            refreshData();
            String message = requestCode == REQUEST_ADD_EXPENSE ?
                    "Dépense ajoutée" : "Dépense modifiée";
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        }
    }

    private void initViews() {
        totalTextView = findViewById(R.id.total_text);
        budgetTextView = findViewById(R.id.budget_text);
        remainingTextView = findViewById(R.id.remaining_text);
        recyclerView = findViewById(R.id.recycler_view);
        fabAdd = findViewById(R.id.fab_add);
    }

    private void setupRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ExpenseAdapter(expenseList, new ExpenseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Expense expense) {
                editExpense(expense);
            }

            @Override
            public void onItemLongClick(Expense expense) {
                showDeleteDialog(expense);
            }
        });
        recyclerView.setAdapter(adapter);
    }

    private void setupListeners() {
        fabAdd.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddExpenseActivity.class);
            startActivityForResult(intent, REQUEST_ADD_EXPENSE);
        });
    }

    private void loadData() {
        expenseList = expenseDAO.getAllExpenses();
        updateDashboard();
        if (adapter != null) adapter.updateData(expenseList);
    }

    private void refreshData() {
        expenseList = expenseDAO.getAllExpenses();
        updateDashboard();
        if (adapter != null) adapter.updateData(expenseList);
    }

    private void updateDashboard() {
        double total = 0;
        for (Expense e : expenseList) total += e.getAmount();

        float budget = preferencesManager.getMonthlyBudget();
        double remaining = budget - total;

        totalTextView.setText(currencyFormat.format(total));
        budgetTextView.setText(currencyFormat.format(budget));
        remainingTextView.setText(currencyFormat.format(remaining));

        if (remaining < 0) {
            remainingTextView.setTextColor(getColor(R.color.error));
        } else {
            remainingTextView.setTextColor(getColor(R.color.success));
        }
    }

    private void editExpense(Expense expense) {
        Intent intent = new Intent(MainActivity.this, AddExpenseActivity.class);
        intent.putExtra("expense_id", expense.getId());
        intent.putExtra("expense_amount", expense.getAmount());
        intent.putExtra("expense_category", expense.getCategoryId());
        intent.putExtra("expense_date", expense.getDate());
        intent.putExtra("expense_description", expense.getDescription());
        intent.putExtra("expense_payment", expense.getPaymentMethod());
        intent.putExtra("is_editing", true);
        startActivityForResult(intent, REQUEST_EDIT_EXPENSE);
    }

    private void showDeleteDialog(Expense expense) {
        new AlertDialog.Builder(this)
                .setTitle("Confirmation")
                .setMessage("Supprimer cette dépense ?")
                .setPositiveButton("Oui", (dialog, which) -> {
                    expenseDAO.deleteExpense(expense.getId());
                    refreshData();
                    Toast.makeText(this, "Dépense supprimée", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Non", null)
                .show();
    }
}