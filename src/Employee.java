import java.time.LocalDateTime;

public class Employee {

	private String name;
	private LocalDateTime dateTime;
	private LocalDateTime startTime;
	private LocalDateTime endTime;
	private long minute;
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public LocalDateTime getDateTime() {
		return dateTime;
	}
	public void setDateTime(LocalDateTime dateTime) {
		this.dateTime = dateTime;
	}
	public LocalDateTime getStartTime() {
		return startTime;
	}
	public void setStartTime(LocalDateTime startTime) {
		this.startTime = startTime;
	}
	public LocalDateTime getEndTime() {
		return endTime;
	}
	public void setEndTime(LocalDateTime endTime) {
		this.endTime = endTime;
	}
	public long getMinute() {
		return minute;
	}
	public void setMinute(long minute) {
		this.minute = minute;
	}
	@Override
	public String toString() {
		return "Employee [name=" + name + ", dateTime=" + dateTime + ", startTime=" + startTime + ", endTime=" + endTime
				+ ", minute=" + minute + "]";
	}

	
	
	

	
	
}
