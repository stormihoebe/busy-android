package com.stormhoebe.busy;

import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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

        }

    }
}
