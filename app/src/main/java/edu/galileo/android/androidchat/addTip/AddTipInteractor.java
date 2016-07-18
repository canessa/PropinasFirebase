package edu.galileo.android.androidchat.addTip;

import java.util.ArrayList;

import edu.galileo.android.androidchat.addTip.entities.Tip;

/**
 * Created by cmiguel on 18/07/2016.
 */
public interface AddTipInteractor {
    void addTip(Tip tip);
    void getTips();
}
