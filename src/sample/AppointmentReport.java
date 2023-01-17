package sample;

public class AppointmentReport {

    int count;
    int month;
    String type;

    public AppointmentReport(int count, int month, String type) {
        this.count = count;
        this.month = month;
        this.type = type;
    }


    /**
     * @return number of appointments of type and in month.
     */
    public int getCount() {
        return count;
    }

    /**
     * @return month evaluated
     */
    public int getMonth() {
        return month;
    }

    /**
     * @return type evaluated
     */
    public String getType() {
        return type;
    }
}
