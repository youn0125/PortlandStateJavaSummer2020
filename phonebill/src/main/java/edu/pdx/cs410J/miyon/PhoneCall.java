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
    if (!checkPNumberPatten(caller)) {
      throw new IllegalArgumentException("Caller number format is not valid");
    }

    if (!checkPNumberPatten(callee)) {
      throw new IllegalArgumentException("Callee number format is not valid");
    }

    if (!checkDatePattern(startDate)) {
      throw new IllegalArgumentException("Start date format is not valid");
    }

    if (!checkTimePattern(startTime)) {
      throw new IllegalArgumentException("Start time format is not valid");
    }

    if (!checkDatePattern(endDate)) {
      throw new IllegalArgumentException("Start date format is not valid");
    }

    if (!checkTimePattern(endTime)) {
      throw new IllegalArgumentException("Start time format is not valid");
    }
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

  private static boolean checkPNumberPatten(String pNumber) {
    String pattern = "(?:\\d{3}-){2}\\d{4}";
    if (pNumber.matches(pattern)) {
      return true;
    } else {
      return false;
    }
  }

  private static boolean checkDatePattern(String date) {
    String pattern = "^\\d{1,2}\\/\\d{1,2}\\/\\d{4}";
    if (date.matches(pattern)) {
      return true;
    } else {
      return false;
    }
  }

  private static boolean checkTimePattern(String date) {
    String pattern = "^\\d{1,2}:\\d{1,2}";
    if (date.matches(pattern)) {
      return true;
    } else {
      return false;
    }
  }
}
