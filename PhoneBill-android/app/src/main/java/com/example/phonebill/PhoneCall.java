package com.example.phonebill;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * This class is represents a <code>PhoneCall</code>.
 */
public class PhoneCall implements Parcelable {
    private final String caller;
    private final String callee;
    private final String start;
    private final String end;

    /**
     * Creates a new <code>PhoneCall</code>
     *
     * @param caller
     *        Phone number of caller
     * @param callee
     *        Phone number of person who was called
     * @param start
     *        Date call began
     * @param end
     *        Date call ended
     */
    public PhoneCall(String caller, String callee, String start, String end) {
        super();

        this.caller = caller;
        this.callee = callee;
        this.start = start;
        this.end = end;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.caller);
        dest.writeString(this.callee);
        dest.writeString(this.start);
        dest.writeString(this.end);
    }

    protected PhoneCall(Parcel in) {
        this.caller = in.readString();
        this.callee = in.readString();
        this.start = in.readString();
        this.end = in.readString();
    }

    public static final Parcelable.Creator<PhoneCall> CREATOR = new Parcelable.Creator<PhoneCall>() {
        @Override
        public PhoneCall createFromParcel(Parcel source) {
            return new PhoneCall(source);
        }

        @Override
        public PhoneCall[] newArray(int size) {
            return new PhoneCall[size];
        }
    };

    /**
     * @return a <code>String</code> of caller
     */
    public String getCaller() {
        return this.caller;
    }
    /**
     * @return a <code>String</code> of callee
     */
    public String getCallee() {
        return this.callee;
    }
    /**
     * @return a <code>String</code> of MM/dd/yy h:mm a format of date and time when the call began
     */
    public String getStartTimeString() {
        return start;
    }
    /**
     * @return a <code>String</code> of MM/dd/yy h:mm a format of date and time when the call ended
     */
    public String getEndTimeString() {
        return end;
    }
    /**
     * @return a <code>Long</code> of duration of each phone call in minutes
     */
    public Long getDurationMinute() {
        long diffInMillies = Math.abs(this.getEndTime().getTime() - this.getStartTime().getTime());
        long diff = TimeUnit.MINUTES.convert(diffInMillies, TimeUnit.MILLISECONDS);
        return diff;
    }
    /**
     * @return a <code>Date</code> of date and time when the call began
     */
    public Date getStartTime() {
        Date myDate = parseDate(this.start);
        return myDate;
    }
    /**
     * @return a <code>Date</code> of date and time when the call ended
     */
    public Date getEndTime() {
        Date myDate = parseDate(this.end);
        return myDate;
    }
    /**
     * @return a <code>Date</code> of MM/dd/yy h:mm a format of date and time
     */
    public static Date parseDate(String date){
        try {
            return new SimpleDateFormat("MM/dd/yyyy h:mm a").parse(date);
        } catch (ParseException ex) {
            System.err.println("Bad date format");
            System.exit(1);
            return null;
        }
    }

    public final String toString() {
        return "Phone call from " + this.getCaller() + " to " + this.getCallee() + " from " + this.getStartTimeString() + " to " + this.getEndTimeString();
    }

}
