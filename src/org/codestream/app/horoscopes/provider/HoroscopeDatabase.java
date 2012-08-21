package org.codestream.app.horoscopes.provider;

import org.codestream.app.horoscopes.provider.HoroscopeContract.Month;
import org.codestream.app.horoscopes.provider.HoroscopeContract.Signs;
import org.codestream.app.horoscopes.provider.HoroscopeContract.Today;
import org.codestream.app.horoscopes.provider.HoroscopeContract.Week;
import org.codestream.app.horoscopes.provider.HoroscopeContract.Year;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class HoroscopeDatabase extends SQLiteOpenHelper{
    private static final String DATABASE_NAME = "horoscopes.db";
    private static final int DATABASE_VERSION = 1;

    public HoroscopeDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    private static final String CREATE_TODAY_HOROSCOPE_TABLE =
            "CREATE TABLE " + Tables.TODAY + "("
            + Today._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + Today.TODAY_ID + " INTEGER ,"
            + Today.SIGN_ID + " INTEGER ,"
            + Today.TODAY_HOROSCOPE + " TEXT,"
            + "UNIQUE (" + Today.TODAY_ID + ") ON CONFLICT REPLACE)";

    private static final String CREATE_WEEK_HOROSCOPE_TABLE =
            "CREATE TABLE " + Tables.WEEK + "("
            + Week._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + Week.WEEK_ID + " INTEGER ,"
            + Week.SIGN_ID + " INTEGER ,"
            + Week.WEEK_HOROSCOPE + " TEXT,"
            + "UNIQUE (" + Week.WEEK_ID + ") ON CONFLICT REPLACE)";

    private static final String CREATE_MONTH_HOROSCOPE_TABLE =
            "CREATE TABLE " + Tables.MONTH + "("
            + Month._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + Month.MONTH_ID + " INTEGER ,"
            + Month.SIGN_ID + " INTEGER ,"
            + Month.MONTH_HOROSCOPE + " TEXT,"
            + "UNIQUE (" + Month.MONTH_ID + ") ON CONFLICT REPLACE)";

    private static final String CREATE_YEAR_HOROSCOPE_TABLE =
            "CREATE TABLE " + Tables.YEAR + "("
            + Year._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + Year.YEAR_ID + " INTEGER ,"
            + Year.SIGN_ID + " INTEGER ,"
            + Year.YEAR_HOROSCOPE + " TEXT,"
            + "UNIQUE (" + Year.YEAR_ID + ") ON CONFLICT REPLACE)";

    private static final String CREATE_SIGNS_TABLE =
            "CREATE TABLE " + Tables.SIGNS + "("
            + Signs._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + Signs.SIGN_ID + " INTEGER ,"
            + Signs.SIGN + " TEXT,"
            + "UNIQUE (" + Signs.SIGN_ID + ") ON CONFLICT REPLACE)";


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        createDatabaseTables(sqLiteDatabase);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Tables.TODAY);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Tables.WEEK);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Tables.MONTH);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Tables.YEAR);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Tables.SIGNS);
        createDatabaseTables(sqLiteDatabase);
    }

    private void createDatabaseTables(SQLiteDatabase sqLiteDatabase){
        sqLiteDatabase.execSQL(CREATE_TODAY_HOROSCOPE_TABLE);
        sqLiteDatabase.execSQL(CREATE_WEEK_HOROSCOPE_TABLE);
        sqLiteDatabase.execSQL(CREATE_MONTH_HOROSCOPE_TABLE);
        sqLiteDatabase.execSQL(CREATE_YEAR_HOROSCOPE_TABLE);
        sqLiteDatabase.execSQL(CREATE_SIGNS_TABLE);
    }

    public interface Tables {
        String TODAY = "today";
        String WEEK = "weekly";
        String MONTH = "monthly";
        String YEAR = "year";
        String SIGNS = "signs";
    }
}
