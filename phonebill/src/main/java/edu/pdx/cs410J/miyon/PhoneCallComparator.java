package edu.pdx.cs410J.miyon;
import java.util.*;

public class PhoneCallComparator implements Comparator<PhoneCall> {
    public int compare(PhoneCall p1, PhoneCall p2) {
        Date startTime1 = p1.getStartTime();
        Date startTime2 = p2.getStartTime();
        String caller1 = p1.getCaller();
        String caller2 = p2.getCaller();
        if (startTime1.compareTo(startTime2) > 0) {
            return 1;
        } else if (startTime1.compareTo(startTime2) < 0) {
            return -1;
        } else {
            if ( caller1.compareTo(caller2) > 0){
                return 1;
            } else if ( caller1.compareTo(caller2) < 0){
                return -1;
            } else {
                return 0;
            }
        }
    }
}