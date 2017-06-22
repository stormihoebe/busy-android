package com.stormhoebe.busy;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.stormhoebe.busy.models.User;
import com.stormhoebe.busy.util.ItemTouchHelperAdapter;

import java.util.List;

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
    }

    public void bindBusiness(User business, boolean showOrNot) {
        TextView bussinessNameTextView = (TextView) mView.findViewById(R.id.businessNameTextView);
        TextView bussinessTypeTextView = (TextView) mView.findViewById(R.id.businessTypeTextView);
        TextView needOfferTextView = (TextView) mView.findViewById(R.id.needOfferTextView);
        heartImageView = (ImageView) mView.findViewById(R.id.heartImageView);
//        if(showOrNot) {
            List<String> offersList = business.getOffers();
            String offerString = concatStringsWSep(offersList, ", ");
            bussinessNameTextView.setText(business.getName());
            bussinessTypeTextView.setText(business.getTag());
            needOfferTextView.setText("Offering : " + offerString);
            heartImageView.setOnClickListener(this);
//        }
//        if(!showOrNot){
//            bussinessNameTextView.setVisibility(View.GONE);
//            bussinessTypeTextView.setVisibility(View.GONE);
//            needOfferTextView.setVisibility(View.GONE);
//
//        }


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
        if (v == heartImageView) {
            Toast.makeText(mContext, " Interested!", Toast.LENGTH_SHORT).show();

        }
    }
}
