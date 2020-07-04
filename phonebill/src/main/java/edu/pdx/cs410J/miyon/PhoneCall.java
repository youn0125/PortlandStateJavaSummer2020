package edu.pdx.cs410J.miyon;

import edu.pdx.cs410J.AbstractPhoneCall;

public class PhoneCall extends AbstractPhoneCall {
  private final String caller;
  private final String callee;
  private final String start;
  private final String end;

  public PhoneCall(String caller, String callee, String start, String end) {
    super();
    this.caller = caller;
    this.callee = callee;
    this.start = start;
    this.end = end;
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
    return this.start;
  }

  @Override
  public String getEndTimeString() {
    return this.end;
  }
}
