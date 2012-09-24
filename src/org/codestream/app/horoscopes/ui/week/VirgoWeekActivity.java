package org.codestream.app.horoscopes.ui.week;

import android.app.Activity;
import android.app.ProgressDialog;
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
import org.codestream.app.horoscopes.ui.BaseActivity;
import org.codestream.app.horoscopes.utils.HoroscopeClipboard;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.codestream.app.horoscopes.R;

public class VirgoWeekActivity extends BaseActivity implements HoroscopeClipboard {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_virgo_week);
        AsyncTask<Void,Integer,String> asyncTask = new AsyncVirgoWeekHoroscope(this);
        asyncTask.execute();
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

    @Override
    protected void cacheCurrentHoroscope() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    protected void saveCurrentHoroscope(){
        HoroscopeDatabase horoscopeDatabase = new HoroscopeDatabase(VirgoWeekActivity.this);
        SQLiteDatabase sqLiteDatabase = horoscopeDatabase.getWritableDatabase();
        TextView textView = (TextView)findViewById(R.id.tvVirgoWeek);
        final ContentValues contentValues = new ContentValues();
        contentValues.put("WEEK_HOROSCOPE",textView.getText().toString());
        sqLiteDatabase.insert(HoroscopeDatabase.Tables.WEEK,null,contentValues);
        sqLiteDatabase.close();
        Toast toast = Toast.makeText(getApplicationContext(),"Successfully saved",200);
        toast.setGravity(Gravity.CENTER,0,1);
        toast.show();
    }

    public void copyHoroscopeToClipboard(){
        TextView textView = (TextView)findViewById(R.id.tvVirgoWeek);
        ClipboardManager clipboardManager = (ClipboardManager)getSystemService(Context.CLIPBOARD_SERVICE);
        clipboardManager.setText(textView.getText());
        Toast toast = Toast.makeText(getApplicationContext(),"Successfully copied",200);
        toast.setGravity(Gravity.CENTER,0,0);
        toast.show();
    }

    private class AsyncVirgoWeekHoroscope extends AsyncTask<Void,Integer,String> {
        private Context mContext;
        private ProgressDialog mDialog;

        public AsyncVirgoWeekHoroscope(Context context){
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

        private static final String TAG = "AsyncVirgoHoroscope";
        @Override
        protected String doInBackground(Void... voids) {
            final String url = "http://goroskop.online.ua/virgo/week";
            String result = "";
            try {
                Document document = Jsoup.connect(url).get();
                Elements elements = document.select("div.text");
                if(elements.size() > 0){
                    result = elements.get(0).text().replace("Эротический", "")
                            .replace("Бизнес", "").replace("Общий","").replace("и карьера", "").trim();
                }
            } catch(Exception ex){
                Log.e(TAG, ex.toString());
            }
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            TextView textView = (TextView)findViewById(R.id.tvVirgoWeek);
            textView.setMovementMethod(new ScrollingMovementMethod());
            textView.setText(result);
            if(mDialog.isShowing()){
                mDialog.dismiss();
            }
        }
    }
}