package edu.galileo.android.androidchat.fragments;

import edu.galileo.android.androidchat.models.TipRecord;

/**
 * Created by cmiguel on 18/07/2016.
 */
public interface TipHistoryListFragmentListener {
    void addToList(TipRecord record);
    void clearList();
}
