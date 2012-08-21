package org.codestream.app.horoscopes.provider;

import android.content.*;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;


import java.util.ArrayList;

import org.codestream.app.horoscopes.provider.HoroscopeContract.Month;
import org.codestream.app.horoscopes.provider.HoroscopeContract.Signs;
import org.codestream.app.horoscopes.provider.HoroscopeContract.Today;
import org.codestream.app.horoscopes.provider.HoroscopeContract.Week;
import org.codestream.app.horoscopes.provider.HoroscopeContract.Year;
import org.codestream.app.horoscopes.provider.HoroscopeDatabase.Tables;

public class HoroscopeProvider extends ContentProvider{

    private HoroscopeDatabase horoscopeDatabase;
    private static UriMatcher uriMatcher = buildUriMatcher();
    private Uri resultUri;

    private static final int TODAY = 100;
    private static final int TODAY_ID = 101;
    private static final int WEEK = 200;
    private static final int WEEK_ID = 201;
    private static final int MONTH = 300;
    private static final int MONTH_ID = 301;
    private static final int YEAR = 400;
    private static final int YEAR_ID = 401;
    private static final int SIGN = 500;
    private static final int SIGN_ID = 501;

    public static UriMatcher buildUriMatcher() {
        final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authorityString = HoroscopeContract.CONTENT_AUTHORITY;
        uriMatcher.addURI(authorityString,"today",TODAY);
        uriMatcher.addURI(authorityString,"today/#",TODAY_ID);
        uriMatcher.addURI(authorityString,"weekly",WEEK);
        uriMatcher.addURI(authorityString,"weekly/#",WEEK_ID);
        uriMatcher.addURI(authorityString,"monthly",MONTH);
        uriMatcher.addURI(authorityString,"monthly/#",MONTH_ID);
        uriMatcher.addURI(authorityString,"year",YEAR);
        uriMatcher.addURI(authorityString,"year/#",YEAR_ID);
        uriMatcher.addURI(authorityString,"signs",SIGN);
        uriMatcher.addURI(authorityString,"signs/#",SIGN_ID);
        return uriMatcher;
    }

    @Override
    public boolean onCreate() {
        horoscopeDatabase = new HoroscopeDatabase(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        String table = null;
        final SQLiteDatabase sqLiteDatabase = horoscopeDatabase.getWritableDatabase();
        final int match = uriMatcher.match(uri);
        switch (match){
            case TODAY:
                table = Tables.TODAY;
                break;
            case TODAY_ID:
                break;
            case WEEK:
                table = Tables.WEEK;
                break;
            case WEEK_ID:
                break;
            case MONTH:
                table = Tables.MONTH;
                break;
            case MONTH_ID:
                break;
            case YEAR:
                table = Tables.YEAR;
                break;
            case YEAR_ID:
                break;
            case SIGN:
                table = Tables.SIGNS;
                break;
            case SIGN_ID:
                break;
            default:
                throw new UnsupportedOperationException("Wrong query uri " + uri);
        }

        final SQLiteQueryBuilder sqLiteQueryBuilder = new SQLiteQueryBuilder();
        sqLiteQueryBuilder.setTables(table);
        final Cursor cursor = sqLiteQueryBuilder.query(sqLiteDatabase,projection,selection,selectionArgs,null,null,
                sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(),uri);
        return cursor;
    }

    @Override
    public String getType(Uri uri) {
        final int match = uriMatcher.match(uri);
        switch (match){
            case TODAY:
                return Today.CONTENT_TYPE;
            case TODAY_ID:
                return Today.CONTENT_ITEM_TYPE;
            case WEEK:
                return Week.CONTENT_TYPE;
            case WEEK_ID:
                return Week.CONTENT_ITEM_TYPE;
            case MONTH:
                return Month.CONTENT_TYPE;
            case MONTH_ID:
                return Month.CONTENT_ITEM_TYPE;
            case YEAR:
                return Year.CONTENT_TYPE;
            case YEAR_ID:
                return Year.CONTENT_ITEM_TYPE;
            case SIGN:
                return Signs.CONTENT_TYPE;
            case SIGN_ID:
                return Signs.CONTENT_ITEM_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri type " + uri);
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        final SQLiteDatabase sqLiteDatabase = horoscopeDatabase.getWritableDatabase();
        final int match = uriMatcher.match(uri);
        switch (match){
            case TODAY:
                sqLiteDatabase.insertOrThrow(Tables.TODAY,"empty",contentValues);
                resultUri = null;
                break;
            case WEEK:
                sqLiteDatabase.insertOrThrow(Tables.WEEK,"empty",contentValues);
                resultUri = null;
                break;
            case MONTH:
                sqLiteDatabase.insertOrThrow(Tables.MONTH,"empty",contentValues);
                resultUri = null;
                break;
            case YEAR:
                sqLiteDatabase.insertOrThrow(Tables.YEAR,"empty",contentValues);
                resultUri = null;
                break;
            case SIGN:
                sqLiteDatabase.insertOrThrow(Tables.SIGNS,"empty",contentValues);
                resultUri = null;
                break;
            default:
                throw new UnsupportedOperationException("Unknown insert uri " + uri);
        }
        return uri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] args) {
        final SQLiteDatabase sqLiteDatabase = horoscopeDatabase.getWritableDatabase();
        final int match = uriMatcher.match(uri);
        Uri resultUri;
        switch (match){
            case TODAY:
                sqLiteDatabase.delete(Tables.TODAY,selection,args);
                resultUri = null;
                break;
            case WEEK:
                sqLiteDatabase.delete(Tables.WEEK,selection,args);
                resultUri = null;
                break;
            case MONTH:
                sqLiteDatabase.delete(Tables.MONTH,selection,args);
                resultUri = null;
                break;
            case YEAR:
                sqLiteDatabase.delete(Tables.YEAR,selection,args);
                resultUri = null;
                break;
            case SIGN:
                sqLiteDatabase.delete(Tables.SIGNS,selection,args);
                resultUri = null;
                break;
            default:
                throw new UnsupportedOperationException("Wrong deletion uri " + uri);
        }
        return match;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String selection, String[] args) {
        final SQLiteDatabase sqLiteDatabase = horoscopeDatabase.getWritableDatabase();
        final int match = uriMatcher.match(uri);
        Uri resultUri;
        switch (match){
            case TODAY:
                sqLiteDatabase.update(Tables.TODAY,contentValues,selection,args);
                resultUri = null;
                break;
            case WEEK:
                sqLiteDatabase.update(Tables.WEEK,contentValues,selection,args);
                resultUri = null;
                break;
            case MONTH:
                sqLiteDatabase.update(Tables.MONTH,contentValues,selection,args);
                resultUri = null;
                break;
            case YEAR:
                sqLiteDatabase.update(Tables.YEAR,contentValues,selection,args);
                resultUri = null;
                break;
            case SIGN:
                sqLiteDatabase.update(Tables.SIGNS,contentValues,selection,args);
                resultUri = null;
                break;
            default:
                throw new UnsupportedOperationException("Wrong update uri " + uri);
        }
        return match;
    }

    @Override
    public ContentProviderResult[] applyBatch(ArrayList<ContentProviderOperation> operations)
            throws OperationApplicationException {
        final SQLiteDatabase sqLiteDatabase = horoscopeDatabase.getWritableDatabase();
        sqLiteDatabase.beginTransaction();
        try {
            final int operationsCount = operations.size();
            final ContentProviderResult[] contentProviderResults = new ContentProviderResult[operationsCount];
            for(int i = 0; i < operationsCount; ++i){
                contentProviderResults[i] = operations.get(i).apply(this,contentProviderResults,i);
            }
            sqLiteDatabase.setTransactionSuccessful();
            return contentProviderResults;
        } finally {
            sqLiteDatabase.endTransaction();
        }
    }
}
