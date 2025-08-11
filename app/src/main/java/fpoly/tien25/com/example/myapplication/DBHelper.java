package fpoly.tien25.com.example.myapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper  extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "mart.db";
    public static final int DATABASE_VERSION = 1;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE Categories (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT NOT NULL UNIQUE)");

        db.execSQL("CREATE TABLE Users (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "username TEXT UNIQUE NOT NULL, " +
                "password TEXT NOT NULL, " +
                "fullName TEXT)");

        db.execSQL("CREATE TABLE Customers (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT NOT NULL, " +
                "phone TEXT, " +
                "address TEXT)");

        db.execSQL("CREATE TABLE Products (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT NOT NULL, " +
                "price REAL NOT NULL, " +
                "stock INTEGER NOT NULL DEFAULT 0, " +
                "categoryId INTEGER, " +
                "FOREIGN KEY(categoryId) REFERENCES Categories(id))");

        db.execSQL("CREATE TABLE Invoices (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "customerId INTEGER, " +
                "userId INTEGER, " +
                "date TEXT NOT NULL, " +
                "total REAL NOT NULL, " +
                "FOREIGN KEY(customerId) REFERENCES Customers(id)," +
                "FOREIGN KEY(userId) REFERENCES Users(id))");

        db.execSQL("CREATE TABLE InvoiceDetails (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "invoiceId INTEGER NOT NULL, " +
                "productId INTEGER NOT NULL, " +
                "quantity INTEGER NOT NULL, " +
                "price REAL NOT NULL, " +
                "FOREIGN KEY(invoiceId) REFERENCES Invoices(id)," +
                "FOREIGN KEY(productId) REFERENCES Products(id))");

        // Seed default admin user
        db.execSQL("INSERT INTO Users(username, password, fullName) VALUES('admin','123','Quản trị')");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS InvoiceDetails");
        db.execSQL("DROP TABLE IF EXISTS Invoices");
        db.execSQL("DROP TABLE IF EXISTS Products");
        db.execSQL("DROP TABLE IF EXISTS Customers");
        db.execSQL("DROP TABLE IF EXISTS Users");
        onCreate(db);

    }
}
