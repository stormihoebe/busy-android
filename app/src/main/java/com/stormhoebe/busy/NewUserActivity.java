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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
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
            addUserInfo();
        }

    }

    private void addUserInfo() {
        mType = mTypeEditText.getText().toString().trim();
        mLocation = mLocationEditText.getText().toString().trim();
        mImage = mImageEditText.getText().toString().trim();
        Log.d("mType", mType);
        Log.d("mLocation", mLocation);
        Log.d("mImage", mImage);



        String name = "name";
        String email = "email";
        String industry = "industry";

        if (mType.equals("")){
            mTypeEditText.setError("Please enter business type");
            return;
        }
        if (mLocation.equals("")){
            mLocationEditText.setError("Please enter business location");
            return;
        }
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();
        Log.d("THIS IS UID", uid);

        User updatedUser = new User(name, email, industry);
        updatedUser.setLocation(mLocation);
        updatedUser.setTag(mType);
        updatedUser.setImage(mImage);
        updatedUser.setId(uid);

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("users");
        mDatabase.child(uid).setValue(updatedUser).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Intent intent = new Intent(NewUserActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);

            }
        });


    }






}
