package edu.pdx.cs410J.miyon;

import edu.pdx.cs410J.ParserException;
import org.junit.Test;

import java.io.*;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class TextDumperTest {

    @Test
    public void textDumperWritesCustomerName() throws IOException, ParserException {
        StringWriter writer = new StringWriter();
        TextDumper dumper =  new TextDumper(writer);

        String customerName = "Customer";
        PhoneBill bill = new PhoneBill(customerName);

        dumper.dump(bill);

        String text = writer.toString();
        assertThat(text, containsString(customerName));



    }
}