package com.stormhoebe.busy;

import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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


public class NeedsFragment extends DialogFragment implements View.OnClickListener  {
    private ListView lv;
    private ArrayList<Need> needArrayList;
    private CustomAdapter customAdapter;
    private Button allButton, noneButton, mSubmitButton;
    private  String[] needsList = new String[]{"Coffee", "Auto Repair", "Laundry", "Delivery", "Accounting", "Web Development", "Interior Decorating", "Catering", "Cleaning Service"};


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_needs, container, false);

        getDialog().setTitle("Needs Fragment");
        mSubmitButton = (Button) rootView.findViewById(R.id.submitNeedsButton);
        lv = (ListView) rootView.findViewById(R.id.listView);
        allButton = (Button) rootView.findViewById(R.id.allButton);
        noneButton = (Button) rootView.findViewById(R.id.noneButton);

        needArrayList = getNeed(false);
        customAdapter = new CustomAdapter(getActivity(), needArrayList);
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
                    needsNodeRef.child(needName).child(uid).setValue(uid);
                }
                if (!need.getSelected()){
                    needsNodeRef.child(needName).child(uid).removeValue();
                }
            }

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
