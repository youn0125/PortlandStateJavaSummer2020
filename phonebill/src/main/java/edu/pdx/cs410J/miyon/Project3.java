package edu.pdx.cs410J.miyon;

import edu.pdx.cs410J.ParserException;
import java.io.*;
import java.util.stream.Collectors;


/**
 * The main class for the CS410J Phone Bill Project
 */
public class Project3 {

    /**
     * Main program parses the command line, creates a
     * <code>PhoneBill</code> and <code>PhoneCall</code>, adds the <code>PhoneCall</code> to the <code>PhoneBill</code>,
     * and optionally prints a description of the <code>PhoneCall</code>
     * by invoking its <code>toString</code> method.
     * In addition, there is a file option to read the <code>PhoneBill</code> and write code>PhoneBill</code>
     * to text file after modifying the code>PhoneBill</code>
     */
    public static void main(String[] args) {

        // Check options from command line
        Option argOption = new Option();
        PhoneBill bill = new PhoneBill("");

        for ( int i = 0; i < 4; i++) {
            if ( args.length > argOption.getNOptions() && args[argOption.getNOptions()].startsWith("-") ) {
                bill = processOption(argOption, args, bill);
            }
        }

        //Check arguments from command line
        int argLength = args.length - argOption.getNOptions();
        //if the command line arguments has all of the phonebill and phone call arguments
        if ( argLength == 9) {
            //phone call start index
            int pCallstartIdx = argOption.getNOptions()+1;
            //if the phone bill's customer is null, set customer name from argument
            if (bill!= null && bill.getCustomer().equals(""))
                bill.setCustomer(args[pCallstartIdx - 1]);
            //if the customer name doesn't match with text file's customer name
            if (argOption.getFileOption() && bill != null && !bill.getCustomer().equals(args[pCallstartIdx - 1]))
                printErrorMessageAndExit("The customer name doesn't match with text file's customer name");

            //Create phone call and add to phone bill
            PhoneCall call = new PhoneCall(args[pCallstartIdx], args[pCallstartIdx+1], args[pCallstartIdx+2],
                    args[pCallstartIdx+3], args[pCallstartIdx+4], args[pCallstartIdx+5], args[pCallstartIdx+6],
                    args[pCallstartIdx+7] );  // Refer to one of Dave's classes so that we can be sure it is on the classpath
            bill.addPhoneCall(call);
            System.out.println("Phone call is added to Phone bill");

            //-pretty option
            if (argOption.getPPrintOption()) {
                dumpBillPretty(bill, argOption.getPPrintTo());
            }
            //-print option
            if (argOption.getPrintOption()) {
                System.out.println(call);
            }
            //-textFile option
            if ( argOption.getFileOption()) {
                dumpBill(bill, argOption.getFileName());
            }
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
            printErrorMessageAndExit("Missing start time am/pm");
        }  else if (argLength == 6) {
            printErrorMessageAndExit("Missing end date");
        }  else if (argLength == 7) {
            printErrorMessageAndExit("Missing end time");
        }  else if (argLength == 8) {
            printErrorMessageAndExit("Missing end time am/pm");
        }  else if (argLength > 9) {
            printErrorMessageAndExit("There are extraneous arguments");
        }
    }

    /**
     * Process option from the command line
     *
     * @param op
     *        option object
     * @param args
     *        arguments from the command line
     * @param bill
     *        <code>PhoneBill</code>
     * @return <code>PhoneBill</code>
     */
    private static PhoneBill processOption(Option op, String[] args, PhoneBill bill) {

        boolean pPrintOption = op.getPPrintOption();
        boolean printOption = op.getPrintOption();
        boolean fileOption = op.getFileOption();
        int nOptions = op.getNOptions();
        String fileName = op.getFileName();
        String pPrintTo = op.getPPrintTo();

        if ( args.length > nOptions && args[nOptions].startsWith("-") ) {

            if ( args[nOptions].equals("-README")) {
                try {
                    printReadmeAndExit();
                } catch (IOException ie) {
                    System.out.println("IOException");
                }
            }
            else if ( args[nOptions].equals("-print")){
                printOption = true;
            }
            else if ( args[nOptions].equals("-textFile")){
                nOptions++;
                //if there is no filename
                try {
                    fileName = args[nOptions];
                } catch (ArrayIndexOutOfBoundsException ex) {
                    printErrorMessageAndExit("Invalid file option: No filename and missing arguments");
                }

                if (!fileOption) {
                    bill = parseTextFile(fileName);
                }
                fileOption = true;
            }
            else if ( args[nOptions].equals("-pretty")){
                pPrintOption = true;
                nOptions++;
                //if there is no print to
                try {
                    pPrintTo = args[nOptions];
                } catch (ArrayIndexOutOfBoundsException ex) {
                    printErrorMessageAndExit("Invalid pretty print option: No printTo and missing arguments");
                }
            }
            else {
                printErrorMessageAndExit("Invalid option.");
            }
            nOptions++;

            op.setPPrintOption(pPrintOption);
            op.setPPrintTo(pPrintTo);
            op.setPrintOption(printOption);
            op.setFileOption(fileOption);
            op.setNOptions(nOptions);
            op.setFileName(fileName);
        }
        return bill;
    }

    /**
     * Parse <code>PhoneBill</code> from text file
     *
     * @param fileName
     *        file name to read
     */
    private static PhoneBill parseTextFile(String fileName){
        try {
            File myFile = new File(fileName);
            if (!myFile.exists()) {
                myFile.createNewFile();
            } else {
                TextParser tp = new TextParser(new FileReader(fileName));
                return tp.parse();
            }
        } catch (IOException ioe) {
            System.out.println("IOException");
        } catch (ParserException pe) {
            System.out.println("ParserException");
        }
        return new PhoneBill("");
    }

    /**
     * Dump <code>PhoneBill</code> to text file
     *
     * @param bill
     *        <code>PhoneBill</code> to dump to text file
     * @param fileName
     *        file name to dump
     */
    private static void dumpBill(PhoneBill bill, String fileName) {
        try {
            TextDumper td = new TextDumper(new FileWriter(fileName));
            td.dump(bill);
        } catch (IOException ioe) {
            System.out.println("IOException");
        }
    }
    /**
     * Dump <code>PhoneBill</code> pretty to writer
     *
     * @param bill
     *        <code>PhoneBill</code> to dump
     * @param pPrintTo
     *        where to dump
     */
    private static void dumpBillPretty(PhoneBill bill, String pPrintTo) {
        try {
            PrettyPrinter pPrint = null;
            if ( pPrintTo.equals("-")){
                pPrint = new PrettyPrinter(new PrintWriter(System.out, true));
            } else {
                pPrint = new PrettyPrinter(new FileWriter(pPrintTo));
            }
            pPrint.dump(bill);
        } catch (IOException ioe) {
            System.out.println("IOException");
        }
    }
    /**
     * Prints error message and exit the program
     */
    public static void printErrorMessageAndExit(String message) {
        System.err.println(message);
        System.exit(1);
    }
    /**
     * Prints README for this project and exit the program
     */
    private static void printReadmeAndExit() throws IOException {
        InputStream readme = Project3.class.getResourceAsStream("README.txt");
        BufferedReader reader = new BufferedReader(new InputStreamReader(readme));
        String AllLine = reader.lines().collect(Collectors.joining(System.lineSeparator()));

        System.out.println(AllLine);
        System.exit(0);
    }
}