package com.vanlam.foodle.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.vanlam.foodle.models.Cart;
import com.vanlam.foodle.models.User;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Foodle_DATABASE.db";
    public static final int DATABASE_VERSION = 2;
    public static final String TABLE_NAME = "UserCart";
    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_USER_ID = "UserID";
    public static final String COLUMN_FOOD_ID = "FoodID";
    public static final String COLUMN_FOOD_NAME = "FoodName";
    public static final String COLUMN_FOOD_IMAGE = "FoodImage";
    public static final String COLUMN_QUANTITY = "Quantity";
    public static final String COLUMN_SIZE = "Size";
    public static final String COLUMN_FOOD_PRICE = "FoodPrice";
    // Thông tin của table thứ 2 chứa dữ liệu về các địa chỉ của user
    public static final String TABLE_ADDRESS = "UserInfoAddress";
    public static final String COLUMN_USER_NAME = "UserInfoAddrName";
    public static final String COLUMN_USER_PHONE = "UserInfoAddrPhone";
    public static final String COLUMN_USER_ADDRESS = "UserAddress";
    private SQLiteDatabase database;
    private String userId;

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE = "CREATE TABLE " +
                TABLE_NAME + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_USER_ID + " TEXT, " +
                COLUMN_FOOD_ID + " TEXT, " +
                COLUMN_FOOD_NAME + " TEXT, " +
                COLUMN_FOOD_IMAGE + " TEXT, " +
                COLUMN_QUANTITY + " INTEGER, " +
                COLUMN_SIZE + " TEXT, " +
                COLUMN_FOOD_PRICE + " TEXT)";
        String SQL_CREATE_ADDRESS_TB = "CREATE TABLE " +
                TABLE_ADDRESS + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_USER_ID + " TEXT, " +
                COLUMN_USER_NAME + " TEXT, " +
                COLUMN_USER_PHONE + " TEXT, " +
                COLUMN_USER_ADDRESS + " TEXT)";

        // Thực hiện tạo table mới
        db.execSQL(SQL_CREATE);
        db.execSQL(SQL_CREATE_ADDRESS_TB);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Xóa bảng nếu tồn tại
        String SQL_DROP = "DROP TABLE IF EXISTS " + TABLE_NAME;
        String SQL_DROP_2 = "DROP TABLE IF EXISTS " + TABLE_ADDRESS;
        db.execSQL(SQL_DROP);
        db.execSQL(SQL_DROP_2);
        // Tạo lại bảng mới
        onCreate(db);
    }

    public void openDatabase(String userId) {
        database = getReadableDatabase();
        this.userId = userId;
    }

    public List<Cart> getCarts() {
        final List<Cart> cartList = new ArrayList<>();
        Cursor cursor = null;
        String[] column = {COLUMN_ID, COLUMN_FOOD_ID, COLUMN_FOOD_NAME, COLUMN_FOOD_IMAGE, COLUMN_QUANTITY, COLUMN_SIZE, COLUMN_FOOD_PRICE};

        database.beginTransaction();
        try {
            cursor = database.query(TABLE_NAME, column, COLUMN_USER_ID + " = ?", new String[] {userId}, null, null, null);
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    do {
                        Cart item = new Cart();
                        item.setCardId(cursor.getInt(0));
                        item.setFoodId(cursor.getString(1));
                        item.setFoodName(cursor.getString(2));
                        item.setImageUrlFood(cursor.getString(3));
                        item.setQuantity(cursor.getInt(4));
                        item.setSize(cursor.getString(5));
                        item.setFoodPrice(Double.valueOf(cursor.getString(6)));

                        cartList.add(item);
                    } while (cursor.moveToNext());
                }
            }
        }
        finally {
            database.endTransaction();
            cursor.close();
        }

        return cartList;
    }

    public void addToCart(Cart cart) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_ID, userId);
        values.put(COLUMN_FOOD_ID, cart.getFoodId());
        values.put(COLUMN_FOOD_NAME, cart.getFoodName());
        values.put(COLUMN_FOOD_IMAGE, cart.getImageUrlFood());
        values.put(COLUMN_QUANTITY, cart.getQuantity());
        values.put(COLUMN_SIZE, cart.getSize());
        values.put(COLUMN_FOOD_PRICE, String.valueOf(cart.getFoodPrice()));

        database.insert(TABLE_NAME, null, values);
    }

    public void clearCart(String userId) {
        String sqlDelete = String.format("DELETE FROM " + TABLE_NAME + " WHERE " + COLUMN_USER_ID + " = " + userId);
        database.execSQL(sqlDelete);
    }

    public void deleteCartItem(int cartId) {
        String sqlDelete = String.format("DELETE FROM " + TABLE_NAME + " WHERE " + COLUMN_ID + " = " + cartId);
        database.execSQL(sqlDelete);
    }

    public void resetTableAndAutoIncrement() {
        database.execSQL("DELETE FROM " + TABLE_NAME);

        // Reset giá trị autoincrement
        database.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME = '" + TABLE_NAME + "'");

        // Đóng database sau khi đã hoàn thành
        database.close();
    }

    public void updateCartItem(int idCart, String newSize, int newQuantity) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_QUANTITY, newQuantity);
        values.put(COLUMN_SIZE, newSize);

        database.update(TABLE_NAME, values, COLUMN_ID + " = ? AND " + COLUMN_USER_ID + " = ?", new String[] {String.valueOf(idCart), userId});
    }
    public List<User> getUserInfomations() {
        final List<User> listInfo = new ArrayList<>();
        Cursor cursor = null;
        String[] selectColumn = {COLUMN_ID, COLUMN_USER_NAME, COLUMN_USER_PHONE, COLUMN_USER_ADDRESS};

        database.beginTransaction();
        try {
            cursor = database.query(TABLE_ADDRESS, selectColumn, COLUMN_USER_ID + " = ?", new String[] {userId}, null, null, null);
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    do {
                        User user = new User();
                        user.setName(cursor.getString(1));
                        user.setPhoneNumber(cursor.getString(2));
                        user.setAddress(cursor.getString(3));

                        listInfo.add(user);
                    } while (cursor.moveToNext());
                }
            }
        }
        finally {
            database.endTransaction();
            cursor.close();
        }
        return listInfo;
    }

    public void createNewInfoAddress(User user) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_ID, userId);
        values.put(COLUMN_USER_NAME, user.getName());
        values.put(COLUMN_USER_PHONE, user.getPhoneNumber());
        values.put(COLUMN_USER_ADDRESS, user.getAddress());

        database.insert(TABLE_ADDRESS, null, values);
    }
}