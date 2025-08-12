package fpoly.tien25.com.example.myapplication.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import fpoly.tien25.com.example.myapplication.DBHelper;
import fpoly.tien25.com.example.myapplication.model.User;

public class UserDao {
    private final DBHelper dbHelper;

    public UserDao(Context context) {
        this.dbHelper = new DBHelper(context);
    }

    public long insert(User user) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("username", user.getUsername());
        values.put("password", user.getPassword());
        values.put("fullName", user.getFullName());
        return db.insert("Users", null, values);
    }

    public boolean exists(String username) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT 1 FROM Users WHERE username=? LIMIT 1", new String[]{username});
        try {
            return c.moveToFirst();
        } finally {
            c.close();
        }
    }

    public User login(String username, String password) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT id, username, password, fullName FROM Users WHERE username=? AND password=?",
                new String[]{username, password});
        try {
            if (c.moveToFirst()) {
                User u = new User();
                u.setId(c.getLong(0));
                u.setUsername(c.getString(1));
                u.setPassword(c.getString(2));
                u.setFullName(c.getString(3));
                return u;
            }
            return null;
        } finally {
            c.close();
        }
    }
}
