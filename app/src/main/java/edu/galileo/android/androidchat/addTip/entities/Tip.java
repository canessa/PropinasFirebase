package edu.galileo.android.androidchat.addTip.entities;

import java.util.Date;

/**
 * Created by cmiguel on 18/07/2016.
 */
public class Tip {
    private double total;
    private int percentage;
    private double tip;
    private Date date;

    public Tip(double total, int percentage, double tip, Date date) {
        this.total = total;
        this.percentage = percentage;
        this.tip = tip;
        this.date = date;
    }

    public Tip() {
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public int getPercentage() {
        return percentage;
    }

    public void setPercentage(int percentage) {
        this.percentage = percentage;
    }

    public double getTip() {
        return tip;
    }

    public void setTip(double tip) {
        this.tip = tip;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Tip{" +
                "total=" + total +
                ", percentage=" + percentage +
                ", tip=" + tip +
                ", date=" + date +
                '}';
    }
}
