package edu.pdx.cs410J.miyon;

import edu.pdx.cs410J.PhoneBillDumper;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;

public class PrettyPrinter implements PhoneBillDumper<PhoneBill> {
    private final Writer writer;

    /**
     * Creates a new <code>TextDumper</code>
     *
     * @param writer
     *        writer for text file
     */
    public PrettyPrinter(Writer writer)
    {
        this.writer = writer;
    }

    /**
     * Write <code>PhoneBill</code> to text file
     */
    @Override
    public void dump(PhoneBill bill) throws IOException {
        try {
            this.writer.write("Customer name: " + bill.getCustomer() +"\n");
            Collection<PhoneCall> calls = bill.getPhoneCalls();
            long duration = 0;
            DateFormat df = new SimpleDateFormat("E M d, y G h:mm a z");
            for ( PhoneCall call : calls) {
                this.writer.write(call.getCaller() + " called to " + call.getCallee() + " at " +
                        df.format(call.getStartTime()) + " and ended at " +
                        df.format(call.getEndTime()) + "." + " The duration of this call is" +
                        call.getDurationMinute() + ".\n");
            }
            writer.close();
        } catch (IOException e) {
            throw new IOException("While dumping text", e);
        }
    }
}
