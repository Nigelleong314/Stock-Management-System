
abstract class Product {
	private String name;
	private double price;
	private int quantity;
	private int ID;
	private boolean status;
	
	//Default Constructor
	public Product() {
		this.name = "";
		this.price = 0.00;
		this.quantity = 0;
		this.ID = 0;
		this.status = true;
	}
	
	//Parameterized Constructor
	public Product(String name, double price, int quantity, int ID) {
		this.name = name;
		this.price = price;
		this.quantity = quantity;
		this.ID = ID;
		this.status = true;
	}
	
	//Set&Get function for name
	public void setName(String name) {
		this.name = name;
	}
	public String getName() {
		return this.name;
	}
	
	//Set&Get function for price
	public void setPrice(double price) {
		this.price = price;
	}
	public double getPrice() {
		return this.price;
	}
	
	//Set&Get function for quantity
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public int getQuantity() {
		return this.quantity;
	}
	
	//Set&Get function for ID
	public void setID(int ID) {
		this.ID = ID;
	}
	public int getID() {
		return this.ID;
	}
	
	//Set&Get function for status
	public void setStatus(boolean status) {
		this.status = status;
	}
	public String getStatus() {
		if (this.status)
			return "Active";
		else
			return "Inactive";
	}
	
	//Get inventory value
	public double getValue() {
		return this.price*this.quantity;
	}
	
	//Add inventory stock
	public boolean addStock(int stock) {
		if(this.status) {
			this.quantity = this.quantity+stock;
			return true;
		}
		else
			return false;
	}
	//Deduct inventory stock
	public boolean deductStock(int stock) {
		if(stock <= this.getQuantity()) {
			this.quantity = this.quantity-stock;
			return true;
		}
		else
			return false;
	}
	
	//toString
	@Override
	public String toString() {
		return ("Item number       : " + this.ID + "\n" +
				"Product name      : " + this.name + "\n" +
				"Quantity available: " + this.quantity + "\n" +
				"Price (RM)        : " + String.format("%.2f", this.price) + "\n" +
				"Stock value (RM)  : " + String.format("%.2f", this.getValue()) + "\n" +
				"Product status    : " + this.getStatus());
	}
}
