package com.stormhoebe.busy.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.firebase.ui.database.FirebaseRecyclerAdapter;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.stormhoebe.busy.FirebaseBusinessViewHolder;
import com.stormhoebe.busy.R;
import com.stormhoebe.busy.database.FirebaseIndexRecyclerAdapter;
import com.stormhoebe.busy.models.User;

import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ShippingActivity extends AppCompatActivity {
    private DatabaseReference mUserReference;
    private FirebaseIndexRecyclerAdapter mFirebaseAdapter;

    private String[] usersArray;
    private List<String> usersList;

    private String uid;

    private DatabaseReference getUsersOfferingMatchQuery;

    @Bind(R.id.mRecyclerView) RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shipping);
        ButterKnife.bind(this);

        uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        Intent intent = getIntent();
        usersArray = intent.getStringArrayExtra("userList");
        usersList = Arrays.asList(usersArray);
        
        mUserReference = FirebaseDatabase.getInstance().getReference("users");
        setUpFirebaseAdapter();
        
    }

    private void setUpFirebaseAdapter() {
        Log.d("usersArray Activity: ", usersList.toString());
        getUsersOfferingMatchQuery = FirebaseDatabase.getInstance().getReference("users").child(uid).child("UsersOfferingMatch");
         mFirebaseAdapter = new FirebaseIndexRecyclerAdapter<User, FirebaseBusinessViewHolder>(
                User.class,
                R.layout.business_list_item,
                FirebaseBusinessViewHolder.class,
                 getUsersOfferingMatchQuery,
                FirebaseDatabase.getInstance().getReference("users")) {
                     @Override
                    protected  void populateViewHolder(FirebaseBusinessViewHolder viewHolder, User model, int position){
                                viewHolder.bindBusiness(model);
                }
        };
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mFirebaseAdapter);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mFirebaseAdapter.cleanup();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_logout) {
            logout();
            return true;
        }
        if (id == R.id.action_mainActivity){
            Intent intent = new Intent(ShippingActivity.this, MainActivity.class);
            startActivity(intent);

        }
        return super.onOptionsItemSelected(item);
    }

    private void logout() {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(ShippingActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }


}
