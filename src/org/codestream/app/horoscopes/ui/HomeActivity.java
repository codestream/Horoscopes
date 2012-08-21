package org.codestream.app.horoscopes.ui;

import org.codestream.app.horoscopes.ui.today.*;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import ua.com.cupidplc.horoscopes.R;

public class HomeActivity extends Activity{
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        final ListView listView = (ListView)findViewById(R.id.list);
        final String[] categories = getResources().getStringArray(R.array.signs);
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,categories);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position,
                                    long id) {
                if(position == 0){
                    final Intent ariesIntent = new Intent(getApplicationContext(),AriesTodayActivity.class);
                    startActivity(ariesIntent);
                } else if(position == 1){
                    final Intent taurusIntent = new Intent(getApplicationContext(),TaurusTodayActivity.class);
                    startActivity(taurusIntent);
                } else if(position == 2){
                    final Intent geminiIntent = new Intent(getApplicationContext(),GeminiTodayActivity.class);
                    startActivity(geminiIntent);
                } else if(position == 3){
                    final Intent cancerIntent = new Intent(getApplicationContext(),CancerTodayActivity.class);
                    startActivity(cancerIntent);
                } else if(position == 4){
                    final Intent leoIntent = new Intent(getApplicationContext(),LeoTodayActivity.class);
                    startActivity(leoIntent);
                } else if(position == 5){
                    final Intent virgoIntent = new Intent(getApplicationContext(),VirgoTodayActivity.class);
                    startActivity(virgoIntent);
                } else if(position == 6){
                    final Intent libraIntent = new Intent(getApplicationContext(),LibraTodayActivity.class);
                    startActivity(libraIntent);
                } else if(position == 7){
                    final Intent scorpioIntent = new Intent(getApplicationContext(),ScorpioTodayActivity.class);
                    startActivity(scorpioIntent);
                } else if(position == 8){
                    final Intent sagIntent = new Intent(getApplicationContext(),SagittariusTodayActivity.class);
                    startActivity(sagIntent);
                } else if(position == 9){
                    final Intent capricornIntent = new Intent(getApplicationContext(),CapricornTodayActivity.class);
                    startActivity(capricornIntent);
                } else if(position == 10){
                    final Intent aquaIntent = new Intent(getApplicationContext(),AquariusTodayActivity.class);
                    startActivity(aquaIntent);
                } else if(position == 11){
                    final Intent piscesIntent = new Intent(getApplicationContext(),PiscesTodayActivity.class);
                    startActivity(piscesIntent);
                }
            }
        });
    }
}
