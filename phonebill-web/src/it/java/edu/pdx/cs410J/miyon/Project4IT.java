package edu.pdx.cs410J.miyon;

import edu.pdx.cs410J.InvokeMainTestCase;
import org.hamcrest.CoreMatchers;
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
    private static final String SEARCHOPTION = "-search";
    private static final String PRINTOPTION = "-print";
    private static final String READMEOPTION = "-README";
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
    public void test5AddPhoneCallWithPrint() {
        String customer = "Customer";
        String caller = "123-456-7890";
        String callee = "523-123-3123";
        String startDate = "04/01/2020";
        String startTime = "12:00";
        String startAMPM = "am";
        String endDate = "04/01/2020";
        String endTime = "1:00";
        String endAMPM = "pm";

        MainMethodResult result = invokeMain( Project4.class, HOSTNAMEOPTION, HOSTNAME, PORTOPTION, PORT, PRINTOPTION,
                customer, caller, callee, startDate, startTime, startAMPM, endDate, endTime, endAMPM);
        assertThat(result.getTextWrittenToStandardOut(), containsString("Phone call from " + caller + " to " +
                callee + " from " + startDate + " " + startTime + " " + startAMPM + " to " +
                endDate + " " + endTime + " " + endAMPM));
        assertThat(result.getExitCode(), equalTo(0));
    }

    @Test
    public void test6PhoneBillIsPrettyPrinted() {
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
        assertThat(pretty, containsString(startTime));
    }

    @Test
    public void test7SearchPhoneBillPrettyPrinted() {
        String customer = "Customer";
        String caller = "123-456-7890";
        String callee = "503-453-1890";
        String startDate = "02/29/2020";
        String startTime = "12:00";
        String startAMPM = "am";
        String endDate = "03/04/2020";
        String endTime = "1:00";
        String endAMPM = "pm";

        MainMethodResult result = invokeMain( Project4.class, HOSTNAMEOPTION, HOSTNAME, PORTOPTION, PORT,
                SEARCHOPTION, customer, startDate, startTime, startAMPM, endDate, endTime, endAMPM);
        assertThat(result.getExitCode(), equalTo(0));
        String pretty = result.getTextWrittenToStandardOut();
        assertThat(pretty, containsString(customer));
        assertThat(pretty, containsString(caller));
        assertThat(pretty, containsString(callee));
    }

    @Test
    public void test8SearchPhoneBillResultedNoPhoneCallBetweenStartEnd() {
        String customer = "Customer";
        String startDate = "07/29/2020";
        String startTime = "12:00";
        String startAMPM = "am";
        String endDate = "10/04/2020";
        String endTime = "1:00";
        String endAMPM = "pm";

        MainMethodResult result = invokeMain( Project4.class, HOSTNAMEOPTION, HOSTNAME, PORTOPTION, PORT,
                SEARCHOPTION, customer, startDate, startTime, startAMPM, endDate, endTime, endAMPM);
        assertThat(result.getExitCode(), equalTo(0));
        String pretty = result.getTextWrittenToStandardOut();
        assertThat(pretty, containsString("There is no phone call between those two times"));
        assertThat(pretty, containsString(customer));
    }

    @Test
    public void test9NoPort() {
        String customer = "Customer";
        String startDate = "07/29/2020";
        String startTime = "12:00";
        String startAMPM = "am";
        String endDate = "10/04/2020";
        String endTime = "1:00";
        String endAMPM = "pm";

        MainMethodResult result = invokeMain( Project4.class, HOSTNAMEOPTION, HOSTNAME,
                customer, startDate, startTime, startAMPM, endDate, endTime, endAMPM);
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString( "Missing port"));
    }

    @Test
    public void test10SevenArgumentsNoSearchOption() {
        String customer = "Customer";
        String caller = "123-456-7890";
        String callee = "503-453-1890";
        String startDate = "02/29/2020";
        String startTime = "12:00";
        String startAMPM = "am";
        String endDate = "03/04/2020";
        String endTime = "1:00";
        String endAMPM = "pm";

        MainMethodResult result = invokeMain( Project4.class, HOSTNAMEOPTION, HOSTNAME, PORTOPTION, PORT,
                customer, caller, callee, startDate, startTime, startAMPM, endDate);
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString( "Missing end time"));
    }

    @Test
    public void test11InvalidCalleeNumber() {
        String customer = "Customer";
        String caller = "123-456-7890";
        String callee = "ABC-453-1890";
        String startDate = "02/29/2020";
        String startTime = "12:00";
        String startAMPM = "am";
        String endDate = "03/04/2020";
        String endTime = "1:00";
        String endAMPM = "pm";

        MainMethodResult result = invokeMain( Project4.class, HOSTNAMEOPTION, HOSTNAME, PORTOPTION, PORT,
                customer, caller, callee, startDate, startTime, startAMPM, endDate, endTime, endAMPM);
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString( "Callee number format is not valid"));
    }

    @Test
    public void test12InvalidStartDate() {
        String customer = "Customer";
        String caller = "123-456-7890";
        String callee = "503-453-1890";
        String startDate = "03/29/20";
        String startTime = "11:00";
        String startAMPM = "am";
        String endDate = "04/04/2020";
        String endTime = "1:00";
        String endAMPM = "pm";

        MainMethodResult result = invokeMain( Project4.class, HOSTNAMEOPTION, HOSTNAME, PORTOPTION, PORT,
                customer, caller, callee, startDate, startTime, startAMPM, endDate, endTime, endAMPM);
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString( "Start date format is not valid"));
    }

    @Test
    public void test12InvalidStartTime() {
        String customer = "Customer";
        String caller = "123-456-7890";
        String callee = "503-453-1890";
        String startDate = "02/29/2020";
        String startTime = "23:00";
        String startAMPM = "am";
        String endDate = "03/04/2020";
        String endTime = "1:00";
        String endAMPM = "pm";

        MainMethodResult result = invokeMain( Project4.class, HOSTNAMEOPTION, HOSTNAME, PORTOPTION, PORT,
                customer, caller, callee, startDate, startTime, startAMPM, endDate, endTime, endAMPM);
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString( "Start time format is not valid"));
    }

    @Test
    public void test13EndTimeIsBeforeStartTime() {
        String customer = "Customer";
        String caller = "123-456-7890";
        String callee = "503-453-1890";
        String startDate = "02/29/2020";
        String startTime = "3:00";
        String startAMPM = "am";
        String endDate = "02/04/2020";
        String endTime = "1:00";
        String endAMPM = "pm";

        MainMethodResult result = invokeMain( Project4.class, HOSTNAMEOPTION, HOSTNAME, PORTOPTION, PORT,
                customer, caller, callee, startDate, startTime, startAMPM, endDate, endTime, endAMPM);
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString( "End time is before its starts time"));
    }

    @Test
    public void test14InvalidServer() {
        String customer = "Customer";
        String caller = "123-456-7890";
        String callee = "503-453-1890";
        String startDate = "02/29/2020";
        String startTime = "3:00";
        String startAMPM = "am";
        String endDate = "03/04/2020";
        String endTime = "1:00";
        String endAMPM = "pm";

        MainMethodResult result = invokeMain( Project4.class, HOSTNAMEOPTION, "abc", PORTOPTION, PORT,
                customer, caller, callee, startDate, startTime, startAMPM, endDate, endTime, endAMPM);
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString( "While contacting server:"));
    }

    @Test
    public void test15ReadmeOption() {
        String readme = "-README";
        MainMethodResult result = invokeMain(Project4.class, READMEOPTION);
        assertThat(result.getExitCode(), CoreMatchers.equalTo(0));
        assertThat(result.getTextWrittenToStandardOut(), CoreMatchers.containsString("edu.pdx.cs410J.miyon.Project4"));
    }
}