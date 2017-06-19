package com.stormhoebe.busy;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * Created by Guest on 6/19/17.
 */

public class OffersFragment extends DialogFragment implements View.OnClickListener{
    private ListView lv;
    private ArrayList<Need> offerArrayList;
    private CustomAdapter customAdapter;
    private Button allButton, noneButton, mSubmitButton;
    private  String[] offersList = new String[]{"Coffee", "Auto Repair", "Laundry", "Delivery", "Accounting", "Web Development", "Interior Decorating", "Catering", "Cleaning Service"};


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_offers, container, false);

        getDialog().setTitle("Offers Fragment");
        mSubmitButton = (Button) rootView.findViewById(R.id.submitOffersButton);
        lv = (ListView) rootView.findViewById(R.id.listView);
        allButton = (Button) rootView.findViewById(R.id.allButton);
        noneButton = (Button) rootView.findViewById(R.id.noneButton);

        offerArrayList = getOffer(false);
        customAdapter = new CustomAdapter(getActivity(), offerArrayList);
        lv.setAdapter(customAdapter);

        mSubmitButton.setOnClickListener(this);
        allButton.setOnClickListener(this);
        noneButton.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onClick(View v) {
        if (v == mSubmitButton){
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            String uid = user.getUid();

            DatabaseReference userOffersRef = FirebaseDatabase
                    .getInstance()
                    .getReference("users")
                    .child(uid).child("offers");

            userOffersRef.removeValue();

            DatabaseReference offersNodeRef = FirebaseDatabase
                    .getInstance()
                    .getReference("offers");

            for (Need offer: offerArrayList) {
                String offerName = offer.getNeed();
                if (offer.getSelected()){
                    userOffersRef.child(offerName).setValue(offerName);
                    offersNodeRef.child(offerName).child(uid).setValue(uid);
                }
                if (!offer.getSelected()){
                    offersNodeRef.child(offerName).child(uid).removeValue();
                }
            }

            dismiss();
        }
        if (v == allButton) {
            offerArrayList = getOffer(true);
            customAdapter = new CustomAdapter(getActivity(), offerArrayList);
            lv.setAdapter(customAdapter);

        }
        if (v == noneButton) {
            offerArrayList = getOffer(false);
            customAdapter = new CustomAdapter(getActivity(), offerArrayList);
            lv.setAdapter(customAdapter);
        }
    }

    private ArrayList<Need> getOffer(boolean isSelect){
        ArrayList<Need> list = new ArrayList<>();
        for(int i = 0; i < offersList.length; i++){

            Need offer = new Need();
            offer.setSelected(isSelect);
            offer.setNeed(offersList[i]);
            list.add(offer);
        }
        return list;
    }
}
