package edu.pdx.cs410J.miyon;

import edu.pdx.cs410J.ParserException;

import java.io.*;
import java.net.HttpURLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

/**
 * The option class for the CS410J Phone Bill-web Project
 */
class Option {
    private String hostName;
    private String port;
    private boolean searchOption;
    private boolean printOption;
    private int optionNum;

    /**
     * Creates a new <code>Option</code>
     * @param hostName
     *        host name from the command line option part
     * @param port
     *        port from the command line option part
     * @param searchOption
     *        search option(T/F)
     * @param printOption
     *        print option(T/F)
     * @param optionNum
     *        number of option
     */
    Option (String hostName, String port, boolean searchOption, boolean printOption, int optionNum) {
        this.hostName = hostName;
        this.port = port;
        this.searchOption = searchOption;
        this.printOption = printOption;
        this.optionNum = optionNum;
    }

    /**
     * @return a <code>String</code> of host name
     */
    String getHostName() {
        return this.hostName;
    }

    /**
     * @return a <code>String</code> of port
     */
    String getPort() {
        return this.port;
    }
    /**
     * @return a <code>boolean</code> of search option
     */
    boolean getSearchOption() {
        return this.searchOption;
    }
    /**
     * @return a <code>boolean</code> of print option
     */
    boolean getPrintOption() {
        return this.printOption;
    }
    /**
     * @return a <code>int</code> of number of options
     */
    int getOptionNum() {
        return this.optionNum;
    }
    /**
     * @param s
     *       String value of host name
     */
    void setHostName(String s) {
        this.hostName = s;
    }
    /**
     * @param s
     *       String value of port
     */
    void setPort(String s) {
        this.port = s;
    }
    /**
     * @param b
     *       T/F value of search option
     */
    void setSearchOption(boolean b) {
        this.searchOption = b;
    }
    /**
     * @param b
     *       T/F value of print option
     */
    void setPrintOption(boolean b) {
        this.printOption = b;
    }

    /**
     * @param num
     *        number of options
     */
    void setOptionNum(int num) {
        this.optionNum = num;
    }

}

/**
 * The main class that parses the command line and communicates with the
 * Phone Bill server using REST.
 */
public class Project4 {

    public static final String MISSING_ARGS = "Missing command line arguments";
    /**
     * Main program parses the command line, make a request to create or get a
     * <code>PhoneBill</code> and <code>PhoneCall</code> by REST API.
     * There is a search option to make a request to get <code>PhoneCall</code> within the condition.
     * In addition there are a print and a readme option.
     */
    public static void main(String... args) {

        // Check options from command line
        Option argOption = new Option(null, null, false, false, 0);

        for ( int i = 0; i < 5; i++) {
            if ( args.length > argOption.getOptionNum() && args[argOption.getOptionNum()].startsWith("-") ) {
                processOption(argOption, args);
            }
        }

        String hostName = argOption.getHostName();
        String portString = argOption.getPort();

        if (hostName == null) {
            usage( MISSING_ARGS);
        } else if ( portString == null) {
            usage( "Missing port" );
        }

        int port;
        try {
            port = Integer.parseInt( portString );
        } catch (NumberFormatException ex) {
            usage("Port \"" + portString + "\" must be an integer");
            return;
        }

        String customer = null;
        String caller = null;
        String callee = null;
        String startDate = null;
        String startTime = null;
        String startAMPM = null;
        String endDate = null;
        String endTime = null;
        String endAMPM = null;

        //Check arguments from command line
        int argLength = args.length - argOption.getOptionNum();
        //search option
        if (argOption.getSearchOption()) {
            if (argLength == 7) {
                //phone call start index
                int pCallstartIdx = argOption.getOptionNum()+1;

                customer = args[pCallstartIdx - 1];
                startDate = args[pCallstartIdx];
                if ( !checkDatePattern(startDate))
                    usage("Start date format is not valid");
                startTime = args[pCallstartIdx + 1];
                startAMPM = args[pCallstartIdx + 2];
                if (!checkTimePattern(startTime + " " + startAMPM)) {
                    usage("Start time format is not valid");
                }
                endDate = args[pCallstartIdx + 3];
                if (!checkDatePattern(endDate)) {
                    usage("End date format is not valid");
                }
                endTime = args[pCallstartIdx + 4];
                endAMPM = args[pCallstartIdx + 5];
                if (!checkTimePattern(endTime + " " + endAMPM)) {
                    usage("End time format is not valid");
                }

                if (!checkStartEndTime(startDate + " " + startTime + " " +startAMPM, endDate + " " + endTime + " " + endAMPM)) {
                    usage("End time is before its starts time");
                }

                String sDate = startDate + " " + startTime + " " +startAMPM;
                String eDate = endDate + " " + endTime + " " + endAMPM;
                PhoneBillRestClient client = new PhoneBillRestClient(hostName, port);
                try {
                    PhoneBill bill = client.getPhoneBill(customer);

                    PhoneBill searchedBill = new PhoneBill(customer);
                    Date searchSDate = parseDate(sDate);
                    Date searchEDate = parseDate(eDate);
                    Collection<PhoneCall> calls =  bill.getPhoneCalls();

                    for (PhoneCall call: calls) {
                        if ( searchSDate.getTime() < call.getStartTime().getTime() && call.getStartTime().getTime() < searchEDate.getTime()) {
                            searchedBill.addPhoneCall(call);
                        }
                    }

                    if(searchedBill.getPhoneCalls().size() == 0)
                        System.out.println("There is no phone call between those two times");
                    bill = searchedBill;

                    PrintWriter pw = new PrintWriter(System.out, true);
                    PrettyPrinter pretty = new PrettyPrinter(pw);
                    pretty.dump(bill);
                } catch (ParserException pe) {
                    error("While parsing phone bill: " + pe);
                    return;
                } catch (IOException ioe) {
                    error("While contacting server: " + ioe);
                    return;
                } catch (PhoneBillRestClient.PhoneBillRestException ex) {
                    switch (ex.getHttpStatusCode()) {
                        case HttpURLConnection.HTTP_NOT_FOUND:
                            error("No phone bill for customer " + customer);
                            return;
                        default:
                    }
                }

            } else {
                usage("Missing searching arguments");
            }
        } //add new phone call
        else {
            //if the command line arguments has all of the phonebill and phone call arguments
            if ( argLength == 9) {
                //phone call start index
                int pCallstartIdx = argOption.getOptionNum()+1;

                customer = args[pCallstartIdx - 1];
                caller = args[pCallstartIdx];
                if ( !checkPNumberPatten(caller))
                    usage("Caller number format is not valid");
                callee = args[pCallstartIdx + 1];
                if ( !checkPNumberPatten(callee))
                    usage("Callee number format is not valid");
                startDate = args[pCallstartIdx + 2];
                if ( !checkDatePattern(startDate))
                    usage("Start date format is not valid");
                startTime = args[pCallstartIdx + 3];
                startAMPM = args[pCallstartIdx + 4];
                if (!checkTimePattern(startTime + " " + startAMPM)) {
                    usage("Start time format is not valid");
                }
                endDate = args[pCallstartIdx + 5];
                if (!checkDatePattern(endDate)) {
                    usage("End date format is not valid");
                }
                endTime = args[pCallstartIdx + 6];
                endAMPM = args[pCallstartIdx + 7];
                if (!checkTimePattern(endTime + " " + endAMPM)) {
                    usage("End time format is not valid");
                }

                if (!checkStartEndTime(startDate + " " + startTime + " " +startAMPM, endDate + " " + endTime + " " + endAMPM)) {
                    usage("End time is before its starts time");
                }

                String sDate = startDate + " " + startTime + " " +startAMPM;
                String eDate = endDate + " " + endTime + " " + endAMPM;
                PhoneBillRestClient client = new PhoneBillRestClient(hostName, port);

                try {
                    client.addPhoneCall(customer, caller, callee, sDate, eDate);
                    //-print option
                    if (argOption.getPrintOption()) {
                        System.out.println(new PhoneCall(caller, callee, sDate, eDate));
                    }
                } catch ( IOException ex ) {
                    error("While contacting server: " + ex);
                    return;
                }

            } else if (argLength == 0) {
                usage("Missing command line arguments");
            } else if (argLength == 1) {
                customer = args[argOption.getOptionNum()];
                PhoneBillRestClient client = new PhoneBillRestClient(hostName, port);
                try {
                    PhoneBill bill = client.getPhoneBill(customer);

                    PrintWriter pw = new PrintWriter(System.out, true);
                    PrettyPrinter pretty = new PrettyPrinter(pw);
                    pretty.dump(bill);
                } catch (ParserException pe) {
                    error("While parsing phone bill: " + pe);
                    return;
                } catch (IOException ioe) {
                    error("While contacting server: " + ioe);
                    return;
                } catch (PhoneBillRestClient.PhoneBillRestException ex) {
                    switch (ex.getHttpStatusCode()) {
                        case HttpURLConnection.HTTP_NOT_FOUND:
                            error("No phone bill for customer " + customer);
                            return;
                        default:

                    }
                }

            } else if (argLength == 2) {
                usage("Missing callee");
            } else if (argLength == 3) {
                usage("Missing start date");
            } else if (argLength == 4) {
                usage("Missing start time");
            } else if (argLength == 5) {
                usage("Missing start time am/pm");
            } else if (argLength == 6) {
                usage("Missing end date");
            }  else if (argLength == 7) {
                usage("Missing end time");
            } else if (argLength == 8) {
                usage("Missing end time am/pm");
            }  else if (argLength > 9) {
                usage("There are extraneous arguments");
            }
        }

        System.exit(0);
    }
    /**
     * Prints error message and exits
     * @param message An error message to print
     */
    private static void error( String message )
    {
        PrintStream err = System.err;
        err.println("** " + message);

        System.exit(1);
    }

    /**
     * Prints usage information for this program and exits
     * @param message An error message to print
     */
    private static void usage( String message )
    {
        PrintStream err = System.err;
        err.println("** " + message);
        err.println();
        err.println("Usage: java edu.pdx.cs410J.miyon.Project4 [options] <args>");
        err.println(" args are (in this order):");
        err.println("  customer: Person whose phone bill we’re modeling");
        err.println("  callerNumber: Phone number of caller");
        err.println("  calleeNumber: Phone number of person who was called");
        err.println("  start: Date and time call began (12-hour time). These are three separate arguments.");
        err.println("  end: Date and time call ended (12-hour time). These are three separate arguments.");
        err.println(" options are (options may appear in any order):");
        err.println("  -host hostname: Host computer on which the server runs");
        err.println("  -port port: Port on which the server is listening");
        err.println("  -search: PHone calls should be searched for");
        err.println("  -print: Prints a description of the new phone call");
        err.println("  -README: Prints a README for this project and exits");
        err.println("  Date and time should be in the format: mm/dd/yyyy hh:mm am/pm (12-hour time)");
        err.println();
        err.println("This simple program posts phone bill with phone calls");
        err.println("to the server.");
        err.println("If no phone call is specified, then the existing phone bill");
        err.println("is printed.");
        err.println("If no word is specified, all dictionary entries are printed");
        err.println();

        System.exit(1);
    }

    /**
     * Process option from the command line
     * @param op
     *        option object
     * @param args
     *        arguments from the command line
     */
    private static void processOption(Option op, String[] args) {

        boolean searchOption = op.getSearchOption();
        boolean printOption = op.getPrintOption();
        int optionNum = op.getOptionNum();
        String hostName = op.getHostName();
        String port = op.getPort();

        if ( args.length > optionNum && args[optionNum].startsWith("-") ) {

            if ( args[optionNum].equals("-README")) {
                try {
                    printReadmeAndExit();
                } catch (IOException ie) {
                    System.out.println("IOException");
                }
            }
            else if ( args[optionNum].equals("-print")){
                printOption = true;
            }
            else if ( args[optionNum].equals("-search")){
                searchOption = true;
            }
            else if ( args[optionNum].equals("-host")){
                optionNum++;
                hostName = args[optionNum];
            }
            else if ( args[optionNum].equals("-port")){
                optionNum++;
                port = args[optionNum];
            }
            else {
                usage("Invalid option.");
            }
            optionNum++;

            op.setHostName(hostName);
            op.setPort(port);
            op.setSearchOption(searchOption);
            op.setPrintOption(printOption);
            op.setOptionNum(optionNum);

        }
    }

    /**
     * Prints README for this project and exit the program
     */
    private static void printReadmeAndExit() throws IOException {
        InputStream readme = Project4.class.getResourceAsStream("README.txt");
        BufferedReader reader = new BufferedReader(new InputStreamReader(readme));
        String AllLine = reader.lines().collect(Collectors.joining(System.lineSeparator()));

        System.out.println(AllLine);
        System.exit(0);
    }
    /**
     * @return a <code>Date</code> of MM/dd/yy h:mm a format of date and time
     */
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
    public static boolean checkPNumberPatten(String pNumber) {
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
    public static boolean checkDatePattern(String date) {
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
    public static boolean checkTimePattern(String date) {

        String pattern = "(1[012]|0?[1-9]):[0-5][0-9](\\s)?(?i)(am|pm)";
        if (date.matches(pattern)) {
            return true;
        } else {
            return false;
        }
    }
    /**
     * @return a <code>boolean</code> of validity of start and end time(endTime-startTime >= 0).
     */
    public static boolean checkStartEndTime(String sTime, String eTime) {
        Date sDate = parseDate(sTime);
        Date eDate = parseDate(eTime);
        if (eDate.getTime() - sDate.getTime() < 0)
            return false;
        return true;
    }
}