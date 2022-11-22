public class StaffInfo {
	private String name;
	private String code;
	private String ID;
	
	//Default Constructor
	public StaffInfo() {	
		this.name = "";
		this.code = "";
		this.ID = "";
	}
	
	//Constructor with name and ID
	public StaffInfo(String name, String ID) {	
		this.name = name;
		this.ID = ID;
		generateCode();
	}
	
	//Ask user for name and set user code
	public void setName(String name) {
		this.name = name;
		generateCode();
	}
	
	//get user code
	public String getCode() {
		return this.code;
	}
	//Set StaffID
	public void setID(String ID) {
		this.ID = ID;
	}
	//Get StaffID
	public String getID() {
		return this.ID;
	}
	
	//Check if name contain space
	private boolean containSpace() {
		return this.name.contains(" ");
	}
	
	//Generate user code
	private void generateCode() {
		if (containSpace()) {
			String[] words = this.name.split(" ");
			this.code = words[0].charAt(0) + words[words.length-1];
		}
		else
			this.code = "guest";
	}
	
	//toString
	@Override
	public String toString() {
		return this.getCode() + ", " + this.getID();
	}
}
