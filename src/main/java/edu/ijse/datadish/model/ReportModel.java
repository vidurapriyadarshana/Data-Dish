package edu.ijse.datadish.model;

import edu.ijse.datadish.db.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class ReportModel {
    public static Map<String, Double> getDailyIncomeData() throws SQLException, ClassNotFoundException {
        String sql = "SELECT Date, SUM(TotalAmount) AS DailyIncome FROM orders GROUP BY Date";
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();

        Map<String, Double> dailyIncomeData = new HashMap<>();
        while (resultSet.next()) {
            dailyIncomeData.put(resultSet.getString("Date"), resultSet.getDouble("DailyIncome"));
        }
        return dailyIncomeData;
    }

    public static Map<String, Double> getMonthlyProfitData() throws SQLException, ClassNotFoundException {
        String sql = "SELECT MONTH(Date) AS Month, SUM(TotalAmount) AS MonthlyProfit FROM orders GROUP BY MONTH(Date)";
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();

        Map<String, Double> profitData = new HashMap<>();
        while (resultSet.next()) {
            profitData.put(getMonthName(resultSet.getInt("Month")), resultSet.getDouble("MonthlyProfit"));
        }
        return profitData;
    }

    public static double getTotalSalaries() throws SQLException, ClassNotFoundException {
        String sql = "SELECT SUM(Amount) AS TotalSalaries FROM salary";
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            return resultSet.getDouble("TotalSalaries");
        }
        return 0;
    }

    private static String getMonthName(int month) {
        switch (month) {
            case 1: return "January";
            case 2: return "February";
            case 3: return "March";
            case 4: return "April";
            case 5: return "May";
            case 6: return "June";
            case 7: return "July";
            case 8: return "August";
            case 9: return "September";
            case 10: return "October";
            case 11: return "November";
            case 12: return "December";
            default: return "Unknown";
        }
    }
}
