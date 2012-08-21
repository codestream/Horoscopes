package org.codestream.app.horoscopes.provider;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

public class HoroscopeContract {

    public static final String CONTENT_AUTHORITY = "org.codestream.app.horoscopes.provider";
    private static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    private static final String PATH_TODAY = "today";
    private static final String PATH_WEEK = "weekly";
    private static final String PATH_MONTH = "monthly";
    private static final String PATH_YEAR = "year";
    private static final String PATH_SIGNS = "signs";

    public static class Today implements BaseColumns,TodayColumns {
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_TODAY).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/vnd.org.codestream.app.horoscopes.today";

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/vnd.org.codestream.app.horoscopes.today";
    }

    public static class Week implements BaseColumns,WeekColumns {
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_WEEK).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/vnd.org.codestream.app.horoscopes.week";

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/vnd.org.codestream.app.horoscopes.week";
    }

    public static class Month implements BaseColumns,MonthColumns {
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_MONTH).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/vnd.org.codestream.app.horoscopes.month";

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/vnd.org.codestream.app.horoscopes.month";
    }

    public static class Year implements BaseColumns,YearColumns {
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_YEAR).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/vnd.org.codestream.app.horoscopes.year";

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/vnd.org.codestream.app.horoscopes.year";
    }

    public static class Signs implements BaseColumns,SignsColumns {
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_SIGNS).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/vnd.org.codestream.app.horoscopes.signs";

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/vnd.org.codestream.app.horoscopes.signs";
    }

    public interface TodayColumns {
        String TODAY_ID = "id";
        String SIGN_ID = "sign_id";
        String TODAY_HOROSCOPE = "today_horoscope";
    }

    public interface WeekColumns {
        String WEEK_ID = "id";
        String SIGN_ID = "sign_id";
        String WEEK_HOROSCOPE = "week_horoscope";
    }

    public interface MonthColumns {
        String MONTH_ID = "id";
        String SIGN_ID = "sign_id";
        String MONTH_HOROSCOPE = "month_horoscope";
    }

    public interface YearColumns {
        String YEAR_ID = "id";
        String SIGN_ID = "sign_id";
        String YEAR_HOROSCOPE = "year_horoscope";
    }

    public interface SignsColumns {
        String SIGN_ID = "id";
        String SIGN = "sign";
    }
}
