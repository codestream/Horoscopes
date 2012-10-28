package org.codestream.app.horoscopes.ui;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.IBinder;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.Toast;
import org.codestream.app.horoscopes.R;

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
    public boolean onOptionsItemSelected(MenuItem item){
        return true;
    }

    public boolean setupMenuOptionItemSelected(MenuItem item){
        return true;
    }

    public void onOpenAboutDialog() throws PackageManager.NameNotFoundException {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.layout_about);
        PackageInfo packageInfo = getPackageManager().getPackageInfo(getPackageName(),0);
        String stringVersion = packageInfo.versionName;
        dialog.setTitle(getResources().getString(R.string.app_name) + " v" + stringVersion);
        Button button = (Button)dialog.findViewById(R.id.feedbackButton);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"okolitiy.viacheslav@gmail.com"});
                emailIntent.setType("message/rfc822");
                startActivity(Intent.createChooser(emailIntent, "Select email application."));
            }
        });

        dialog.show();
    }

    public void showParsingError(){
        Toast toast = Toast.makeText(getApplicationContext(),"Error while fetching data",500);
        toast.setGravity(Gravity.CENTER,19,19);
        toast.show();
    }

    protected abstract void cacheCurrentHoroscope();

    protected abstract void saveCurrentHoroscope();
}