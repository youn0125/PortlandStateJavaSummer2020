package edu.pdx.cs410J.miyon;

import edu.pdx.cs410J.InvokeMainTestCase;
import edu.pdx.cs410J.ParserException;
import org.junit.Ignore;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Vector;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Tests the functionality in the {@link Project3} main class.
 */
public class Project3IT extends InvokeMainTestCase {

    /**
     * Invokes the main method of {@link Project3} with the given arguments.
     */
    private MainMethodResult invokeMain(String... args) {
        return invokeMain( Project3.class, args );
    }

    /**
     * Tests that invoking the main method with no arguments issues an error
     */
    @Test
    public void testNoCommandLineArguments() {
        MainMethodResult result = invokeMain();
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString("Missing command line arguments"));
    }

    @Test
    public void testOneCommandLineArguments() {
        MainMethodResult result = invokeMain("Brian Griffin");
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString("Missing command line arguments"));
    }

    @Test
    public void testTwoCommandLineArguments() {
        MainMethodResult result = invokeMain("Brian Griffin", "234-567-8901");
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString("Missing command line arguments"));
    }

    @Test
    public void testThreeCommandLineArguments() {
        MainMethodResult result = invokeMain("Brian Griffin", "234-567-8901", "123-456-7890");
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString("Missing command line arguments"));
    }

    @Test
    public void testFourCommandLineArguments() {
        MainMethodResult result = invokeMain("Brian Griffin",  "234-567-8901", "123-456-7890","01/23/2020");
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString("Missing command line arguments"));
    }

    @Test
    public void testFiveCommandLineArguments() {
        MainMethodResult result = invokeMain("Brian Griffin", "234-567-8901", "123-456-7890","01/23/2020", "09:12");
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString("Missing command line arguments"));
    }

    @Test
    public void testSixCommandLineArguments() {
        MainMethodResult result = invokeMain("Brian Griffin", "234-567-8901", "123-456-7890","01/23/2020",
                "09:12", "am");
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString("Missing command line arguments"));
    }

    @Test
    public void testSevenCommandLineArguments() {
        MainMethodResult result = invokeMain("Brian Griffin", "234-567-8901", "123-456-7890","01/23/2020",
                "09:12", "am", "01/23/2020");
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString("Missing command line arguments"));
    }

    @Test
    public void testEightCommandLineArguments() {
        MainMethodResult result = invokeMain("Brian Griffin", "234-567-8901", "123-456-7890","01/23/2020",
                "09:12", "am", "01/23/2020", "12:12");
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString("Missing command line arguments"));
    }

    @Test
    public void testTenCommandLineArguments() {
        MainMethodResult result = invokeMain("Brian Griffin",  "234-567-8901", "123-456-7890","01/23/2020",
                "09:12", "am", "01/23/2020", "10:12", "am", "cse");
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString("There are extraneous arguments"));
    }
    @Test
    public void testNineCommandLineArgumentsWithNoOption() {
        String customer = "Brian Griffin";
        String caller = "234-567-8901";
        String callee = "123-456-7890";
        String startDate = "01/23/2020";
        String startTime = "09:12";
        String startTimeAMPM = "am";
        String endDate = "01/23/2020";
        String endTime = "10:12";
        String endTimeAMPM = "am";
        MainMethodResult result = invokeMain(customer, caller, callee, startDate, startTime, startTimeAMPM, endDate, endTime, endTimeAMPM);
        PhoneCall call = new PhoneCall(caller, callee, startDate, startTime, startTimeAMPM, endDate, endTime, endTimeAMPM);
        PhoneBill bill = new PhoneBill(customer);
        bill.addPhoneCall(call);
        assertThat (result.getTextWrittenToStandardOut(), containsString("Phone call is added to Phone bill"));
    }

    @Test
    public void testNineCommandLineArgumentsWithPrintOption() {
        String print = "-print";
        String customer = "Brian Griffin";
        String caller = "234-567-8901";
        String callee = "123-456-7890";
        String startDate = "01/23/2020";
        String startTime = "09:12";
        String startTimeAMPM = "am";
        String endDate = "01/23/2020";
        String endTime = "10:12";
        String endTimeAMPM = "am";
        MainMethodResult result = invokeMain(print, customer, caller, callee, startDate, startTime, startTimeAMPM, endDate, endTime, endTimeAMPM);
        PhoneCall call = new PhoneCall(caller, callee, startDate, startTime, startTimeAMPM, endDate, endTime, endTimeAMPM);

        assertThat (result.getTextWrittenToStandardOut(),
                containsString("Phone call from " + caller + " to " + callee + " from " +
                        call.getStartTimeString() + " to " + call.getEndTimeString()));
    }
    @Test
    public void testNineCommandLineArgumentsWithPrPrintOptionToSOut() {
        String pretty = "-pretty";
        String printTo = "-";
        String customer = "Brian Griffin";
        String caller = "234-567-8901";
        String callee = "123-456-7890";
        String startDate = "01/23/2020";
        String startTime = "09:12";
        String startTimeAMPM = "am";
        String endDate = "01/23/2020";
        String endTime = "10:12";
        String endTimeAMPM = "am";
        MainMethodResult result = invokeMain(pretty, printTo, customer, caller, callee, startDate, startTime,
                startTimeAMPM, endDate, endTime, endTimeAMPM);
        PhoneCall call = new PhoneCall(caller, callee, startDate, startTime, startTimeAMPM, endDate, endTime, endTimeAMPM);
        DateFormat df = new SimpleDateFormat("E M d, y G h:mm a z");

        assertThat (result.getTextWrittenToStandardOut(), containsString("Customer name: " + customer));
        assertThat (result.getTextWrittenToStandardOut(), containsString(call.getCaller() + " called to "
                + call.getCallee() + " at " + df.format(call.getStartTime()) + " and ended at " +
                df.format(call.getEndTime()) + "." + " The duration of this call is " +
                call.getDurationMinute() + " minutes."));
    }

    @Test
    public void testNineCommandLineArgumentsWithPrPrintOptionToFile() {
        String pretty = "-pretty";
        String printTo = "pretty";
        String customer = "Brian Griffin";
        String caller = "523-567-8901";
        String callee = "123-456-7890";
        String startDate = "01/22/2020";
        String startTime = "6:12";
        String startTimeAMPM = "am";
        String endDate = "01/22/2020";
        String endTime = "10:12";
        String endTimeAMPM = "am";
        MainMethodResult result = invokeMain(pretty, printTo, customer, caller, callee, startDate, startTime,
                startTimeAMPM, endDate, endTime, endTimeAMPM);
    }

    @Test
    public void testNineCommandLineArgumentsWithInvalidOption() {
        String invalidOption = "-invalid";
        String customer = "Brian Griffin";
        String caller = "234-567-8901";
        String callee = "123-456-7890";
        String startDate = "01/23/2020";
        String startTime = "09:12";
        String startTimeAMPM = "am";
        String endDate = "01/23/2020";
        String endTime = "10:12";
        String endTimeAMPM = "am";
        MainMethodResult result = invokeMain(invalidOption, customer, caller, callee, startDate, startTime,
                startTimeAMPM, endDate, endTime, endTimeAMPM);
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString("Invalid option."));
    }
    @Test
    public void testReadmeOption() {
        String readme = "-README";
        MainMethodResult result = invokeMain(readme);
        assertThat(result.getExitCode(), equalTo(0));
        assertThat(result.getTextWrittenToStandardOut(), containsString("edu.pdx.cs410J.miyon.Project2"));
    }

    @Test
    public void testPrintReadmeOption() {
        String print = "-print";
        String readme = "-README";
        MainMethodResult result = invokeMain(print, readme);
        assertThat(result.getExitCode(), equalTo(0));
        assertThat(result.getTextWrittenToStandardOut(), containsString("edu.pdx.cs410J.miyon.Project2"));
    }

    @Test
    public void testPrintOptionOnly() {
        String print = "-print";
        MainMethodResult result = invokeMain(print);
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString("Missing command line arguments"));
    }

    @Test
    public void testPrPrintOptionOnly() {
        String pretty = "-pretty";
        MainMethodResult result = invokeMain(pretty);
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(),
                containsString("Invalid pretty print option: No printTo and missing arguments"));
    }

    @Test
    public void testTextFileOptionFileNotExist() {
        String textFile = "-textFile";
        String fileName = "textFile";
        String customer = "Brian Griffin";
        String caller = "234-567-8901";
        String callee = "123-456-7234";
        String startDate = "01/23/2020";
        String startTime = "09:12";
        String startTimeAMPM = "am";
        String endDate = "01/23/2020";
        String endTime = "10:12";
        String endTimeAMPM = "am";
        MainMethodResult result = invokeMain(textFile, fileName, customer, caller, callee, startDate, startTime,
                startTimeAMPM, endDate, endTime, endTimeAMPM);
    }

    @Test
    public void testTextFileOptionFileExist() {
        String textFile = "-textFile";
        String fileName = "textFile";
        String customer = "Brian Griffin";
        String caller = "234-567-8902";
        String callee = "123-456-7233";
        String startDate = "01/02/2020";
        String startTime = "10:12";
        String startTimeAMPM = "am";
        String endDate = "01/02/2020";
        String endTime = "11:12";
        String endTimeAMPM = "am";
        MainMethodResult result = invokeMain(textFile, fileName, customer, caller, callee, startDate, startTime,
                startTimeAMPM, endDate, endTime, endTimeAMPM);
    }

    @Ignore
    @Test
    public void testTextFileOptionWithMalFormatted() {
        String textFile = "-textFile";
        String fileName = "textFile";
        String customer = "Brian Griffin";
        String caller = "234-567-8902";
        String callee = "123-456-7233";
        String startDate = "01/02/2020";
        String startTime = "11:12";
        String startTimeAMPM = "am";
        String endDate = "01/02/2020";
        String endTime = "11:22";
        String endTimeAMPM = "am";
        MainMethodResult result = invokeMain(textFile, fileName, customer, caller, callee, startDate, startTime,
                startTimeAMPM, endDate, endTime, endTimeAMPM);
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString("Text file has mal-formatted phone call"));
    }

    @Test
    public void testTextFileOptionWithNotMatchingCustomerName() {
        String textFile = "-textFile";
        String fileName = "textFile";
        String customer = "abc";
        String caller = "234-567-8901";
        String callee = "123-456-7234";
        String startDate = "01/23/2020";
        String startTime = "09:12";
        String startTimeAMPM = "am";
        String endDate = "01/23/2020";
        String endTime = "09:12";
        String endTimeAMPM = "am";
        MainMethodResult result = invokeMain(textFile, fileName, customer, caller, callee, startDate, startTime, startTimeAMPM, endDate, endTime, endTimeAMPM);
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString("The bill's customer name doesn't match with text file's customer name"));
    }

    @Test
    public void testNineCommandLineArgumentsWithInvalidCallerNumber() {
        String textFile = "-textFile";
        String fileName = "textFile";
        String customer = "Brian Griffin";
        String caller = "ABC-123-4567";
        String callee = "123-456-7890";
        String startDate = "01/23/2020";
        String startTime = "09:12";
        String startTimeAMPM = "am";
        String endDate = "01/23/2020";
        String endTime = "10:12";
        String endTimeAMPM = "am";
        MainMethodResult result = invokeMain(customer, caller, callee, startDate, startTime, startTimeAMPM, endDate, endTime, endTimeAMPM);
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString("Caller number format is not valid"));
    }
    @Test
    public void testNineCommandLineArgumentsWithInvalidDate() {
        String customer = "Brian Griffin";
        String caller = "234-567-8901";
        String callee = "123-456-7890";
        String startDate = "01/23/20";
        String startTime = "9:12";
        String startTimeAMPM = "am";
        String endDate = "01/23/2020";
        String endTime = "11:12";
        String endTimeAMPM = "am";
        MainMethodResult result = invokeMain(customer, caller, callee, startDate, startTime, startTimeAMPM, endDate, endTime, endTimeAMPM);
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString("Start date format is not valid"));
    }

    @Test
    public void testNineCommandLineArgumentsWithInvalidTime() {
        String customer = "Brian Griffin";
        String caller = "234-567-8901";
        String callee = "123-456-7890";
        String startDate = "01/23/2020";
        String startTime = "09:12";
        String startTimeAMPM = "AM";
        String endDate = "1/23/2020";
        String endTime = "13:22";
        String endTimeAMPM = "am";
        MainMethodResult result = invokeMain(customer, caller, callee, startDate, startTime, startTimeAMPM, endDate, endTime, endTimeAMPM);
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString("End time format is not valid"));
    }

    @Test
    public void testNineCommandLineArgumentsWithInvalidTimeAMPM() {
        String customer = "Brian Griffin";
        String caller = "234-567-8901";
        String callee = "123-456-7890";
        String startDate = "01/23/2020";
        String startTime = "09:12";
        String startTimeAMPM = "em";
        String endDate = "01/23/2020";
        String endTime = "10:12";
        String endTimeAMPM = "am";
        MainMethodResult result = invokeMain(customer, caller, callee, startDate, startTime, startTimeAMPM, endDate, endTime, endTimeAMPM);
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString("Start time format is not valid"));
    }

    @Test
    public void testNineCommandLineArgumentsWithInvalidStartEndTime() {
        String customer = "Brian Griffin";
        String caller = "234-567-8901";
        String callee = "123-456-7890";
        String startDate = "01/26/2020";
        String startTime = "09:12";
        String startTimeAMPM = "am";
        String endDate = "01/23/2020";
        String endTime = "10:12";
        String endTimeAMPM = "am";
        MainMethodResult result = invokeMain(customer, caller, callee, startDate, startTime, startTimeAMPM, endDate, endTime, endTimeAMPM);
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString("End time is before its starts time"));
    }

}