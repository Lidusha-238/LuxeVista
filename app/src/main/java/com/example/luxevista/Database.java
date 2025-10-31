package com.example.luxevista;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class Database extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "LuxeVista.db";
    public static final int DATABASE_VERSION = 5;

    // -------------------- USERS TABLE --------------------
    public static final String TABLE_USERS = "users";
    public static final String USER_ID = "id";
    public static final String USER_NAME = "name";
    public static final String USER_EMAIL = "email";
    public static final String USER_PASSWORD = "password";
    public static final String USER_PHONE = "phone";

    // -------------------- ROOMS TABLE --------------------
    public static final String TABLE_ROOMS = "rooms";
    public static final String ROOM_ID = "room_id";
    public static final String ROOM_NAME = "room_name";
    public static final String ROOM_DESC = "description";
    public static final String ROOM_PRICE = "price";
    public static final String ROOM_TYPE = "type";
    public static final String ROOM_IMAGE = "image";
    public static final String ROOM_AVAIL = "availability";
    public static final String ROOM_RATING = "rating";

    // -------------------- ROOM BOOKINGS TABLE --------------------
    public static final String TABLE_BOOKINGS = "bookings";
    public static final String BOOK_ID = "book_id";
    public static final String BOOK_ROOM = "room_name";
    public static final String BOOK_USER = "user_name";
    public static final String BOOK_EMAIL = "email";
    public static final String BOOK_PHONE = "phone";
    public static final String BOOK_DATE = "date";
    public static final String BOOK_NIGHTS = "no_of_nights";
    public static final String BOOK_TOTAL = "total_price";

    // -------------------- SERVICES TABLE --------------------
    public static final String TABLE_SERVICES = "services";
    public static final String SERVICE_ID = "service_id";
    public static final String SERVICE_NAME = "service_name";
    public static final String SERVICE_DESC = "description";
    public static final String SERVICE_PRICE = "price";
    public static final String SERVICE_IMAGE = "image";
    public static final String SERVICE_RATING = "rating";

    // -------------------- SERVICE BOOKINGS TABLE --------------------
    public static final String TABLE_SERVICE_BOOKINGS = "service_bookings";
    public static final String SERVICE_BOOKING_ID = "booking_id";
    public static final String SERVICE_BOOKING_NAME = "service_name";
    public static final String SERVICE_GUEST_NAME = "guest_name";
    public static final String SERVICE_GUEST_EMAIL = "guest_email";
    public static final String SERVICE_GUEST_PHONE = "guest_phone";
    public static final String SERVICE_DATE = "date";
    public static final String SERVICE_PRICE_BOOKED = "price";

    // -------------------- ATTRACTIONS TABLE --------------------
    public static final String TABLE_ATTRACTIONS = "attractions";
    public static final String ATTRACTION_ID = "id";
    public static final String ATTRACTION_NAME = "name";
    public static final String ATTRACTION_DESC = "description";
    public static final String ATTRACTION_IMAGE = "image";
    public static final String ATTRACTION_TYPE = "type";
    public static final String ATTRACTION_RATING = "rating";


    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // USERS TABLE
        db.execSQL("CREATE TABLE " + TABLE_USERS + " (" +
                USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                USER_NAME + " TEXT," +
                USER_EMAIL + " TEXT UNIQUE," +
                USER_PASSWORD + " TEXT," +
                USER_PHONE + " TEXT)");

        // ROOMS TABLE
        db.execSQL("CREATE TABLE " + TABLE_ROOMS + " (" +
                ROOM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                ROOM_NAME + " TEXT," +
                ROOM_DESC + " TEXT," +
                ROOM_PRICE + " REAL," +
                ROOM_TYPE + " TEXT," +
                ROOM_IMAGE + " TEXT," +
                ROOM_AVAIL + " TEXT," +
                ROOM_RATING + " REAL)");

        // BOOKINGS TABLE
        db.execSQL("CREATE TABLE " + TABLE_BOOKINGS + " (" +
                BOOK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                BOOK_ROOM + " TEXT," +
                BOOK_USER + " TEXT," +
                BOOK_EMAIL + " TEXT," +
                BOOK_PHONE + " TEXT," +
                BOOK_DATE + " TEXT," +
                BOOK_NIGHTS + " INTEGER," +
                BOOK_TOTAL + " REAL)");

        // SERVICES TABLE
        db.execSQL("CREATE TABLE " + TABLE_SERVICES + " (" +
                SERVICE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                SERVICE_NAME + " TEXT," +
                SERVICE_DESC + " TEXT," +
                SERVICE_PRICE + " REAL," +
                SERVICE_IMAGE + " TEXT," +
                SERVICE_RATING + " REAL)");


        // SERVICE BOOKINGS TABLE
        db.execSQL("CREATE TABLE " + TABLE_SERVICE_BOOKINGS + " (" +
                SERVICE_BOOKING_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                SERVICE_BOOKING_NAME + " TEXT," +
                SERVICE_GUEST_NAME + " TEXT," +
                SERVICE_GUEST_EMAIL + " TEXT," +
                SERVICE_GUEST_PHONE + " TEXT," +
                SERVICE_DATE + " TEXT," +
                SERVICE_PRICE_BOOKED + " REAL)");

        // ATTRACTIONS TABLE
        db.execSQL("CREATE TABLE " + TABLE_ATTRACTIONS + " (" +
                ATTRACTION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                ATTRACTION_NAME + " TEXT," +
                ATTRACTION_DESC + " TEXT," +
                ATTRACTION_IMAGE + " TEXT," +
                ATTRACTION_TYPE + " TEXT," +
                ATTRACTION_RATING + " REAL)");

        insertSampleRooms(db);
        insertSampleServices(db);
        insertSampleAttractions(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ROOMS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BOOKINGS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SERVICES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SERVICE_BOOKINGS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ATTRACTIONS);
        onCreate(db);
    }

    // USER
    public boolean insertUser(String name, String email, String password, String phone) {
        ContentValues values = new ContentValues();
        values.put(USER_NAME, name);
        values.put(USER_EMAIL, email);
        values.put(USER_PASSWORD, password);
        values.put(USER_PHONE, phone);
        return getWritableDatabase().insert(TABLE_USERS, null, values) != -1;
    }

    public boolean checkUserExists(String email) {
        Cursor cursor = getReadableDatabase().rawQuery("SELECT * FROM " + TABLE_USERS + " WHERE " + USER_EMAIL + "=?", new String[]{email});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

    public boolean validateUser(String email, String password) {
        Cursor cursor = getReadableDatabase().rawQuery("SELECT * FROM " + TABLE_USERS + " WHERE " + USER_EMAIL + "=? AND " + USER_PASSWORD + "=?", new String[]{email, password});
        boolean valid = cursor.getCount() > 0;
        cursor.close();
        return valid;
    }

    // ROOMS
    private void insertSampleRooms(SQLiteDatabase db) {
        insertRoom(db, "Ocean View Suite", "Spacious suite with breathtaking ocean view.", 20000.0, "Suite", "ocean_suite", "Available", 4.7);
        insertRoom(db, "Deluxe Room", "Elegant room with private balcony and city view.", 17000.0, "Deluxe", "deluxe_room", "Available",4.8);
        insertRoom(db, "Family Room", "Comfortable space for families, with 2 queen beds.", 15000.0, "Family", "family_room", "Available",4.5);
    }

    private boolean insertRoom(SQLiteDatabase db, String name, String desc, double price, String type, String image, String availability,double rating) {
        ContentValues values = new ContentValues();
        values.put(ROOM_NAME, name);
        values.put(ROOM_DESC, desc);
        values.put(ROOM_PRICE, price);
        values.put(ROOM_TYPE, type);
        values.put(ROOM_IMAGE, image);
        values.put(ROOM_AVAIL, availability);
        values.put(ROOM_RATING, rating);
        return db.insert(TABLE_ROOMS, null, values) != -1;
    }

    public ArrayList<Room> getAllRooms(String filter, String sort) {
        ArrayList<Room> list = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_ROOMS + " WHERE " + ROOM_AVAIL + "='Available'";
        if (!filter.equals("All")) query += " AND " + ROOM_TYPE + "='" + filter + "'";
        if (sort.equals("Price Low-High")) query += " ORDER BY " + ROOM_PRICE + " ASC";
        else if (sort.equals("Price High-Low")) query += " ORDER BY " + ROOM_PRICE + " DESC";

        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                list.add(new Room(
                        cursor.getInt(cursor.getColumnIndexOrThrow(ROOM_ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(ROOM_NAME)),
                        cursor.getString(cursor.getColumnIndexOrThrow(ROOM_DESC)),
                        cursor.getDouble(cursor.getColumnIndexOrThrow(ROOM_PRICE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(ROOM_TYPE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(ROOM_IMAGE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(ROOM_AVAIL)),
                        cursor.getDouble(cursor.getColumnIndexOrThrow(ROOM_RATING))
                ));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    public boolean saveBooking(String room, String name, String email, String phone, String date, int nights, double total) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(BOOK_ROOM, room);
        values.put(BOOK_USER, name);
        values.put(BOOK_EMAIL, email);
        values.put(BOOK_PHONE, phone);
        values.put(BOOK_DATE, date);
        values.put(BOOK_NIGHTS, nights);
        values.put(BOOK_TOTAL, total);
        long result = db.insert(TABLE_BOOKINGS, null, values);

        // Mark room as booked
        ContentValues update = new ContentValues();
        update.put(ROOM_AVAIL, "Booked");
        db.update(TABLE_ROOMS, update, ROOM_NAME + "=?", new String[]{room});

        return result != -1;
    }

    //  SERVICES
    private void insertSampleServices(SQLiteDatabase db) {
        insertService(db, "Luxury Spa Therapy", "Relaxing full-body spa session.", 1500.0, "spa",4.9);
        insertService(db, "Fine Dining Experience", "Exclusive 5-course gourmet meal.", 2000.0, "dinning",4.7);
        insertService(db, "Poolside Cabana", "Private shaded cabana beside the pool.", 1200.0, "cabanas",5.0);
        insertService(db, "Beach Tour", "Guided sunrise and sunset beach tour.", 1800.0, "beach_tours", 4.8);
    }

    private boolean insertService(SQLiteDatabase db, String name, String desc, double price, String image, double rating) {
        ContentValues values = new ContentValues();
        values.put(SERVICE_NAME, name);
        values.put(SERVICE_DESC, desc);
        values.put(SERVICE_PRICE, price);
        values.put(SERVICE_IMAGE, image);
        values.put(ATTRACTION_RATING, rating);
        return db.insert(TABLE_SERVICES, null, values) != -1;
    }

    public ArrayList<Service> getAllServices() {
        ArrayList<Service> list = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_SERVICES, null);
        if (cursor.moveToFirst()) {
            do {
                list.add(new Service(
                        cursor.getInt(cursor.getColumnIndexOrThrow(SERVICE_ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(SERVICE_NAME)),
                        cursor.getString(cursor.getColumnIndexOrThrow(SERVICE_DESC)),
                        cursor.getDouble(cursor.getColumnIndexOrThrow(SERVICE_PRICE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(SERVICE_IMAGE)),
                        cursor.getDouble(cursor.getColumnIndexOrThrow(SERVICE_RATING))
                ));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    public boolean insertServiceBooking(String serviceName, String guestName, String guestEmail, String guestPhone, String date, double price) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(SERVICE_BOOKING_NAME, serviceName);
        values.put(SERVICE_GUEST_NAME, guestName);
        values.put(SERVICE_GUEST_EMAIL, guestEmail);
        values.put(SERVICE_GUEST_PHONE, guestPhone);
        values.put(SERVICE_DATE, date);
        values.put(SERVICE_PRICE_BOOKED, price);
        return db.insert(TABLE_SERVICE_BOOKINGS, null, values) != -1;
    }

    // ATTRACTIONS
    private void insertSampleAttractions(SQLiteDatabase db) {
        insertAttraction(db, "Sunset Beach Tour", "Guided tour of Luxe Vista’s beach with sunset views.", "beach_tours", "Beach", 4.7);
        insertAttraction(db, "Adventure Diving", "Discover marine life with guided diving tours nearby.", "diving", "Water Sports", 4.6);
        insertAttraction(db, "Fine Dining by Luxe Vista", "Luxurious dining experience with oceanfront view.", "dinning", "Dining", 4.5);
        insertAttraction(db, "Surfing Adventure", "Exciting surfing experience near Luxe Vista beach.", "surfing", "Water Sports", 4.5);
    }


    private boolean insertAttraction(SQLiteDatabase db, String name, String desc, String image, String type, double rating) {
        ContentValues values = new ContentValues();
        values.put(ATTRACTION_NAME, name);
        values.put(ATTRACTION_DESC, desc);
        values.put(ATTRACTION_IMAGE, image);
        values.put(ATTRACTION_TYPE, type);
        values.put(ATTRACTION_RATING, rating);
        return db.insert(TABLE_ATTRACTIONS, null, values) != -1;
    }



    public ArrayList<Attraction> getAllAttractions() {
        ArrayList<Attraction> list = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_ATTRACTIONS, null);
        if (cursor.moveToFirst()) {
            do {
                list.add(new Attraction(
                        cursor.getInt(cursor.getColumnIndexOrThrow(ATTRACTION_ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(ATTRACTION_NAME)),
                        cursor.getString(cursor.getColumnIndexOrThrow(ATTRACTION_DESC)),
                        cursor.getString(cursor.getColumnIndexOrThrow(ATTRACTION_IMAGE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(ATTRACTION_TYPE)),
                        cursor.getDouble(cursor.getColumnIndexOrThrow(ATTRACTION_RATING))
                ));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    // Get user by ID
    public Cursor getUserByIdCursor(int userId) {
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_USERS + " WHERE " + USER_ID + "=?", new String[]{String.valueOf(userId)});
    }

    // Update user profile
    public boolean updateUser(int userId, String name, String email, String phone, String password) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(USER_NAME, name);
        values.put(USER_EMAIL, email);
        values.put(USER_PHONE, phone);
        values.put(USER_PASSWORD, password);
        return db.update(TABLE_USERS, values, USER_ID + "=?", new String[]{String.valueOf(userId)}) > 0;
    }

    // Get bookings
    public Cursor getUserBookingsCursor(int userId) {
        SQLiteDatabase db = getReadableDatabase();
        // Use email to match user bookings
        Cursor userCursor = db.rawQuery("SELECT " + USER_EMAIL + " FROM " + TABLE_USERS + " WHERE " + USER_ID + "=?", new String[]{String.valueOf(userId)});
        String email = "";
        if (userCursor.moveToFirst()) {
            email = userCursor.getString(userCursor.getColumnIndexOrThrow(USER_EMAIL));
        }
        userCursor.close();
        return db.rawQuery("SELECT * FROM " + TABLE_BOOKINGS + " WHERE " + BOOK_EMAIL + "=?", new String[]{email});
    }

    // Cancel a booking
    public boolean cancelBooking(int bookingId) {
        SQLiteDatabase db = getWritableDatabase();
        // Optionally, mark the room as available again
        Cursor cursor = db.rawQuery("SELECT " + BOOK_ROOM + " FROM " + TABLE_BOOKINGS + " WHERE " + BOOK_ID + "=?", new String[]{String.valueOf(bookingId)});
        String roomName = "";
        if (cursor.moveToFirst()) {
            roomName = cursor.getString(cursor.getColumnIndexOrThrow(BOOK_ROOM));
        }
        cursor.close();

        ContentValues updateRoom = new ContentValues();
        updateRoom.put(ROOM_AVAIL, "Available");
        db.update(TABLE_ROOMS, updateRoom, ROOM_NAME + "=?", new String[]{roomName});

        return db.delete(TABLE_BOOKINGS, BOOK_ID + "=?", new String[]{String.valueOf(bookingId)}) > 0;
    }

    // Get service bookings
    public Cursor getUserServiceBookingsCursor(int userId) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor userCursor = db.rawQuery("SELECT " + USER_EMAIL + " FROM " + TABLE_USERS + " WHERE " + USER_ID + "=?", new String[]{String.valueOf(userId)});
        String email = "";
        if (userCursor.moveToFirst()) {
            email = userCursor.getString(userCursor.getColumnIndexOrThrow(USER_EMAIL));
        }
        userCursor.close();
        return db.rawQuery("SELECT * FROM " + TABLE_SERVICE_BOOKINGS + " WHERE " + SERVICE_GUEST_EMAIL + "=?", new String[]{email});
    }

    // Cancel service booking
    public boolean cancelServiceBooking(int bookingId) {
        SQLiteDatabase db = getWritableDatabase();
        return db.delete(TABLE_SERVICE_BOOKINGS, SERVICE_BOOKING_ID + "=?", new String[]{String.valueOf(bookingId)}) > 0;
    }


}
