package com.stormhoebe.busy;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by Guest on 6/20/17.
 */

public class FirebaseBusinessViewHolder  extends RecyclerView.ViewHolder {
    private static final int MAX_WIDTH = 200;
    private static final int MAX_HEIGHT = 200;

    View mView;
    Context mContext;

    public FirebaseBusinessViewHolder(View itemView) {
        super(itemView);
        mView = itemView;
        mContext = itemView.getContext();
    }

    public void bindBusiness(User business, String needOrOffer, String need) {
        TextView bussinessNameTextView = (TextView) mView.findViewById(R.id.businessNameTextView);
        TextView bussinessTypeTextView = (TextView) mView.findViewById(R.id.businessTypeTextView);
        TextView needOfferTextView = (TextView) mView.findViewById(R.id.needOfferTextView);
        String needOfferString;

        bussinessNameTextView.setText(business.getName());
        bussinessTypeTextView.setText(business.getTag());

        if (needOrOffer.equals("need")){
            needOfferString = "Needs " + need;
            needOfferTextView.setText(needOfferString);
        }
        if (needOrOffer.equals("offer")){
            needOfferString = "Is offering " + need;
            needOfferTextView.setText(needOfferString);
        }



    }
}
