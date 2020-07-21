package edu.pdx.cs410J.miyon;

import edu.pdx.cs410J.ParserException;
import edu.pdx.cs410J.PhoneBillParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

import static edu.pdx.cs410J.miyon.Project2.printErrorMessageAndExit;

/**
 * This class is represents a <code>TextParser</code>.
 */
public class TextParser implements PhoneBillParser<PhoneBill> {
    private final Reader reader;

    /**
     * Creates a new <code>TextParser</code>
     *
     * @param reader
     *        reader for text file
     */
    TextParser(Reader reader) {
        this.reader = reader;
    }

    /**
     * @return a <code>PhoneBill</code> after reading the text file
     */
    @Override
    public PhoneBill parse() throws ParserException {
        BufferedReader br = new BufferedReader(this.reader);
        try {
            String customerName = br.readLine();
            if ( customerName == null)
                customerName = "";
            PhoneBill bill = new PhoneBill(customerName);
            String phoneCallStr = br.readLine();
            while (phoneCallStr != null) {
                String[] phoneCallArgs = phoneCallStr.split(" ");
                if (phoneCallArgs.length == 8) {
                    PhoneCall call = new PhoneCall(phoneCallArgs[0], phoneCallArgs[1], phoneCallArgs[2], phoneCallArgs[3],
                            phoneCallArgs[4], phoneCallArgs[5], phoneCallArgs[6], phoneCallArgs[7]);
                    bill.addPhoneCall(call);
                } else {
                    br.close();
                    reader.close();
                    printErrorMessageAndExit("Text file has malformatted phone call");
                }
                phoneCallStr = br.readLine();
            }
            br.close();
            reader.close();
            return bill;
        } catch (IOException e) {
            throw new ParserException("While parsing text", e);
        }
    }
}
