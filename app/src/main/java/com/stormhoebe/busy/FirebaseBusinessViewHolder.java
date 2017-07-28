package com.stormhoebe.busy;

import android.content.Context;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.stormhoebe.busy.models.User;
import com.stormhoebe.busy.ui.ShippingActivity;
import com.stormhoebe.busy.util.ItemTouchHelperAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by Guest on 6/20/17.
 */

public class FirebaseBusinessViewHolder  extends RecyclerView.ViewHolder implements View.OnClickListener {
    private static final int MAX_WIDTH = 200;
    private static final int MAX_HEIGHT = 200;

    View mView;
    Context mContext;
    ImageView heartImageView;

    public FirebaseBusinessViewHolder(View itemView) {
        super(itemView);
        mView = itemView;
        mContext = itemView.getContext();
        itemView.setOnClickListener(this);
//        itemView.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//                mClickListener.onItemLongClick(v, getAdapterPosition());
//                return true;
//            }
//        });
    }

    private FirebaseBusinessViewHolder.ClickListener mClickListener;

    //Interface to send callbacks...
    public interface ClickListener{
        public void onItemClick(View view, int position);
        public void onItemLongClick(View view, int position);
    }

    public void setOnClickListener(FirebaseBusinessViewHolder.ClickListener clickListener){
        mClickListener = clickListener;
    }

    public void bindBusiness(User business) {
        TextView bussinessNameTextView = (TextView) mView.findViewById(R.id.businessNameTextView);
        TextView bussinessTypeTextView = (TextView) mView.findViewById(R.id.businessTypeTextView);
        TextView needOfferTextView = (TextView) mView.findViewById(R.id.needOfferTextView);
        TextView needNeedTextView = (TextView) mView.findViewById(R.id.needNeedTextView);
        heartImageView = (ImageView) mView.findViewById(R.id.heartImageView);
        heartImageView.setOnClickListener(this);
            List<String> offersList = business.getOffers();
            String offerString = concatStringsWSep(offersList, ", ");
            List<String> needsList = business.getNeeds();
            String needString = concatStringsWSep(needsList, ", ");
            bussinessNameTextView.setText(business.getName());
            bussinessTypeTextView.setText(business.getTag());
            needOfferTextView.setText("Offering : " + offerString);
            needNeedTextView.setText("In Search Of : " + needString );



    }

    public static String concatStringsWSep(Iterable<String> strings, String separator) {
        StringBuilder sb = new StringBuilder();
        String sep = "";
        for(String s: strings) {
            sb.append(sep).append(s);
            sep = separator;
        }
        return sb.toString();
    }


    @Override
    public void onClick(View v) {
        Toast.makeText(mContext,"Interested!", Toast.LENGTH_SHORT).show();
        int itemPosition = getLayoutPosition();
        String interestedUID = getInterestedUID(itemPosition);
        Log.d("blah", itemPosition+"");

    }

    public String getInterestedUID(final int itemPosition){
        String uid;
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String currentUID = user.getUid();

        DatabaseReference matchingUsers = FirebaseDatabase
                .getInstance()
                .getReference("users")
                .child(currentUID).child("UsersOfferingMatch");

        matchingUsers.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                HashMap<String, String> allUsersOfferingMatch = (HashMap<String, String>) dataSnapshot.getValue();
                List<String> keys =new ArrayList<String>(allUsersOfferingMatch.keySet());
                Log.d("keys viewholder:", keys.toString());
                final String uid = keys.get(itemPosition);
                setInterestedUID(uid);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        return "UID";

    };

    void setInterestedUID(String id){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String currentUID = user.getUid();

        DatabaseReference interestedUsers = FirebaseDatabase
                .getInstance()
                .getReference("users")
                .child(id).child("InterestedUsers");
        interestedUsers.child(currentUID).setValue(currentUID);



        Log.d("firebaseviewholder","The UID is: " + id);
    }
}
