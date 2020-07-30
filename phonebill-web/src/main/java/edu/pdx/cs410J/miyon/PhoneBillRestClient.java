package edu.pdx.cs410J.miyon;

import com.google.common.annotations.VisibleForTesting;
import edu.pdx.cs410J.ParserException;
import edu.pdx.cs410J.web.HttpRequestHelper;

import java.io.IOException;
import java.io.StringReader;
import java.util.Map;

import static edu.pdx.cs410J.miyon.PhoneBillURLParameters.*;
import static java.net.HttpURLConnection.HTTP_OK;

/**
 * A helper class for accessing the rest client.  Note that this class provides
 * an example of how to make gets and posts to a URL.
 */
public class PhoneBillRestClient extends HttpRequestHelper
{
    private static final String WEB_APP = "phonebill";
    private static final String SERVLET = "calls";
    private final String url;


    /**
     * Creates a client to the Phone Bill REST service running on the given host and port
     * @param hostName The name of the host
     * @param port The port
     */
    public PhoneBillRestClient( String hostName, int port )
    {
        this.url = String.format( "http://%s:%d/%s/%s", hostName, port, WEB_APP, SERVLET );
    }

    /**
     * Returns the phone bill for the given customer
     */
    public PhoneBill getPhoneBill(String customer) throws IOException, ParserException {
        Response response = get(this.url, Map.of(CUSTOMER_PARAMETER, customer));
        throwExceptionIfNotOkayHttpStatus(response);
        String content = response.getContent();
        TextParser parser = new TextParser(new StringReader(content));
        return parser.parse();
    }
    /**
     * Request Post to server to add a <code>PhoneCall</code>
     */
    public void addPhoneCall(String customer, String caller, String callee, String start, String end ) throws IOException {
        Response response = postToMyURL(Map.of(CUSTOMER_PARAMETER, customer, CALLER_NUMBER_PARAMETER, caller,
                CALLEE_NUMBER_PARAMETER, callee, START_PARAMETER, start, END_PARAMETER, end));
        throwExceptionIfNotOkayHttpStatus(response);
    }
    /**
     * Request Post to server
     */
    @VisibleForTesting
    Response postToMyURL(Map<String, String> phoneBillEntries) throws IOException {
        return post(this.url, phoneBillEntries);
    }
    /**
     * Request Delete Phone bills to server
     */
    public void removeAllPhoneBills() throws IOException {
        Response response = delete(this.url, Map.of());
        throwExceptionIfNotOkayHttpStatus(response);
    }
    /**
     * Throw exception if the response didn't get 200(HTTP_OK) code
     */
    private Response throwExceptionIfNotOkayHttpStatus(Response response) {
        int code = response.getCode();
        if (code != HTTP_OK) {
            throw new PhoneBillRestException(code);
        }
        return response;
    }

    /**
     * A helper class for throwing exception
     */
    @VisibleForTesting
    class PhoneBillRestException extends RuntimeException {
        private final int httpStatusCode;
        /**
         * Creates a new <code>PhoneBillRestException</code>
         * @param httpStatusCode
         *        a HTTP Status Code
         */
        PhoneBillRestException(int httpStatusCode) {
            super("Got an HTTP Status Code of " + httpStatusCode);
            this.httpStatusCode = httpStatusCode;
        }
        /**
         * Return a HTTP Status Code
         */
        public int getHttpStatusCode() {
            return this.httpStatusCode;
        }
    }

}