package edu.galileo.android.androidchat.addTip;

import java.util.ArrayList;

import edu.galileo.android.androidchat.addTip.entities.Tip;

/**
 * Created by cmiguel on 18/07/2016.
 */
public class AddTipInteractorImpl implements AddTipInteractor {
    @Override
    public void addTip(Tip tip) {
        AddTipRepositoryImpl repository = new AddTipRepositoryImpl();
        repository.addTip(tip);
    }

    @Override
    public void getTips() {
        AddTipRepositoryImpl repository = new AddTipRepositoryImpl();
        repository.getTips();
    }
}
