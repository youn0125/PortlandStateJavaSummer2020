package edu.pdx.cs410J.miyon;

import edu.pdx.cs410J.InvokeMainTestCase;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Tests the functionality in the {@link Project1} main class.
 */
public class Project1IT extends InvokeMainTestCase {

    /**
     * Invokes the main method of {@link Project1} with the given arguments.
     */
    private MainMethodResult invokeMain(String... args) {
        return invokeMain( Project1.class, args );
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
        MainMethodResult result = invokeMain("Brian Griffin",  "234-567-8901", "123-456-7890","01/23/2020 09:12");
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString("Missing command line arguments"));
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
    public void testFiveCommandLineArgumentsWithInvalidPNumber() {
        String customer = "Brian Griffin";
        String caller = "234-567-8901";
        String callee = "1234567890";
        String startDate = "01/23/2020";
        String startTime = "09:12";
        String endDate = "01/23/2020";
        String endTime = "10:12";
        MainMethodResult result = invokeMain(customer, caller, callee, startDate, startTime, endDate, endTime);
        assertThat (result.getTextWrittenToStandardError(), containsString("Check Phone number pattern"));
    }
    @Test
    public void testFiveCommandLineArgumentsWithInvalidDate() {
        String customer = "Brian Griffin";
        String caller = "234-567-8901";
        String callee = "123-456-7890";
        String startDate = "01/23/20";
        String startTime = "09:12";
        String endDate = "01/23/2020";
        String endTime = "10:12";
        MainMethodResult result = invokeMain(customer, caller, callee, startDate, startTime, endDate, endTime);
        assertThat (result.getTextWrittenToStandardError(), containsString("Check date pattern"));
    }
}