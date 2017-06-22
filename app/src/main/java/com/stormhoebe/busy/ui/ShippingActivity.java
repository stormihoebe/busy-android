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
import com.stormhoebe.busy.FirebaseBusinessViewHolder;
import com.stormhoebe.busy.R;
import com.stormhoebe.busy.models.User;

import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ShippingActivity extends AppCompatActivity {
    private DatabaseReference mUserReference;
    private FirebaseRecyclerAdapter mFirebaseAdapter;

    private String[] usersArray;
    private List<String> usersList;

    @Bind(R.id.mRecyclerView) RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shipping);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        usersArray = intent.getStringArrayExtra("userList");
        usersList = Arrays.asList(usersArray);
        
        mUserReference = FirebaseDatabase.getInstance().getReference("users");
        setUpFirebaseAdapter();
        
    }

    private void setUpFirebaseAdapter() {
        mFirebaseAdapter = new FirebaseRecyclerAdapter<User, FirebaseBusinessViewHolder>
                (User.class, R.layout.business_list_item, FirebaseBusinessViewHolder.class, mUserReference){
            @Override
                    protected  void populateViewHolder(FirebaseBusinessViewHolder viewHolder, User model, int position){

                for (String businessID : usersList) {
                    if (businessID.equals(model.getId())) {
                        viewHolder.bindBusiness(model, true);
                    }
//                    if (!businessID.equals(model.getId())){
//                        viewHolder.bindBusiness(model, false);
//                    }

                }



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
