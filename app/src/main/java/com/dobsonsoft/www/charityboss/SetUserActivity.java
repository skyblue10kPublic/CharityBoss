package com.dobsonsoft.www.charityboss;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SetUserActivity extends AppCompatActivity {

    private String userName;
    private String userEmail;
    private String defaultUserName;
    private String defaultEmail;


    private EditText etName;
    private EditText etEmail;
    private Button btnAction;
    private Button btnClearData;
    private SharedPreferences sharedPreferences;

    private String etPreviousUserName;
    private String etPreviousEmail;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_user);



        etName = (EditText) findViewById(R.id.editTextUserName);
        etEmail = (EditText) findViewById(R.id.editTextUserEmail);
        btnAction = (Button) findViewById(R.id.buttonUserActionButton);
        btnClearData = (Button) findViewById(R.id.buttonClearUserData);

        sharedPreferences = this.getSharedPreferences( getString(R.string.preference_file_key),Context.MODE_PRIVATE);

        String name =    GetSharedPreferencesString("userName");
        String email =   GetSharedPreferencesString("userEmail");

        if (! (name == null)) {
            etName.setText(name);
            userName = name;
        }
        if (! (email == null))
        {
            etEmail.setText(email);
            userEmail = email;
        }



        defaultUserName =  getResources().getString(R.string.setUserNameText);
        defaultEmail =  getResources().getString(R.string.setUserEmailText);



        btnClearData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                etPreviousUserName = defaultUserName;
                etPreviousEmail = defaultEmail;

                etName.setText("");
                etEmail.setText("");
            }
        });

        btnAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (userEmail == null || userEmail.contentEquals(defaultEmail))
                    userEmail = "";

                Boolean skipValidationForDemo = false;

                if ( userName!=null && userName.contentEquals("Nobody"))
                {
                    userName = "";
                    userEmail = "";
                    skipValidationForDemo = true;
                }

                if (skipValidationForDemo == false && (userName == null || userName.contentEquals("") == true || userName.contentEquals(defaultUserName)) )
                {
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(SetUserActivity.this,R.style.AlertDialogCustom);
                    String invalidInfo = getResources().getString(R.string.searchInvalidInfo);
                    builder1.setMessage(invalidInfo);
                    builder1.setCancelable(false);

                    builder1.setPositiveButton(
                            "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                   dialog.dismiss();
                                }
                            });
/*
                    builder1.setNegativeButton(
                            "No",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
*/
                    AlertDialog alert11 = builder1.create();
                    alert11.show();
                }
                else
                {
                    SetSharedPreferencesString("userName",userName);
                    SetSharedPreferencesString("userEmail",userEmail);

                    Intent returnIntent = new Intent();
                    setResult(Activity.RESULT_OK,returnIntent);
                    finish();
                }
            }
        });

        etName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    etPreviousUserName = etName.getText().toString();
                    etName.setText("");
                }
                else {
                    if (etName.getText().toString().equals(""))
                        etName.setText(etPreviousUserName);
                }
            }
        });

        etEmail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    etPreviousEmail = etEmail.getText().toString();
                    etEmail.setText("");
                }
                else {
                    if (etEmail.getText().toString().equals(""))
                        etEmail.setText(etPreviousEmail);
                }
            }
        });

        etName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                userName = etName.getText().toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        etEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                userEmail = etEmail.getText().toString();

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    private void SetSharedPreferencesString(String key,String value)
    {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }


    private String GetSharedPreferencesString(String key)
    {
        return sharedPreferences.getString(key,"");
    }




}
