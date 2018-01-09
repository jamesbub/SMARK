package com.roomapp.james.smark;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.kyleduo.blurpopupwindow.library.BlurPopupWindow;
import com.roomapp.james.smark.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class UpdateUserProfile extends AppCompatActivity {


    BlurPopupWindow.Builder builder;
    BlurPopupWindow blurPopupWindow;
    PopupWindow popupWindow;

    @InjectView(R.id.input_name)
    EditText _name;

    @InjectView(R.id.input_phone)
    EditText _phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_user_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }*/

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initiatePopupWindowGeneral(view);
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    private void initiatePopupWindowGeneral(View view) {

        try {
            //We need to get the instance of the LayoutInflater, use the context of this activity
            LayoutInflater inflater = (LayoutInflater) UpdateUserProfile.this
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            //Inflate the view from a predefined XML layout
            View layout = inflater.inflate(R.layout.update_popup_layout,
                    (ViewGroup) findViewById(R.id.popup_element));

            // create the popup window
            int width = LinearLayout.LayoutParams.WRAP_CONTENT;
            int height = LinearLayout.LayoutParams.WRAP_CONTENT;
            boolean focusable = true; // lets taps outside the popup also dismiss it
            popupWindow = new PopupWindow(layout, width, height, focusable);


            // display the popup in the center
            popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
            // dismiss the popup window when touched
            layout.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    popupWindow.dismiss();
                    return true;
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void initiatePopupWindow(final View view) {

        builder =  new BlurPopupWindow.Builder(view.getContext());
        blurPopupWindow = builder.setContentView(R.layout.update_popup_layout)
                .bindClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Toast.makeText(v.getContext(), _name.getText().toString()+_phone.getText().toString(), Toast.LENGTH_SHORT).show();

                        if (null != blurPopupWindow)
                        blurPopupWindow.dismiss();
                    }
                }, R.id.btn_update)
                .setGravity(Gravity.CENTER)
                .setScaleRatio(0.2f)
                .setBlurRadius(10)
                .setTintColor(0x30000000)
                .build();
        blurPopupWindow.show();
    }
}