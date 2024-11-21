package edu.ijse.datadish.controller;

import edu.ijse.datadish.model.ReportModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;

import java.sql.SQLException;
import java.util.Map;

public class ReportController {
    @FXML
    private BarChart<String, Number> dailyIncomeChart;

    @FXML
    private BarChart<String, Number> profitChart;

    @FXML
    private Label lblTotalSalaries;

    @FXML
    private Label lblMostSoldItem;

    public void initialize() {
        loadDailyIncomeChart();
        loadProfitChart();
        loadSummaryData();
    }

    private void loadDailyIncomeChart() {
        try {
            Map<String, Double> dailyIncomeData = ReportModel.getDailyIncomeData();

            ObservableList<XYChart.Data<String, Number>> chartData = FXCollections.observableArrayList();
            for (Map.Entry<String, Double> entry : dailyIncomeData.entrySet()) {
                chartData.add(new XYChart.Data<>(entry.getKey(), entry.getValue()));
            }

            CategoryAxis xAxis = (CategoryAxis) dailyIncomeChart.getXAxis();
            xAxis.setLabel("Date");

            NumberAxis yAxis = (NumberAxis) dailyIncomeChart.getYAxis();
            yAxis.setLabel("Income (LKR)");

            XYChart.Series<String, Number> series = new XYChart.Series<>();
            series.setName("Daily Income");
            series.setData(chartData);

            dailyIncomeChart.getData().clear();
            dailyIncomeChart.getData().add(series);

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void loadProfitChart() {
        try {
            Map<String, Double> profitData = ReportModel.getMonthlyProfitData();

            ObservableList<XYChart.Data<String, Number>> chartData = FXCollections.observableArrayList();
            for (Map.Entry<String, Double> entry : profitData.entrySet()) {
                chartData.add(new XYChart.Data<>(entry.getKey(), entry.getValue()));
            }

            CategoryAxis xAxis = (CategoryAxis) profitChart.getXAxis();
            xAxis.setLabel("Month");

            NumberAxis yAxis = (NumberAxis) profitChart.getYAxis();
            yAxis.setLabel("Profit (LKR)");

            XYChart.Series<String, Number> series = new XYChart.Series<>();
            series.setName("Monthly Profit");
            series.setData(chartData);

            profitChart.getData().clear();
            profitChart.getData().add(series);

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void loadSummaryData() {
        try {
            double totalSalaries = ReportModel.getTotalSalaries();
            lblTotalSalaries.setText("Total Salaries: LKR " + totalSalaries);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
