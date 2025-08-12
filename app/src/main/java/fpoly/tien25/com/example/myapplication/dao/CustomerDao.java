package fpoly.tien25.com.example.myapplication.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import fpoly.tien25.com.example.myapplication.DBHelper;
import fpoly.tien25.com.example.myapplication.model.Customer;

public class CustomerDao {
    private final DBHelper dbHelper;

    public CustomerDao(Context context) {
        this.dbHelper = new DBHelper(context);
    }

    public long insert(Customer ctm) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", ctm.getName());
        values.put("phone", ctm.getPhone());
        values.put("address", ctm.getAddress());
        return db.insert("Customers", null, values);
    }

    public int update(Customer ctm) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", ctm.getName());
        values.put("phone", ctm.getPhone());
        values.put("address", ctm.getAddress());
        return db.update("Customers", values, "id=?", new String[]{String.valueOf(ctm.getId())});
    }

    public int delete(long id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        return db.delete("Customers", "id=?", new String[]{String.valueOf(id)});
    }

    public List<Customer> getAll() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT id, name, phone, address FROM Customers ORDER BY id DESC", null);
        List<Customer> list = new ArrayList<>();
        try {
            while (c.moveToNext()) {
                Customer cs = new Customer();
                cs.setId(c.getLong(0));
                cs.setName(c.getString(1));
                cs.setPhone(c.getString(2));
                cs.setAddress(c.getString(3));
                list.add(cs);
            }
            return list;
        } finally {
            c.close();
        }
    }
}
