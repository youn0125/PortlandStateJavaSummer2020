package edu.pdx.cs410J.miyon;
import java.util.*;

/**
 * This class represents a <code>PhoneCallComparator</code>.
 * <code>PhoneCallComparator</code> specifies a total ordering over a set of <code>PhoneCall</code>
 */
public class PhoneCallComparator implements Comparator<PhoneCall> {

    /**
     * compare <code>PhoneCall</code>
     *
     * @param p1
     *        Receiver of <code>PhoneCall</code>
     * @param p2
     *        Another <code>PhoneCall</code>
     * @return result of comparison between receiver and another <code>PhoneCall</code>
     */
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