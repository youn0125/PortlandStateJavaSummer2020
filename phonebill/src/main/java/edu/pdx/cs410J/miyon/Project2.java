package edu.pdx.cs410J.miyon;

import edu.pdx.cs410J.ParserException;
import org.w3c.dom.Text;

import java.io.*;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * The option class for the CS410J Phone Bill Project
 */

class Option {
    private boolean prPrintOption;
    private String pPrintTo;
    private boolean printOption;
    private boolean fileOption;
    private int optionNum;
    private String fileName;

    /**
     * Creates a new <code>Option</code>
     *
     * @param printOption
     *        print option(T/F)
     * @param fileOption
     *        file option(T/F)
     * @param optionNum
     *        number of option
     * @param fileName
     *        file name from the command line option part
     *
     */
    Option (boolean prPrintOption, String pPrintTo, boolean printOption, boolean fileOption, int optionNum, String fileName) {
        this.prPrintOption = prPrintOption;
        this.pPrintTo = pPrintTo;
        this.printOption = printOption;
        this.fileOption = fileOption;
        this.optionNum = optionNum;
        this.fileName = fileName;
    }
    /**
     * @return a <code>boolean</code> of pretty print option
     */
    boolean getPrPrintOption() {
        return this.prPrintOption;
    }
    /**
     * @return a <code>String</code> of pretty print to
     */
    String getPPrintTo() {
        return this.pPrintTo;
    }
    /**
     * @return a <code>boolean</code> of print option
     */
    boolean getPrintOption() {
        return this.printOption;
    }
    /**
     * @return a <code>boolean</code> of file option
     */
    boolean getFileOption() {
        return this.fileOption;
    }
    /**
     * @return a <code>int</code> of number of options
     */
    int getOptionNum() {
        return this.optionNum;
    }
    /**
     * @return a <code>String</code> of file name
     */
    String getFileName() {
        return this.fileName;
    }

    /**
     * @param b
     *       T/F value of pretty print option
     */
    void setPrPrintOption(boolean b) {
        this.prPrintOption = b;
    }
    /**
     * @param s
     *       Where to pretty print
     */
    void setPPrintTo(String s) {
        this.pPrintTo = s;
    }
    /**
     * @param b
     *       T/F value of print option
     */
    void setPrintOption(boolean b) {
        this.printOption = b;
    }

    /**
     * @param b
     *       T/F value of file option
     */
    void setFileOption(boolean b) {
        this.fileOption = b;
    }
    /**
     * @param num
     *        number of options
     */
    void setOptionNum(int num) {
        this.optionNum = num;
    }
    /**
     * @param fName
     *        file name
     */
    void setFileName(String fName) {
        this.fileName = fName;
    }
}

/**
 * The main class for the CS410J Phone Bill Project
 */
public class Project2 {

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
        Option argOption = new Option(false, null, false, false, 0, null);
        PhoneBill bill = new PhoneBill("");

        for ( int i = 0; i < 4; i++) {
            if ( args.length > argOption.getOptionNum() && args[argOption.getOptionNum()].startsWith("-") ) {
                bill = processOption(argOption, args, bill);
            }
        }

        //Check arguments from command line
        int argLength = args.length - argOption.getOptionNum();
        //if the command line arguments has all of the phonebill and phone call arguments
        if ( argLength == 9) {
            //phone call start index
            int pCallstartIdx = argOption.getOptionNum()+1;
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
//            Collection<PhoneCall> calls = bill.getPhoneCalls();
//            if ( calls != null) {
//                for ( PhoneCall call2 : calls) {
//                    System.out.println(call2);
//                }
//            }
            //-pretty option
            if (argOption.getPrPrintOption()) {
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

        boolean prPrintOption = op.getPrPrintOption();
        boolean printOption = op.getPrintOption();
        boolean fileOption = op.getFileOption();
        int optionNum = op.getOptionNum();
        String fileName = op.getFileName();
        String pPrintTo = op.getPPrintTo();

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
            else if ( args[optionNum].equals("-textFile")){
                optionNum++;
                //if there is no filename
                try {
                    fileName = args[optionNum];
                } catch (ArrayIndexOutOfBoundsException ex) {
                    printErrorMessageAndExit("Invalid file option: No filename and missing arguments");
                }

                if (!fileOption) {
                    bill = parseTextFile(fileName);
                }
                fileOption = true;
            }
            else if ( args[optionNum].equals("-pretty")){
                prPrintOption = true;
                optionNum++;
                //if there is no print to
                try {
                    pPrintTo = args[optionNum];
                } catch (ArrayIndexOutOfBoundsException ex) {
                    printErrorMessageAndExit("Invalid pretty print option: No printTo and missing arguments");
                }
            }
            else {
                printErrorMessageAndExit("Invalid option.");
            }
            optionNum++;

            op.setPrPrintOption(prPrintOption);
            op.setPPrintTo(pPrintTo);
            op.setPrintOption(printOption);
            op.setFileOption(fileOption);
            op.setOptionNum(optionNum);
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
        InputStream readme = Project2.class.getResourceAsStream("README.txt");
        BufferedReader reader = new BufferedReader(new InputStreamReader(readme));
        String AllLine = reader.lines().collect(Collectors.joining(System.lineSeparator()));

        System.out.println(AllLine);
        System.exit(0);
    }
}