package edu.pdx.cs410J.miyon;

import edu.pdx.cs410J.ParserException;
import java.io.*;
import java.util.stream.Collectors;

/**
 * The main class for the CS410J Phone Bill Project
 */
public class Project3 {
    static final int MAX_NUM_OF_OPTION = 4;
    static final int VALID_NUM_OF_PHONE_ARGS = 9;
    /**
     * Main program parses the command line, creates a
     * <code>PhoneBill</code> and <code>PhoneCall</code>, adds the <code>PhoneCall</code> to the <code>PhoneBill</code>,
     * and optionally prints a description of the <code>PhoneCall</code>
     * by invoking its <code>toString</code> method.
     * In addition, there is a file option to read the <code>PhoneBill</code> and write code>PhoneBill</code>
     * to text file after modifying the code>PhoneBill</code>
     */
    public static void main(String[] args) {

        // set option related arguments to Option instance
        Option argOption = new Option();
        PhoneBill bill = new PhoneBill("");

        for ( int i = 0; i < MAX_NUM_OF_OPTION; i++) {
            if ( isOptionArgs(args, argOption)) {
                setOptionArg(argOption, args, bill);
            }
        }

        // process arguments
        int phoneArgLength = args.length - argOption.getNOptions();
        if ( phoneArgLength == VALID_NUM_OF_PHONE_ARGS) {
            int pCallStartIdx = argOption.getNOptions() + 1;
            String customer = args[pCallStartIdx - 1];

            if (argOption.getFileOption()  && !bill.getCustomer().equals(customer))
                printErrorMessageAndExit("The bill's customer name doesn't match with text file's customer name");
            if (bill.getCustomer().equals(""))
                bill.setCustomer(customer);

            //Create phone call and add to phone bill
            PhoneCall call = new PhoneCall(args[pCallStartIdx], args[pCallStartIdx+1], args[pCallStartIdx+2],
                    args[pCallStartIdx+3], args[pCallStartIdx+4], args[pCallStartIdx+5], args[pCallStartIdx+6],
                    args[pCallStartIdx+7] );
            bill.addPhoneCall(call);
            System.out.println("Phone call is added to Phone bill");

            //process option arguments (-pretty, -print, -textFile)
            if (argOption.getPPrintOption()) {
                dumpBillPretty(bill, argOption.getPPrintTo());
            }
            if (argOption.getPrintOption()) {
                System.out.println(call);
            }
            if ( argOption.getFileOption()) {
                dumpBill(bill, argOption.getFileName());
            }
        } else if (phoneArgLength < 9) {
            printErrorMessageAndExit("Missing command line arguments");
        } else if (phoneArgLength > 9) {
            printErrorMessageAndExit("There are extraneous arguments");
        }
    }

    private static Boolean isOptionArgs(String[] args, Option argOption) {
        if (args.length > argOption.getNOptions() && args[argOption.getNOptions()].startsWith("-"))
            return true;
        else
            return false;
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
    private static void setOptionArg(Option op, String[] args, PhoneBill bill) {
        int nOptions = op.getNOptions();

        if ( args[nOptions].equals("-README")) {
            try {
                printReadmeAndExit();
            } catch (IOException ie) {
                System.out.println("IOException");
            }
        } else if ( args[nOptions].equals("-print")){
            op.setPrintOption(true);
        } else if ( args[nOptions].equals("-textFile")){
            String fileName = "";
            nOptions++;
            //if there is no file
            try {
                fileName = args[nOptions];
                op.setFileName(fileName);
            } catch (ArrayIndexOutOfBoundsException ex) {
                printErrorMessageAndExit("Invalid file option: No file and missing arguments");
            }

            bill = parseTextFile(fileName);
            op.setFileOption(true);
        } else if ( args[nOptions].equals("-pretty")){
            String pPrintTo = "";
            nOptions++;
            //if there is no print to
            try {
                pPrintTo = args[nOptions];
                op.setPPrintOption(true);
                op.setPPrintTo(pPrintTo);
            } catch (ArrayIndexOutOfBoundsException ex) {
                printErrorMessageAndExit("Invalid pretty print option: No printTo and missing arguments");
            }
        } else {
            printErrorMessageAndExit("Invalid option.");
        }
        nOptions++;
        op.setNOptions(nOptions);
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