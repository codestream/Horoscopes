package org.codestream.app.horoscopes.ui.today;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.ClipboardManager;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import org.codestream.app.horoscopes.provider.HoroscopeDatabase;
import org.codestream.app.horoscopes.ui.BaseActivity;
import org.codestream.app.horoscopes.ui.month.AquariusMonthActivity;
import org.codestream.app.horoscopes.ui.week.AquariusWeekActivity;
import org.codestream.app.horoscopes.ui.year.AquariusYearActivity;
import org.codestream.app.horoscopes.utils.HoroscopeClipboard;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.codestream.app.horoscopes.R;

public class AquariusTodayActivity extends BaseActivity implements HoroscopeClipboard {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_aquarius_today);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.simple_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.week:
                onAquariusWeekClick();
                return true;
            case R.id.month:
                onAquariusMonthClick();
                return true;
            case R.id.year:
                onYearAquariusClick();
                return true;
            case R.id.save:
                saveCurrentHoroscope();
                return true;
            case R.id.toClipboard:
                copyHoroscopeToClipboard();
                return true;
            default:
                return true;
        }
    }

    private void onAquariusWeekClick(){
        final Intent weekAquariusIntent = new Intent(getApplicationContext(),AquariusWeekActivity.class);
        startActivity(weekAquariusIntent);
    }

    private void onAquariusMonthClick(){
        final Intent monthIntent = new Intent(getApplicationContext(), AquariusMonthActivity.class);
        startActivity(monthIntent);
    }

    private void onYearAquariusClick(){
        final Intent intent = new Intent(getApplicationContext(), AquariusYearActivity.class);
        startActivity(intent);
    }

    @Override
    protected void cacheCurrentHoroscope() {

    }

    @Override
    protected void saveCurrentHoroscope() {
        HoroscopeDatabase horoscopeDatabase = new HoroscopeDatabase(AquariusTodayActivity.this);
        SQLiteDatabase sqLiteDatabase = horoscopeDatabase.getWritableDatabase();
        TextView textView = (TextView)findViewById(R.id.tvAquariusToday);
        final ContentValues contentValues = new ContentValues();
        contentValues.put("TODAY_HOROSCOPE",textView.getText().toString());
        sqLiteDatabase.insert(HoroscopeDatabase.Tables.TODAY,null,contentValues);
        sqLiteDatabase.close();
        Toast toast = Toast.makeText(getApplicationContext(),"Successfully saved",200);
        toast.setGravity(Gravity.CENTER,0,1);
        toast.show();
    }

    @Override
    public void copyHoroscopeToClipboard() {
        TextView textView = (TextView)findViewById(R.id.tvAquariusToday);
        ClipboardManager clipboardManager = (ClipboardManager)getSystemService(Context.CLIPBOARD_SERVICE);
        clipboardManager.setText(textView.getText());
        Toast toast = Toast.makeText(getApplicationContext(),"Successfully copied",200);
        toast.setGravity(Gravity.CENTER,0,0);
        toast.show();
    }

    private class AsyncAquariusHoroscope extends AsyncTask<Void,Integer,String> {

        private Context mContext;
        private ProgressDialog mDialog;
        private static final String TAG = "AsyncAquariusHoroscope";

        public AsyncAquariusHoroscope(Context context){
            this.mContext = context;
            this.mDialog = new ProgressDialog(mContext);
        }

        @Override
        protected void onPreExecute() {
            mDialog.setTitle("Loading horoscopes");
            mDialog.setMessage("Please wait....");
            mDialog.setIndeterminate(false);
            mDialog.setCancelable(false);
            mDialog.show();
        }

        @Override
        protected String doInBackground(Void... voids) {
            final String url = "http://goroskop.online.ua/aquarius/";
            String result = "";
            try {
                Document document = Jsoup.connect(url).get();
                Elements elements = document.select("div.text");
                if(elements.size() > 0){
                    result = elements.get(0).text();
                }
            } catch(Exception ex){
                Log.e(TAG, ex.toString());
            }
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            TextView textView = (TextView)findViewById(R.id.tvAquariusToday);
            textView.setMovementMethod(new ScrollingMovementMethod());
            textView.setText(result);
            if(mDialog.isShowing()){
                mDialog.dismiss();
            }
        }
    }
}