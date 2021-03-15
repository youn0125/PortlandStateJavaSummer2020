package edu.pdx.cs410J.miyon;

import edu.pdx.cs410J.PhoneBillDumper;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;

/**
 * This class represents a <code>PrettyPrinter</code>.
 */
public class PrettyPrinter implements PhoneBillDumper<PhoneBill> {
    private final Writer writer;

    /**
     * Creates a new <code>PrettyPrinter</code>
     *
     * @param writer
     *        writer for printTo
     */
    public PrettyPrinter(Writer writer)
    {
        this.writer = writer;
    }

    /**
     * Write <code>PhoneBill</code> pretty to writer
     */
    @Override
    public void dump(PhoneBill bill) throws IOException {
        BufferedWriter bw = new BufferedWriter(this.writer);
        try {
            bw.write("Customer name: " + bill.getCustomer() +"\n");
            Collection<PhoneCall> calls = bill.getPhoneCalls();
            long duration = 0;
            DateFormat df = new SimpleDateFormat("E M d, y G h:mm a z");
            for ( PhoneCall call : calls) {
                bw.write(call.getCaller() + " called to " + call.getCallee() + " at " +
                        df.format(call.getStartTime()) + " and ended at " +
                        df.format(call.getEndTime()) + "." + " The duration of this call is " +
                        call.getDurationMinute() + " minutes.\n");
            }
            bw.close();
            writer.close();
        } catch (IOException e) {
            throw new IOException("While dumping text", e);
        }
    }
}
