import java.util.Scanner; //Scanner for user inputs
import java.time.format.DateTimeFormatter; //Formatting date and time
import java.time.LocalDateTime; //Getting local date and time
import java.util.ArrayList;	//Array list to store list of products

public class StockManagement {
	
	private static int ProductTypes = 4;
	
	//Main
	public static void main (String[] args) {
		Scanner input = new Scanner(System.in); //Define scanner for user input
		StaffInfo temp = new StaffInfo(); //Define StaffInfo temp for storing staff Info
		int no_product = 0;
		ArrayList<Product> ProductList = new ArrayList<Product>();
		
		//Displaying the welcome message and date time
		System.out.println("Welcome to Stock Management System");
		System.out.println("Current date time is : " + DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss").format(LocalDateTime.now()) + "\n");
		
		//Getting user name and staff ID
		temp.setName(getString(input, "your first name and surname"));
		temp.setID(getString(input, "your staff ID"));
		
		//Check if user wish to add any product
		if (checkAddProducts(input)) {
			//Prompt user for number of products
			no_product = getInt(input, "how many products would you like to add (0 to exit)", 0, Integer.MAX_VALUE);
		}
		
		//Get user to input information for each product
		for(int i = 1; i <= no_product; i++) {
			ProductList.add(addProduct(i, input));
		}
		
		//Display menu
		if(no_product > 0)
			Menu(input, ProductList);
		
		//Display UserID and StaffID when exit
		System.out.println();
		System.out.println("\nGoodbye "+temp.toString());
		
		//Close scanner
		input.close();
		return;
	}
		
	//Add Product
	private static Product addProduct(int count, Scanner input) {
		int ProductType;
		int ProductNumber;
		String ProductName;
		double ProductPrice;
		int ProductQuantity;
		Product temp = null;

		System.out.println();
		System.out.println("Please enter details for product number "+ count +" below :");
		
		//Setting Product Type
		ProductType = getInt(input, "product type (1 = Refrigerator, 2 = TV, 3 = Microwave, 4 = Air-Conditioner)", 1, ProductTypes);
		
		//Setting Product Quantity
		ProductNumber = getInt(input, getProductType(ProductType)+"'s Item Number", 0,Integer.MAX_VALUE);
		
		//Setting Product Name
		ProductName = getString(input, getProductType(ProductType) +  " name");
		
		//Setting Product Quantity
		ProductQuantity = getInt(input, getProductType(ProductType)+"'s total stock quantity", 0,Integer.MAX_VALUE);
		
		//Setting Product Price
		ProductPrice = getDouble(input, getProductType(ProductType)+"'s price", "RM", 0.00, Double.MAX_VALUE);
		
		switch(ProductType) {
		case 1:
			String r_door_design;
			String r_color;
			int r_capacity;
			
			//Setting door design
			r_door_design = getString(input, getProductType(ProductType) +  " door design");
			//Setting color
			r_color = getString(input, getProductType(ProductType) +  " color");
			//Setting capacity
			r_capacity = getInt(input, getProductType(ProductType)+"'s capacity (Litre)", 0,Integer.MAX_VALUE);
			temp = new Refrigerator(ProductName,ProductPrice,ProductQuantity,ProductNumber,r_door_design,r_color,r_capacity);
			break;
			
		case 2:
			String t_type;
			String t_resolution;
			int t_display_size;
			
			//Setting type
			t_type = getString(input, getProductType(ProductType) +  " type");
			//Setting resolution
			t_resolution = getString(input, getProductType(ProductType) +  " resolution");
			//Setting display size
			t_display_size = getInt(input, getProductType(ProductType)+"'s display size (Inch)", 0,Integer.MAX_VALUE);
			temp = new TV(ProductName,ProductPrice,ProductQuantity,ProductNumber,t_type,t_resolution,t_display_size);
			break;
			
		case 3:
			String m_color;
			int m_power;
			int m_volume;

			//Setting color
			m_color = getString(input, getProductType(ProductType) +  " color");
			//Setting power
			m_power = getInt(input, getProductType(ProductType)+"'s power (Watt)", 0,Integer.MAX_VALUE);
			//Setting volume
			m_volume = getInt(input, getProductType(ProductType)+"'s volume (Litre)", 0,Integer.MAX_VALUE);
			temp = new Microwave(ProductName,ProductPrice,ProductQuantity,ProductNumber,m_color,m_power,m_volume);
			break;
		
		case 4:
			String a_type;
			int a_power_consumption;
			double a_horse_power;

			//Setting type
			a_type = getString(input, getProductType(ProductType) +  " type");
			//Setting power consumption
			a_power_consumption = getInt(input, getProductType(ProductType)+"'s Power Consumption (Watt)", 0,Integer.MAX_VALUE);
			//Setting horse power
			a_horse_power = getDouble(input, getProductType(ProductType)+"'s Horse Power (HP)", "", 0,Integer.MAX_VALUE);
			temp = new AirConditioner(ProductName,ProductPrice,ProductQuantity,ProductNumber,a_type,a_power_consumption,a_horse_power);
			break;
			
		default:
			break;
		}
		
		return temp;
	}

	//Display menu and stock management
	private static void Menu(Scanner input, ArrayList<Product> List) {  
		int menu_opt = 0;
		int index = 0;
		int stock = 0;
		do {
			System.out.println();
			System.out.println("1. View stock");
			System.out.println("2. Add stock");
			System.out.println("3. Deduct stock");
			System.out.println("4. Discontinue a product");
			System.out.println("0. Exit");
			menu_opt = getInt(input, "a menu option", 0, 4);
			
			switch (menu_opt) {
			case 1:
				//Show all stock info with index number
				System.out.println();
				System.out.println("VIEW STOCK INFO\n");
				displayStockInfo(List);
				System.out.println("\nPress enter to go back to main menu...\n");
				input.nextLine();
				break;
				
			case 2:
				System.out.println();
				System.out.println("ADD STOCK\n");
				
				//Display which product for user to add stock too
				displayArrayItem(List);
				index = getInt(input, "item's index number you wish to add stock to", 0,List.size()-1);

				//Prints product info
				System.out.println();
				System.out.println("ADD STOCK\n");
				System.out.println(List.get(index).toString() + "\n");
				
				//Prompts user how many stocks to add and add it into the stock quantity
				stock = getInt(input, "quantity to add", 0,Integer.MAX_VALUE);
				if(List.get(index).addStock(stock))
					System.out.println("\nItem stock successfully added.");
				else
					System.out.println("\nFailed to add stock quantity for discontinued product.");
				
				System.out.println("Press enter to go back to main menu...\n");
				input.nextLine();
				break;
				
			case 3:
				System.out.println();
				System.out.println("DEDUCT STOCK\n");
				
				//Display which product for user to deduct stock from
				displayArrayItem(List);
				index = getInt(input, "item's index number you wish to deduct stock from", 0,List.size()-1);

				//Prints product info123
				System.out.println();
				System.out.println("DEDUCT STOCK\n");
				System.out.println(List.get(index).toString() + "\n");
				
				//Prompts user how many stocks to deduct and deduct it from the stock quantity
				stock = getInt(input, "quantity to deduct", 0,List.get(index).getQuantity());
				List.get(index).deductStock(stock);
				System.out.println("\nItem stock successfully deducted.");
					
				System.out.println("Press enter to go back to main menu...\n");
				input.nextLine();
				break;
				
			case 4:
				System.out.println();
				System.out.println("DISCONTINUE A PRODUCT\n");
				
				//Display which product for user to discontinue
				displayArrayItem(List);
				index = getInt(input, "item's index number you wish to discontinue", 0,List.size()-1);

				System.out.println();
				System.out.println("DISCONTINUE A PRODUCT");
				
				//Discontinue the product if product status is active
				if(List.get(index).getStatus() == "Active") {
					List.get(index).setStatus(false);
					System.out.println("\nProduct is now discontinued.\n");
				}
				else
					System.out.println("\nProduct is already discontinued.\n");
				
				//Prints product info
				System.out.println(List.get(index).toString() + "\n");

				System.out.println("Press enter to go back to main menu...\n");
				input.nextLine();
				break;
				
			case 0:
				break;
				
			default: 
				break;
			}
			
		} while(menu_opt != 0);
	}
	
	//Check if user wants to add any products
	public static boolean checkAddProducts(Scanner input) {
		String user_input = "yes";
		
		//only accept yes or no as input ignoring case sensitive
		do {
			if(!(user_input.equalsIgnoreCase("yes") || user_input.equalsIgnoreCase("no")))
				System.out.println("\nPlease enter only 'Yes' or 'No'.");
			
			System.out.print("Do you wish to add any product(s) ? :");
			user_input = input.nextLine();
		}while(!(user_input.equalsIgnoreCase("yes") || user_input.equalsIgnoreCase("no")));
		
		//if user doesn't want to enter product, mandate user to enter 0 to exit
		if(user_input.equalsIgnoreCase("no")) {
			getInt(input, "0 to exit", 0, 0);
			return false;
		}
		
		return true;
	}
	
	//Display all stock info
	public static void displayStockInfo(ArrayList<Product> List) {
		for (int i = 0; i < List.size(); i++) {
			  System.out.println("Item number: " + i);
			  System.out.println(List.get(i).toString() + "\n");
		}
	}
	
	//Display array index and item name
	public static void displayArrayItem(ArrayList<Product> List) {
		 System.out.println("Index No.\tProduct Name");
		for (int i = 0; i < List.size(); i++) {
			  System.out.println(i + "\t" + List.get(i).getName());
		}
	}

	//Get Product Type Name
	private static String getProductType(int type) {
		switch(type) {
		case 1:
			return "refrigerator";
		case 2:
			return "TV";
		case 3:
			return "microwave";
		case 4:
			return "air-conditioner";
		default:
			return "general product";
		}
	}
	
	//Get and validate integer value, make sure it is within range
	private static int getInt(Scanner input, String string1, int min, int max) {
		int user_input = min;
		boolean invalid = false;
		
		do {
			if((user_input < min || user_input > max) && !invalid)
				if(min == max)
					System.out.println("\nPlease enter only " + min + ".");
				else {
					if (max == Integer.MAX_VALUE)
						System.out.println("\nPlease enter a value of minimum " + min + ".");
					else
						System.out.println("\nPlease enter a value between " + min + " and " + max + ".");
				}
			
			if (invalid)
				System.out.println("\nInvalid input! Please enter a valid integer value.");
			
			System.out.print("Please enter "+ string1 +" : ");
			invalid = false;
			
			//Catch any exception entered such as alphabet entered for this integer
			try {
				user_input = input.nextInt();
			} catch (Exception e) {
				user_input = -1;
				invalid = true;
				input.nextLine();
			}			
			
		}while(user_input < min || user_input > max);

		input.nextLine();
		return user_input;
	}
	
	//Get and validate double value, make sure it is within range
	private static double getDouble(Scanner input, String string1, String string2, double min, double max) {
		double user_input = 1;
		boolean invalid = false;
		
		do {
			if((user_input < min || user_input > max) && !invalid)
				if(min == max)
					System.out.println("\nPlease enter only " + min + ".");
				else {
					if (max == Double.MAX_VALUE)
						System.out.println("\nPlease enter a value of minimum " + min + ".");
					else
						System.out.println("\nPlease enter a value between " + min + " and " + max + ".");
				}
			
			if (invalid)
				System.out.println("\nInvalid input! Please enter a valid double value.");
			
			System.out.print("Please enter "+ string1 +" : " + string2);
			invalid = false;
			
			//Catch any exception entered such as alphabet entered for this integer
			try {
				user_input = input.nextDouble();
			} catch (Exception e) {
				user_input = -1;
				invalid = true;
				input.nextLine();
			}			
			
		}while(user_input < min || user_input > max);
		
		input.nextLine();
		return user_input;
	}
	
	//Get and validate string making sure its not empty
	private static String getString(Scanner input, String string1) {
		String user_input = "temp";
		
		do {
			if(user_input.length() == 0)
				System.out.println("\nPlease enter non-empty string.");
			
			System.out.print("Please enter "+ string1 +" : ");
			
			user_input = input.nextLine();
			
		}while(user_input.length() == 0);
		
		return user_input;
	}
	
}
