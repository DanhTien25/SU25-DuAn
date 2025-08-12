package fpoly.tien25.com.example.myapplication.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import fpoly.tien25.com.example.myapplication.DBHelper;

public class StatisticsDao {

    private final DBHelper dbHelper;

    public StatisticsDao(Context context) {
        this.dbHelper = new DBHelper(context);
    }

    public double getTotalRevenue() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT SUM(total) FROM Invoices", null);
        try {
            if (c.moveToFirst()) {
                return c.getDouble(0);
            }
            return 0;
        } finally {
            c.close();
        }
    }

    public List<TopProduct> getTopProducts(int limit) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String sql = "SELECT p.name, SUM(d.quantity) as totalSold, SUM(d.quantity * d.price) as revenue " +
                "FROM Products p " +
                "JOIN InvoiceDetails d ON p.id = d.productId " +
                "GROUP BY p.id " +
                "ORDER BY totalSold DESC " +
                "LIMIT ?";
        Cursor c = db.rawQuery(sql, new String[]{String.valueOf(limit)});
        List<TopProduct> list = new ArrayList<>();
        try {
            while (c.moveToNext()) {
                TopProduct tp = new TopProduct();
                tp.setName(c.getString(0));
                tp.setQuantitySold(c.getInt(1));
                tp.setRevenue(c.getDouble(2));
                list.add(tp);
            }
            return list;
        } finally {
            c.close();
        }
    }

    public List<TopCustomer> getTopCustomers(int limit) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String sql = "SELECT c.name, COUNT(i.id) as orderCount, SUM(i.total) as totalSpent " +
                "FROM Customers c " +
                "JOIN Invoices i ON c.id = i.customerId " +
                "GROUP BY c.id " +
                "ORDER BY totalSpent DESC " +
                "LIMIT ?";
        Cursor c = db.rawQuery(sql, new String[]{String.valueOf(limit)});
        List<TopCustomer> list = new ArrayList<>();
        try {
            while (c.moveToNext()) {
                TopCustomer tc = new TopCustomer();
                tc.setName(c.getString(0));
                tc.setOrderCount(c.getInt(1));
                tc.setTotalSpent(c.getDouble(2));
                list.add(tc);
            }
            return list;
        } finally {
            c.close();
        }
    }

    public static class TopProduct {
        private String name;
        private int quantitySold;
        private double revenue;

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public int getQuantitySold() { return quantitySold; }
        public void setQuantitySold(int quantitySold) { this.quantitySold = quantitySold; }
        public double getRevenue() { return revenue; }
        public void setRevenue(double revenue) { this.revenue = revenue; }
    }

    public static class TopCustomer {
        private String name;
        private int orderCount;
        private double totalSpent;

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public int getOrderCount() { return orderCount; }
        public void setOrderCount(int orderCount) { this.orderCount = orderCount; }
        public double getTotalSpent() { return totalSpent; }
        public void setTotalSpent(double totalSpent) { this.totalSpent = totalSpent; }
    }
}
