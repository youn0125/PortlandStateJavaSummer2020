package edu.pdx.cs410J.miyon;

import org.junit.Test;

import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Unit tests for the {@link PhoneCall} class.
 *
 * You'll need to update these unit tests as you build out you program.
 */
public class PhoneCallTest {
  private PhoneCall createPhoneCall() {
    String caller = "234-567-8901";
    String callee = "123-456-7890";
    String startDate = "01/23/2020";
    String startTime = "09:12";
    String endDate = "01/23/2020";
    String endTime = "10:12";
    return new PhoneCall(caller, callee, startDate, startTime, endDate, endTime);
  }

  @Test
  public void getStartTimeStringNeedsToBeImplemented() {
    PhoneCall call = createPhoneCall();
    String start = "01/23/2020 09:12";
    assertThat(call.getStartTimeString(), equalTo(start));
  }

  @Test
  public void getEndTimeStringNeedsToBeImplemented() {
    PhoneCall call = createPhoneCall();
    String end = "01/23/2020 10:12";
    assertThat(call.getEndTimeString(), equalTo(end));
  }

  @Test
  public void initiallyAllPhoneCallsHaveTheSameCallee() {
    PhoneCall call = createPhoneCall();
    String callee = "123-456-7890";
    assertThat(call.getCallee(), equalTo(callee));
  }

  @Test
  public void customerPhoneCallToCallee() {
    PhoneCall call = createPhoneCall();
    String caller = "234-567-8901";
    assertThat(call.getCaller(), equalTo(caller));
  }

  @Test
  public void forProject1ItIsOkayIfGetStartTimeReturnsNull() {
    PhoneCall call = new PhoneCall(null,null, null, null, null, null);
    assertThat(call.getStartTime(), is(nullValue()));
  }

}
