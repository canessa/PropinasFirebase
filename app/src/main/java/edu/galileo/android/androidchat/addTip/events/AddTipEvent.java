package edu.galileo.android.androidchat.addTip.events;

/**
 * Created by cmiguel on 18/07/2016.
 */
public class AddTipEvent {
    boolean error = false;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }
}
