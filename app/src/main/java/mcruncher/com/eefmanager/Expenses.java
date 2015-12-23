package mcruncher.com.eefmanager;

/**
 * Created by vignesh on 22/12/2015.
 */
public class Expenses {

    private String dateTime;
    private String expenseName;
    private int value;

    public Expenses(String dateTime, String expenseName, int value) {
        this.dateTime = dateTime;
        this.expenseName = expenseName;
        this.value = value;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getExpenseName() {
        return expenseName;
    }

    public void setExpenseName(String expenseName) {
        this.expenseName = expenseName;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
