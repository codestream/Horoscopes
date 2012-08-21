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
import org.codestream.app.horoscopes.ui.month.AriesMonthActivity;
import org.codestream.app.horoscopes.ui.week.AriesWeekActivity;
import org.codestream.app.horoscopes.ui.year.AriesYearActivity;
import org.codestream.app.horoscopes.utils.HoroscopeClipboard;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import ua.com.cupidplc.horoscopes.R;

public class AriesTodayActivity extends Activity implements HoroscopeClipboard{
    private Context context;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_aries_today);
        new AsyncAriesHoroscope().execute();
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
               onAriesWeekClick();
               return true;
           case R.id.month:
               onAriesMonthClick();
               return true;
           case R.id.year:
               onYearAriesClick();
               return true;
           case R.id.save:
               onSaveAriesClick();
               return true;
           case R.id.toClipboard:
               copyHoroscopeToClipboard();
               return true;
           default:
               return super.onOptionsItemSelected(item);
       }
    }

    private void onAriesWeekClick(){
        final Intent weekAriesIntent = new Intent(getApplicationContext(),AriesWeekActivity.class);
        startActivity(weekAriesIntent);
    }

    private void onAriesMonthClick(){
        final Intent monthIntent = new Intent(getApplicationContext(), AriesMonthActivity.class);
        startActivity(monthIntent);
    }

    private void onYearAriesClick(){
        final Intent intent = new Intent(getApplicationContext(), AriesYearActivity.class);
        startActivity(intent);
    }

    public void copyHoroscopeToClipboard(){
        TextView textView = (TextView)findViewById(R.id.tvAries);
        ClipboardManager clipboardManager = (ClipboardManager)getSystemService(Context.CLIPBOARD_SERVICE);
        clipboardManager.setText(textView.getText());
        Toast toast = Toast.makeText(getApplicationContext(),"Successfully copied",200);
        toast.setGravity(Gravity.CENTER,0,0);
        toast.show();
    }

    public void onSaveAriesClick(){
        HoroscopeDatabase horoscopeDatabase = new HoroscopeDatabase(AriesTodayActivity.this);
        SQLiteDatabase sqLiteDatabase = horoscopeDatabase.getWritableDatabase();
        TextView textView = (TextView)findViewById(R.id.tvAries);
        final ContentValues contentValues = new ContentValues();
        contentValues.put("TODAY_HOROSCOPE",textView.getText().toString());
        sqLiteDatabase.insert(HoroscopeDatabase.Tables.TODAY,null,contentValues);
        sqLiteDatabase.close();
        Toast toast = Toast.makeText(getApplicationContext(),"Successfully saved",200);
        toast.setGravity(Gravity.CENTER,0,1);
        toast.show();
    }

    private class AsyncAriesHoroscope extends AsyncTask<Void,Integer,String>{

        private static final String TAG = "AsyncAriesHoroscope";
        @Override
        protected String doInBackground(Void... voids) {
            final String url = "http://goroskop.online.ua/aries/";
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
            TextView textView = (TextView)findViewById(R.id.tvAries);
            textView.setMovementMethod(new ScrollingMovementMethod());
            textView.setText(result);
        }
    }
}