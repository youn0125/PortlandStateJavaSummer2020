package edu.pdx.cs410J.miyon;

import edu.pdx.cs410J.AbstractPhoneBill;

import java.util.Collection;
import java.util.Vector;

public class PhoneBill extends AbstractPhoneBill<PhoneCall> {
    private final String customer;
    private Vector<PhoneCall> phoneCalls;

    public PhoneBill(String customer) {
        super();
        this.customer = customer;
        this.phoneCalls = new Vector<>();
    }

    @Override
    public String getCustomer() {
        return this.customer;
    }

    @Override
    public void addPhoneCall(PhoneCall phoneCall) {
        phoneCalls.add(phoneCall);
    }

    @Override
    public Collection<PhoneCall> getPhoneCalls() {
        return phoneCalls;
    }
}
