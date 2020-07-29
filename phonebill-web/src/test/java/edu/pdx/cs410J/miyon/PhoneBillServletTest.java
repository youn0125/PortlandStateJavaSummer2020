package edu.pdx.cs410J.miyon;

import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mockito;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import static edu.pdx.cs410J.miyon.PhoneBillURLParameters.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

/**
 * A unit test for the {@link PhoneBillServlet}.  It uses mockito to
 * provide mock http requests and responses.
 */
public class PhoneBillServletTest {

  @Test
  public void requestWithNoCustomerReturnMissingParameter() throws ServletException, IOException {
    PhoneBillServlet servlet = new PhoneBillServlet();

    HttpServletRequest request = mock(HttpServletRequest.class);
    HttpServletResponse response = mock(HttpServletResponse.class);

    servlet.doGet(request, response);

    verify(response).sendError(HttpServletResponse.SC_PRECONDITION_FAILED, Messages.missingRequiredParameter(CUSTOMER_PARAMETER));
  }

  @Test
  public void requestCustomerWithNoPhoneBillReturnsNotFound() throws ServletException, IOException {
    PhoneBillServlet servlet = new PhoneBillServlet();

    HttpServletRequest request = mock(HttpServletRequest.class);
    String customerName = "Dave";
    when(request.getParameter(CUSTOMER_PARAMETER)).thenReturn(customerName);

    HttpServletResponse response = mock(HttpServletResponse.class);

    servlet.doGet(request, response);

    verify(response).sendError(HttpServletResponse.SC_NOT_FOUND, Messages.noPhoneBillForCustomer(customerName));

  }

  @Test
  public void addPhoneCallToPhoneBill() throws ServletException, IOException {
    PhoneBillServlet servlet = new PhoneBillServlet();

    String customer = "Brian Knox";
    String caller = "503-123-4567";
    String callee = "503-453-1890";
    String start = "03/01/2020 12:00 am";
    String end = "03/01/2020 1:00 pm";

    HttpServletRequest request = mock(HttpServletRequest.class);
    when(request.getParameter(CUSTOMER_PARAMETER)).thenReturn(customer);
    when(request.getParameter(CALLER_NUMBER_PARAMETER)).thenReturn(caller);
    when(request.getParameter(CALLEE_NUMBER_PARAMETER)).thenReturn(callee);
    when(request.getParameter(START_PARAMETER)).thenReturn(start);
    when(request.getParameter(END_PARAMETER)).thenReturn(end);

    HttpServletResponse response = mock(HttpServletResponse.class);
    PrintWriter pw = mock(PrintWriter.class);

    when(response.getWriter()).thenReturn(pw);

    servlet.doPost(request, response);

    verify(pw, times(0)).println(Mockito.any(String.class));
    verify(response).setStatus(HttpServletResponse.SC_OK);

    PhoneBill phoneBill = servlet.getPhoneBill(customer);
    assertThat(phoneBill, notNullValue());
    assertThat(phoneBill.getCustomer(), equalTo(customer));

    PhoneCall phoneCall = phoneBill.getPhoneCalls().iterator().next();
    assertThat(phoneCall.getCaller(), equalTo(caller));
    assertThat(phoneCall.getCallee(), equalTo(callee));
    assertThat(phoneCall.getStartTimeString(), equalTo(start));
    assertThat(phoneCall.getEndTimeString(), equalTo(end));
  }

  @Test
  public void requestingExistingPhoneBillDumpsItToPrintWriter() throws IOException, ServletException {
    String customer = "Brian Knox";
    String caller = "503-123-4567";
    String callee = "503-634-4213";
    String start = "04/01/2020 12:00 am";
    String end = "04/01/2020 1:00 pm";

    PhoneBill bill = new PhoneBill(customer);
    bill.addPhoneCall(new PhoneCall(caller, callee, start, end));

    PhoneBillServlet servlet = new PhoneBillServlet();
    servlet.addPhoneBill(bill);

    HttpServletRequest request = mock(HttpServletRequest.class);
    when(request.getParameter(CUSTOMER_PARAMETER)).thenReturn(customer);

    HttpServletResponse response = mock(HttpServletResponse.class);
    StringWriter sw = new StringWriter();
    PrintWriter pw = new PrintWriter(sw, true);
    when(response.getWriter()).thenReturn(pw);

    servlet.doGet(request, response);

    verify(response).setStatus(HttpServletResponse.SC_OK);

    String textPhoneBill = sw.toString();
    assertThat(textPhoneBill, containsString(customer));
    assertThat(textPhoneBill, containsString(caller));

  }

  @Test
  public void requestingSearchPhoneBillDumpsItToPrintWriter() throws IOException, ServletException {
    String customer = "Brian Knox";
    String caller = "503-123-4567";
    String callee = "503-634-4213";
    String start = "04/01/2020 12:00 am";
    String end = "04/01/2020 1:00 pm";

    String caller2 = "503-123-4567";
    String callee2 = "412-523-4213";
    String start2 = "04/06/2020 12:00 am";
    String end2 = "04/06/2020 1:00 pm";

    String searchedStart = "03/29/2020 12:00 am";
    String searchedEnd = "04/03/2020 12:00 am";

    PhoneBill bill = new PhoneBill(customer);
    bill.addPhoneCall(new PhoneCall(caller, callee, start, end));
    bill.addPhoneCall(new PhoneCall(caller2, callee2, start2, end2));

    PhoneBillServlet servlet = new PhoneBillServlet();
    servlet.addPhoneBill(bill);

    HttpServletRequest request = mock(HttpServletRequest.class);
    when(request.getParameter(CUSTOMER_PARAMETER)).thenReturn(customer);
    when(request.getParameter(START_PARAMETER)).thenReturn(searchedStart);
    when(request.getParameter(END_PARAMETER)).thenReturn(searchedEnd);

    HttpServletResponse response = mock(HttpServletResponse.class);
    StringWriter sw = new StringWriter();
    PrintWriter pw = new PrintWriter(sw, true);
    when(response.getWriter()).thenReturn(pw);

    servlet.doGet(request, response);

    verify(response).setStatus(HttpServletResponse.SC_OK);

    String textPhoneBill = sw.toString();
    assertThat(textPhoneBill, containsString(customer));
    assertThat(textPhoneBill, containsString(caller));
    assertThat(textPhoneBill, containsString(callee));
    assertThat(textPhoneBill, containsString(start));
    assertThat(textPhoneBill, containsString(end));
    assertThat(textPhoneBill, not(containsString(callee2)));
    assertThat(textPhoneBill, not(containsString(start2)));
    assertThat(textPhoneBill, not(containsString(end2)));

  }
  @Test
  public void requestingSearchPhoneBillWithNoResult() throws IOException, ServletException {
    String customer = "Brian Knox";
    String caller = "503-123-4567";
    String callee = "503-634-4213";
    String start = "04/01/2020 12:00 am";
    String end = "04/01/2020 1:00 pm";

    String caller2 = "503-123-4567";
    String callee2 = "412-523-4213";
    String start2 = "04/06/2020 12:00 am";
    String end2 = "04/06/2020 1:00 pm";

    String searchedStart = "05/29/2020 12:00 am";
    String searchedEnd = "06/03/2020 12:00 am";

    PhoneBill bill = new PhoneBill(customer);
    bill.addPhoneCall(new PhoneCall(caller, callee, start, end));
    bill.addPhoneCall(new PhoneCall(caller2, callee2, start2, end2));

    PhoneBillServlet servlet = new PhoneBillServlet();
    servlet.addPhoneBill(bill);

    HttpServletRequest request = mock(HttpServletRequest.class);
    when(request.getParameter(CUSTOMER_PARAMETER)).thenReturn(customer);
    when(request.getParameter(START_PARAMETER)).thenReturn(searchedStart);
    when(request.getParameter(END_PARAMETER)).thenReturn(searchedEnd);

    HttpServletResponse response = mock(HttpServletResponse.class);
    StringWriter sw = new StringWriter();
    PrintWriter pw = new PrintWriter(sw, true);
    when(response.getWriter()).thenReturn(pw);

    servlet.doGet(request, response);

    verify(response).setStatus(HttpServletResponse.SC_OK);

    String textPhoneBillWithNoPhoneCall = sw.toString();
    assertThat(textPhoneBillWithNoPhoneCall, containsString("There is no phone call between those two times"));
    assertThat(textPhoneBillWithNoPhoneCall, containsString(customer));
    assertThat(textPhoneBillWithNoPhoneCall, not(containsString(caller)));
    assertThat(textPhoneBillWithNoPhoneCall, not(containsString(callee)));

  }

  @Test
  public void requestSearchWithInvalidStartDateTime() throws ServletException, IOException {
    String customer = "Brian Knox";
    String searchedStart = "05/29/20 12:00 am";
    String searchedEnd = "06/03/2020 12:00 am";

    PhoneBill bill = new PhoneBill(customer);

    PhoneBillServlet servlet = new PhoneBillServlet();
    servlet.addPhoneBill(bill);

    HttpServletRequest request = mock(HttpServletRequest.class);
    when(request.getParameter(CUSTOMER_PARAMETER)).thenReturn(customer);
    when(request.getParameter(START_PARAMETER)).thenReturn(searchedStart);
    when(request.getParameter(END_PARAMETER)).thenReturn(searchedEnd);

    HttpServletResponse response = mock(HttpServletResponse.class);
    StringWriter sw = new StringWriter();
    PrintWriter pw = new PrintWriter(sw, true);
    when(response.getWriter()).thenReturn(pw);

    servlet.doGet(request, response);

    verify(response).sendError(HttpServletResponse.SC_PRECONDITION_FAILED, Messages.malformatParameter(START_PARAMETER));
  }

  @Test
  public void requestSearchWithInvalidEndDateTime() throws ServletException, IOException {
    String customer = "Brian Knox";
    String searchedStart = "05/29/2020 12:00 am";
    String searchedEnd = "06/03/2020 16:00 am";

    PhoneBill bill = new PhoneBill(customer);

    PhoneBillServlet servlet = new PhoneBillServlet();
    servlet.addPhoneBill(bill);

    HttpServletRequest request = mock(HttpServletRequest.class);
    when(request.getParameter(CUSTOMER_PARAMETER)).thenReturn(customer);
    when(request.getParameter(START_PARAMETER)).thenReturn(searchedStart);
    when(request.getParameter(END_PARAMETER)).thenReturn(searchedEnd);

    HttpServletResponse response = mock(HttpServletResponse.class);
    StringWriter sw = new StringWriter();
    PrintWriter pw = new PrintWriter(sw, true);
    when(response.getWriter()).thenReturn(pw);

    servlet.doGet(request, response);

    verify(response).sendError(HttpServletResponse.SC_PRECONDITION_FAILED, Messages.malformatParameter(END_PARAMETER));
  }

  @Test
  public void addPhoneCallWithInvalidCallee() throws ServletException, IOException {
    PhoneBillServlet servlet = new PhoneBillServlet();

    String customer = "Brian Knox";
    String caller = "ABC-123-4567";
    String callee = "ABC-453-1890";
    String start = "03/01/2020 12:00 am";
    String end = "03/01/2020 1:00 pm";

    HttpServletRequest request = mock(HttpServletRequest.class);
    when(request.getParameter(CUSTOMER_PARAMETER)).thenReturn(customer);
    when(request.getParameter(CALLER_NUMBER_PARAMETER)).thenReturn(caller);
    when(request.getParameter(CALLEE_NUMBER_PARAMETER)).thenReturn(callee);
    when(request.getParameter(START_PARAMETER)).thenReturn(start);
    when(request.getParameter(END_PARAMETER)).thenReturn(end);

    HttpServletResponse response = mock(HttpServletResponse.class);
    PrintWriter pw = mock(PrintWriter.class);

    when(response.getWriter()).thenReturn(pw);

    servlet.doPost(request, response);

    verify(response).sendError(HttpServletResponse.SC_PRECONDITION_FAILED, Messages.malformatParameter(CALLEE_NUMBER_PARAMETER));

  }
}