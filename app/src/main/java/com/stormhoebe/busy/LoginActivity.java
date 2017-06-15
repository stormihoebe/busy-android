package com.stormhoebe.busy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import butterknife.Bind;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    @Bind(R.id.signUpbutton) Button mSignUpButton;
    @Bind(R.id.loginButton) Button mLoginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        mSignUpButton.setOnClickListener(this);
        mLoginButton.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        if (v == mSignUpButton) {
            Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
            startActivity(intent);        }
        if (v == mLoginButton) {

        }

    }
}
