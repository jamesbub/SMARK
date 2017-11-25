package com.roomapp.james.smark;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class EntryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry);

        Intent launchIntent = new Intent();
        Class<?> launchActivity;
        if(false)
        {
            launchActivity = LoginActivity.class;
        }
        else
        {
            launchActivity = MainActivity.class;
        }
        launchIntent.setClass(getApplicationContext(), launchActivity);
        startActivity(launchIntent);

        finish();
    }

}

