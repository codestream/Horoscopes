package org.codestream.app.horoscopes.ui;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public abstract class BaseActivity extends Activity {

    @Override
    protected abstract void onCreate(Bundle savedInstanceState);

    @Override
    public abstract boolean onCreateOptionsMenu(Menu menu);

    protected abstract void putTextDataToClipboard();

    protected abstract void saveHoroscope();

    protected abstract void cacheTextData();

    protected abstract void cacheBitmaps();

    @Override
    public abstract boolean onOptionsItemSelected(MenuItem item);

    protected abstract class AsyncHoroscopeTask extends AsyncTask<Void,Integer,String>{

    }
}