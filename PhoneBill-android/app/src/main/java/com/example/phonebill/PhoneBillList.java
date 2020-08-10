package com.example.phonebill;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class PhoneBillList implements Parcelable {
    private Map<String, PhoneBill> phoneBills;

    public PhoneBillList() {
        phoneBills = new HashMap<>();
    }

    public Map<String, PhoneBill> getPhoneBills() {
        return phoneBills;
    }

    /**
     * Define the kind of object that you gonna parcel,
     * You can use hashCode() here
     */
    @Override
    public int describeContents() {
        return 0;
    }
    /**
     * Actual object serialization happens here, Write object content
     * to parcel one by one, reading should be done according to this write order
     * @param dest parcel
     * @param flags Additional flags about how the object should be written
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeMap(this.phoneBills);
    }

    protected PhoneBillList(Parcel in) {
        phoneBills = new HashMap<String, PhoneBill>();
        in.readMap(phoneBills, PhoneBill.class.getClassLoader());
    }

    /**
     * This field is needed for Android to be able to
     * create new objects, individually or as arrays
     *
     * If you don’t do that, Android framework will through exception
     * Parcelable protocol requires a Parcelable.Creator object called CREATOR
     */
    public static final Parcelable.Creator<PhoneBillList> CREATOR = new Parcelable.Creator<PhoneBillList>() {

        public PhoneBillList createFromParcel(Parcel in) {
            return new PhoneBillList(in);
        }

        public PhoneBillList[] newArray(int size) {
            return new PhoneBillList[size];
        }
    };

    /**
     * @return a <code>PhoneBill</code>
     */
    PhoneBill getPhoneBill(String customer) {
        return this.phoneBills.get(customer);
    }
    /**
     * Adds a <code>PhoneBill</code>
     */
    void addPhoneBill(PhoneBill bill) {
        this.phoneBills.put(bill.getCustomer(), bill);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry bill : phoneBills.entrySet()) {
            sb.append("Key: " + bill.getKey() +"/n");
            Collection<PhoneCall> calls = ((PhoneBill)bill.getValue()).getPhoneCalls();
            for (PhoneCall call: calls) {
                sb.append(call + "\n");
            }
        }
        return sb.toString();
    }
}
