package edu.pdx.cs410J.miyon;

import edu.pdx.cs410J.ParserException;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;

public class TextParserTest {

    @Test
    public void phoneBillFromTextFileContainsCustomerName() throws ParserException {
        InputStream testFile = getClass().getResourceAsStream("PhoneBillWithCustomer.txt");
        TextParser parser = new TextParser(new InputStreamReader(testFile));

        PhoneBill bill = parser.parse();

        assertThat(bill.getCustomer(), equalTo("Customer"));
    }



}