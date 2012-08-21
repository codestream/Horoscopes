package org.codestream.app.horoscopes.ui.today;

import android.app.Activity;
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
import org.codestream.app.horoscopes.ui.month.LeoMonthActivity;
import org.codestream.app.horoscopes.ui.week.LeoWeekActivity;
import org.codestream.app.horoscopes.ui.year.LeoYearActivity;
import org.codestream.app.horoscopes.utils.HoroscopeClipboard;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import ua.com.cupidplc.horoscopes.R;

public class LeoTodayActivity extends Activity implements HoroscopeClipboard {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_leo_today);
        new AsyncLeoHoroscope().execute();
    }

    /* (non-Javadoc)
    * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
    */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.simple_menu,menu);
        return true;
    }

    /* (non-Javadoc)
	 * @see android.app.Activity#onOptionsItemSelected(android.view.MenuItem)
	 */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.week:
                onLeoWeekClick();
                return true;
            case R.id.month:
                onLeoMonthClick();
                return true;
            case R.id.year:
                onYearLeoClick();
                return true;
            case R.id.save:
                onSaveLeoClick();
                return true;
            case R.id.toClipboard:
                copyHoroscopeToClipboard();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void onLeoWeekClick(){
        final Intent weekLeoIntent = new Intent(getApplicationContext(),LeoWeekActivity.class);
        startActivity(weekLeoIntent);
    }

    private void onLeoMonthClick(){
        final Intent monthIntent = new Intent(getApplicationContext(), LeoMonthActivity.class);
        startActivity(monthIntent);
    }

    private void onYearLeoClick(){
        final Intent intent = new Intent(getApplicationContext(), LeoYearActivity.class);
        startActivity(intent);
    }

    private void onSaveLeoClick(){
        HoroscopeDatabase horoscopeDatabase = new HoroscopeDatabase(LeoTodayActivity.this);
        SQLiteDatabase sqLiteDatabase = horoscopeDatabase.getWritableDatabase();
        TextView textView = (TextView)findViewById(R.id.tvLeoToday);
        final ContentValues contentValues = new ContentValues();
        contentValues.put("TODAY_HOROSCOPE",textView.getText().toString());
        sqLiteDatabase.insert(HoroscopeDatabase.Tables.TODAY,null,contentValues);
        sqLiteDatabase.close();
        Toast toast = Toast.makeText(getApplicationContext(),"Successfully saved",200);
        toast.setGravity(Gravity.CENTER,0,1);
        toast.show();
    }

    public void copyHoroscopeToClipboard(){
        TextView textView = (TextView)findViewById(R.id.tvLeoToday);
        ClipboardManager clipboardManager = (ClipboardManager)getSystemService(Context.CLIPBOARD_SERVICE);
        clipboardManager.setText(textView.getText());
        Toast toast = Toast.makeText(getApplicationContext(),"Successfully copied",200);
        toast.setGravity(Gravity.CENTER,0,0);
        toast.show();
    }

    private class AsyncLeoHoroscope extends AsyncTask<Void,Integer,String> {

        private static final String TAG = "AsyncLeoHoroscope";
        @Override
        protected String doInBackground(Void... voids) {
            final String url = "http://goroskop.online.ua/leo/";
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
            TextView textView = (TextView)findViewById(R.id.tvLeoToday);
            textView.setMovementMethod(new ScrollingMovementMethod());
            textView.setText(result);
        }
    }
}