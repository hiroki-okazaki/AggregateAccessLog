import java.time.LocalDateTime;

public class GetterSetter {

	
	private LocalDateTime dateTime;
	
	private Integer lessthan500;
	
	private Integer lessthan2000;
	
	private Integer morethan2001;
	
	private Integer avarage;
	
	
	public Double avarage() {
		if((lessthan500 + lessthan2000 + morethan2001) == 0) {
			return (double) 0;
		}
		
		Double answer =  (double) (avarage / (lessthan500 + lessthan2000 + morethan2001));
		return answer;
	}
	
	

	public LocalDateTime getDateTime() {
		return dateTime;
	}

	public void setDateTime(LocalDateTime dateTime) {
		this.dateTime = dateTime;
	}

	public Integer getLessthan500() {
		return lessthan500;
	}

	public void setLessthan500(Integer lessthan500) {
		this.lessthan500 = lessthan500;
	}

	public Integer getLessthan2000() {
		return lessthan2000;
	}

	public void setLessthan2000(Integer lessthan2000) {
		this.lessthan2000 = lessthan2000;
	}

	public Integer getMorethan2001() {
		return morethan2001;
	}

	public void setMorethan2001(Integer morethan2001) {
		this.morethan2001 = morethan2001;
	}

	public Integer getAvarage() {
		return avarage;
	}

	public void setAvarage(Integer avarage) {
		this.avarage = avarage;
	}

	@Override
	public String toString() {
		return "getterSetter [dateTime=" + dateTime + ", lessthan500=" + lessthan500 + ", lessthan2000=" + lessthan2000
				+ ", morethan2001=" + morethan2001 + ", avarage=" + avarage + "]";
	}
	
	
}
