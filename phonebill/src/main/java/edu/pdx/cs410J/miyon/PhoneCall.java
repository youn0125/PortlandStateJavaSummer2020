package edu.pdx.cs410J.miyon;

import edu.pdx.cs410J.AbstractPhoneCall;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.StringTokenizer;
import java.util.concurrent.TimeUnit;

import static edu.pdx.cs410J.miyon.Project3.printErrorMessageAndExit;

/**
 * This class is represents a <code>PhoneCall</code>.
 */
public class PhoneCall extends AbstractPhoneCall {
  private final String caller;
  private final String callee;
  private final String startDate;
  private final String startTime;
  private final String startTimeAMPM;
  private final String endDate;
  private final String endTime;
  private final String endTimeAMPM;
  /**
   * Creates a new <code>PhoneCall</code>
   *
   * @param caller
   *        Phone number of caller
   * @param callee
   *        Phone number of person who was called
   * @param startDate
   *        Date call began
   * @param startTime
   *        Time call began (12-hour time)
   * @param startTimeAMPM
   *        AM/PM call began
   * @param endDate
   *        Date call ended
   * @param endTime
   *        Time call ended (12-hour time)
   * @param endTimeAMPM
   *        AM/PM call ended
   */
  public PhoneCall(String caller, String callee, String startDate, String startTime, String startTimeAMPM,
                   String endDate, String endTime, String endTimeAMPM) {
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

    if (!checkTimePattern(startTime + " " + startTimeAMPM)) {
      printErrorMessageAndExit("Start time format is not valid");
    }

    if (!checkDatePattern(endDate)) {
      printErrorMessageAndExit("End date format is not valid");
    }

    if (!checkTimePattern(endTime + " " + endTimeAMPM)) {
      printErrorMessageAndExit("End time format is not valid");
    }

    if (!checkStartEndTime(startDate + " " + startTime + " " +startTimeAMPM, endDate + " " + endTime + " " + endTimeAMPM)) {
      printErrorMessageAndExit("End time is before its starts time");
    }
    this.caller = caller;
    this.callee = callee;
    this.startDate = startDate;
    this.startTime = startTime;
    this.startTimeAMPM = startTimeAMPM;
    this.endDate = endDate;
    this.endTime = endTime;
    this.endTimeAMPM = endTimeAMPM;
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
   * @return a <code>String</code> of date and time(12-hour time) with am/pm when the call began
   */
  @Override
  public String getStartTimeString() {
    String startTimeDate = this.startDate + " " + this.startTime + " " + startTimeAMPM;
    Date startDate = parseDate(startTimeDate);

    int f = DateFormat.SHORT;
    DateFormat df = DateFormat.getDateTimeInstance(f, f);

    return df.format(startDate);
  }
  /**
   * @return a <code>String</code> of date and time when the call ended
   */
  @Override
  public String getEndTimeString() {
    String endTimeDate = this.endDate + " " + this.endTime + " " + this.endTimeAMPM;
    Date endDate = parseDate(endTimeDate);

    int f = DateFormat.SHORT;
    DateFormat df = DateFormat.getDateTimeInstance(f, f);

    return df.format(endDate);
  }

  public String getStartTimeStringFromCommandLine() {
    return  this.startDate + " " + this.startTime + " " + startTimeAMPM;
  }

  public String getEndTimeStringFromCommandLine() {
    return  this.endDate + " " + this.endTime + " " + endTimeAMPM;
  }

  public Long getDurationMinute() {
    long diffInMillies = Math.abs(this.getEndTime().getTime() - this.getStartTime().getTime());
    long diff = TimeUnit.MINUTES.convert(diffInMillies, TimeUnit.MILLISECONDS);
    return diff;
  }

  @Override
  public Date getEndTime() {
    Date myDate = parseDate(this.endDate + " " + this.endTime + " " + this.endTimeAMPM);
    return myDate;
  }

  @Override
  public Date getStartTime() {
    Date myDate = parseDate(this.startDate + " " + this.startTime + " " + this.startTimeAMPM);
    return myDate;
  }

  private static Date parseDate(String date){
    try {
      return new SimpleDateFormat("MM/dd/yyyy h:mm a").parse(date);
    } catch (ParseException ex) {
      System.err.println("Bad date format");
      System.exit(1);
      return null;
    }
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

    String pattern = "(1[012]|0?[1-9]):[0-5][0-9](\\s)?(?i)(am|pm)";
    if (date.matches(pattern)) {
      return true;
    } else {
      return false;
    }
  }

  private static boolean checkStartEndTime(String sTime, String eTime) {
    Date sDate = parseDate(sTime);
    Date eDate = parseDate(eTime);
    if (eDate.getTime() - sDate.getTime() < 0)
      return false;
    return true;
  }
}
