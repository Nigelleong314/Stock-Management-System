
public class TV extends Product  {
	private String type;
	private String resolution;
	private int display_size;
	
	//Parameterized Constructor
	public TV(String name, double price, int quantity, int ID, String type, String resolution, int display_size) {
		super(name, price, quantity, ID);
		this.setType(type);
		this.setResolution(resolution);
		this.setSize(display_size);
	}
	
	//Set&Get function for type
	public void setType(String type) {
		this.type = type;
	}
	public String getType() {
		return this.type;
	}
	
	//Set&Get function for resolution
	public void setResolution(String resolution) {
		this.resolution = resolution;
	}
	public String getResolution() {
		return this.resolution;
	}
	
	//Set&Get function for display_size
	public void setSize(int display_size) {
		this.display_size = display_size;
	}
	public int getSize() {
		return this.display_size;
	}
	
	//Get refrigerator inventory value with 15% re-stocking fee
	@Override
	public double getValue() {
		return super.getValue()*1.15;
	}
	
	//toString
	@Override
	public String toString() {
		return ("Item number           : " + this.getID() + "\n" +
				"TV name               : " + this.getName() + "\n" +
				"TV type               : " + this.type + "\n" +
				"TV resolution         : " + this.resolution + "\n" +
				"TV display size (Inch): " + this.display_size + "\n" +
				"Quantity available    : " + this.getQuantity() + "\n" +
				"Price (RM)            : " + String.format("%.2f", this.getPrice()) + "\n" +
				"Stock value (RM)      : " + String.format("%.2f", this.getValue()) + "\n" +
				"Product status        : " + this.getStatus());
	}
}
