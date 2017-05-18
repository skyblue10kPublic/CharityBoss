package com.dobsonsoft.www.charityboss;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    private Button btnDonate;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        btnDonate = (Button) findViewById(R.id.btnSearchDonate);

        sharedPreferences = this.getSharedPreferences( getString(R.string.preference_file_key), Context.MODE_PRIVATE);

        btnDonate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent returnIntent = new Intent();
                setResult(Activity.RESULT_OK,returnIntent);
                finish();
            }
        });

        ArrayList<String> arrayList = new ArrayList<>();

        arrayList.add("Red Cross");
        arrayList.add("Goodwill");
        arrayList.add("Salvation Army");
        arrayList.add("Feeding America");
        arrayList.add("United Way");
        arrayList.add("Jim Dobson's Super Fund");
        arrayList.add("YMCA");
        arrayList.add("Boy's & Girls Club");
        arrayList.add("James Gappy Recovery Effort");
        arrayList.add("UNICEF Worldwide");


        final ListView lv = (ListView) findViewById(R.id.listViewCharities);

        lv.setAdapter(new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,arrayList));

        lv.setClickable(true);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object o = lv.getItemAtPosition(position);
                String selectedCharity=(String)o;

                String donateText =  getResources().getString(R.string.searchDonateButton);

                SetSharedPreferencesString("donateTo",selectedCharity);
                btnDonate.setText(donateText + " " +selectedCharity);

            }
        });

    }

    private void SetSharedPreferencesString(String key,String value)
    {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }
}
