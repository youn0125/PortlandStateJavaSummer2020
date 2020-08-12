package com.example.phonebill;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.Vector;

/**
 * This class is represents a <code>PhoneBill</code>.
 */
public class PhoneBill implements Parcelable {
    private String customer;
    private List<PhoneCall> phoneCalls;

    /**
     * Creates a new <code>PhoneBill</code>
     *
     * @param customer
     *        Person whose phone bill we’re modeling
     */
    public PhoneBill(String customer) {
        this.customer = customer;
        this.phoneCalls = new ArrayList<>();
    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.customer);
        dest.writeTypedList(this.phoneCalls);
    }

    protected PhoneBill(Parcel in) {
        customer = in.readString();
        phoneCalls = in.createTypedArrayList(PhoneCall.CREATOR);
    }

    public static final Creator<PhoneBill> CREATOR = new Creator<PhoneBill>() {
        @Override
        public PhoneBill createFromParcel(Parcel in) {
            return new PhoneBill(in);
        }

        @Override
        public PhoneBill[] newArray(int size) {
            return new PhoneBill[size];
        }
    };
    /**
     * @return a <code>String</code> of customer
     */
    public String getCustomer() {
        return this.customer;
    }

    /**
     * Adds a <code>PhoneCall</code> to <code>Vector<PhoneCall></code> of phoneCall
     */
    public void addPhoneCall(PhoneCall phoneCall) {
        phoneCalls.add(phoneCall);
    }

    /**
     * @return a <code>Collection<PhoneCall></code> of phoneCalls
     */
    public Collection<PhoneCall> getPhoneCalls() {
        return phoneCalls;
    }

    /**
     * @param customer
     *          Customer name for <code>PhoneBill</code>
     */
    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String prettyPrinter() {
        StringBuilder sb = new StringBuilder();
        sb.append("Customer name: " + this.customer +"\n\n");
        Collection<PhoneCall> calls = this.phoneCalls;
        long duration = 0;
        DateFormat df = new SimpleDateFormat("E M d, y G h:mm a z");
        for ( PhoneCall call : calls) {
            sb.append(call.getCaller() + " called to " + call.getCallee() + " at " +
                    df.format(call.getStartTime()) + " and ended at " +
                    df.format(call.getEndTime()) + "." + " The duration of this call is " +
                    call.getDurationMinute() + " minutes.\n\n");
        }
        return sb.toString().trim();
    }
}
