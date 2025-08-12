package fpoly.tien25.com.example.myapplication.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import fpoly.tien25.com.example.myapplication.DBHelper;
import fpoly.tien25.com.example.myapplication.model.Invoice;
import fpoly.tien25.com.example.myapplication.model.InvoiceDetail;

public class InvoiceDao {
    private final DBHelper dbHelper;

    public InvoiceDao(Context context) {
        this.dbHelper = new DBHelper(context);
    }

    public long createInvoice(Invoice invoice) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put("customerId", invoice.getCustomerId());
            values.put("userId", invoice.getUserId());
            values.put("date", invoice.getDate());
            values.put("total", invoice.getTotal());
            long invoiceId = db.insert("Invoices", null, values);

            for (InvoiceDetail d : invoice.getDetails()) {
                ContentValues dv = new ContentValues();
                dv.put("invoiceId", invoiceId);
                dv.put("productId", d.getProductId());
                dv.put("quantity", d.getQuantity());
                dv.put("price", d.getPrice());
                db.insert("InvoiceDetails", null, dv);
                // decrease stock
                db.execSQL("UPDATE Products SET stock = stock - ? WHERE id = ?", new Object[]{d.getQuantity(), d.getProductId()});
            }

            db.setTransactionSuccessful();
            return invoiceId;
        } finally {
            db.endTransaction();
        }
    }

    public List<Invoice> getAllInvoices() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT id, customerId, userId, date, total FROM Invoices ORDER BY id DESC", null);
        List<Invoice> list = new ArrayList<>();
        try {
            while (c.moveToNext()) {
                Invoice i = new Invoice();
                i.setId(c.getLong(0));
                i.setCustomerId(c.getLong(1));
                i.setUserId(c.getLong(2));
                i.setDate(c.getString(3));
                i.setTotal(c.getDouble(4));
                list.add(i);
            }
            return list;
        } finally {
            c.close();
        }
    }
}
