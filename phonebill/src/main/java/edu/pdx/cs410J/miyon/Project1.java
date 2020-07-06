package edu.pdx.cs410J.miyon;

import java.util.regex.*;

/**
 * The main class for the CS410J Phone Bill Project
 */
public class Project1 {

  public static void main(String[] args) {
    boolean print = false;
    int optionNum = 0;
    if ( args.length > 0 && args[0].startsWith("-") ) {
      optionNum++;
      if ( args[0].equals("-README")) {
        //print readme and exit
      }
      else if ( args[0].equals("-print")){
        print = true;
      }
    }
    if ( args.length > 1 && args[1].startsWith("-") ) {
      optionNum++;
      if ( args[0].equals("-README")) {
        //print readme and exit
      }
      else if ( args[0].equals("-print")) {
        print = true;
      }
    }
    if ( args.length - optionNum == 7) {
      int pCallstartIdx = optionNum+1;
      boolean chkPNumber = checkPNumberPatten(args[pCallstartIdx]);
      chkPNumber = checkPNumberPatten(args[pCallstartIdx+1]) && chkPNumber;
      boolean chkDate = checkDatePattern(args[pCallstartIdx+2] + " " + args[pCallstartIdx+3]);
      chkDate = checkDatePattern(args[pCallstartIdx+4] + " " + args[pCallstartIdx+5]) && chkDate;

      if (chkPNumber && chkDate) {
        PhoneBill bill = new PhoneBill(args[pCallstartIdx-1]);
        PhoneCall call = new PhoneCall(args[pCallstartIdx], args[pCallstartIdx+1],
                args[pCallstartIdx+2], args[pCallstartIdx+3], args[pCallstartIdx+4], args[pCallstartIdx+5]);  // Refer to one of Dave's classes so that we can be sure it is on the classpath
        bill.addPhoneCall(call);
        if (print) {
          System.out.println(call);
        }
        System.out.println("Phone call is added to Phone bill");
      } else if ( !chkPNumber){
        System.err.println("Check Phone number pattern");
        for (String arg : args) {
          System.out.println(arg);
        }
        System.exit(1);
      } else {
        System.err.println("Check date pattern");
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

  private static boolean checkPNumberPatten(String pNumber) {
    String pattern = "(?:\\d{3}-){2}\\d{4}";
    if (pNumber.matches(pattern)) {
      return true;
    } else {
      return false;
    }
  }

  private static boolean checkDatePattern(String date) {
    String pattern = "^\\d{1,2}\\/\\d{1,2}\\/\\d{4} \\d{1,2}:\\d{1,2}";
    if (date.matches(pattern)) {
      return true;
    } else {
      return false;
    }
  }
}