package com.dobsonsoft.www.charityboss;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;

    private static final int RATING_POPUP_DELAY_MIN = 8000;
    private static final int RATING_POPUP_DELAY_MAX = 15000;


    private final Handler postDelayHandler = new Handler();
    private Dialog rateDialog;
    private RatingBar ratingBar;
    private Button btnSetUserId;
    private Button btnSearch;
    private TextView textViewUserName;

    float appRating = 4;
    int delayBeforePopupRating = 4000;

    private Boolean ratingAlreadyDisplayed = false;

    private final Runnable FinishActivity = new Runnable() {
        @Override
        public void run() {
            ShowRatingDialog();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        btnSetUserId = (Button) findViewById(R.id.btnCreateIdentity);
        btnSearch = (Button) findViewById(R.id.btnSearchCharities);

        textViewUserName = (TextView) findViewById(R.id.textUserName);

        delayBeforePopupRating =  (int)(Math.random() * (RATING_POPUP_DELAY_MAX-RATING_POPUP_DELAY_MIN))+RATING_POPUP_DELAY_MIN;
        sharedPreferences = this.getSharedPreferences( getString(R.string.preference_file_key),Context.MODE_PRIVATE);


            btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = GetSharedPreferenceString("userName");
                if (name == null || name.contentEquals("")) {
                    String warning = getResources().getString(R.string.mainActivityNeedToSetUserFirst);
                    Toast.makeText(getApplicationContext(), warning, Toast.LENGTH_LONG).show();
                }
                else {
                    Intent intent = new Intent(v.getContext(), SearchActivity.class);
                    startActivityForResult(intent, 3);
                }
            }
        });

        btnSetUserId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(v.getContext(),SetUserActivity.class);
                startActivityForResult(intent,2);
            }
        });

        UpdateUserNameText();

        Intent intent = new Intent(this,SplashFSActivity.class);
        startActivityForResult(intent,1);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode)
        {
            case 1: postDelayHandler.postDelayed(FinishActivity, delayBeforePopupRating);
                break;

            case 2:
            {
                //String userName = GetSharedPreferenceString("userName");
                //textViewUserName.setText(userName);
                UpdateUserNameText();
                break;
            }

            case 3:
            {
                String thanks =  GetSharedPreferenceString("donateTo")+" "+getResources().getString(R.string.searchThanksDude)+ " " + GetSharedPreferenceString("userName");

                Toast.makeText(getApplicationContext(),thanks,Toast.LENGTH_SHORT).show();

                UpdateUserNameText();

                break;
            }
        }
    }

    private void ShowRatingDialog()
    {
        if (ratingAlreadyDisplayed==true)
            return;

        ratingAlreadyDisplayed = true;

        rateDialog = new Dialog(MainActivity.this, R.style.FullHeightDialog);
        rateDialog.setContentView(R.layout.rank_dialog);
        rateDialog.setCancelable(true);
        ratingBar = (RatingBar)rateDialog.findViewById(R.id.dialog_ratingbar);
        ratingBar.setRating(appRating);

        Button updateButton = (Button) rateDialog.findViewById(R.id.rank_dialog_button);
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rateDialog.dismiss();
            }
        });



        rateDialog.show();
    }

    private void SetSharedPreferencesInt(String key,int value)
    {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    private int GetSharedPreferenceInt(String key)
    {
        return sharedPreferences.getInt(key,0);
    }

    private void SetSharedPreferencesString(String key,String value)
    {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    private String GetSharedPreferenceString(String key)
    {
        return sharedPreferences.getString(key,"");
    }

    private void UpdateUserNameText()
    {

        String name = GetSharedPreferenceString("userName");
        String lastGave = GetSharedPreferenceString("donateTo");

        if (!(name == null && name.contentEquals("")) )
        {
            String preface;

            if ( lastGave != null && !lastGave.contentEquals(""))
            {
                preface = getResources().getString(R.string.mainActivityLastGavePreface)+" "+lastGave+" "+name;
            }
            else
            {
                preface = name;
            }
            textViewUserName.setText(preface);
        }
    }



}
