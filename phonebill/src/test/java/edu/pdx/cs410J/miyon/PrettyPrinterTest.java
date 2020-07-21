package edu.pdx.cs410J.miyon;

import edu.pdx.cs410J.ParserException;
import org.junit.Test;

import java.io.*;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class PrettyPrinterTest {

    @Test
    public void prettyPrinterWritesCustomerName() throws IOException {
        StringWriter writer = new StringWriter();
        PrettyPrinter pDumper =  new PrettyPrinter(writer);

        String customerName = "Customer";
        PhoneBill bill = new PhoneBill(customerName);

        pDumper.dump(bill);

        String text = writer.toString();
        assertThat(text, containsString(customerName));

    }
}