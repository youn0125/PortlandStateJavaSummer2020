package edu.pdx.cs410J.miyon;

import edu.pdx.cs410J.ParserException;
import edu.pdx.cs410J.PhoneBillParser;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import static edu.pdx.cs410J.miyon.Project2.printErrorMessageAndExit;

public class TextParser implements PhoneBillParser<PhoneBill> {
    private File myFile;

    TextParser(File myFile) {
        this.myFile = myFile;
    }
    public PhoneBill parse() throws ParserException {

        try {
            Scanner myReader = new Scanner(myFile);
            String customer = myReader.nextLine();
            PhoneBill bill = new PhoneBill(customer);
            while (myReader.hasNextLine()) {
                String phoneCallStr = myReader.nextLine();
                String[] phoneCallArgs = phoneCallStr.split(" ");
                if (phoneCallArgs.length == 6) {
                    PhoneCall call = new PhoneCall(phoneCallArgs[0], phoneCallArgs[1], phoneCallArgs[2], phoneCallArgs[3],
                            phoneCallArgs[4], phoneCallArgs[5]);
                    bill.addPhoneCall(call);
                } else {
                    printErrorMessageAndExit("Text file has malformatted phone call");
                }

            }
            myReader.close();
            return bill;
        } catch (FileNotFoundException e){
            System.out.println("FileNotFoundException occurred");
            e.printStackTrace();
        }

        return null;
    }
}
