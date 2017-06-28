package com.stormhoebe.busy.ui;

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
import com.stormhoebe.busy.CustomAdapter;
import com.stormhoebe.busy.R;
import com.stormhoebe.busy.models.Need;

import java.util.ArrayList;
import java.util.List;


public class NeedsFragment extends DialogFragment implements View.OnClickListener  {
    private ListView lv;
    private ArrayList<Need> needArrayList;
    private CustomAdapter customAdapter;
    private Button allButton, noneButton, mSubmitButton;
    private  String[] needsList = new String[]{"Coffee", "Gelato and Ice Cream", "Prepared Foods", "Catering", "Auto Repair", "Cleaning Service", "Laundry" , "Interior Decorating", "Delivery", "Accounting", "Legal Assistance", "Business Mentorship", "Marketing", "Web Development" };

    private FirebaseUser user;
    private String uid;
    private DatabaseReference currentUserRef;
    private List<String> userNeeds;
    private List<String> usersOfferingWhatCurrentUserNeeds;
    private DatabaseReference offersRef;

    List<String> userList;


    public OnSubmitButtonSelectedListener mCallback;

    //OnSubmitButtonSelectedListener interface allows Fragment to pass needs list to Activity.
    public interface OnSubmitButtonSelectedListener {
        public void onNeedsSelected(List<String> needsList);
    }


    // Creating Dialogue fragment view.
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

            //instantiate reference of needs node as a child of user.
            DatabaseReference userNeedsRef = FirebaseDatabase
                    .getInstance()
                    .getReference("users")
                    .child(uid).child("needs");

            // remove the value of needs node, delete current array
            userNeedsRef.removeValue();

            // instantiate reference of needs node (not as a child of the user)
            DatabaseReference needsNodeRef = FirebaseDatabase
                    .getInstance()
                    .getReference("needs");


            for (Need need: needArrayList) {
                String needName = need.getNeed();
                if (need.getSelected()){
                    userNeeds.add(needName);
                    needsNodeRef.child(needName).child(uid).setValue(uid);
                }
                if (!need.getSelected()){
                    needsNodeRef.child(needName).child(uid).removeValue();
                }

            }
            userNeedsRef.setValue(userNeeds);


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
