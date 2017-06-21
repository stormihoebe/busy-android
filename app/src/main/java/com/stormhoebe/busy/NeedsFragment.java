package com.stormhoebe.busy;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

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


public class NeedsFragment extends DialogFragment implements View.OnClickListener  {
    private ListView lv;
    private ArrayList<Need> needArrayList;
    private CustomAdapter customAdapter;
    private Button allButton, noneButton, mSubmitButton;
    private  String[] needsList = new String[]{"Coffee", "Auto Repair", "Laundry", "Delivery", "Accounting", "Web Development", "Interior Decorating", "Catering", "Cleaning Service"};

    private FirebaseUser user;
    private String uid;
    private DatabaseReference currentUserRef;
    private List<String> userNeeds;
    private List<String> usersOfferingWhatCurrentUserNeeds;
    private DatabaseReference offersRef;

    List<String> userList;


    public OnSubmitButtonSelectedListener mCallback;

    public interface OnSubmitButtonSelectedListener {
        public void onNeedsSelected(List<String> needsList);
    }

    public static  NeedsFragment newInstance (OnSubmitButtonSelectedListener mCallback){
        NeedsFragment newFragment = new NeedsFragment();
        return newFragment;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mCallback = (OnSubmitButtonSelectedListener) getActivity();

        View rootView = inflater.inflate(R.layout.fragment_needs, container, false);

        getDialog().setTitle("Needs Fragment");
        mSubmitButton = (Button) rootView.findViewById(R.id.submitNeedsButton);
        lv = (ListView) rootView.findViewById(R.id.listView);
        allButton = (Button) rootView.findViewById(R.id.allButton);
        noneButton = (Button) rootView.findViewById(R.id.noneButton);


        user =  FirebaseAuth.getInstance().getCurrentUser();
        uid = user.getUid();

        userNeeds = new ArrayList<>();


        needArrayList = getNeed(false);
        customAdapter = new CustomAdapter(getActivity(), needArrayList);
        lv.setAdapter(customAdapter);

        currentUserRef = FirebaseDatabase.getInstance().getReference("users").child(uid);
        offersRef = FirebaseDatabase.getInstance().getReference("offers");
        uid = user.getUid();


        mSubmitButton.setOnClickListener(this);
        allButton.setOnClickListener(this);
        noneButton.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onClick(View v) {
        if (v == mSubmitButton){
            DatabaseReference userNeedsRef = FirebaseDatabase
                    .getInstance()
                    .getReference("users")
                    .child(uid).child("needs");

            userNeedsRef.removeValue();

            DatabaseReference needsNodeRef = FirebaseDatabase
                    .getInstance()
                    .getReference("needs");

            for (Need need: needArrayList) {
                String needName = need.getNeed();
                if (need.getSelected()){
                    userNeedsRef.child(needName).setValue(needName);
                    userNeeds.add(needName);

                    needsNodeRef.child(needName).child(uid).setValue(uid);
                }
                if (!need.getSelected()){
                    needsNodeRef.child(needName).child(uid).removeValue();
                }
            }


//            getMatches();


            mCallback.onNeedsSelected(userNeeds);

            dismiss();
        }
        if (v == allButton) {
            needArrayList = getNeed(true);
            customAdapter = new CustomAdapter(getActivity(), needArrayList);
            lv.setAdapter(customAdapter);

        }
        if (v == noneButton) {
            needArrayList = getNeed(false);
            customAdapter = new CustomAdapter(getActivity(), needArrayList);
            lv.setAdapter(customAdapter);
        }

    }

    private ArrayList<Need> getNeed(boolean isSelect){
        ArrayList<Need> list = new ArrayList<>();
        for(int i = 0; i < needsList.length; i++){

            Need need = new Need();
            need.setSelected(isSelect);
            need.setNeed(needsList[i]);
            list.add(need);
        }
        return list;
    }





}
