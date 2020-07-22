package edu.pdx.cs410J.miyon;

import edu.pdx.cs410J.PhoneBillDumper;

import java.io.*;
import java.util.Collection;

/**
 * This class represents a <code>TextDumper</code>.
 */
public class TextDumper implements PhoneBillDumper<PhoneBill> {
    private final Writer writer;

    /**
     * Creates a new <code>TextDumper</code>
     *
     * @param writer
     *        writer for text file
     */
    public TextDumper(Writer writer) {
        this.writer = writer;
    }

    /**
     * Write <code>PhoneBill</code> to text file
     */
    @Override
    public void dump(PhoneBill bill) throws IOException {
        try {
            this.writer.write(bill.getCustomer() +"\n");
            Collection<PhoneCall> calls = bill.getPhoneCalls();
            for ( PhoneCall call : calls) {
                this.writer.write(call.getCaller() + " " + call.getCallee() + " " +
                        call.getStartTimeStringFromCommandLine() + " " + call.getEndTimeStringFromCommandLine() +"\n");
            }
            writer.close();
        } catch (IOException e) {
            throw new IOException("While dumping text", e);
        }
    }

}
