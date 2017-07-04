package com.stormhoebe.busy.ui;

import android.app.FragmentManager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.stormhoebe.busy.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, NeedsFragment.OnSubmitButtonSelectedListener{
    @Bind(R.id.needButton) Button mNeedButton;
    @Bind(R.id.offerButton) Button mOfferButton;
    @Bind(R.id.findPartnersButton) Button mFindPartnersButton;

    private FirebaseUser user;
    private String uid;
    private DatabaseReference offersRef;
    private List<String> userNeeds;
    private List<String> userList;
    private String[] userArray;
    private  DatabaseReference usersOfferingWhatCurrentUserNeedsRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        mNeedButton.setOnClickListener(this);
        mOfferButton.setOnClickListener(this);
        mFindPartnersButton.setOnClickListener(this);
        user =  FirebaseAuth.getInstance().getCurrentUser();
        uid = user.getUid();

        offersRef = FirebaseDatabase.getInstance().getReference("offers");
        usersOfferingWhatCurrentUserNeedsRef = FirebaseDatabase.getInstance().getReference("users").child(uid).child("UsersOfferingMatch");


        userNeeds = new ArrayList<>();
        userArray = new String[0];

    }

    @Override
    public void onClick(View v) {
        if (v == mOfferButton) {
            FragmentManager fm = getFragmentManager();
            OffersFragment newOffersFragmet = new OffersFragment();
            newOffersFragmet.show(fm, "Offers Fragment");

        }
        if (v == mNeedButton) {
            FragmentManager fm = getFragmentManager();
            NeedsFragment newNeedsFragment = new NeedsFragment();
            newNeedsFragment.show(fm, "Needs Fragment");
        }
        if (v == mFindPartnersButton) {
            if (userArray.length > 0){
                Intent intent = new Intent(MainActivity.this, ShippingActivity.class);
                intent.putExtra("userList", userArray);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
            if(userArray.length < 1) {
                Toast.makeText(MainActivity.this, "Update your needs to find more partnerships!",
                        Toast.LENGTH_SHORT).show();
            }

        }

    }

    public void getUsersForNeeds(List<String> userNeeds) {
        userList = new ArrayList<>();

        //for each need in user need list, check offers node for that need and get users who are offering
        for (String need : userNeeds) {

            if (offersRef.child(need)!= null){

                Query getUsersOfferingNeedsQuery = offersRef.child(need).orderByKey();
                getUsersOfferingNeedsQuery.addChildEventListener(new ChildEventListener() {

                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        userList.add(dataSnapshot.getKey());
                        setUserList(userList);
                    }

                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }

                });

            }

        }
    };

    public void setUserList(List<String> users) {
        userList = users;
        userArray = userList.toArray(new String[userList.size()]);
        usersOfferingWhatCurrentUserNeedsRef.removeValue();
        updateDatabaseWithUserList(users);

    }
    public void updateDatabaseWithUserList(List<String> users){
        for(String user : users) {
            usersOfferingWhatCurrentUserNeedsRef.child(user).setValue(user);
        }
    }

    //// TODO: 6/19/17  (Day 1) Where I'm at: The app currently has the ability to update users needs and offers.
    // TODO: 6/19/17 (Day 2) Next Step: Matcherioni and Cheese! I have to query the database to match users partners.
    // TODO: 6/19/17 Breaking that down: 1. For each need a user has, go to the needs node, find the corresponding offer node, collect UID's for users under that offer.
    // TODO: 6/19/17 Breaking that down: 2. Find each user that corresponds with that ID. Display those offerers to the current user.
    // TODO: 6/19/17(Day 3) Moving Forward: Allow user to select if they are interested (swiping right) or not interested. Collect "Interested" in new node "matches"
    // TODO: 6/19/17 (Day 4?) Making matches: When both parties swipe right, notify both users and exchange contact information.
    // TODO: 6/19/17 Extra goodies: add yelp info using yelp API

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
            Intent intent = new Intent(MainActivity.this, MainActivity.class);
            startActivity(intent);

        }
        return super.onOptionsItemSelected(item);
    }

    private void logout() {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    public void onNeedsSelected(List<String> needsList) {
        userNeeds = needsList;
        getUsersForNeeds(userNeeds);
    }
}