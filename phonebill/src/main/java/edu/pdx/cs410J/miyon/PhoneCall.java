package edu.pdx.cs410J.miyon;

import edu.pdx.cs410J.AbstractPhoneCall;

public class PhoneCall extends AbstractPhoneCall {
  private final String caller;
  private final String callee;
  private final String startDate;
  private final String startTime;
  private final String endDate;
  private final String endTime;

  public PhoneCall(String caller, String callee, String startDate, String startTime, String endDate, String endTime) {
    super();
    this.caller = caller;
    this.callee = callee;
    this.startDate = startDate;
    this.startTime = startTime;
    this.endDate = endDate;
    this.endTime = endTime;
  }
  @Override
  public String getCaller() {
    return this.caller;
  }

  @Override
  public String getCallee() {
    return this.callee;
  }

  @Override
  public String getStartTimeString() {
    return this.startDate + " " + this.startTime;
  }

  @Override
  public String getEndTimeString() {
    return this.endDate + " " + this.endTime;
  }
}
