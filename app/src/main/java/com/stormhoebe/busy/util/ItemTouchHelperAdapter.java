package com.stormhoebe.busy.util;

/**
 * Created by Guest on 6/22/17.
 */

public interface ItemTouchHelperAdapter {
    boolean onItemMove(int fromPosition, int toPosition);
    void onItemDismiss(int position);
}