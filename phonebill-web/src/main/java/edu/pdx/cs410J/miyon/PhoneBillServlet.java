package edu.pdx.cs410J.miyon;

import com.google.common.annotations.VisibleForTesting;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static edu.pdx.cs410J.miyon.PhoneBillURLParameters.*;
import static edu.pdx.cs410J.miyon.PhoneCall.parseDate;
/**
 * This servlet ultimately provides a REST API for working with an
 * <code>PhoneBill</code>.  However, in its current state, it is an example
 * of how to use HTTP and Java servlets to store simple dictionary of words
 * and their definitions.
 */
public class PhoneBillServlet extends HttpServlet
{

    private final Map<String, PhoneBill> phoneBills = new HashMap<>();

    /**
     * Handles an HTTP GET request from a client by writing the definition of the
     * word specified in the "word" HTTP parameter to the HTTP response.  If the
     * "word" parameter is not specified, all of the entries in the dictionary
     * are written to the HTTP response.
     */
    @Override
    protected void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException
    {
        response.setContentType( "text/plain" );

        String customer = getParameter(CUSTOMER_PARAMETER, request );
        String start = getParameter(START_PARAMETER, request);
        String end = getParameter(END_PARAMETER, request);
        if (customer == null) {
            missingRequiredParameter(response, CUSTOMER_PARAMETER);
            return;
        } else if ( start != null && end == null) {
            missingRequiredParameter(response, END_PARAMETER);
            return;
        } else if ( start == null && end != null) {
            missingRequiredParameter(response, START_PARAMETER);
            return;
        }


        PhoneBill bill = getPhoneBill(customer);
        if (bill == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, Messages.noPhoneBillForCustomer(customer));
        } else {
            if (start != null && end != null) {
                if (!checkDateTimeParameter(start) ) {
                    malformatParameter(response, START_PARAMETER);
                    return;
                } else if (!checkDateTimeParameter(end) ) {
                    malformatParameter(response, END_PARAMETER);
                    return;
                }


                PhoneBill searchedBill = new PhoneBill(customer);
                Date searchSDate = parseDate(start);
                Date searchEDate = parseDate(end);
                Collection<PhoneCall> calls =  bill.getPhoneCalls();

                for (PhoneCall call: calls) {
                    if ( searchSDate.getTime() < call.getStartTime().getTime() && call.getStartTime().getTime() < searchEDate.getTime()) {
                        searchedBill.addPhoneCall(call);
                    }
                }
                if(searchedBill.getPhoneCalls().size() == 0){
                    PrintWriter writer = response.getWriter();
                    writer.println("There is no phone call between those two times");
                }


                bill = searchedBill;
            }
            TextDumper dumper = new TextDumper(response.getWriter());
            dumper.dump(bill);
            response.setStatus(HttpServletResponse.SC_OK);
        }
    }

    /**
     * Handles an HTTP POST request by storing the dictionary entry for the
     * "word" and "definition" request parameters.  It writes the dictionary
     * entry to the HTTP response.
     */
    @Override
    protected void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException
    {
        response.setContentType( "text/plain" );

        String customer = getParameter(CUSTOMER_PARAMETER, request );
        if (customer == null) {
            missingRequiredParameter(response, CUSTOMER_PARAMETER);
            return;
        }

        String caller = getParameter(CALLER_NUMBER_PARAMETER, request );
        if ( caller == null) {
            missingRequiredParameter( response, CALLER_NUMBER_PARAMETER);
            return;
        }

        String callee = getParameter(CALLEE_NUMBER_PARAMETER, request );
        if ( callee == null) {
            missingRequiredParameter( response, CALLEE_NUMBER_PARAMETER);
            return;
        }

        String start = getParameter(START_PARAMETER, request );
        if ( start == null) {
            missingRequiredParameter( response, START_PARAMETER);
            return;
        }

        String end = getParameter(END_PARAMETER, request );
        if ( end == null) {
            missingRequiredParameter( response, END_PARAMETER);
            return;
        }

        PhoneBill bill = getPhoneBill(customer);
        if (bill == null) {
            bill = new PhoneBill(customer);
            if (!Project4.checkPNumberPatten(caller) ) {
                malformatParameter(response, CALLER_NUMBER_PARAMETER);
                return;
            } else if (!Project4.checkPNumberPatten(callee) ) {
                malformatParameter(response, CALLEE_NUMBER_PARAMETER);
                return;
            }
            if (!checkDateTimeParameter(start) ) {
                malformatParameter(response, START_PARAMETER);
                return;
            } else if (!checkDateTimeParameter(end) ) {
                malformatParameter(response, END_PARAMETER);
                return;
            }
            bill.addPhoneCall(new PhoneCall(caller, callee, start, end));
            this.phoneBills.put(customer, bill);
        } else {
            if (!Project4.checkPNumberPatten(caller) ) {
                malformatParameter(response, CALLER_NUMBER_PARAMETER);
                return;
            } else if (!Project4.checkPNumberPatten(callee) ) {
                malformatParameter(response, CALLEE_NUMBER_PARAMETER);
                return;
            }
            if (!checkDateTimeParameter(start) ) {
                malformatParameter(response, START_PARAMETER);
                return;
            } else if (!checkDateTimeParameter(end) ) {
                malformatParameter(response, END_PARAMETER);
                return;
            }
            bill.addPhoneCall(new PhoneCall(caller, callee, start, end));
        }

        response.setStatus( HttpServletResponse.SC_OK);
    }

    /**
     * Handles an HTTP DELETE request by removing all dictionary entries.  This
     * behavior is exposed for testing purposes only.  It's probably not
     * something that you'd want a real application to expose.
     */
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain");

        this.phoneBills.clear();

        PrintWriter pw = response.getWriter();
        pw.println(Messages.allPhoneBillEntriesDeleted());
        pw.flush();

        response.setStatus(HttpServletResponse.SC_OK);

    }

    /**
     * Writes an error message about a missing parameter to the HTTP response.
     *
     * The text of the error message is created by {@link Messages#missingRequiredParameter(String)}
     */
    private void missingRequiredParameter( HttpServletResponse response, String parameterName )
            throws IOException
    {
        String message = Messages.missingRequiredParameter(parameterName);
        response.sendError(HttpServletResponse.SC_PRECONDITION_FAILED, message);
    }

    /**
     * Writes an error message about a missing parameter to the HTTP response.
     *
     * The text of the error message is created by {@link Messages#missingRequiredParameter(String)}
     */
    private void malformatParameter( HttpServletResponse response, String parameterName )
            throws IOException
    {
        String message = Messages.malformatParameter(parameterName);
        response.sendError(HttpServletResponse.SC_PRECONDITION_FAILED, message);
    }

    /**
     * Writes an error message about a missing parameter to the HTTP response.
     *
     * The text of the error message is created by {@link Messages#missingRequiredParameter(String)}
     */
    private boolean checkDateTimeParameter(String timeParameter) {
        String[] dateTime = timeParameter.split(" ");
        if ( dateTime.length != 3) {
            return false;
        } else if (!Project4.checkDatePattern(dateTime[0])){
            return false;
        } else if (!Project4.checkTimePattern(dateTime[1] + " " + dateTime[2])) {
            return false;
        }
        return true;
    }

    /**
     * Returns the value of the HTTP request parameter with the given name.
     *
     * @return <code>null</code> if the value of the parameter is
     *         <code>null</code> or is the empty string
     */
    private String getParameter(String name, HttpServletRequest request) {
        String value = request.getParameter(name);
        if (value == null || "".equals(value)) {
            return null;

        } else {
            return value;
        }
    }

    @VisibleForTesting
    PhoneBill getPhoneBill(String customer) {
        return this.phoneBills.get(customer);
    }

    @VisibleForTesting
    void addPhoneBill(PhoneBill bill) {
        this.phoneBills.put(bill.getCustomer(), bill);
    }
}