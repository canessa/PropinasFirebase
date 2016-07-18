package edu.galileo.android.androidchat.addTip;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import edu.galileo.android.androidchat.addTip.entities.Tip;
import edu.galileo.android.androidchat.addTip.events.AddTipEvent;
import edu.galileo.android.androidchat.domain.FirebaseHelper;
import edu.galileo.android.androidchat.fragments.TipHistoryListFragmentListener;
import edu.galileo.android.androidchat.lib.EventBus;
import edu.galileo.android.androidchat.lib.GreenRobotEventBus;
import edu.galileo.android.androidchat.models.TipRecord;
import edu.galileo.android.androidchat.tipcalc.entities.User;
import edu.galileo.android.androidchat.tipcalc.ui.TipActivity;

/**
 * Created by cmiguel on 18/07/2016.
 */
public class AddTipRepositoryImpl implements AddTipRepository {
    @Override
    public void addTip(final Tip tip) {

        FirebaseHelper helper = FirebaseHelper.getInstance();
        final DatabaseReference userReference = helper.getUserReference( FirebaseHelper.getInstance().getAuthUserEmail());

        userReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                FirebaseHelper helper = FirebaseHelper.getInstance();
                AddTipEvent event = new AddTipEvent();
                if (helper.getAuthUserEmail() != null) {

                    String currentUserEmailKey = helper.getAuthUserEmail();
                    currentUserEmailKey = currentUserEmailKey.replace(".","_");

                    DatabaseReference userTipsReference = helper.getTipsReference(currentUserEmailKey);
                    userTipsReference.child("tip_"+tip.getDate().toString()).setValue(tip);
                } else {
                    event.setError(true);
                }
                EventBus eventBus = GreenRobotEventBus.getInstance();
                eventBus.post(event);
            }

            @Override
            public void onCancelled(DatabaseError firebaseError) {}
        });
    }

    @Override
    public void getTips() {

        FirebaseHelper helper = FirebaseHelper.getInstance();
        String currentUserEmailKey = helper.getAuthUserEmail();
        currentUserEmailKey = currentUserEmailKey.replace(".","_");
        final DatabaseReference userReference = helper.getTipsReference(currentUserEmailKey);

        userReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                TipActivity.fragmentListener.clearList();

                for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                    Tip tip = postSnapshot.getValue(Tip.class);

                    TipRecord tipRecord   =   new TipRecord();
                    tipRecord.setBill(tip.getTotal());
                    tipRecord.setTipPercentage(tip.getPercentage());
                    tipRecord.setTimestamp(tip.getDate());

                    TipActivity.fragmentListener.addToList(tipRecord);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }

        });

    }
}
