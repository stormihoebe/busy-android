package com.stormhoebe.busy.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
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
                    Log.d("MODEL", model.getTag());

                    viewHolder.bindBusiness(model);

//                for (String businessID : usersList) {
//                    if(businessID.equals(model.getId())){
//
//
//                    }
//                }
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
}
