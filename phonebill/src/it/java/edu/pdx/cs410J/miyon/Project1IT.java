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
    /**
     * Tests that invoking the main method with no arguments issues an error
     */
    @Test
    public void testOneCommandLineArguments() {
        MainMethodResult result = invokeMain("Brian Griffin");
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString("Missing command line arguments"));
    }
    private PhoneCall createPhoneCall() {
        String callee = "123-456-7890";
        String caller = "234-567-8901";
        String start = "01/23/2020 09:12";
        String end = "01/23/2020 10:12";
        return new PhoneCall(caller, callee, start, end);
    }

    /**
     * Tests that invoking the main method with no arguments issues an error
     */
    @Test
    public void testTwoCommandLineArguments() {
        MainMethodResult result = invokeMain("Brian Griffin", "123-456-7890");
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString("Missing command line arguments"));
    }
    /**
     * Tests that invoking the main method with no arguments issues an error
     */
    @Test
    public void testThreeCommandLineArguments() {
        MainMethodResult result = invokeMain("Brian Griffin", "123-456-7890", "234-567-8901");
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString("Missing command line arguments"));
    }
    /**
     * Tests that invoking the main method with no arguments issues an error
     */
    @Test
    public void testFourCommandLineArguments() {
        MainMethodResult result = invokeMain("Brian Griffin", "123-456-7890", "234-567-8901", "01/23/2020 09:12");
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString("Missing command line arguments"));
    }

    /**
     * Tests that invoking the main method with no arguments issues an error
     */
    @Test
    public void testFiveCommandLineArgumentsWithValidArgs() {
        String customer = "Brian Griffin";
        String callee = "123-456-7890";
        String caller = "234-567-8901";
        String start = "01/23/2020 09:12";
        String end = "01/23/2020 10:12";
        MainMethodResult result = invokeMain(customer, callee, caller, start, end);
        PhoneCall call = new PhoneCall(callee, caller, start, end);
        PhoneBill bill = new PhoneBill(customer);
        bill.addPhoneCall(call);
        assertThat (result.getTextWrittenToStandardOut(), containsString("Phone call is added to Phone bill"));
    }
}