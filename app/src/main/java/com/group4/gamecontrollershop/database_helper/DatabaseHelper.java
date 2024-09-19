package com.group4.gamecontrollershop.database_helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "mydatabase.db";
    private static final int DATABASE_VERSION = 1;

    private static final String CREATE_TABLE_USER = "CREATE TABLE User (" +
            "userId INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "username TEXT, " +
            "password TEXT, " +
            "avatarUrl TEXT, " +
            "address TEXT, " +
            "phone TEXT);";

    private static final String CREATE_TABLE_PRODUCT = "CREATE TABLE Product (" +
            "productId INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "name TEXT, " +
            "description TEXT, " +
            "imgUrl TEXT, " +
            "oldPrice REAL, " +
            "quantity INTEGER, " +
            "brand TEXT, " +
            "releaseDate TEXT);";

    private static final String CREATE_TABLE_ORDER = "CREATE TABLE `Order` (" +
            "orderId INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "userId INTEGER, " +
            "totalAmount REAL, " +
            "orderDate TEXT, " +
            "status TEXT, " +
            "FOREIGN KEY(userId) REFERENCES User(userId));";

    private static final String CREATE_TABLE_ORDER_ITEM = "CREATE TABLE OrderItem (" +
            "orderItemId INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "orderId INTEGER, " +
            "productId INTEGER, " +
            "quantity INTEGER, " +
            "price REAL, " +
            "FOREIGN KEY(orderId) REFERENCES `Order`(orderId), " +
            "FOREIGN KEY(productId) REFERENCES Product(productId));";

    private static final String CREATE_TABLE_FAVORITE = "CREATE TABLE Favorite (" +
            "favoriteId INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "userId INTEGER, " +
            "productId INTEGER, " +
            "FOREIGN KEY(userId) REFERENCES User(userId), " +
            "FOREIGN KEY(productId) REFERENCES Product(productId));";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USER);
        db.execSQL(CREATE_TABLE_PRODUCT);
        db.execSQL(CREATE_TABLE_ORDER);
        db.execSQL(CREATE_TABLE_ORDER_ITEM);
        db.execSQL(CREATE_TABLE_FAVORITE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Favorite");
        db.execSQL("DROP TABLE IF EXISTS OrderItem");
        db.execSQL("DROP TABLE IF EXISTS `Order`");
        db.execSQL("DROP TABLE IF EXISTS Product");
        db.execSQL("DROP TABLE IF EXISTS User");
        onCreate(db);
    }
}
