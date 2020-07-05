package edu.pdx.cs410J.miyon;

import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Vector;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Unit tests for the {@link PhoneBill} class.
 *
 * You'll need to update these unit tests as you build out you program.
 */
public class PhoneBillTest {

    private PhoneCall createPhoneCall() {
        String callee = "123-456-7890";
        String caller = "234-567-8901";
        String start = "01/23/2020 09:12";
        String end = "01/23/2020 10:12";
        return new PhoneCall(caller, callee, start, end);
    }

    @Test
    public void getCustomerNeedsToBeImplemented() {
        String customer = "Brian Griffin";
        PhoneBill bill = new PhoneBill(customer);
        assertThat(bill.getCustomer(), equalTo(customer));
    }

    @Ignore
    @Test
    public void addPhoneCallNeedsToBeImplemented() {
        String customer = "Brian Griffin";
        PhoneBill bill = new PhoneBill(customer);
        PhoneCall call = createPhoneCall();
        bill.addPhoneCall(call);
        assertThat(bill.getPhoneCalls(), equalTo(customer));
    }

    @Test
    public void getPhoneCallsNeedsToBeImplemented() {
        String customer = "Brian Griffin";
        PhoneBill bill = new PhoneBill(customer);
        PhoneCall call = createPhoneCall();
        Collection<PhoneCall> calls = new Vector<>();
        calls.add(call);
        bill.addPhoneCall(call);
        assertThat(bill.getPhoneCalls(), equalTo(calls));
    }


}
