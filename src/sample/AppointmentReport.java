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


    public int getCount() {
        return count;
    }

    public int getMonth() {
        return month;
    }

    public String getType() {
        return type;
    }
}
