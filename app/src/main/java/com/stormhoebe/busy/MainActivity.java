package com.stormhoebe.busy;

import android.app.FragmentManager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    @Bind(R.id.needButton) Button mNeedButton;
    @Bind(R.id.offerButton) Button mOfferButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mNeedButton.setOnClickListener(this);
        mOfferButton.setOnClickListener(this);
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

}