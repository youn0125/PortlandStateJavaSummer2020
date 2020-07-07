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
        printReadmeAndExit();
      }
      else if ( args[0].equals("-print")){
        print = true;
      }
    }
    if ( args.length > 1 && args[1].startsWith("-") ) {
      optionNum++;
      if ( args[0].equals("-README")) {
        printReadmeAndExit();
      }
      else if ( args[0].equals("-print")) {
        print = true;
      }
    }

    int argLength = args.length - optionNum;
    if ( argLength == 7) {
      int pCallstartIdx = optionNum+1;

      PhoneBill bill = new PhoneBill(args[pCallstartIdx-1]);
      PhoneCall call = new PhoneCall(args[pCallstartIdx], args[pCallstartIdx+1],
              args[pCallstartIdx+2], args[pCallstartIdx+3], args[pCallstartIdx+4], args[pCallstartIdx+5]);  // Refer to one of Dave's classes so that we can be sure it is on the classpath
      bill.addPhoneCall(call);
      if (print) {
        System.out.println(call);
      }
      System.out.println("Phone call is added to Phone bill");

    } else if (argLength == 0) {
      printErrorMessageAndExit("Missing command line arguments");
    } else if (argLength == 1) {
      printErrorMessageAndExit("Missing caller");
    } else if (argLength == 2) {
      printErrorMessageAndExit("Missing callee");
    } else if (argLength == 3) {
      printErrorMessageAndExit("Missing start date");
    }  else if (argLength == 4) {
      printErrorMessageAndExit("Missing start time");
    }  else if (argLength == 5) {
      printErrorMessageAndExit("Missing end date");
    }  else if (argLength == 6) {
      printErrorMessageAndExit("Missing end time");
    }  else if (argLength > 7) {
      printErrorMessageAndExit("There are extraneous arguments");
    }
  }

  private static void printErrorMessageAndExit(String message) {
    System.err.println(message);
    System.exit(1);
  }

  private static void printReadmeAndExit() {
    String readme = "This README is written by Mi Yon Kim and referred from Project1 AppClasses document.\n" +
            "Usage: java edu.pdx.cs410J.miyon.Project1 [options] <args>\n" +
            "args are (in this order):\n" +
            "   customer: Person whose phone bill we’re modeling\n" +
            "   callerNumber: Phone number of caller\n" +
            "   calleeNumber: Phone number of person who was called\n" +
            "   start: Date and time call began (24-hour time). These are two separate arguments.\n" +
            "   end: Date and time call ended (24-hour time). These are two separate arguments.\n" +
            "options are (options may appear in any order):\n" +
            "   -print Prints a description of the new phone call\n" +
            "   -README Prints a README for this project and exits\n" +
            "Date and time should be in the format: mm/dd/yyyy hh:mm";
    System.out.println(readme);
    System.exit(0);
  }
}