package edu.pdx.cs410J.miyon;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class for formatting messages on the server side.  This is mainly to enable
 * test methods that validate that the server returned expected strings.
 */
public class Messages
{
    /**
     * Returns an error message about a missing parameter to the HTTP response.
     */
    public static String missingRequiredParameter( String parameterName )
    {
        return String.format("The required parameter \"%s\" is missing", parameterName);
    }
    /**
     * Returns an error message about a malformatted parameter to the HTTP response.
     */
    public static String malformatParameter( String parameterName )
    {
        return String.format("The parameter \"%s\" is malforamtted", parameterName);
    }
    /**
     * Returns a message about deleting all phone bill entries
     */
    public static String allPhoneBillEntriesDeleted() {
        return "All phone bill entries have been deleted";
    }

    /**
     * Returns a message about no phone bill for customer
     * @Param String customerName
     */
    static String noPhoneBillForCustomer(String customerName) {
        return String.format("No phone bill for customer %s", customerName);
    }
}