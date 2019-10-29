import java.time.LocalDate;

public class User {

	
	private Integer id;
	
	private LocalDate date;
	
	private StringBuilder corce;
	
	private String special;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public StringBuilder getCorce() {
		return corce;
	}

	public void setCorce(StringBuilder corce) {
		this.corce = corce;
	}

	public String getSpecial() {
		return special;
	}

	public void setSpecial(String special) {
		this.special = special;
	}


	
}
