package edu.pdx.cs410J.miyon;

import edu.pdx.cs410J.PhoneBillDumper;

import java.io.IOException;
import java.io.PrintWriter;

public class TextDumper implements PhoneBillDumper<PhoneBill> {
    private final PrintWriter  writer;

    /**
     * Creates a new <code>TextDumper</code>
     *
     * @param writer
     *        writer for text file
     */
    public TextDumper(PrintWriter writer) {
        this.writer = writer;
    }

    /**
     * Write <code>PhoneBill</code> to text file
     */
    @Override
    public void dump(PhoneBill bill) throws IOException {
        this.writer.println(bill.getCustomer());

        for(PhoneCall call : bill.getPhoneCalls()) {
            this.writer.println(call.getCaller() + " " + call.getCallee() + " " +
                    call.getStartTimeString() + " " + call.getEndTimeString());
        }
    }
}
