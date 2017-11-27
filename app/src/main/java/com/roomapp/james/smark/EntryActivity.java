package com.roomapp.james.smark;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class EntryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);

        Intent launchIntent = new Intent();
        launchIntent.setClass(getApplicationContext(), MainActivity.class);
        startActivity(launchIntent);

        finish();
    }

}

