
public class Microwave extends Product {
	private String color;
	private int power;
	private int volume;
	
	//Parameterized Constructor
	public Microwave(String name, double price, int quantity, int ID, String color, int power, int volume) {
		super(name, price, quantity, ID);
		this.setColor(color);
		this.setPower(power);
		this.setVolume(volume);
	}
	
	//Set&Get function for color
	public void setColor(String color) {
		this.color = color;
	}
	public String getColor() {
		return this.color;
	}
	
	//Set&Get function for power
	public void setPower(int power) {
		this.power = power;
	}
	public int getPower() {
		return this.power;
	}
	
	//Set&Get function for volume
	public void setVolume(int volume) {
		this.volume = volume;
	}
	public int getVolume() {
		return this.volume;
	}
	
	//Get refrigerator inventory value with 18% re-stocking fee
	@Override
	public double getValue() {
		return super.getValue()*1.18;
	}
	
	//toString
	@Override
	public String toString() {
		return ("Item number             : " + this.getID() + "\n" +
				"Microwave name          : " + this.getName() + "\n" +
				"Microwave color         : " + this.color + "\n" +
				"Microwave power (Watt)  : " + this.power + "\n" +
				"Microwave volume (Litre): " + this.volume + "\n" +
				"Quantity available      : " + this.getQuantity() + "\n" +
				"Price (RM)              : " + String.format("%.2f", this.getPrice()) + "\n" +
				"Stock value (RM)        : " + String.format("%.2f", this.getValue()) + "\n" +
				"Product status          : " + this.getStatus());
	}
}
