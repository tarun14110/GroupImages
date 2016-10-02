package com.rocks.mafia.groupimages;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mafia on 1/10/16.
 */
public class DAOdb {

    private SQLiteDatabase database;
    private DBHelper dbHelper;

    public DAOdb(Context context) {
        dbHelper = new DBHelper(context);
        database = dbHelper.getWritableDatabase();
    }

    /**
     * close any database object
     */
    public void close() {
        dbHelper.close();
    }

    /**
     * insert a text report item to the location database table
     *
     * @param image
     * @return the row ID of the newly inserted row, or -1 if an error occurred
     */
    public long addImage(MyImage image) {
        ContentValues cv = new ContentValues();
        cv.put(DBHelper.COLUMN_PATH, image.getPath());
        cv.put(DBHelper.COLUMN_TITLE, image.getTitle());
        cv.put(DBHelper.COLUMN_DESCRIPTION, image.getDescription());
        cv.put(DBHelper.COLUMN_DATETIME, System.currentTimeMillis());
        return database.insert(DBHelper.TABLE_NAME, null, cv);
    }

    /**
     * delete the given image from database
     *
     * @param image
     */
    public void deleteImage(MyImage image) {
        String whereClause =
                DBHelper.COLUMN_TITLE + "=? AND " + DBHelper.COLUMN_DATETIME +
                        "=?";
        String[] whereArgs = new String[]{image.getTitle(),
                String.valueOf(image.getDatetimeLong())};
        database.delete(DBHelper.TABLE_NAME, whereClause, whereArgs);
    }

    /**
     * @return all image as a List
     */
    public List<MyImage> getImages() {
        List<MyImage> MyImages = new ArrayList<>();
        Cursor cursor =
                database.query(DBHelper.TABLE_NAME, null, null, null, null,
                        null, DBHelper.COLUMN_DATETIME + " DESC");
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            MyImage MyImage = cursorToMyImage(cursor);
            MyImages.add(MyImage);
            cursor.moveToNext();
        }
        cursor.close();
        return MyImages;
    }

    /**
     * updates image
     */
    public void updateImage(MyImage image, String desc) {
        List<MyImage> MyImages = new ArrayList<>();
        Cursor cursor =
                database.query(DBHelper.TABLE_NAME, null, null, null, null,
                        null, DBHelper.COLUMN_DATETIME + " DESC");
        cursor.moveToFirst();

        ContentValues cv = new ContentValues();
        cv.put(DBHelper.COLUMN_PATH, image.getPath());
        cv.put(DBHelper.COLUMN_TITLE, image.getTitle());
        cv.put(DBHelper.COLUMN_DESCRIPTION, desc);
        cv.put(DBHelper.COLUMN_DATETIME, System.currentTimeMillis());



        String where = DBHelper.COLUMN_TITLE + "=? and " + DBHelper.COLUMN_DATETIME + "=?";
        String[] whereArgs = new String[] {image.getTitle(), Long.toString(image.getDatetimeLong())};
        Log.v("RARARARRARARARARARA", where+ " " + whereArgs[0] +  " " + whereArgs[1] + "    "+image.getDatetimeLong());
        int res = database.update(DBHelper.TABLE_NAME, cv, where, whereArgs);
        Log.v("RESULT", Integer.toString(res));

    }

    /**
     * read the cursor row and convert the row to a MyImage object
     *
     * @param cursor
     * @return MyImage object
     */
    private MyImage cursorToMyImage(Cursor cursor) {
        MyImage image = new MyImage();
        image.setPath(
                cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_PATH)));
        image.setTitle(
                cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_TITLE)));
        image.setDatetime(cursor.getLong(
                cursor.getColumnIndex(DBHelper.COLUMN_DATETIME)));
        image.setDescription(cursor.getString(
                cursor.getColumnIndex(DBHelper.COLUMN_DESCRIPTION)));
        return image;
    }
}