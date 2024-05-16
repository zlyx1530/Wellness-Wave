import java.util.Date;

public class WellnessData {
	private int metricId;
    private String metricName;
    private String value;
    private long userId;
    private Date loggedAt;
    private Date date;


    public int getMetricId() {
        return metricId;
    }

    public void setMetricId(int metricId) {
        this.metricId = metricId;
    }
    
    public String getMetricName() {
        return metricName;
    }

    public void setMetricName(String metricName) {
        this.metricName = metricName;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
    
    public Date getDate() {
    	return date;
    }
    
    public void setDate(Date x) {
    	this.date = x;
    }
    
    public Date getLoggedAt() {
        return loggedAt;
    }

    public void setLoggedAt(Date y) {
        this.loggedAt = y;
    }
}
