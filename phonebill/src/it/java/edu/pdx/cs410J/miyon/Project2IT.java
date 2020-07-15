package edu.pdx.cs410J.miyon;

import edu.pdx.cs410J.InvokeMainTestCase;
import edu.pdx.cs410J.ParserException;
import org.junit.Ignore;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collection;
import java.util.Vector;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Tests the functionality in the {@link Project2} main class.
 */
public class Project2IT extends InvokeMainTestCase {

    /**
     * Invokes the main method of {@link Project2} with the given arguments.
     */
    private MainMethodResult invokeMain(String... args) {
        return invokeMain( Project2.class, args );
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
        assertThat(result.getTextWrittenToStandardError(), containsString("Missing caller"));
    }

    @Test
    public void testTwoCommandLineArguments() {
        MainMethodResult result = invokeMain("Brian Griffin", "234-567-8901");
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString("Missing callee"));
    }

    @Test
    public void testThreeCommandLineArguments() {
        MainMethodResult result = invokeMain("Brian Griffin", "234-567-8901", "123-456-7890");
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString("Missing start date"));
    }

    @Test
    public void testFourCommandLineArguments() {
        MainMethodResult result = invokeMain("Brian Griffin",  "234-567-8901", "123-456-7890","01/23/2020");
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString("Missing start time"));
    }

    @Test
    public void testFiveCommandLineArguments() {
        MainMethodResult result = invokeMain("Brian Griffin",  "234-567-8901", "123-456-7890","01/23/2020",
                "09:12");
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString("Missing end date"));
    }
    @Test
    public void testSixCommandLineArguments() {
        MainMethodResult result = invokeMain("Brian Griffin",  "234-567-8901", "123-456-7890","01/23/2020",
                "09:12", "01/23/2020");
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString("Missing end time"));
    }
    @Test
    public void testEightCommandLineArguments() {
        MainMethodResult result = invokeMain("Brian Griffin",  "234-567-8901", "123-456-7890","01/23/2020",
                "09:12", "01/23/2020", "10:12", "cse");
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString("There are extraneous arguments"));
    }
    @Test
    public void testSevenCommandLineArgumentsWithValidArgsNoOption() {
        String customer = "Brian Griffin";
        String caller = "234-567-8901";
        String callee = "123-456-7890";
        String startDate = "01/23/2020";
        String startTime = "09:12";
        String endDate = "01/23/2020";
        String endTime = "10:12";
        MainMethodResult result = invokeMain(customer, caller, callee, startDate, startTime, endDate, endTime);
        PhoneCall call = new PhoneCall(caller, callee, startDate, startTime, endDate, endTime);
        PhoneBill bill = new PhoneBill(customer);
        bill.addPhoneCall(call);
        assertThat (result.getTextWrittenToStandardOut(), containsString("Phone call is added to Phone bill"));
    }

    @Test
    public void testSevenCommandLineArgumentsWithValidArgsPrintOption() {
        String print = "-print";
        String customer = "Brian Griffin";
        String caller = "234-567-8901";
        String callee = "123-456-7890";
        String startDate = "01/23/2020";
        String startTime = "09:12";
        String endDate = "01/23/2020";
        String endTime = "10:12";
        MainMethodResult result = invokeMain(print, customer, caller, callee, startDate, startTime, endDate, endTime);
        PhoneCall call = new PhoneCall(caller, callee, startDate, startTime, endDate, endTime);
        PhoneBill bill = new PhoneBill(customer);
        bill.addPhoneCall(call);
        assertThat (result.getTextWrittenToStandardOut(),
                containsString("Phone call from " + caller + " to " + callee + " from " +
                        startDate + " " + startTime + " to " + endDate + " " + endTime));
    }

    @Test
    public void testSevenCommandLineArgumentsWithInvalidOption() {
        String invalidOption = "-invalid";
        String customer = "Brian Griffin";
        String caller = "234-567-8901";
        String callee = "123-456-7890";
        String startDate = "01/23/2020";
        String startTime = "09:12";
        String endDate = "01/23/2020";
        String endTime = "10:12";
        MainMethodResult result = invokeMain(invalidOption, customer, caller, callee, startDate, startTime, endDate, endTime);
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString("Invalid option."));
    }
    @Test
    public void testReadmeOption() {
        String readme = "-README";
        MainMethodResult result = invokeMain(readme);
        assertThat(result.getExitCode(), equalTo(0));
        assertThat(result.getTextWrittenToStandardOut(), containsString("edu.pdx.cs410J.miyon.Project1"));
    }

    @Test
    public void testPrintReadmeOption() {
        String print = "-print";
        String readme = "-README";
        MainMethodResult result = invokeMain(print, readme);
        assertThat(result.getExitCode(), equalTo(0));
        assertThat(result.getTextWrittenToStandardOut(), containsString("edu.pdx.cs410J.miyon.Project1"));
    }

    @Ignore
    @Test
    public void testTextFileOption() {
        String textFile = "-textFile";
        String fileName = "textFile";
        String customer = "Brian Griffin";
        String caller = "234-567-8901";
        String callee = "123-456-7234";
        String startDate = "01/23/2020";
        String startTime = "09:12";
        String endDate = "01/23/2020";
        String endTime = "10:12";
        MainMethodResult result = invokeMain(textFile, fileName, customer, caller, callee, startDate, startTime, endDate, endTime);
    }

    @Test
    public void testTextFileOptionWithBadCustomerName() {
        String textFile = "-textFile";
        String fileName = "textFile";
        String customer = "abc";
        String caller = "234-567-8901";
        String callee = "123-456-7234";
        String startDate = "01/23/2020";
        String startTime = "09:12";
        String endDate = "01/23/2020";
        String endTime = "10:12";
        MainMethodResult result = invokeMain(textFile, fileName, customer, caller, callee, startDate, startTime, endDate, endTime);
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString("The customer name doesn't match with text file's customer name"));
    }

    @Test
    public void testSevenCommandLineArgumentsWithInvalidCallerNumber() {
        String customer = "Brian Griffin";
        String caller = "2345678901";
        String callee = "123-456-7890";
        String startDate = "01/23/2020";
        String startTime = "09:12";
        String endDate = "01/23/2020";
        String endTime = "10:12";
        MainMethodResult result = invokeMain(customer, caller, callee, startDate, startTime, endDate, endTime);
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString("Caller number format is not valid"));
    }
    @Test
    public void testSevenCommandLineArgumentsWithInvalidDate() {
        String customer = "Brian Griffin";
        String caller = "234-567-8901";
        String callee = "123-456-7890";
        String startDate = "01/23/20";
        String startTime = "09:12";
        String endDate = "01/23/2020";
        String endTime = "10:12";
        MainMethodResult result = invokeMain(customer, caller, callee, startDate, startTime, endDate, endTime);
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString("Start date format is not valid"));
    }

    @Test
    public void testSevenCommandLineArgumentsWithInvalidTime() {
        String customer = "Brian Griffin";
        String caller = "234-567-8901";
        String callee = "123-456-7890";
        String startDate = "01/23/2020";
        String startTime = "09:12";
        String endDate = "01/23/2020";
        String endTime = "1012";
        MainMethodResult result = invokeMain(customer, caller, callee, startDate, startTime, endDate, endTime);
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString("End time format is not valid"));
    }
}