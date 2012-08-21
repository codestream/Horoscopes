package org.codestream.app.horoscopes.ui.year;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
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
import org.codestream.app.horoscopes.utils.HoroscopeClipboard;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import ua.com.cupidplc.horoscopes.R;

public class CapricornYearActivity extends Activity implements HoroscopeClipboard {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_capricorn_year);
        new AsyncCapricornYearHoroscope().execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.choise_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.saveThis:
                saveCurrentHoroscope();
                return true;
            case R.id.copyThis:
                copyHoroscopeToClipboard();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void copyHoroscopeToClipboard() {
        TextView textView = (TextView)findViewById(R.id.tvCapricornYear);
        ClipboardManager clipboardManager = (ClipboardManager)getSystemService(Context.CLIPBOARD_SERVICE);
        clipboardManager.setText(textView.getText());
        Toast toast = Toast.makeText(getApplicationContext(),"Successfully copied",200);
        toast.setGravity(Gravity.CENTER,0,0);
        toast.show();
    }

    private void saveCurrentHoroscope(){
        HoroscopeDatabase horoscopeDatabase = new HoroscopeDatabase(CapricornYearActivity.this);
        SQLiteDatabase sqLiteDatabase = horoscopeDatabase.getWritableDatabase();
        TextView textView = (TextView)findViewById(R.id.tvCapricornYear);
        final ContentValues contentValues = new ContentValues();
        contentValues.put("YEAR_HOROSCOPE",textView.getText().toString());
        sqLiteDatabase.insert(HoroscopeDatabase.Tables.YEAR,null,contentValues);
        sqLiteDatabase.close();
        Toast toast = Toast.makeText(getApplicationContext(),"Successfully saved",200);
        toast.setGravity(Gravity.CENTER,0,1);
        toast.show();
    }

    private class AsyncCapricornYearHoroscope extends AsyncTask<Void,Integer,String> {

        private static final String TAG = "AsyncCapricornYearHoroscope";
        @Override
        protected String doInBackground(Void... voids) {
            final String url = "http://goroskop.online.ua/capricorn/year";
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
            TextView textView = (TextView)findViewById(R.id.tvCapricornYear);
            textView.setMovementMethod(new ScrollingMovementMethod());
            textView.setText(result);
        }
    }
}