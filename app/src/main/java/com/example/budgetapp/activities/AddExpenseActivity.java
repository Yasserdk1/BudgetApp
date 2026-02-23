package com.example.budgetapp.activities;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.budgetapp.R;
import com.example.budgetapp.database.CategoryDAO;
import com.example.budgetapp.database.ExpenseDAO;
import com.example.budgetapp.models.Category;
import com.example.budgetapp.models.Expense;
import com.example.budgetapp.utils.PreferencesManager;

import java.util.Calendar;
import java.util.List;

public class AddExpenseActivity extends AppCompatActivity {

    private EditText amountEditText;
    private EditText descriptionEditText;
    private Spinner categorySpinner;
    private Spinner paymentSpinner;
    private DatePicker datePicker;
    private Button saveButton;
    private Button cancelButton;

    private List<Category> categoryList;
    private ExpenseDAO expenseDAO;
    private CategoryDAO categoryDAO;

    private boolean isEditing = false;
    private int editingExpenseId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        PreferencesManager.getInstance(this).applyTheme(this);
        setContentView(R.layout.activity_add_expense);

        expenseDAO = new ExpenseDAO(this);
        categoryDAO = new CategoryDAO(this);

        initViews();
        loadCategories();
        setupSpinners();
        checkEditMode();
        setupListeners();
    }

    private void initViews() {
        amountEditText = findViewById(R.id.amount_edittext);
        descriptionEditText = findViewById(R.id.description_edittext);
        categorySpinner = findViewById(R.id.category_spinner);
        paymentSpinner = findViewById(R.id.payment_spinner);
        datePicker = findViewById(R.id.date_picker);
        saveButton = findViewById(R.id.save_button);
        cancelButton = findViewById(R.id.cancel_button);
    }

    private void loadCategories() {
        categoryList = categoryDAO.getAllCategories();
    }

    private void setupSpinners() {
        ArrayAdapter<Category> categoryAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, categoryList);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(categoryAdapter);

        ArrayAdapter<CharSequence> paymentAdapter = ArrayAdapter.createFromResource(
                this, R.array.payment_methods, android.R.layout.simple_spinner_item);
        paymentAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        paymentSpinner.setAdapter(paymentAdapter);
    }

    private void checkEditMode() {
        Bundle extras = getIntent().getExtras();
        if (extras != null && extras.getBoolean("is_editing", false)) {
            isEditing = true;
            editingExpenseId = extras.getInt("expense_id");

            amountEditText.setText(String.valueOf(extras.getDouble("expense_amount")));
            descriptionEditText.setText(extras.getString("expense_description"));

            // Sélectionner catégorie
            int catId = extras.getInt("expense_category");
            for (int i = 0; i < categoryList.size(); i++) {
                if (categoryList.get(i).getId() == catId) {
                    categorySpinner.setSelection(i);
                    break;
                }
            }

            // Sélectionner paiement
            String payment = extras.getString("expense_payment");
            ArrayAdapter adapter = (ArrayAdapter) paymentSpinner.getAdapter();
            for (int i = 0; i < adapter.getCount(); i++) {
                if (adapter.getItem(i).equals(payment)) {
                    paymentSpinner.setSelection(i);
                    break;
                }
            }

            // Date
            String date = extras.getString("expense_date");
            String[] parts = date.split("/");
            if (parts.length == 3) {
                datePicker.updateDate(
                        Integer.parseInt(parts[2]),
                        Integer.parseInt(parts[1]) - 1,
                        Integer.parseInt(parts[0])
                );
            }
        }
    }

    private void setupListeners() {
        saveButton.setOnClickListener(v -> saveExpense());
        cancelButton.setOnClickListener(v -> finish());
    }

    private void saveExpense() {
        String amountStr = amountEditText.getText().toString().trim();
        if (amountStr.isEmpty()) {
            amountEditText.setError("Montant requis");
            return;
        }

        double amount;
        try {
            amount = Double.parseDouble(amountStr);
            if (amount <= 0) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            amountEditText.setError("Montant invalide");
            return;
        }

        Category category = (Category) categorySpinner.getSelectedItem();
        String payment = paymentSpinner.getSelectedItem().toString();
        String description = descriptionEditText.getText().toString().trim();

        String date = String.format("%02d/%02d/%d",
                datePicker.getDayOfMonth(),
                datePicker.getMonth() + 1,
                datePicker.getYear()
        );

        Expense expense = new Expense();
        expense.setAmount(amount);
        expense.setCategoryId(category.getId());
        expense.setDate(date);
        expense.setDescription(description);
        expense.setPaymentMethod(payment);

        boolean success;
        if (isEditing) {
            expense.setId(editingExpenseId);
            success = expenseDAO.updateExpense(expense) > 0;
        } else {
            success = expenseDAO.addExpense(expense) != -1;
        }

        if (success) {
            setResult(RESULT_OK);
            finish();
        } else {
            Toast.makeText(this, "Erreur", Toast.LENGTH_SHORT).show();
        }
    }
}