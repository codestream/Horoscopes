package org.codestream.app.horoscopes.ui;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.IBinder;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;

public abstract class BaseActivity extends Activity {

    public void onHideKeyBoard(IBinder binder){
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(binder, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    @Override
    public abstract boolean onCreateOptionsMenu(Menu menu);

    public Menu createOptionsMenu(Menu menu){
        return menu;
    }

    @Override
    public abstract boolean onOptionsItemSelected(MenuItem item);

    public boolean setupMenuOptionItemSelected(MenuItem item){
        return true;
    }
}