package uk.co.sweby.counselform;

import java.util.StringTokenizer;

/**
 * Created by Paul on 25/12/2016.
 */

public class CounselHistoryItem implements Comparable {

    private static final String COMMA = "|";
    private String pointOfCounsel;
    private String dateSet;
    private String dateCompleted;

    public String getPointOfCounsel() {
        return pointOfCounsel;
    }

    public void setPointOfCounsel(String pointOfCounsel) {
        this.pointOfCounsel = pointOfCounsel;
    }

    public String getDateSet() {
        return dateSet;
    }

    public void setDateSet(String dateSet) {
        this.dateSet = dateSet;
    }

    public String getDateCompleted() {
        return dateCompleted;
    }

    public void setDateCompleted(String dateCompleted) {
        this.dateCompleted = dateCompleted;
    }

    public String toString() {
        return pointOfCounsel + COMMA + dateSet + COMMA + (dateCompleted != null ? dateCompleted : "N/A");
    }

    @Override
    public int compareTo(Object o) {
        if (o instanceof CounselHistoryItem) {
            CounselHistoryItem other = (CounselHistoryItem) o;
            String d1 = invertDate(this.getDateSet());
            String d2 = invertDate(other.getDateSet());
            return d1.compareTo(d2);
        }
        return -1;
    }

    private String invertDate(String s) {
        StringTokenizer st = new StringTokenizer(s, "/");
        String day = st.nextToken();
        String month = st.nextToken();
        String year = st.nextToken();
        return year + month + day;
    }
}
