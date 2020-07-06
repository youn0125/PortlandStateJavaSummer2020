package edu.pdx.cs410J.miyon;

import java.util.regex.*;

/**
 * The main class for the CS410J Phone Bill Project
 */
public class Project1 {

  public static void main(String[] args) {
    if ( args.length > 4) {
      boolean chkPNumber = checkPNumberPatten(args[1]);
      chkPNumber = checkPNumberPatten(args[2]);
      boolean chkDate = checkDatePattern(args[3]);
      chkDate = checkDatePattern(args[4]);

      if (chkPNumber && chkDate) {
        PhoneBill bill = new PhoneBill(args[0]);
        PhoneCall call = new PhoneCall(args[1], args[2], args[3], args[4]);  // Refer to one of Dave's classes so that we can be sure it is on the classpath
        bill.addPhoneCall(call);
        System.out.println("Phone call is added to Phone bill");
      } else {
        System.err.println("Check arguments' pattern");
        for (String arg : args) {
          System.out.println(arg);
        }
        System.exit(1);
      }

    }
    else {
      System.err.println("Missing command line arguments");
      for (String arg : args) {
        System.out.println(arg);
      }
      System.exit(1);
    }

  }

  public static boolean checkPNumberPatten(String pNumber) {
    String pattern = "(?:\\d{3}-){2}\\d{4}";
    if (pNumber.matches(pattern)) {
      return true;
    } else {
      return false;
    }
  }
  public static boolean checkDatePattern(String date) {
    String pattern = "^\\d{1,2}\\/\\d{1,2}\\/\\d{4} \\d{1,2}:\\d{1,2}";
    if (date.matches(pattern)) {
      return true;
    } else {
      return false;
    }
  }
}