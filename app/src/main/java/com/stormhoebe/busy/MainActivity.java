package com.stormhoebe.busy;

import android.app.FragmentManager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    @Bind(R.id.needButton) Button mNeedButton;
    @Bind(R.id.offerButton) Button mOfferButton;
    @Bind(R.id.businessRecyclerView) RecyclerView mRecyclerView;

    private FirebaseUser user;
    private DatabaseReference currentUserRef;
    private DatabaseReference needsRef;
    private DatabaseReference offersRef;
    private List<String> userNeeds;
    private List<String> userOffers;
    private List<String> usersOfferingWhatCurrentUserNeeds;
    private List<String> usersNeedingWhatCurrentUserOffers;
    List<String> list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        mNeedButton.setOnClickListener(this);
        mOfferButton.setOnClickListener(this);
        user =  FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();

        setUpFirebaseAdapter();

        currentUserRef = FirebaseDatabase.getInstance().getReference("users").child(uid);
        needsRef = FirebaseDatabase.getInstance().getReference("needs");
        offersRef = FirebaseDatabase.getInstance().getReference("offers");

        userNeeds = new ArrayList<>();
        userOffers= new ArrayList<>();
        usersOfferingWhatCurrentUserNeeds = new ArrayList<>();
        usersNeedingWhatCurrentUserOffers = new ArrayList<>();

        getMatches();

    }



    public void getMatches() {
        //get current users needs
        Query userNeedsQuery = currentUserRef.child("needs").orderByKey();

        userNeedsQuery.addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
                userNeeds.add(dataSnapshot.getKey());
                getUsersForNeeds();
                Log.d("USERSOFFERING5", usersOfferingWhatCurrentUserNeeds.size() +"");


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


        public void getUsersForNeeds() {
            //for each need in user need list, check offers node for that need and get users who are offering
            for (String need : userNeeds) {

                if (offersRef.child(need)!= null){
                    Query getUsersOfferingNeedsQuery = offersRef.child(need).orderByKey();
                    getUsersOfferingNeedsQuery.addChildEventListener(new ChildEventListener() {

                        @Override
                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                            usersOfferingWhatCurrentUserNeeds.add(dataSnapshot.getKey());
                            Log.d("USERSOFFERING", usersOfferingWhatCurrentUserNeeds.size() +"");

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
                    Log.d("USERSOFFERING2", usersOfferingWhatCurrentUserNeeds.size() +"");

                }
                Log.d("USERSOFFERING3", usersOfferingWhatCurrentUserNeeds.size() +"");

            }
            Log.d("USERSOFFERING4", usersOfferingWhatCurrentUserNeeds.size() +"");

        };


    private void setUpFirebaseAdapter() {
//// TODO: 6/20/17 Gotta do this???
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
            NeedsFragment newNeedsFragment =new NeedsFragment();
            newNeedsFragment.show(fm, "Needs Fragment");
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
        return super.onOptionsItemSelected(item);
    }

    private void logout() {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

}