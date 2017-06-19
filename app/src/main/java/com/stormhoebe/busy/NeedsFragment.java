package com.stormhoebe.busy;

import android.app.DialogFragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class NeedsFragment extends DialogFragment implements View.OnClickListener  {

    Button mSubmitButton;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_needs, container, false);

        getDialog().setTitle("Needs Fragment");
        mSubmitButton = (Button) rootView.findViewById(R.id.submitNeedsButton);


        mSubmitButton.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onClick(View v) {

    }
}
