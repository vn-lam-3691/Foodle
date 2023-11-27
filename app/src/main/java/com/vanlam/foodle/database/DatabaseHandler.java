package com.vanlam.foodle.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.vanlam.foodle.models.Cart;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Foodle_DATABASE.db";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "UserCart";
    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_USER_ID = "UserID";
    public static final String COLUMN_FOOD_ID = "FoodID";
    public static final String COLUMN_FOOD_NAME = "FoodName";
    public static final String COLUMN_FOOD_IMAGE = "FoodImage";
    public static final String COLUMN_QUANTITY = "Quantity";
    public static final String COLUMN_SIZE = "Size";
    public static final String COLUMN_FOOD_PRICE = "FoodPrice";
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
        // Thực hiện tạo table mới
        db.execSQL(SQL_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Xóa bảng nếu tồn tại
        String SQL_DROP = "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(SQL_DROP);
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
        String[] column = {COLUMN_FOOD_ID, COLUMN_FOOD_NAME, COLUMN_FOOD_IMAGE, COLUMN_QUANTITY, COLUMN_SIZE, COLUMN_FOOD_PRICE};

        database.beginTransaction();
        try {
            cursor = database.query(TABLE_NAME, column, COLUMN_USER_ID + " = ?", new String[] {userId}, null, null, null);
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    do {
                        Cart item = new Cart();
                        item.setFoodId(cursor.getString(0));
                        item.setFoodName(cursor.getString(1));
                        item.setImageUrlFood(cursor.getString(2));
                        item.setQuantity(cursor.getInt(3));
                        item.setSize(cursor.getString(4));
                        item.setFoodPrice(Double.valueOf(cursor.getString(5)));

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
}