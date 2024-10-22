package com.group4.gamecontrollershop.database_helper;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.group4.gamecontrollershop.model.Brand;
import com.group4.gamecontrollershop.model.CartItem;
import com.group4.gamecontrollershop.model.Favorite;
import com.group4.gamecontrollershop.model.Order;
import com.group4.gamecontrollershop.model.Product;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "game_controller.db";
    private static final int DATABASE_VERSION = 3;

    //product
    private static final String TABLE_PRODUCT = "Product";
    private static final String TABLE_FAVORITE = "Favorite";
    private static final String TABLE_CART = "Cart";
    private static final String PRODUCT_COLUMN_ID = "id";
    private static final String PRODUCT_COLUMN_NAME = "name";
    private static final String PRODUCT_COLUMN_DESCRIPTION = "description";
    private static final String PRODUCT_COLUMN_IMG_URL = "imgUrl";
    private static final String PRODUCT_COLUMN_DETAIL_IMG_URL_FIRST = "detailImgUrlFirst";
    private static final String PRODUCT_COLUMN_DETAIL_IMG_URL_SECOND = "detailImgUrlSecond";
    private static final String PRODUCT_COLUMN_DETAIL_IMG_URL_THIRD = "detailImgUrlThird";
    private static final String PRODUCT_COLUMN_OLD_PRICE = "oldPrice";
    private static final String PRODUCT_COLUMN_NEW_PRICE = "newPrice";
    private static final String PRODUCT_COLUMN_QUANTITY = "quantity";
    private static final String PRODUCT_COLUMN_RELEASE_DATE = "releaseDate";
    private static final String PRODUCT_COLUMN_STATUS = "status";
    private static final String PRODUCT_COLUMN_BRAND_ID = "brandId";

    //brand
    private static final String TABLE_BRAND = "Brand";
    private static final String BRAND_COLUMN_ID = "id";
    private static final String BRAND_COLUMN_NAME = "name";


    private static final String CREATE_TABLE_USER = "CREATE TABLE User (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "fullname TEXT, " +
            "username TEXT, " +
            "password TEXT, " +
            "avatarUrl TEXT, " +
            "address TEXT, " +
            "googleId INTEGER, " +
            "status TEXT, " +
            "phone TEXT);";

    private static final String CREATE_TABLE_BRAND = "CREATE TABLE Brand (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "name TEXT);";

    private static final String CREATE_TABLE_PRODUCT = "CREATE TABLE Product (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "name TEXT, " +
            "description TEXT, " +
            "imgUrl TEXT, " +
            "detailImgUrlFirst TEXT, " +
            "detailImgUrlSecond TEXT, " +
            "detailImgUrlThird TEXT, " +
            "oldPrice REAL, " +
            "newPrice REAL, " +
            "quantity INTEGER, " +
            "brandId INTEGER, " +
            "releaseDate TEXT, " +
            "status TEXT, " +
            "FOREIGN KEY(brandId) REFERENCES Brand(id));";

    private static final String CREATE_TABLE_ORDER = "CREATE TABLE `Order` (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "userId INTEGER, " +
            "totalAmount REAL, " +
            "orderDate TEXT, " +
            "status TEXT, " +
            "FOREIGN KEY(userId) REFERENCES User(id));";

    private static final String CREATE_TABLE_ORDER_ITEM = "CREATE TABLE OrderItem (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "orderId INTEGER, " +
            "productId INTEGER, " +
            "quantity INTEGER, " +
            "price REAL, " +
            "FOREIGN KEY(orderId) REFERENCES `Order`(id), " +
            "FOREIGN KEY(productId) REFERENCES Product(id));";

    private static final String CREATE_TABLE_FAVORITE = "CREATE TABLE Favorite (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "userId INTEGER, " +
            "productId INTEGER, " +
            "FOREIGN KEY(userId) REFERENCES User(id), " +
            "FOREIGN KEY(productId) REFERENCES Product(id));";

    private static final String CREATE_TABLE_CART = "CREATE TABLE Cart (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "userId INTEGER, " +
            "productId INTEGER, " +
            "quantity INTEGER, " +
            "FOREIGN KEY(userId) REFERENCES User(id), " +
            "FOREIGN KEY(productId) REFERENCES Product(id));";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USER);
        db.execSQL(CREATE_TABLE_BRAND);
        db.execSQL(CREATE_TABLE_PRODUCT);
        db.execSQL(CREATE_TABLE_ORDER);
        db.execSQL(CREATE_TABLE_ORDER_ITEM);
        db.execSQL(CREATE_TABLE_FAVORITE);
        db.execSQL(CREATE_TABLE_CART);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Xóa các bảng cũ nếu tồn tại
        db.execSQL("DROP TABLE IF EXISTS Favorite");
        db.execSQL("DROP TABLE IF EXISTS OrderItem");
        db.execSQL("DROP TABLE IF EXISTS `Order`");
        db.execSQL("DROP TABLE IF EXISTS Product");
        db.execSQL("DROP TABLE IF EXISTS Brand");
        db.execSQL("DROP TABLE IF EXISTS User");
        db.execSQL("DROP TABLE IF EXISTS Cart");

        // Tạo lại các bảng mới
        onCreate(db);
    }

    public void insertProduct(Product product) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(PRODUCT_COLUMN_NAME, product.getName());
        values.put(PRODUCT_COLUMN_DESCRIPTION, product.getDescription());
        values.put(PRODUCT_COLUMN_IMG_URL, product.getImgUrl());
        values.put(PRODUCT_COLUMN_DETAIL_IMG_URL_FIRST, product.getDetailImgUrlFirst());
        values.put(PRODUCT_COLUMN_DETAIL_IMG_URL_SECOND, product.getDetailImgUrlSecond());
        values.put(PRODUCT_COLUMN_DETAIL_IMG_URL_THIRD, product.getDetailImgUrlThird());
        values.put(PRODUCT_COLUMN_OLD_PRICE, product.getOldPrice());
        values.put(PRODUCT_COLUMN_NEW_PRICE, product.getNewPrice());
        values.put(PRODUCT_COLUMN_QUANTITY, product.getQuantity());
        values.put(PRODUCT_COLUMN_BRAND_ID, product.getBrandId()); // Sử dụng brandId
        values.put(PRODUCT_COLUMN_STATUS, product.getStatus());

        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String releaseDateString = dateFormat.format(product.getReleaseDate());
        values.put(PRODUCT_COLUMN_RELEASE_DATE, releaseDateString);

        db.insert(TABLE_PRODUCT, null, values);
        db.close();
    }

    public Product getProduct(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_PRODUCT, new String[]{
                        PRODUCT_COLUMN_ID, PRODUCT_COLUMN_NAME, PRODUCT_COLUMN_DESCRIPTION, PRODUCT_COLUMN_IMG_URL,
                        PRODUCT_COLUMN_DETAIL_IMG_URL_FIRST, PRODUCT_COLUMN_DETAIL_IMG_URL_SECOND, PRODUCT_COLUMN_DETAIL_IMG_URL_THIRD,
                        PRODUCT_COLUMN_OLD_PRICE, PRODUCT_COLUMN_NEW_PRICE, PRODUCT_COLUMN_QUANTITY,
                        PRODUCT_COLUMN_BRAND_ID, PRODUCT_COLUMN_RELEASE_DATE, PRODUCT_COLUMN_STATUS},
                PRODUCT_COLUMN_ID + "=?", new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date releaseDate = null;
            try {
                releaseDate = dateFormat.parse(cursor.getString(11));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            Product product = new Product(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3),
                    cursor.getString(4), cursor.getString(5), cursor.getString(6),
                    cursor.getDouble(7), cursor.getDouble(8), cursor.getInt(9), releaseDate, cursor.getString(12), cursor.getInt(10));
            cursor.close();
            return product;
        } else {
            return null;
        }
    }

    public List<Product> getActiveProducts() {
        List<Product> productList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_PRODUCT, new String[]{
                        PRODUCT_COLUMN_ID, PRODUCT_COLUMN_NAME, PRODUCT_COLUMN_DESCRIPTION, PRODUCT_COLUMN_IMG_URL,
                        PRODUCT_COLUMN_DETAIL_IMG_URL_FIRST, PRODUCT_COLUMN_DETAIL_IMG_URL_SECOND, PRODUCT_COLUMN_DETAIL_IMG_URL_THIRD,
                        PRODUCT_COLUMN_OLD_PRICE, PRODUCT_COLUMN_NEW_PRICE, PRODUCT_COLUMN_QUANTITY,
                        PRODUCT_COLUMN_BRAND_ID, PRODUCT_COLUMN_RELEASE_DATE, PRODUCT_COLUMN_STATUS},
                PRODUCT_COLUMN_STATUS + "=?", new String[]{"ACTIVE"}, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            do {
                Date releaseDate = null;
                try {
                    releaseDate = dateFormat.parse(cursor.getString(11));
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                Product product = new Product(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3),
                        cursor.getString(4), cursor.getString(5), cursor.getString(6),
                        cursor.getDouble(7), cursor.getDouble(8), cursor.getInt(9), releaseDate, cursor.getString(12), cursor.getInt(10));
                productList.add(product);
            } while (cursor.moveToNext());

            cursor.close();
        }

        return productList;
    }

    public List<Product> getActiveProductsBySort(String sortOrder) {
        List<Product> productList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor;

        if (sortOrder.equals("ALL")) {
            cursor = db.query(TABLE_PRODUCT, new String[]{
                            PRODUCT_COLUMN_ID, PRODUCT_COLUMN_NAME, PRODUCT_COLUMN_DESCRIPTION, PRODUCT_COLUMN_IMG_URL,
                            PRODUCT_COLUMN_DETAIL_IMG_URL_FIRST, PRODUCT_COLUMN_DETAIL_IMG_URL_SECOND, PRODUCT_COLUMN_DETAIL_IMG_URL_THIRD,
                            PRODUCT_COLUMN_OLD_PRICE, PRODUCT_COLUMN_NEW_PRICE, PRODUCT_COLUMN_QUANTITY, PRODUCT_COLUMN_BRAND_ID,
                            PRODUCT_COLUMN_RELEASE_DATE, PRODUCT_COLUMN_STATUS},
                    PRODUCT_COLUMN_STATUS + "=?", new String[]{"ACTIVE"}, null, null, null, null);
        } else {
            String orderBy = PRODUCT_COLUMN_NEW_PRICE + " " + sortOrder;

            cursor = db.query(TABLE_PRODUCT, new String[]{
                            PRODUCT_COLUMN_ID, PRODUCT_COLUMN_NAME, PRODUCT_COLUMN_DESCRIPTION, PRODUCT_COLUMN_IMG_URL,
                            PRODUCT_COLUMN_DETAIL_IMG_URL_FIRST, PRODUCT_COLUMN_DETAIL_IMG_URL_SECOND, PRODUCT_COLUMN_DETAIL_IMG_URL_THIRD,
                            PRODUCT_COLUMN_OLD_PRICE, PRODUCT_COLUMN_NEW_PRICE, PRODUCT_COLUMN_QUANTITY, PRODUCT_COLUMN_BRAND_ID,
                            PRODUCT_COLUMN_RELEASE_DATE, PRODUCT_COLUMN_STATUS},
                    PRODUCT_COLUMN_STATUS + "=?", new String[]{"ACTIVE"}, null, null, orderBy, null);
        }

        if (cursor != null && cursor.moveToFirst()) {
            @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            do {
                Date releaseDate = null;
                try {
                    releaseDate = dateFormat.parse(cursor.getString(11));
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                Product product = new Product(
                        cursor.getInt(0), // id
                        cursor.getString(1), // name
                        cursor.getString(2), // description
                        cursor.getString(3), // imgUrl
                        cursor.getString(4), // detailImgUrlFirst
                        cursor.getString(5), // detailImgUrlSecond
                        cursor.getString(6), // detailImgUrlThird
                        cursor.getDouble(7), // oldPrice
                        cursor.getDouble(8), // newPrice
                        cursor.getInt(9), // quantity
                        releaseDate, // releaseDate
                        cursor.getString(12), // status
                        cursor.getInt(10) // brandId
                );
                productList.add(product);
            } while (cursor.moveToNext());

            cursor.close();
        }

        return productList;
    }

    public List<Product> searchProductsByName(String productName) {
        List<Product> productList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_PRODUCT, new String[]{
                        PRODUCT_COLUMN_ID, PRODUCT_COLUMN_NAME, PRODUCT_COLUMN_DESCRIPTION, PRODUCT_COLUMN_IMG_URL,
                        PRODUCT_COLUMN_DETAIL_IMG_URL_FIRST, PRODUCT_COLUMN_DETAIL_IMG_URL_SECOND, PRODUCT_COLUMN_DETAIL_IMG_URL_THIRD,
                        PRODUCT_COLUMN_OLD_PRICE, PRODUCT_COLUMN_NEW_PRICE, PRODUCT_COLUMN_QUANTITY, PRODUCT_COLUMN_BRAND_ID,
                        PRODUCT_COLUMN_RELEASE_DATE, PRODUCT_COLUMN_STATUS},
                PRODUCT_COLUMN_NAME + " LIKE ?", new String[]{"%" + productName + "%"}, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            do {
                Date releaseDate = null;
                try {
                    releaseDate = dateFormat.parse(cursor.getString(11));
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                Product product = new Product(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3),
                        cursor.getString(4), cursor.getString(5), cursor.getString(6),
                        cursor.getDouble(7), cursor.getDouble(8), cursor.getInt(9), releaseDate, cursor.getString(12), cursor.getInt(10));
                productList.add(product);
            } while (cursor.moveToNext());

            cursor.close();
        }

        return productList;
    }

    public void deleteAllProducts() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PRODUCT, null, null);
        db.close();
    }

    public void insertBrand(Brand brand) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(BRAND_COLUMN_NAME, brand.getName());
        db.insert(TABLE_BRAND, null, values);
        db.close();
    }

    // Hàm getActiveBrands
    public List<Brand> getActiveBrands() {
        List<Brand> brandList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_BRAND, new String[]{
                        BRAND_COLUMN_ID, BRAND_COLUMN_NAME},
                null, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                Brand brand = new Brand(cursor.getInt(0), cursor.getString(1));
                brandList.add(brand);
            } while (cursor.moveToNext());

            cursor.close();
        }

        return brandList;
    }

    public void deleteAllBrands() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_BRAND, null, null);
        db.close();
    }

    public void deleteAllFavorite() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_FAVORITE, null, null);
        db.close();
    }

    public List<Favorite> getFavoriteList(int userId) {
        List<Favorite> favoriteList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_FAVORITE, new String[]{"productId"},
                "userId=?", new String[]{String.valueOf(userId)}, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                int productId = cursor.getInt(0);
                Product product = getProduct(productId); // Get product details from the Product table
                if (product != null) {
                    Favorite favorite = new Favorite(productId, userId, null, productId, product);
                    favoriteList.add(favorite);
                }
            } while (cursor.moveToNext());

            cursor.close();
        }

        return favoriteList;
    }

    public void insertFavorite(int userId, int productId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("userId", userId);
        values.put("productId", productId);

        db.insert(TABLE_FAVORITE, null, values);
        db.close();
    }

    public boolean removeFavorite(int userId, int productId) {
        SQLiteDatabase db = this.getWritableDatabase();
        int affectedRows = db.delete(TABLE_FAVORITE, "userId=? AND productId=?", new String[]{String.valueOf(userId), String.valueOf(productId)});

        return affectedRows > 0;
    }

    public List<Order> getAllOrders(int userId) {
        List<Order> orderList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); // Update format as needed

        Cursor cursor = db.query("`Order`", new String[]{"id", "totalAmount", "orderDate", "status"},
                "userId=?", new String[]{String.valueOf(userId)}, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                int orderId = cursor.getInt(0);
                double totalAmount = cursor.getDouble(1);
                String orderDateString = cursor.getString(2);
                String status = cursor.getString(3);

                Date orderDate = null;
                try {
                    orderDate = dateFormat.parse(orderDateString); // Parse the date
                } catch (ParseException e) {
                    e.printStackTrace(); // Handle parsing exception
                }

                Order order = new Order(orderId, userId, totalAmount, orderDate, status);
                orderList.add(order);
            } while (cursor.moveToNext());

            cursor.close();
        }

        return orderList;
    }

    public void insertOrder(int userId, double totalAmount, Date orderDate, String status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        // Format the date as a string
        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String orderDateString = dateFormat.format(orderDate);

        values.put("userId", userId);
        values.put("totalAmount", totalAmount);
        values.put("orderDate", orderDateString); // Save as string in database
        values.put("status", status);

        db.insert("`Order`", null, values);
        db.close();
    }

    // Cart
    public void insertCartItem(int userId, int productId, int quantity) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("userId", userId);
        values.put("productId", productId);
        values.put("quantity", quantity);
        db.insert("Cart", null, values);
        db.close();
    }

    public List<CartItem> getCartItems(int userId) {
        List<CartItem> cartItems = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query("Cart", new String[]{"id", "productId", "quantity"},
                "userId=?", new String[]{String.valueOf(userId)}, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                int productId = cursor.getInt(1);
                int quantity = cursor.getInt(2);
                cartItems.add(new CartItem(id, userId, null, productId, getProduct(productId), quantity));
            } while (cursor.moveToNext());
            cursor.close();
        }
        return cartItems;
    }

    public void updateCartItem(int cartItemId, int quantity) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("quantity", quantity);
        db.update("Cart", values, "id=?", new String[]{String.valueOf(cartItemId)});
        db.close();
    }

    public boolean deleteCartItem(int userId, int productId) {
        SQLiteDatabase db = this.getWritableDatabase();
        int affectedRows = db.delete("Cart", "userId=? AND productId=?", new String[]{String.valueOf(userId), String.valueOf(productId)});
        db.close();
        return affectedRows > 0;
    }

}

