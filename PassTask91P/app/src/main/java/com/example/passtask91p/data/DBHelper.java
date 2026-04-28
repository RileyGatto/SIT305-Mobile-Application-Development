package com.example.passtask91p.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    //used for testing purposes to trigger new version
    public DBHelper(Context context) {
        super(context, "LF.db", null, 7);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS items");
        onCreate(db);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE items(" + "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "type TEXT," +
                "category TEXT," +
                "name TEXT,"+
                "phone TEXT,"+
                "description TEXT," +
                "location TEXT,"+
                "latitude REAL," +
                "longitude REAL," +
                "image TEXT," +
                "date TEXT)");
    }

    public boolean insert(String type, String category, String name, String phone, String desc, String location, double latitude, double longitude, String image, String date) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("type", type);
        cv.put("category", category);
        cv.put("name", name);
        cv.put("phone", phone);
        cv.put("description", desc);
        cv.put("location", location);
        cv.put("latitude", latitude);
        cv.put("longitude", longitude);
        cv.put("image", image);
        cv.put("date", date);

        return db.insert("items", null, cv) != -1;
    }

    public Cursor getItems() {

        return getReadableDatabase().rawQuery("SELECT * FROM items ORDER BY id DESC", null);
    }

    public void delete(int id) {
        getWritableDatabase().delete("items", "id=?", new String[]{String.valueOf(id)});
    }

    public Cursor getItemById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM items WHERE id=?", new String[]{String.valueOf(id)});
    }





}
