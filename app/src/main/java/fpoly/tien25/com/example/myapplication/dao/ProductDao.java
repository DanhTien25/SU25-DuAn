package fpoly.tien25.com.example.myapplication.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import fpoly.tien25.com.example.myapplication.DBHelper;
import fpoly.tien25.com.example.myapplication.model.Product;

public class ProductDao {
    private final DBHelper dbHelper;

    public ProductDao(Context context) {
        this.dbHelper = new DBHelper(context);
    }

    public long insert(Product p) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", p.getName());
        values.put("price", p.getPrice());
        values.put("stock", p.getStock());
        return db.insert("Products", null, values);
    }

    public int update(Product p) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", p.getName());
        values.put("price", p.getPrice());
        values.put("stock", p.getStock());
        return db.update("Products", values, "id=?", new String[]{String.valueOf(p.getId())});
    }

    public int delete(long id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        return db.delete("Products", "id=?", new String[]{String.valueOf(id)});
    }

    public List<Product> getAll() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT id, name, price, stock FROM Products ORDER BY id DESC", null);
        List<Product> list = new ArrayList<>();
        try {
            while (c.moveToNext()) {
                Product p = new Product();
                p.setId(c.getLong(0));
                p.setName(c.getString(1));
                p.setPrice(c.getDouble(2));
                p.setStock(c.getInt(3));
                list.add(p);
            }
            return list;
        } finally {
            c.close();
        }
    }
}
