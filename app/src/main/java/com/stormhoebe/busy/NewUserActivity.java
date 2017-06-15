package com.stormhoebe.busy;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.Bind;
import butterknife.ButterKnife;

public class NewUserActivity extends AppCompatActivity implements View.OnClickListener{

    @Bind(R.id.typeEditText) EditText mTypeEditText;
    @Bind(R.id.locationEditText) EditText mLocationEditText;
    @Bind(R.id.imageEditText) EditText mImageEditText;
    @Bind(R.id.continueButton) Button mContinueButton;

    private String mType;
    private String mLocation;
    private String mImage;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user);
        ButterKnife.bind(this);

        mContinueButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == mContinueButton) {

            updateUser();

        }

    }

    private boolean checkFields() {
        mType = mTypeEditText.toString().trim();
        mLocation = mLocationEditText.toString().trim();
        mImage = mImageEditText.toString().trim();

        if (mType == "" || mLocation == "") {
            return false;
        } else {
            return true;
        }
    }

    private void updateUser() {
        mType = mTypeEditText.toString().trim();
        mLocation = mLocationEditText.toString().trim();
        mImage = mImageEditText.toString().trim();


//        Intent intent = new Intent(NewUserActivity.this, MainActivity.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        startActivity(intent);

    }
}
