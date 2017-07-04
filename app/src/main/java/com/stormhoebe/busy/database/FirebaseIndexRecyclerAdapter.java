package com.stormhoebe.busy.database;


import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

public abstract class FirebaseIndexRecyclerAdapter<T, VH extends RecyclerView.ViewHolder>
        extends FirebaseRecyclerAdapter<T, VH> {

    public FirebaseIndexRecyclerAdapter(SnapshotParser<T> parser,
                                        @LayoutRes int modelLayout,
                                        Class<VH> viewHolderClass,
                                        Query keyQuery,
                                        DatabaseReference dataRef) {
        super(new FirebaseIndexArray<>(keyQuery, dataRef, parser), modelLayout, viewHolderClass);
    }

    /**
     * @see #FirebaseIndexRecyclerAdapter(SnapshotParser, int, Class, Query, DatabaseReference)
     */
    public FirebaseIndexRecyclerAdapter(Class<T> modelClass,
                                        @LayoutRes int modelLayout,
                                        Class<VH> viewHolderClass,
                                        Query keyQuery,
                                        DatabaseReference dataRef) {
        this(new ClassSnapshotParser<>(modelClass),
             modelLayout,
             viewHolderClass,
             keyQuery,
             dataRef);
    }
}
