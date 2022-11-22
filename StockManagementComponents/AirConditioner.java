
public class AirConditioner extends Product {
	private String type;
	private int power_consumption;
	private double horse_power;
	
	//Parameterized Constructor
	public AirConditioner(String name, double price, int quantity, int ID, String type, int power_consumption, double horse_power) {
		super(name, price, quantity, ID);
		this.setType(type);
		this.setPowerConsumption(power_consumption);
		this.setHorsePower(horse_power);
	}
	
	//Set&Get function for type
	public void setType(String type) {
		this.type = type;
	}
	public String getType() {
		return this.type;
	}
	
	//Set&Get function for power
	public void setPowerConsumption(int power_consumption) {
		this.power_consumption = power_consumption;
	}
	public int getPowerConsumption() {
		return this.power_consumption;
	}
	
	//Set&Get function for capacity
	public void setHorsePower(double horse_power) {
		this.horse_power = horse_power;
	}
	public double getHorsePower() {
		return this.horse_power;
	}
	
	//Get air-conditioner inventory value with 20% re-stocking fee
	@Override
	public double getValue() {
		return super.getValue()*1.20;
	}
	
	//toString
	@Override
	public String toString() {
		return ("Item number                             : " + this.getID() + "\n" +
				"Air-Conditioner name                    : " + this.getName() + "\n" +
				"Air-Conditioner type                    : " + this.type + "\n" +
				"Air-Conditioner Power Consumption (Watt): " + this.power_consumption + "\n" +
				"Air-Conditioner Horse Power (HP)        : " + this.horse_power + "\n" +
				"Quantity available                      : " + this.getQuantity() + "\n" +
				"Price (RM)                              : " + String.format("%.2f", this.getPrice()) + "\n" +
				"Stock value (RM)                        : " + String.format("%.2f", this.getValue()) + "\n" +
				"Product status                          : " + this.getStatus());
	}
}
