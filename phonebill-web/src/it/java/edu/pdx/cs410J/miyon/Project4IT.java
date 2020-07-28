package edu.pdx.cs410J.miyon;

import edu.pdx.cs410J.InvokeMainTestCase;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

/**
 * Tests the {@link Project4} class by invoking its main method with various arguments
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class Project4IT extends InvokeMainTestCase {
    private static final String HOSTNAMEOPTION = "-host";
    private static final String HOSTNAME = "localhost";
    private static final String PORTOPTION = "-port";
    private static final String PORT = System.getProperty("http.port", "8080");

    @Test
    public void test0RemoveAllMappings() throws IOException {
        PhoneBillRestClient client = new PhoneBillRestClient(HOSTNAME, Integer.parseInt(PORT));
        client.removeAllPhoneBills();
    }

    @Test
    public void test1NoCommandLineArguments() {
        MainMethodResult result = invokeMain( Project4.class );
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString(Project4.MISSING_ARGS));
    }

    @Test
    public void test3UnknownPhoneBillIssuesUnknownPhoneBillError() throws Throwable {
        String customer = "Customer";
        MainMethodResult result = invokeMain(Project4.class, HOSTNAMEOPTION, HOSTNAME, PORTOPTION, PORT, customer);
        assertThat(result.getTextWrittenToStandardError(), containsString("No phone bill for customer " + customer));
        assertThat(result.getExitCode(), equalTo(1));
    }

    @Test
    public void test4AddPhoneCall() {
        String customer = "Customer";
        String caller = "123-456-7890";
        String callee = "503-453-1890";
        String startDate = "03/01/2020";
        String startTime = "12:00";
        String startAMPM = "am";
        String endDate = "03/01/2020";
        String endTime = "1:00";
        String endAMPM = "pm";

        MainMethodResult result = invokeMain( Project4.class, HOSTNAMEOPTION, HOSTNAME, PORTOPTION, PORT,
                customer, caller, callee, startDate, startTime, startAMPM, endDate, endTime, endAMPM);
        assertThat(result.getTextWrittenToStandardOut(), equalTo(""));
        assertThat(result.getExitCode(), equalTo(0));
    }

    @Test
    public void test5PhoneBillIsPrettyPrinted() {
        String customer = "Customer";
        String caller = "123-456-7890";
        String callee = "503-453-1890";
        String startDate = "03/01/2020";
        String startTime = "12:00";
        String startAMPM = "am";
        String endDate = "03/01/2020";
        String endTime = "1:00";
        String endAMPM = "pm";

        MainMethodResult result = invokeMain( Project4.class, HOSTNAMEOPTION, HOSTNAME, PORTOPTION, PORT,
                customer);
        assertThat(result.getExitCode(), equalTo(0));
        String pretty = result.getTextWrittenToStandardOut();
        assertThat(pretty, containsString(customer));
        assertThat(pretty, containsString(caller));
        assertThat(pretty, containsString(callee));
    }
}