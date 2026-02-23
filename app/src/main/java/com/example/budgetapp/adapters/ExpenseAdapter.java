package com.example.budgetapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.budgetapp.R;
import com.example.budgetapp.models.Expense;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseAdapter.ViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(Expense expense);
        void onItemLongClick(Expense expense);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        View colorIndicator;
        TextView descriptionText;
        TextView categoryText;
        TextView amountText;
        TextView dateText;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.expense_card);
            colorIndicator = itemView.findViewById(R.id.color_indicator);
            descriptionText = itemView.findViewById(R.id.description_text);
            categoryText = itemView.findViewById(R.id.category_text);
            amountText = itemView.findViewById(R.id.amount_text);
            dateText = itemView.findViewById(R.id.date_text);
        }
    }

    private List<Expense> expenseList;
    private OnItemClickListener listener;
    private NumberFormat currencyFormat;

    public ExpenseAdapter(List<Expense> expenseList, OnItemClickListener listener) {
        this.expenseList = expenseList;
        this.listener = listener;
        this.currencyFormat = NumberFormat.getCurrencyInstance(Locale.FRANCE);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_expense, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Expense expense = expenseList.get(position);

        holder.descriptionText.setText(expense.getDescription());
        holder.categoryText.setText(expense.getCategoryName());
        holder.amountText.setText(currencyFormat.format(expense.getAmount()));
        holder.dateText.setText(expense.getDate());

        // Couleur selon catégorie
        int color = getCategoryColor(expense.getCategoryName());
        holder.colorIndicator.setBackgroundColor(color);
        holder.categoryText.setTextColor(color);

        holder.cardView.setOnClickListener(v -> {
            if (listener != null) listener.onItemClick(expense);
        });

        holder.cardView.setOnLongClickListener(v -> {
            if (listener != null) listener.onItemLongClick(expense);
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return expenseList != null ? expenseList.size() : 0;
    }

    public void updateData(List<Expense> newList) {
        this.expenseList = newList;
        notifyDataSetChanged();
    }

    private int getCategoryColor(String categoryName) {
        if (categoryName == null) return 0xFFFF5252;

        switch (categoryName) {
            case "Alimentation": return 0xFFFF5252;
            case "Transport": return 0xFFFF9800;
            case "Loisirs": return 0xFFE040FB;
            case "Logement": return 0xFF4CAF50;
            case "Santé": return 0xFF2196F3;
            case "Shopping": return 0xFFFFEB3B;
            case "Éducation": return 0xFF795548;
            default: return 0xFF9E9E9E;
        }
    }
}