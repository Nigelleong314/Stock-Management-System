
public class Refrigerator extends Product {
	private String door_design;
	private String color;
	private int capacity;
	
	//Parameterized Constructor
	public Refrigerator(String name, double price, int quantity, int ID, String design, String color, int capacity) {
		super(name, price, quantity, ID);
		this.setDesign(design);
		this.setColor(color);
		this.setCapacity(capacity);
	}
	
	//Set&Get function for door design
	public void setDesign(String design) {
		this.door_design = design;
	}
	public String getDesign() {
		return this.door_design;
	}
	
	//Set&Get function for color
	public void setColor(String color) {
		this.color = color;
	}
	public String getColor() {
		return this.color;
	}
	
	//Set&Get function for capacity
	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}
	public int getCapacity() {
		return this.capacity;
	}
	
	//Get refrigerator inventory value with 25% re-stocking fee
	@Override
	public double getValue() {
		return super.getValue()*1.25;
	}
	
	//toString
	@Override
	public String toString() {
		return ("Item number                  : " + this.getID() + "\n" +
				"Refrigerator name            : " + this.getName() + "\n" +
				"Refrigerator door            : " + this.door_design + "\n" +
				"Refrigerator color           : " + this.color + "\n" +
				"Refrigerator capacity (Litre): " + this.capacity + "\n" +
				"Quantity available           : " + this.getQuantity() + "\n" +
				"Price (RM)                   : " + String.format("%.2f", this.getPrice()) + "\n" +
				"Stock value (RM)             : " + String.format("%.2f", this.getValue()) + "\n" +
				"Product status               : " + this.getStatus());
	}
}
