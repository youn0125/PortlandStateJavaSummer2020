package edu.pdx.cs410J.miyon;

import edu.pdx.cs410J.AbstractPhoneCall;

import static edu.pdx.cs410J.miyon.Project1.printErrorMessageAndExit;

/**
 * This class is represents a <code>PhoneCall</code>.
 */
public class PhoneCall extends AbstractPhoneCall {
  private final String caller;
  private final String callee;
  private final String startDate;
  private final String startTime;
  private final String endDate;
  private final String endTime;

  /**
   * Creates a new <code>PhoneCall</code>
   *
   * @param caller
   *        Phone number of caller
   * @param callee
   *        Phone number of person who was called
   * @param startDate
   *        Date call began (24-hour time)
   * @param startTime
   *        Time call began (24-hour time)
   * @param endDate
   *        Date call ended (24-hour time)
   * @param endTime
   *        Time call ended (24-hour time)
   */
  public PhoneCall(String caller, String callee, String startDate, String startTime, String endDate, String endTime) {
    super();
    if (!checkPNumberPatten(caller)) {
      printErrorMessageAndExit("Caller number format is not valid");
    }

    if (!checkPNumberPatten(callee)) {
      printErrorMessageAndExit("Callee number format is not valid");
    }

    if (!checkDatePattern(startDate)) {
      printErrorMessageAndExit("Start date format is not valid");
    }

    if (!checkTimePattern(startTime)) {
      printErrorMessageAndExit("Start time format is not valid");
    }

    if (!checkDatePattern(endDate)) {
      printErrorMessageAndExit("End date format is not valid");
    }

    if (!checkTimePattern(endTime)) {
      printErrorMessageAndExit("End time format is not valid");
    }
    this.caller = caller;
    this.callee = callee;
    this.startDate = startDate;
    this.startTime = startTime;
    this.endDate = endDate;
    this.endTime = endTime;
  }

  /**
   * @return a <code>String</code> of caller
   */
  @Override
  public String getCaller() {
    return this.caller;
  }

  /**
   * @return a <code>String</code> of callee
   */
  @Override
  public String getCallee() {
    return this.callee;
  }

  /**
   * @return a <code>String</code> of date and time when the call began
   */
  @Override
  public String getStartTimeString() {
    return this.startDate + " " + this.startTime;
  }

  /**
   * @return a <code>String</code> of date and time when the call ended
   */
  @Override
  public String getEndTimeString() {
    return this.endDate + " " + this.endTime;
  }

  /**
   * @return a <code>boolean</code> of validity of phone number.
   */
  private static boolean checkPNumberPatten(String pNumber) {
    String pattern = "(?:\\d{3}-){2}\\d{4}";
    if (pNumber.matches(pattern)) {
      return true;
    } else {
      return false;
    }
  }

  /**
   * @return a <code>boolean</code> of validity of date.
   */
  private static boolean checkDatePattern(String date) {
    String pattern = "^\\d{1,2}\\/\\d{1,2}\\/\\d{4}";
    if (date.matches(pattern)) {
      return true;
    } else {
      return false;
    }
  }

  /**
   * @return a <code>boolean</code> of validity of time.
   */
  private static boolean checkTimePattern(String date) {
    String pattern = "^\\d{1,2}:\\d{1,2}";
    if (date.matches(pattern)) {
      return true;
    } else {
      return false;
    }
  }
}
