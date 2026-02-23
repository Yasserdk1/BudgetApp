package com.example.budgetapp.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.budgetapp.R;
import com.example.budgetapp.database.ExpenseDAO;
import com.example.budgetapp.utils.PreferencesManager;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class StatisticsActivity extends AppCompatActivity {

    private PieChart pieChart;
    private TextView totalTextView;
    private ExpenseDAO expenseDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PreferencesManager.getInstance(this).applyTheme(this);
        setContentView(R.layout.activity_statistics);

        expenseDAO = new ExpenseDAO(this);
        initViews();
        setupChart();
        loadStatistics();
    }

    private void initViews() {
        pieChart = findViewById(R.id.pie_chart);
        totalTextView = findViewById(R.id.total_stat_text);
    }

    private void setupChart() {
        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(false);
        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleColor(Color.WHITE);
        pieChart.setTransparentCircleRadius(61f);
        pieChart.setHoleRadius(58f);
        pieChart.setDrawCenterText(true);
        pieChart.setCenterText("Par catégorie");
        pieChart.setRotationEnabled(true);
    }

    private void loadStatistics() {
        Map<String, Double> data = expenseDAO.getExpensesByCategory();
        List<PieEntry> entries = new ArrayList<>();

        for (Map.Entry<String, Double> entry : data.entrySet()) {
            entries.add(new PieEntry(entry.getValue().floatValue(), entry.getKey()));
        }

        PieDataSet dataSet = new PieDataSet(entries, "");
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        dataSet.setSliceSpace(3f);

        PieData pieData = new PieData(dataSet);
        pieData.setValueTextSize(12f);
        pieData.setValueTextColor(Color.BLACK);

        pieChart.setData(pieData);
        pieChart.invalidate();

        double total = expenseDAO.getTotalExpenses();
        totalTextView.setText(String.format("Total: €%.2f", total));
    }
}