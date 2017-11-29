package com.roomapp.james.smark;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class SignUpActivity extends AppCompatActivity {
    private static final String TAG = "SignUpActivity";

    private FirebaseAuth firebaseAuth;
    ProgressDialog progressDialog;

    @InjectView(R.id.input_email_signup)
    EditText _emailText;

    @InjectView(R.id.input_password_signup)
    EditText _passwordText;

    @InjectView(R.id.btn_signup)
    Button _signupButton;


    @InjectView(R.id.link_sigin)
    TextView _signinLink;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        ButterKnife.inject(this);

        firebaseAuth = FirebaseAuth.getInstance();

        _signupButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                signUp();
            }
        });

        _signinLink.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
            }
        });

    }

    private void signUp() {
        Log.d(TAG, "Login");

        if (!validate()) {
            onLoginFailed();
            return;
        }

        _signupButton.setEnabled(false);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Registering...");
        progressDialog.show();

        final String email = _emailText.getText().toString().trim();
        String password = _passwordText.getText().toString().trim();

        firebaseAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){

                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(email.split("@")[0])
                                    .build();

                            user.updateProfile(profileUpdates)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Log.d(TAG, "User profile updated.");
                                            }
                                        }
                                    });

                            new AlertDialog.Builder(SignUpActivity.this , R.style.AlertDialogStyle)
                                    .setIcon(android.R.drawable.ic_dialog_alert)
                                    .setTitle("Registration Successful")
                                    .setMessage("Welcome to SMARK.")
                                    .setPositiveButton("Close", new DialogInterface.OnClickListener()
                                    {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            firebaseAuth.signOut();
                                            finish();
                                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                                        }

                                    })
                                   /* .setNegativeButton("Close", new DialogInterface.OnClickListener()
                                    {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.cancel();
                                        }

                                    })*/
                                    .show();


                        }else{

                            Toast.makeText(getBaseContext(), "Could not Register. Contact Admin", Toast.LENGTH_LONG).show();

                            _signupButton.setEnabled(true);
                        }
                        progressDialog.dismiss();
                    }
                });

    }

    private void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Registration failed", Toast.LENGTH_LONG).show();

        _signupButton.setEnabled(true);
    }

    private boolean validate() {
        boolean valid = true;

        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText.setError("enter a valid email address");
            valid = false;
        } else {
            _emailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 6 || password.length() > 20) {
            _passwordText.setError("between 6 and 20 characters");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        return valid;
    }
}
