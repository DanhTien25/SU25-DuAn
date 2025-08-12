package fpoly.tien25.com.example.myapplication.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import fpoly.tien25.com.example.myapplication.DBHelper;
import fpoly.tien25.com.example.myapplication.model.Category;

public class CategoryDao {
    private final DBHelper dbHelper;

    public CategoryDao(Context context) {
        this.dbHelper = new DBHelper(context);
    }

    public long insert(Category c) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues v = new ContentValues();
        v.put("name", c.getName());
        return db.insert("Categories", null, v);
    }

    public int update(Category c) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues v = new ContentValues();
        v.put("name", c.getName());
        return db.update("Categories", v, "id=?", new String[]{String.valueOf(c.getId())});
    }

    public int delete(long id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        return db.delete("Categories", "id=?", new String[]{String.valueOf(id)});
    }

    public List<Category> getAll() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT id, name FROM Categories ORDER BY name", null);
        List<Category> list = new ArrayList<>();
        try {
            while (c.moveToNext()) {
                Category ct = new Category();
                ct.setId(c.getLong(0));
                ct.setName(c.getString(1));
                list.add(ct);
            }
            return list;
        } finally { c.close(); }
    }
}
