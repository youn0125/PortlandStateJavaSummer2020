package edu.pdx.cs410J.miyon;

import edu.pdx.cs410J.AbstractPhoneBill;

import java.util.Collection;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.Vector;

/**
 * This class is represents a <code>PhoneBill</code>.
 */
public class PhoneBill extends AbstractPhoneBill<PhoneCall> {
    private String customer;
    private SortedSet<PhoneCall> phoneCalls;

    /**
     * Creates a new <code>PhoneBill</code>
     *
     */
    public PhoneBill() {
        super();
        this.customer = "";
        this.phoneCalls = new TreeSet<>(new PhoneCallComparator());
    }

    /**
     * Creates a new <code>PhoneBill</code>
     *
     * @param customer
     *        Person whose phone bill we’re modeling
     */
    public PhoneBill(String customer) {
        super();
        this.customer = customer;
        this.phoneCalls = new TreeSet<>(new PhoneCallComparator());
    }

    /**
     * @return a <code>String</code> of customer
     */
    @Override
    public String getCustomer() {
        return this.customer;
    }

    /**
     * Adds a <code>PhoneCall</code> to <code>Vector<PhoneCall></code> of phoneCall
     */
    @Override
    public void addPhoneCall(PhoneCall phoneCall) {
        phoneCalls.add(phoneCall);
    }

    /**
     * @return a <code>Collection<PhoneCall></code> of phoneCalls
     */
    @Override
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
}
