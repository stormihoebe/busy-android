package com.stormhoebe.busy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener{
    @Bind(R.id.industrySpinner) Spinner mIndustrySpinner;
    @Bind(R.id.signUpButton) Button mSignUpButton;
    @Bind(R.id.signInTextView) TextView mSignInTextView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        ButterKnife.bind(this);

        mSignUpButton.setOnClickListener(this);
        mSignInTextView.setOnClickListener(this);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.industries_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mIndustrySpinner.setAdapter(adapter);


    }

    @Override
    public void onClick(View v) {
        if (v == mSignUpButton) {
            Log.d("SIGNUP BUTTON", "is working");
        }
        if (v == mSignInTextView) {
            Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
            startActivity(intent);
        }

    }
}
