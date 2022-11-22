import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class StockManagementGUI extends Application {
	private static boolean confirmation;
	private static int no_product = 0;
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		//Stock Management Info
		ArrayList<Product> ProductList = new ArrayList<Product>();
		StaffInfo staff = new StaffInfo(); 
		
		//GUI Variable for window, each Scene and Pane
		Stage window;
		Scene Sc_Login, Sc_Add, Sc_No_Add, Sc_Product, Sc_Menu, Sc_ViewStock, Sc_AddStock, Sc_DeductStock, Sc_Discontinue;
		GridPane Pn_Login, Pn_Add, Pn_No_Add, Pn_Refrigerator, Pn_TV, Pn_Microwave, Pn_AirConditioner, Pn_Menu, Pn_ViewStock, Pn_AddStock, Pn_DeductStock, Pn_Discontinue;
		
		//Set window to main window
		window = primaryStage;
		
		//==============================
		//Discontinue Product Scene
		//==============================
		//Creating tables and columns
		TableView tbl_Discontinue = new TableView();
		tbl_Discontinue.setEditable(false);
		//Bind column to ID
		TableColumn colIndex_Discontinue = new TableColumn("Index No.");
        colIndex_Discontinue.setCellValueFactory(new PropertyValueFactory<>("ID"));
        colIndex_Discontinue.setMinWidth(100);
        //Bind column to name
        TableColumn colName_Discontinue = new TableColumn("Product Name");
        colName_Discontinue.setCellValueFactory(new PropertyValueFactory<>("name"));
        colName_Discontinue.setMinWidth(300);
        //Bind column to quantity
        TableColumn colQuantity_Discontinue = new TableColumn("Quantity");
        colQuantity_Discontinue.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        colQuantity_Discontinue.setMinWidth(100);
        //Bind column to status
        TableColumn colStatus_Discontinue = new TableColumn("Status");
        colStatus_Discontinue.setCellValueFactory(new PropertyValueFactory<>("status"));
        colStatus_Discontinue.setMinWidth(100);
        tbl_Discontinue.getColumns().addAll(colIndex_Discontinue, colName_Discontinue, colQuantity_Discontinue, colStatus_Discontinue);
        
        //Creating Labels and buttons
        Button btnBack_Discontinue = new Button("Back");
        TextField txtIndex_Discontinue = new TextField();
        Button btnDiscontinue = new Button("Discontinue Product");
        
        //Setting pane
        Pn_Discontinue = new GridPane();
        Pn_Discontinue.setAlignment(Pos.CENTER);
        Pn_Discontinue.setPadding(new Insets(15));
        Pn_Discontinue.setHgap(5);
        Pn_Discontinue.setVgap(5);
        //Adding everything to pane
        Pn_Discontinue.add(tbl_Discontinue, 0, 0, 6, 1);
        Pn_Discontinue.add(btnBack_Discontinue, 0, 1);
        Pn_Discontinue.add(new Label("\t\t\t\t\t\t"), 1, 1);
        Pn_Discontinue.add(new Label("Index No:"), 2, 1);
        Pn_Discontinue.add(txtIndex_Discontinue, 3, 1);
        Pn_Discontinue.add(new Label(" "), 4, 1);
        Pn_Discontinue.add(btnDiscontinue, 5, 1);
        
        //Button Function - Validate user input and make sure [quantity to be deducted] is less or equals to [product quantity]
        btnDiscontinue.setOnAction(
			e -> {
				if(!checkInt(txtIndex_Discontinue, "Index Number", 0, ProductList.size()-1)){}
				else {
					//Get product into temp variable for ease of use
					Product temp = ProductList.get(Integer.parseInt(txtIndex_Discontinue.getText()));
					
					//Creating new product with index no as ID to be displayed
					Product tempInsert = new Refrigerator(temp.getName(), 
														  temp.getPrice(), 
														  temp.getQuantity(), 
														  Integer.parseInt(txtIndex_Discontinue.getText()), 
														  "", 
														  "", 
														  0);
					
					//Prompt confirmation to set discontinue
					if(temp.getStatus() == "Active") {
						if(Display("Discontinue", "Are you sure to discontinue product " + txtIndex_Discontinue.getText() + ": " + temp.getName() + "?")) {
							//Setting product status to inactive
							temp.setStatus(false);
							tempInsert.setStatus(false);
							
							//Remove the product from tableview and insert the updated product, then refresh the table
							ObservableList olDiscontinue = tbl_Discontinue.getItems();
							olDiscontinue.remove(Integer.parseInt(txtIndex_Discontinue.getText()));
							olDiscontinue.add(Integer.parseInt(txtIndex_Discontinue.getText()), tempInsert);
							tbl_Discontinue.setItems(olDiscontinue);
							tbl_Discontinue.refresh();

							//Tell user product discontinued
							Alert("Discontinue","Product " + temp.getName() + " status has been set to inactive.");
						}
					}
					else {
						//Tell user product already discontinued
						Alert("Discontinue","Product " + temp.getName() + " is already inactive.");
					}

					//Clear text input field
					txtIndex_Discontinue.setText("");
				}
			}
		);
        
        //Setting Deduct Stock scene
        Sc_Discontinue = new Scene(Pn_Discontinue);
		
		
		//==============================
		//Deduct Stock Scene
		//==============================
		//Creating tables and columns
		TableView tbl_DeductStock = new TableView();
		tbl_DeductStock.setEditable(false);
		//Bind column to ID
		TableColumn colIndex_DeductStock = new TableColumn("Index No.");
        colIndex_DeductStock.setCellValueFactory(new PropertyValueFactory<>("ID"));
        colIndex_DeductStock.setMinWidth(100);
        //Bind column to name
        TableColumn colName_DeductStock = new TableColumn("Product Name");
        colName_DeductStock.setCellValueFactory(new PropertyValueFactory<>("name"));
        colName_DeductStock.setMinWidth(300);
        //Bind column to quantity
        TableColumn colQuantity_DeductStock = new TableColumn("Quantity");
        colQuantity_DeductStock.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        colQuantity_DeductStock.setMinWidth(100);
        //Bind column to status
        TableColumn colStatus_DeductStock = new TableColumn("Status");
        colStatus_DeductStock.setCellValueFactory(new PropertyValueFactory<>("status"));
        colStatus_DeductStock.setMinWidth(100);
        tbl_DeductStock.getColumns().addAll(colIndex_DeductStock, colName_DeductStock, colQuantity_DeductStock, colStatus_DeductStock);
        
        //Creating Labels and buttons
        Button btnBack_DeductStock = new Button("Back");
        TextField txtIndex_DeductStock = new TextField();
        TextField txtQuantity_DeductStock = new TextField();
        Button btnDeduct_DeductStock = new Button("Deduct Stock");
        
        //Setting pane
        Pn_DeductStock = new GridPane();
        Pn_DeductStock.setAlignment(Pos.CENTER);
        Pn_DeductStock.setPadding(new Insets(15));
        Pn_DeductStock.setHgap(5);
        Pn_DeductStock.setVgap(5);
        //Adding everything to pane
        Pn_DeductStock.add(tbl_DeductStock, 0, 0, 9, 1);
        Pn_DeductStock.add(btnBack_DeductStock, 0, 1);
        Pn_DeductStock.add(new Label("\t"), 1, 1);
        Pn_DeductStock.add(new Label("Index No:"), 2, 1);
        Pn_DeductStock.add(txtIndex_DeductStock, 3, 1);
        Pn_DeductStock.add(new Label(" "), 4, 1);
        Pn_DeductStock.add(new Label("Quantity:"), 5, 1);
        Pn_DeductStock.add(txtQuantity_DeductStock, 6, 1);
        Pn_DeductStock.add(new Label(" "), 7, 1);
        Pn_DeductStock.add(btnDeduct_DeductStock, 8, 1);
        
        //Button Function - Validate user input and make sure quantity to be deducted is less or equals to product quantity
        btnDeduct_DeductStock.setOnAction(
			e -> {
				if(!checkInt(txtIndex_DeductStock, "Index Number", 0, ProductList.size()-1)){}
				else if(!checkInt(txtQuantity_DeductStock, "Quantity", 0, ProductList.get(Integer.parseInt(txtIndex_DeductStock.getText())).getQuantity())){}
				else {
					//Get the product into temp for ease of use
					Product temp = ProductList.get(Integer.parseInt(txtIndex_DeductStock.getText()));
					
					//Deduct the quantity from product list
					temp.deductStock(Integer.parseInt(txtQuantity_DeductStock.getText()));
					
					//Creating new product with index no as ID to be displayed
					Product tempInsert = new Refrigerator(temp.getName(), 
														  temp.getPrice(), 
														  temp.getQuantity(), 
														  Integer.parseInt(txtIndex_DeductStock.getText()), 
														  "", 
														  "", 
														  0);
					
					if(temp.getStatus() == "Inactive")
						tempInsert.setStatus(false);
					
					//Remove the product from tableview and insert the updated product, then refresh the table
					ObservableList olDeductStock = tbl_DeductStock.getItems();
					olDeductStock.remove(Integer.parseInt(txtIndex_DeductStock.getText()));
					olDeductStock.add(Integer.parseInt(txtIndex_DeductStock.getText()), tempInsert);
					tbl_DeductStock.setItems(olDeductStock);
					tbl_DeductStock.refresh();
					
					//Tell user product stock deducted
					Alert("Deduct Stock","Product " + temp.getName() + " stock has been updated to " + temp.getQuantity() + ".");
				}

				//Clear text input field
				txtIndex_DeductStock.setText("");
				txtQuantity_DeductStock.setText("");
			}
		);
        
        //Setting Deduct Stock scene
        Sc_DeductStock = new Scene(Pn_DeductStock);
		

		//==============================
		//Add Stock Scene
		//==============================
		//Creating tables and columns
		TableView tbl_AddStock = new TableView();
		tbl_AddStock.setEditable(false);
		//Bind column to ID
		TableColumn colIndex_AddStock = new TableColumn("Index No.");
        colIndex_AddStock.setCellValueFactory(new PropertyValueFactory<>("ID"));
        colIndex_AddStock.setMinWidth(100);
        //Bind column to name
        TableColumn colName_AddStock = new TableColumn("Product Name");
        colName_AddStock.setCellValueFactory(new PropertyValueFactory<>("name"));
        colName_AddStock.setMinWidth(300);
        //Bind column to quantity
        TableColumn colQuantity_AddStock = new TableColumn("Quantity");
        colQuantity_AddStock.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        colQuantity_AddStock.setMinWidth(100);
        //Bind column to status
        TableColumn colStatus_AddStock = new TableColumn("Status");
        colStatus_AddStock.setCellValueFactory(new PropertyValueFactory<>("status"));
        colStatus_AddStock.setMinWidth(100);
        tbl_AddStock.getColumns().addAll(colIndex_AddStock, colName_AddStock, colQuantity_AddStock, colStatus_AddStock);
        
        //Creating Labels and buttons
        Button btnBack_AddStock = new Button("Back");
        TextField txtIndex_AddStock = new TextField();
        TextField txtQuantity_AddStock = new TextField();
        Button btnAdd_AddStock = new Button("Add Stock");
        
        //Setting pane
        Pn_AddStock = new GridPane();
        Pn_AddStock.setAlignment(Pos.CENTER);
        Pn_AddStock.setPadding(new Insets(15));
        Pn_AddStock.setHgap(5);
        Pn_AddStock.setVgap(5);
        //Adding everything to pane
        Pn_AddStock.add(tbl_AddStock, 0, 0, 9, 1);
        Pn_AddStock.add(btnBack_AddStock, 0, 1);
        Pn_AddStock.add(new Label("\t"), 1, 1);
        Pn_AddStock.add(new Label("Index No:"), 2, 1);
        Pn_AddStock.add(txtIndex_AddStock, 3, 1);
        Pn_AddStock.add(new Label(" "), 4, 1);
        Pn_AddStock.add(new Label("Quantity:"), 5, 1);
        Pn_AddStock.add(txtQuantity_AddStock, 6, 1);
        Pn_AddStock.add(new Label(" "), 7, 1);
        Pn_AddStock.add(btnAdd_AddStock, 8, 1);
        
        //Button Function - validate input
        btnAdd_AddStock.setOnAction(
			e -> {
				if(!checkInt(txtIndex_AddStock, "Index Number", 0, ProductList.size()-1)){}
				else if(!checkInt(txtQuantity_AddStock, "Quantity", 0, Integer.MAX_VALUE)){}
				else {
					//Get the product from product list into temp for ease of use
					Product temp = ProductList.get(Integer.parseInt(txtIndex_AddStock.getText()));
					
					//Apply the add product to that product in the product list
					if(temp.addStock(Integer.parseInt(txtQuantity_AddStock.getText()))) {
						
						//Creating new product with index no as ID to be displayed
						Product tempInsert = new Refrigerator(temp.getName(), 
															  temp.getPrice(), 
															  temp.getQuantity(), 
															  Integer.parseInt(txtIndex_AddStock.getText()), 
															  "", 
															  "", 
															  0);
						
						if(temp.getStatus() == "Inactive")
							tempInsert.setStatus(false);
						
						//Remove the product from tableview and insert the updated product, then refresh the table
						ObservableList olAddStock = tbl_AddStock.getItems();
						olAddStock.remove(Integer.parseInt(txtIndex_AddStock.getText()));
						olAddStock.add(Integer.parseInt(txtIndex_AddStock.getText()), tempInsert);
						tbl_AddStock.setItems(olAddStock);
						tbl_AddStock.refresh();
						
						//Tell user product stock added
						Alert("Add Stock","Product " + temp.getName() + " stock has been updated to " + temp.getQuantity() + ".");
					}
					else
						Alert("Add Stock","Failed to add stock for " + temp.getName() + " due to status inactive.");
				}

				//Clear text input field
				txtIndex_AddStock.setText("");
				txtQuantity_AddStock.setText("");
			}
		);
        
        //Setting add stock scene
        Sc_AddStock = new Scene(Pn_AddStock);
        
		
		//==============================
		//View Stock Scene
		//==============================
		//Setting labels, text and buttons
		Label lblProductNo_View = new Label();
		lblProductNo_View.setFont(Font.font("Calibri", FontWeight.BOLD, FontPosture.REGULAR, 16));
		TextField txtType_View = new TextField();
		txtType_View.setEditable(false);
		TextField txtNumber_View = new TextField();
		txtNumber_View.setEditable(false);
		TextField txtName_View = new TextField();
		txtName_View.setEditable(false);
		Label lblDetail1_View = new Label();
		TextField txtDetail1_View = new TextField();
		txtDetail1_View.setEditable(false);
		Label lblDetail2_View = new Label();
		TextField txtDetail2_View = new TextField();
		txtDetail2_View.setEditable(false);
		Label lblDetail3_View = new Label();
		TextField txtDetail3_View = new TextField();
		txtDetail3_View.setEditable(false);
		TextField txtQuantity_View = new TextField();
		txtQuantity_View.setEditable(false);
		TextField txtPrice_View = new TextField();
		txtPrice_View.setEditable(false);
		TextField txtStock_View = new TextField();
		txtStock_View.setEditable(false);
		TextField txtStatus_View = new TextField();
		txtStatus_View.setEditable(false);
		Button btnBack_View = new Button("Back");
		Button btnPrev_View = new Button("\u25C0");
		Button btnNext_View = new Button("\u25B6");
		
		//Setting pane size
		Pn_ViewStock = new GridPane();
		Pn_ViewStock.setAlignment(Pos.CENTER);
		Pn_ViewStock.setPadding(new Insets(15));
		Pn_ViewStock.setHgap(5);
		Pn_ViewStock.setVgap(5);
		Pn_ViewStock.setMinWidth(410);
		//Adding everything onto pane
		Pn_ViewStock.add(lblProductNo_View, 0, 0, 2, 1);
		Pn_ViewStock.add(new Label("Product Type: "), 0, 1);
		Pn_ViewStock.add(txtType_View, 1, 1);
		Pn_ViewStock.add(new Label("Product Number: "), 0, 2);
		Pn_ViewStock.add(txtNumber_View, 1, 2);
		Pn_ViewStock.add(new Label("Name: "), 0, 3);
		Pn_ViewStock.add(txtName_View, 1, 3);
		Pn_ViewStock.add(lblDetail1_View, 0, 4);
		Pn_ViewStock.add(txtDetail1_View, 1, 4);
		Pn_ViewStock.add(lblDetail2_View, 0, 5);
		Pn_ViewStock.add(txtDetail2_View, 1, 5);
		Pn_ViewStock.add(lblDetail3_View, 0, 6);
		Pn_ViewStock.add(txtDetail3_View, 1, 6);
		Pn_ViewStock.add(new Label("Quantity available: "), 0, 7);
		Pn_ViewStock.add(txtQuantity_View, 1, 7);
		Pn_ViewStock.add(new Label("Price (RM): "), 0, 8);
		Pn_ViewStock.add(txtPrice_View, 1, 8);
		Pn_ViewStock.add(new Label("Stock Value (RM): "), 0, 9);
		Pn_ViewStock.add(txtStock_View, 1, 9);
		Pn_ViewStock.add(new Label("Status: "), 0, 10);
		Pn_ViewStock.add(txtStatus_View, 1, 10);
		
		//Create a horizontal box for prev and next button
		//Add the horizontal box onto last item of pane with the back button
		//Align back button and prev next button
		HBox hbBtm_View = new HBox();
		hbBtm_View.getChildren().addAll(btnPrev_View,btnNext_View);
		Pn_ViewStock.add(btnBack_View, 0, 11);
		Pn_ViewStock.add(hbBtm_View, 1, 11);
		hbBtm_View.setAlignment(Pos.CENTER_RIGHT);
		GridPane.setHalignment(btnBack_View, HPos.LEFT);
		GridPane.setHalignment(hbBtm_View, HPos.RIGHT);
		GridPane.setHgrow(hbBtm_View, Priority.ALWAYS);
		
		//Button Functions - show previous product, disable prev button if product = 1
		btnPrev_View.setOnAction(
			e -> {
				//Setting value of 1st product
				no_product -=1 ;
				lblProductNo_View.setText("Product " + (no_product+1) + " of " + ProductList.size());
				txtNumber_View.setText(Integer.toString(ProductList.get(no_product).getID()));
				txtName_View.setText(ProductList.get(no_product).getName());
				txtQuantity_View.setText(Integer.toString(ProductList.get(no_product).getQuantity()));
				txtPrice_View.setText(String.format("%.2f", ProductList.get(no_product).getPrice()));
				txtStock_View.setText(String.format("%.2f", ProductList.get(no_product).getValue()));
				txtStatus_View.setText(ProductList.get(no_product).getStatus());
				
				//Setting dynamic values for different type of products
				if(ProductList.get(no_product) instanceof Refrigerator) {
					txtType_View.setText("Refrigerator");
					lblDetail1_View.setText("Door design: ");
					txtDetail1_View.setText(((Refrigerator) ProductList.get(no_product)).getDesign());
					lblDetail2_View.setText("Color: ");
					txtDetail2_View.setText(((Refrigerator) ProductList.get(no_product)).getColor());
					lblDetail3_View.setText("Capacity (Litre): ");
					txtDetail3_View.setText(Integer.toString(((Refrigerator) ProductList.get(no_product)).getCapacity()));
				}else if (ProductList.get(no_product) instanceof TV) {
					txtType_View.setText("TV");
					lblDetail1_View.setText("Type: ");
					txtDetail1_View.setText(((TV) ProductList.get(no_product)).getType());
					lblDetail2_View.setText("Resolution: ");
					txtDetail2_View.setText(((TV) ProductList.get(no_product)).getResolution());
					lblDetail3_View.setText("Display Size (Inch): ");
					txtDetail3_View.setText(Integer.toString(((TV) ProductList.get(no_product)).getSize()));
				}else if (ProductList.get(no_product) instanceof Microwave) {
					txtType_View.setText("Microwave");
					lblDetail1_View.setText("Color: ");
					txtDetail1_View.setText(((Microwave) ProductList.get(no_product)).getColor());
					lblDetail2_View.setText("Power (Watt): ");
					txtDetail2_View.setText(Integer.toString(((Microwave) ProductList.get(no_product)).getPower()));
					lblDetail3_View.setText("Volume (Litre): ");
					txtDetail3_View.setText(Integer.toString(((Microwave) ProductList.get(no_product)).getVolume()));
				}else if (ProductList.get(no_product) instanceof AirConditioner) {
					txtType_View.setText("AirConditioner");
					lblDetail1_View.setText("Type: ");
					txtDetail1_View.setText(((AirConditioner) ProductList.get(no_product)).getType());
					lblDetail2_View.setText("Power Consumption (Watt): ");
					txtDetail2_View.setText(Integer.toString(((AirConditioner) ProductList.get(no_product)).getPowerConsumption()));
					lblDetail3_View.setText("Horse Power (HP): ");
					txtDetail3_View.setText(String.format("%.2f", ((AirConditioner) ProductList.get(no_product)).getHorsePower()));
				}
				
				//Disable next button if its last product
				if(no_product+1 >= ProductList.size())
					btnNext_View.setDisable(true);
				else
					btnNext_View.setDisable(false);

				//Disable Prev button if its 1st product
				if(no_product+1 <= 1)
					btnPrev_View.setDisable(true);
				else
					btnPrev_View.setDisable(false);
			}
		);
		//Button Functions - show next product, disable next button if product = max product
		btnNext_View.setOnAction(
			e -> {
				//Setting value of 1st product
				no_product +=1 ;
				lblProductNo_View.setText("Product " + (no_product+1) + " of " + ProductList.size());
				txtNumber_View.setText(Integer.toString(ProductList.get(no_product).getID()));
				txtName_View.setText(ProductList.get(no_product).getName());
				txtQuantity_View.setText(Integer.toString(ProductList.get(no_product).getQuantity()));
				txtPrice_View.setText(String.format("%.2f", ProductList.get(no_product).getPrice()));
				txtStock_View.setText(String.format("%.2f", ProductList.get(no_product).getValue()));
				txtStatus_View.setText(ProductList.get(no_product).getStatus());
				
				//Setting dynamic values for different type of products
				if(ProductList.get(no_product) instanceof Refrigerator) {
					txtType_View.setText("Refrigerator");
					lblDetail1_View.setText("Door design: ");
					txtDetail1_View.setText(((Refrigerator) ProductList.get(no_product)).getDesign());
					lblDetail2_View.setText("Color: ");
					txtDetail2_View.setText(((Refrigerator) ProductList.get(no_product)).getColor());
					lblDetail3_View.setText("Capacity (Litre): ");
					txtDetail3_View.setText(Integer.toString(((Refrigerator) ProductList.get(no_product)).getCapacity()));
				}else if (ProductList.get(no_product) instanceof TV) {
					txtType_View.setText("TV");
					lblDetail1_View.setText("Type: ");
					txtDetail1_View.setText(((TV) ProductList.get(no_product)).getType());
					lblDetail2_View.setText("Resolution: ");
					txtDetail2_View.setText(((TV) ProductList.get(no_product)).getResolution());
					lblDetail3_View.setText("Display Size (Inch): ");
					txtDetail3_View.setText(Integer.toString(((TV) ProductList.get(no_product)).getSize()));
				}else if (ProductList.get(no_product) instanceof Microwave) {
					txtType_View.setText("Microwave");
					lblDetail1_View.setText("Color: ");
					txtDetail1_View.setText(((Microwave) ProductList.get(no_product)).getColor());
					lblDetail2_View.setText("Power (Watt): ");
					txtDetail2_View.setText(Integer.toString(((Microwave) ProductList.get(no_product)).getPower()));
					lblDetail3_View.setText("Volume (Litre): ");
					txtDetail3_View.setText(Integer.toString(((Microwave) ProductList.get(no_product)).getVolume()));
				}else if (ProductList.get(no_product) instanceof AirConditioner) {
					txtType_View.setText("AirConditioner");
					lblDetail1_View.setText("Type: ");
					txtDetail1_View.setText(((AirConditioner) ProductList.get(no_product)).getType());
					lblDetail2_View.setText("Power Consumption (Watt): ");
					txtDetail2_View.setText(Integer.toString(((AirConditioner) ProductList.get(no_product)).getPowerConsumption()));
					lblDetail3_View.setText("Horse Power (HP): ");
					txtDetail3_View.setText(String.format("%.2f", ((AirConditioner) ProductList.get(no_product)).getHorsePower()));
				}
				
				//Disable next button if its last product
				if(no_product+1 >= ProductList.size())
					btnNext_View.setDisable(true);
				else
					btnNext_View.setDisable(false);

				//Disable Prev button if 1st last product
				if(no_product+1 <= 1)
					btnPrev_View.setDisable(true);
				else
					btnPrev_View.setDisable(false);
			}
		);
		
		//Set View Stock scene
		Sc_ViewStock = new Scene(Pn_ViewStock);
		
		
		//==============================
		//Menu Scene
		//==============================
		//Setting labels and buttons
		Label lblMenu_menu = new Label("MENU");
		Button btnViewStock_menu = new Button("View Stock");
		Button btnAddStock_menu = new Button("Add Stock");
		Button btnDeductStock_menu = new Button("Deduct Stock");
		Button btnDiscontinue_menu = new Button("Discontinue Product");
		Button btnExit_menu = new Button("Exit");
		
		//Setting pane size and adding label
		Pn_Menu = new GridPane();
		Pn_Menu.setAlignment(Pos.CENTER);
		Pn_Menu.setPadding(new Insets(15));
		Pn_Menu.setHgap(5);
		Pn_Menu.setVgap(5);
		Pn_Menu.setMinWidth(250);
		Pn_Menu.add(lblMenu_menu, 0, 0);
		GridPane.setHalignment(lblMenu_menu, HPos.CENTER);
		lblMenu_menu.setFont(Font.font("Times New Roman", FontWeight.BOLD, FontPosture.REGULAR, 20));
		
		//Setting first product details, disable prev button, disable next button if only 1 product is added
		//Change scene to view stock scene
		btnViewStock_menu.setOnAction(
			e -> {
				//Setting value of 1st product
				no_product = 0;
				lblProductNo_View.setText("Product " + (no_product+1) + " of " + ProductList.size());
				txtNumber_View.setText(Integer.toString(ProductList.get(no_product).getID()));
				txtName_View.setText(ProductList.get(no_product).getName());
				txtQuantity_View.setText(Integer.toString(ProductList.get(no_product).getQuantity()));
				txtPrice_View.setText(String.format("%.2f", ProductList.get(no_product).getPrice()));
				txtStock_View.setText(String.format("%.2f", ProductList.get(no_product).getValue()));
				txtStatus_View.setText(ProductList.get(no_product).getStatus());
				
				//Setting dynamic values for different type of products
				if(ProductList.get(no_product) instanceof Refrigerator) {
					txtType_View.setText("Refrigerator");
					lblDetail1_View.setText("Door design: ");
					txtDetail1_View.setText(((Refrigerator) ProductList.get(no_product)).getDesign());
					lblDetail2_View.setText("Color: ");
					txtDetail2_View.setText(((Refrigerator) ProductList.get(no_product)).getColor());
					lblDetail3_View.setText("Capacity (Litre): ");
					txtDetail3_View.setText(Integer.toString(((Refrigerator) ProductList.get(no_product)).getCapacity()));
				}else if (ProductList.get(no_product) instanceof TV) {
					txtType_View.setText("TV");
					lblDetail1_View.setText("Type: ");
					txtDetail1_View.setText(((TV) ProductList.get(no_product)).getType());
					lblDetail2_View.setText("Resolution: ");
					txtDetail2_View.setText(((TV) ProductList.get(no_product)).getResolution());
					lblDetail3_View.setText("Display Size (Inch): ");
					txtDetail3_View.setText(Integer.toString(((TV) ProductList.get(no_product)).getSize()));
				}else if (ProductList.get(no_product) instanceof Microwave) {
					txtType_View.setText("Microwave");
					lblDetail1_View.setText("Color: ");
					txtDetail1_View.setText(((Microwave) ProductList.get(no_product)).getColor());
					lblDetail2_View.setText("Power (Watt): ");
					txtDetail2_View.setText(Integer.toString(((Microwave) ProductList.get(no_product)).getPower()));
					lblDetail3_View.setText("Volume (Litre): ");
					txtDetail3_View.setText(Integer.toString(((Microwave) ProductList.get(no_product)).getVolume()));
				}else if (ProductList.get(no_product) instanceof AirConditioner) {
					txtType_View.setText("AirConditioner");
					lblDetail1_View.setText("Type: ");
					txtDetail1_View.setText(((AirConditioner) ProductList.get(no_product)).getType());
					lblDetail2_View.setText("Power Consumption (Watt): ");
					txtDetail2_View.setText(Integer.toString(((AirConditioner) ProductList.get(no_product)).getPowerConsumption()));
					lblDetail3_View.setText("Horse Power (HP): ");
					txtDetail3_View.setText(String.format("%.2f", ((AirConditioner) ProductList.get(no_product)).getHorsePower()));
				}
				
				//Disable next button if there is only 1 product added
				if(no_product+1 == ProductList.size())
					btnNext_View.setDisable(true);
				//Disable prev button by default
				btnPrev_View.setDisable(true);
				
				window.setScene(Sc_ViewStock);
			}
		);
		
		//Clearing original observable list of table
		//Looping for all products in product list and changing its ID to index number
		//Add all items into observable list
		//Change scene to add stock scene
		btnAddStock_menu.setOnAction(
			e -> {
				Product temp, tempInsert;
				ObservableList olAddStock = tbl_AddStock.getItems();
				olAddStock.clear();
				
				for(int i = 0; i < ProductList.size(); i++) {
					temp = ProductList.get(i);
					tempInsert = new Refrigerator(temp.getName(), temp.getPrice(), temp.getQuantity(), i, "", "", 0);
					
					if(temp.getStatus() == "Inactive")
						tempInsert.setStatus(false);
					
					olAddStock.add(i, tempInsert);
				}
				tbl_AddStock.setItems(olAddStock);
				tbl_AddStock.refresh();
				
				window.setScene(Sc_AddStock);
			}
		);
		
		//Clearing original observable list of table
		//Looping for all products in product list and changing its ID to index number
		//Add all items into observable list
		//Change scene to add stock scene
		btnDeductStock_menu.setOnAction(
			e -> {
				Product temp, tempInsert;
				ObservableList olDeductStock = tbl_DeductStock.getItems();
				olDeductStock.clear();
				
				for(int i = 0; i < ProductList.size(); i++) {
					temp = ProductList.get(i);
					tempInsert = new Refrigerator(temp.getName(), temp.getPrice(), temp.getQuantity(), i, "", "", 0);
					
					if(temp.getStatus() == "Inactive")
						tempInsert.setStatus(false);
					
					olDeductStock.add(i, tempInsert);
				}
				tbl_DeductStock.setItems(olDeductStock);
				tbl_DeductStock.refresh();
				
				window.setScene(Sc_DeductStock);
			}
		);
		
		//Clearing original observable list of table
		//Looping for all products in product list and changing its ID to index number
		//Add all items into observable list
		//Change scene to add stock scene
		btnDiscontinue_menu.setOnAction(
			e -> {
				Product temp, tempInsert;
				ObservableList olDiscontinue = tbl_Discontinue.getItems();
				olDiscontinue.clear();
				
				for(int i = 0; i < ProductList.size(); i++) {
					temp = ProductList.get(i);
					tempInsert = new Refrigerator(temp.getName(), temp.getPrice(), temp.getQuantity(), i, "", "", 0);
					
					if(temp.getStatus() == "Inactive")
						tempInsert.setStatus(false);
					
					olDiscontinue.add(i, tempInsert);
				}
				tbl_Discontinue.setItems(olDiscontinue);
				tbl_Discontinue.refresh();
				
				window.setScene(Sc_Discontinue);
			}
		);

		btnExit_menu.setOnAction(
			e -> {
				if(Display("Exit", "User ID: " + staff.getCode() + "\nStaff ID: " + staff.getID() + "\nAre you sure to exit?"))
					window.close();
			}
		);
		
		//Adding button to pane
		Pn_Menu.add(btnViewStock_menu, 0, 1);
		Pn_Menu.add(btnAddStock_menu, 0, 2);
		Pn_Menu.add(btnDeductStock_menu, 0, 3);
		Pn_Menu.add(btnDiscontinue_menu, 0, 4);
		Pn_Menu.add(btnExit_menu, 0, 5);
		btnViewStock_menu.setMaxWidth(Double.MAX_VALUE);
		btnAddStock_menu.setMaxWidth(Double.MAX_VALUE);
		btnDeductStock_menu.setMaxWidth(Double.MAX_VALUE);
		btnDiscontinue_menu.setMaxWidth(Double.MAX_VALUE);
		btnExit_menu.setMaxWidth(Double.MAX_VALUE);
		
		//Setting Scene Menu
		Sc_Menu = new Scene(Pn_Menu);

		//Menu Item's back button - View Stock
		btnBack_View.setOnAction(e -> {window.setScene(Sc_Menu);});
		btnBack_AddStock.setOnAction(e -> {window.setScene(Sc_Menu);});
		btnBack_DeductStock.setOnAction(e -> {window.setScene(Sc_Menu);});
		btnBack_Discontinue.setOnAction(e -> {window.setScene(Sc_Menu);});
		
		//==============================
		//Product Scene
		//==============================
		//Setting text fields and buttons
		Text txtNoOfProduct = new Text();
		Label lblDetails_Refri = new Label("Enter refrigerator details");
		Label lblNumber_Refri = new Label("Number:");
		TextField txtNumber_Refri = new TextField();
		Label lblName_Refri = new Label("Name:");
		TextField txtName_Refri = new TextField();
		Label lblQuantity_Refri = new Label("Quantity:");
		TextField txtQuantity_Refri = new TextField();
		Label lblPrice_Refri = new Label("Price (RM):");
		TextField txtPrice_Refri = new TextField();
		Label lblDesign_Refri = new Label("Door design:");
		TextField txtDesign_Refri = new TextField();
		Label lblColor_Refri = new Label("Color:");
		TextField txtColor_Refri = new TextField();
		Label lblCapacity_Refri = new Label("Capacity (Litre):");
		TextField txtCapacity_Refri = new TextField();
		Button btnAdd_Refri = new Button("Add Refrigerator");
		
		//=======Refrigerator
		//Setting pane size
		Pn_Refrigerator = new GridPane();
		Pn_Refrigerator.setAlignment(Pos.BASELINE_LEFT);
		Pn_Refrigerator.setPadding(new Insets(15));
		Pn_Refrigerator.setHgap(5);
		Pn_Refrigerator.setVgap(5);
		//Adding everything onto pane
		Pn_Refrigerator.add(lblDetails_Refri, 0, 0, 2, 1);
		Pn_Refrigerator.add(lblNumber_Refri, 0, 1);
		Pn_Refrigerator.add(txtNumber_Refri, 1, 1);
		Pn_Refrigerator.add(lblName_Refri, 0, 2);
		Pn_Refrigerator.add(txtName_Refri, 1, 2);
		Pn_Refrigerator.add(lblQuantity_Refri, 0, 3);
		Pn_Refrigerator.add(txtQuantity_Refri, 1, 3);
		Pn_Refrigerator.add(lblPrice_Refri, 0, 4);
		Pn_Refrigerator.add(txtPrice_Refri, 1, 4);
		Pn_Refrigerator.add(lblDesign_Refri, 0, 5);
		Pn_Refrigerator.add(txtDesign_Refri, 1, 5);
		Pn_Refrigerator.add(lblColor_Refri, 0, 6);
		Pn_Refrigerator.add(txtColor_Refri, 1, 6);
		Pn_Refrigerator.add(lblCapacity_Refri, 0, 7);
		Pn_Refrigerator.add(txtCapacity_Refri, 1, 7);
		Pn_Refrigerator.add(btnAdd_Refri, 0, 8, 2, 1);
		GridPane.setHalignment(btnAdd_Refri, HPos.LEFT);

		//Button Function - Check all data type and add a new refrigerator into array list
		//Change scene to menu once finish added products
		btnAdd_Refri.setOnAction(
			e -> {
				if(!checkInt(txtNumber_Refri, "Product Number", 0, Integer.MAX_VALUE)){}
				else if(txtName_Refri.getText().isEmpty()) {Alert("Name", "Please enter non-empty value.");}
				else if(!checkInt(txtQuantity_Refri, "Quantity", 0, Integer.MAX_VALUE)) {}
				else if(!checkDouble(txtPrice_Refri, "Price", 0, Double.MAX_VALUE)) {}
				else if(txtDesign_Refri.getText().isEmpty()) {Alert("Door Design", "Please enter non-empty value.");}
				else if(txtColor_Refri.getText().isEmpty()) {Alert("Color", "Please enter non-empty value.");}
				else if(!checkInt(txtCapacity_Refri, "Capacity", 0, Integer.MAX_VALUE)) {}
				else {
					ProductList.add(new Refrigerator(txtName_Refri.getText(),
													 Double.parseDouble(txtPrice_Refri.getText()),
													 Integer.parseInt(txtQuantity_Refri.getText()),
													 Integer.parseInt(txtNumber_Refri.getText()),
													 txtDesign_Refri.getText(),
													 txtColor_Refri.getText(),
													 Integer.parseInt(txtCapacity_Refri.getText())));
					
					no_product -= 1;
					txtNoOfProduct.setText("Product remaining: " + no_product);
					
					txtName_Refri.clear();
					txtPrice_Refri.clear();
					txtQuantity_Refri.clear();
					txtNumber_Refri.clear();
					txtDesign_Refri.clear();
					txtColor_Refri.clear();
					txtCapacity_Refri.clear();
					if(no_product == 0)
						window.setScene(Sc_Menu);
				}
			}
		);
		
		//=======TV
		//Setting text fields and buttons
		Label lblDetails_TV = new Label("Enter TV details");
		Label lblNumber_TV = new Label("Number:");
		TextField txtNumber_TV = new TextField();
		Label lblName_TV = new Label("Name:");
		TextField txtName_TV = new TextField();
		Label lblQuantity_TV = new Label("Quantity:");
		TextField txtQuantity_TV = new TextField();
		Label lblPrice_TV = new Label("Price (RM):");
		TextField txtPrice_TV = new TextField();
		Label lblType_TV = new Label("Type:");
		TextField txtType_TV = new TextField();
		Label lblResolution_TV = new Label("Resolution:");
		TextField txtResolution_TV = new TextField();
		Label lblDisplaySize_TV = new Label("Display Size (Inch):");
		TextField txtDisplaySize_TV = new TextField();
		Button btnAdd_TV = new Button("Add TV");
		
		//Setting pane size
		Pn_TV = new GridPane();
		Pn_TV.setAlignment(Pos.BASELINE_LEFT);
		Pn_TV.setPadding(new Insets(15));
		Pn_TV.setHgap(5);
		Pn_TV.setVgap(5);
		//Adding everything onto pane
		Pn_TV.add(lblDetails_TV, 0, 0, 2, 1);
		Pn_TV.add(lblNumber_TV, 0, 1);
		Pn_TV.add(txtNumber_TV, 1, 1);
		Pn_TV.add(lblName_TV, 0, 2);
		Pn_TV.add(txtName_TV, 1, 2);
		Pn_TV.add(lblQuantity_TV, 0, 3);
		Pn_TV.add(txtQuantity_TV, 1, 3);
		Pn_TV.add(lblPrice_TV, 0, 4);
		Pn_TV.add(txtPrice_TV, 1, 4);
		Pn_TV.add(lblType_TV, 0, 5);
		Pn_TV.add(txtType_TV, 1, 5);
		Pn_TV.add(lblResolution_TV, 0, 6);
		Pn_TV.add(txtResolution_TV, 1, 6);
		Pn_TV.add(lblDisplaySize_TV, 0, 7);
		Pn_TV.add(txtDisplaySize_TV, 1, 7);
		Pn_TV.add(btnAdd_TV, 0, 8, 2, 1);
		GridPane.setHalignment(btnAdd_TV, HPos.LEFT);

		//Button Function - Check all data type and add a new TV into array list
		//Change scene to menu once finish added products
		btnAdd_TV.setOnAction(
			e -> {
				if(!checkInt(txtNumber_TV, "Product Number", 0, Integer.MAX_VALUE)){}
				else if(txtName_TV.getText().isEmpty()) {Alert("Name", "Please enter non-empty value.");}
				else if(!checkInt(txtQuantity_TV, "Quantity", 0, Integer.MAX_VALUE)) {}
				else if(!checkDouble(txtPrice_TV, "Price", 0, Double.MAX_VALUE)) {}
				else if(txtType_TV.getText().isEmpty()) {Alert("Type", "Please enter non-empty value.");}
				else if(txtResolution_TV.getText().isEmpty()) {Alert("Resolution", "Please enter non-empty value.");}
				else if(!checkInt(txtDisplaySize_TV, "Display Size", 0, Integer.MAX_VALUE)) {}
				else {
					ProductList.add(new TV(txtName_TV.getText(),
										   Double.parseDouble(txtPrice_TV.getText()),
										   Integer.parseInt(txtQuantity_TV.getText()),
										   Integer.parseInt(txtNumber_TV.getText()),
										   txtType_TV.getText(),
										   txtResolution_TV.getText(),
										   Integer.parseInt(txtDisplaySize_TV.getText())));
					
					no_product -= 1;
					txtNoOfProduct.setText("Product remaining: " + no_product);
					
					txtName_TV.clear();
					txtPrice_TV.clear();
					txtQuantity_TV.clear();
					txtNumber_TV.clear();
					txtType_TV.clear();
					txtResolution_TV.clear();
					txtDisplaySize_TV.clear();
					if(no_product == 0)
						window.setScene(Sc_Menu);
				}
			}
		);
		
		//=======Microwave
		//Setting text fields and buttons
		Label lblDetails_Micro = new Label("Enter microwave details");
		Label lblNumber_Micro = new Label("Number:");
		TextField txtNumber_Micro = new TextField();
		Label lblName_Micro = new Label("Name:");
		TextField txtName_Micro = new TextField();
		Label lblQuantity_Micro = new Label("Quantity:");
		TextField txtQuantity_Micro = new TextField();
		Label lblPrice_Micro = new Label("Price (RM):");
		TextField txtPrice_Micro = new TextField();
		Label lblColor_Micro = new Label("Color:");
		TextField txtColor_Micro = new TextField();
		Label lblPower_Micro = new Label("Power (Watt):");
		TextField txtPower_Micro = new TextField();
		Label lblVolume_Micro = new Label("Volume (Litre):");
		TextField txtVolume_Micro = new TextField();
		Button btnAdd_Micro = new Button("Add microwave");
		
		//Setting pane size
		Pn_Microwave = new GridPane();
		Pn_Microwave.setAlignment(Pos.BASELINE_LEFT);
		Pn_Microwave.setPadding(new Insets(15));
		Pn_Microwave.setHgap(5);
		Pn_Microwave.setVgap(5);
		//Adding everything onto pane
		Pn_Microwave.add(lblDetails_Micro, 0, 0, 2, 1);
		Pn_Microwave.add(lblNumber_Micro, 0, 1);
		Pn_Microwave.add(txtNumber_Micro, 1, 1);
		Pn_Microwave.add(lblName_Micro, 0, 2);
		Pn_Microwave.add(txtName_Micro, 1, 2);
		Pn_Microwave.add(lblQuantity_Micro, 0, 3);
		Pn_Microwave.add(txtQuantity_Micro, 1, 3);
		Pn_Microwave.add(lblPrice_Micro, 0, 4);
		Pn_Microwave.add(txtPrice_Micro, 1, 4);
		Pn_Microwave.add(lblColor_Micro, 0, 5);
		Pn_Microwave.add(txtColor_Micro, 1, 5);
		Pn_Microwave.add(lblPower_Micro, 0, 6);
		Pn_Microwave.add(txtPower_Micro, 1, 6);
		Pn_Microwave.add(lblVolume_Micro, 0, 7);
		Pn_Microwave.add(txtVolume_Micro, 1, 7);
		Pn_Microwave.add(btnAdd_Micro, 0, 8, 2, 1);
		GridPane.setHalignment(btnAdd_Micro, HPos.LEFT);

		//Button Function - Check all data type and add a new microwave into array list
		//Change scene to menu once finish added products
		btnAdd_Micro.setOnAction(
			e -> {
				if(!checkInt(txtNumber_Micro, "Product Number", 0, Integer.MAX_VALUE)){}
				else if(txtName_Micro.getText().isEmpty()) {Alert("Name", "Please enter non-empty value.");}
				else if(!checkInt(txtQuantity_Micro, "Quantity", 0, Integer.MAX_VALUE)) {}
				else if(!checkDouble(txtPrice_Micro, "Price", 0, Double.MAX_VALUE)) {}
				else if(txtColor_Micro.getText().isEmpty()) {Alert("Color", "Please enter non-empty value.");}
				else if(!checkInt(txtPower_Micro, "Power", 0, Integer.MAX_VALUE)) {}
				else if(!checkInt(txtVolume_Micro, "Volume", 0, Integer.MAX_VALUE)) {}
				else {
					ProductList.add(new Microwave(txtName_Micro.getText(),
										   Double.parseDouble(txtPrice_Micro.getText()),
										   Integer.parseInt(txtQuantity_Micro.getText()),
										   Integer.parseInt(txtNumber_Micro.getText()),
										   txtColor_Micro.getText(),
										   Integer.parseInt(txtPower_Micro.getText()),
										   Integer.parseInt(txtVolume_Micro.getText())));
					
					no_product -= 1;
					txtNoOfProduct.setText("Product remaining: " + no_product);
					
					txtName_Micro.clear();
					txtPrice_Micro.clear();
					txtQuantity_Micro.clear();
					txtNumber_Micro.clear();
					txtColor_Micro.clear();
					txtPower_Micro.clear();
					txtVolume_Micro.clear();
					if(no_product == 0)
						window.setScene(Sc_Menu);
				}
			}
		);
		
		//=======Air-Conditioner
		//Setting text fields and buttons
		Label lblDetails_AC = new Label("Enter air-conditioner details");
		Label lblNumber_AC = new Label("Number:");
		TextField txtNumber_AC = new TextField();
		Label lblName_AC = new Label("Name:");
		TextField txtName_AC = new TextField();
		Label lblQuantity_AC = new Label("Quantity:");
		TextField txtQuantity_AC = new TextField();
		Label lblPrice_AC = new Label("Price (RM):");
		TextField txtPrice_AC = new TextField();
		Label lblType_AC = new Label("Type:");
		TextField txtType_AC = new TextField();
		Label lblPower_AC = new Label("Power Consumption (Watt):");
		TextField txtPower_AC = new TextField();
		Label lblHorsePower_AC = new Label("Horse Power (HP):");
		TextField txtHorsePower_AC = new TextField();
		Button btnAdd_AC = new Button("Add air-conditioner");
		
		//Setting pane size
		Pn_AirConditioner = new GridPane();
		Pn_AirConditioner.setAlignment(Pos.BASELINE_LEFT);
		Pn_AirConditioner.setPadding(new Insets(15));
		Pn_AirConditioner.setHgap(5);
		Pn_AirConditioner.setVgap(5);
		//Adding everything onto pane
		Pn_AirConditioner.add(lblDetails_AC, 0, 0, 2, 1);
		Pn_AirConditioner.add(lblNumber_AC, 0, 1);
		Pn_AirConditioner.add(txtNumber_AC, 1, 1);
		Pn_AirConditioner.add(lblName_AC, 0, 2);
		Pn_AirConditioner.add(txtName_AC, 1, 2);
		Pn_AirConditioner.add(lblQuantity_AC, 0, 3);
		Pn_AirConditioner.add(txtQuantity_AC, 1, 3);
		Pn_AirConditioner.add(lblPrice_AC, 0, 4);
		Pn_AirConditioner.add(txtPrice_AC, 1, 4);
		Pn_AirConditioner.add(lblType_AC, 0, 5);
		Pn_AirConditioner.add(txtType_AC, 1, 5);
		Pn_AirConditioner.add(lblPower_AC, 0, 6);
		Pn_AirConditioner.add(txtPower_AC, 1, 6);
		Pn_AirConditioner.add(lblHorsePower_AC, 0, 7);
		Pn_AirConditioner.add(txtHorsePower_AC, 1, 7);
		Pn_AirConditioner.add(btnAdd_AC, 0, 8, 2, 1);
		GridPane.setHalignment(btnAdd_AC, HPos.LEFT);

		//Button Function - Check all data type and add a new air-conditioner into array list
		//Change scene to menu once finish added products
		btnAdd_AC.setOnAction(
			e -> {
				if(!checkInt(txtNumber_AC, "Product Number", 0, Integer.MAX_VALUE)){}
				else if(txtName_AC.getText().isEmpty()) {Alert("Name", "Please enter non-empty value.");}
				else if(!checkInt(txtQuantity_AC, "Quantity", 0, Integer.MAX_VALUE)) {}
				else if(!checkDouble(txtPrice_AC, "Price", 0, Double.MAX_VALUE)) {}
				else if(txtType_AC.getText().isEmpty()) {Alert("Type", "Please enter non-empty value.");}
				else if(!checkInt(txtPower_AC, "Power Consumption", 0, Integer.MAX_VALUE)) {}
				else if(!checkDouble(txtHorsePower_AC, "Horse Power", 0, Double.MAX_VALUE)) {}
				else {
					ProductList.add(new AirConditioner(txtName_AC.getText(),
										   Double.parseDouble(txtPrice_AC.getText()),
										   Integer.parseInt(txtQuantity_AC.getText()),
										   Integer.parseInt(txtNumber_AC.getText()),
										   txtType_AC.getText(),
										   Integer.parseInt(txtPower_AC.getText()),
										   Double.parseDouble(txtHorsePower_AC.getText())));
					
					no_product -= 1;
					txtNoOfProduct.setText("Product remaining: " + no_product);
					
					txtName_AC.clear();
					txtPrice_AC.clear();
					txtQuantity_AC.clear();
					txtNumber_AC.clear();
					txtType_AC.clear();
					txtPower_AC.clear();
					txtHorsePower_AC.clear();
					if(no_product == 0)
						window.setScene(Sc_Menu);
				}
			}
		);
		
		//Setting Radio Button
		RadioButton rdRefri = new RadioButton("Refrigerator");
		RadioButton rdTV = new RadioButton("TV");
		RadioButton rdMicro = new RadioButton("Microwave");
		RadioButton rdAC = new RadioButton("Air-conditioner");
		//Making all radio button to belong to same group
		ToggleGroup rdGroup = new ToggleGroup();
		rdRefri.setToggleGroup(rdGroup);
		rdTV.setToggleGroup(rdGroup);
		rdMicro.setToggleGroup(rdGroup);
		rdAC.setToggleGroup(rdGroup);
		rdRefri.setSelected(true);
		
		//Left Pane vertical box having all the radio buttons
		VBox vbLeft_Product = new VBox(15);
		vbLeft_Product.setPadding(new Insets(5));
		vbLeft_Product.getChildren().addAll(rdRefri, rdTV, rdMicro,rdAC);

		//Setting border pane
		BorderPane borderPane = new BorderPane();
		borderPane.setPadding(new Insets(15));
		borderPane.setMinWidth(575);
		//Adding top message, left pane which is the radio button, and setting initial center pane as refrigerator
		borderPane.setTop(txtNoOfProduct);
		borderPane.setLeft(vbLeft_Product);
		borderPane.setCenter(Pn_Refrigerator);
		
		//Radio button on select, change pane to respective product-type
		rdRefri.setOnAction(e -> {if(rdRefri.isSelected()) borderPane.setCenter(Pn_Refrigerator);});
		rdTV.setOnAction(e -> {if(rdTV.isSelected()) borderPane.setCenter(Pn_TV);});
		rdMicro.setOnAction(e -> {if(rdMicro.isSelected()) borderPane.setCenter(Pn_Microwave);});
		rdAC.setOnAction(e -> {if(rdAC.isSelected()) borderPane.setCenter(Pn_AirConditioner);});
		
		//Setting scene Product
		Sc_Product = new Scene(borderPane);
		//Set to trigger add product of each product type when enter is pressed
		Sc_Product.setOnKeyPressed(
			e -> {
				if(e.getCode() == KeyCode.ENTER) {
					if(rdRefri.isSelected())
						btnAdd_Refri.fire();
					else if(rdTV.isSelected())
						btnAdd_TV.fire();
					else if(rdMicro.isSelected())
						btnAdd_Micro.fire();
					else if(rdAC.isSelected())
						btnAdd_AC.fire();
					
					e.consume();
				}
			}
		);
		
		
		//==============================
		//Add product Scene
		//==============================
		//Setting label, text fields and buttons
		Label lblNumProduct_Add = new Label("Enter number of product to add");
		TextField txtNumProduct_Add = new TextField();
		Button btnNext_Add = new Button("Next");
		
		//Setting Pane size
		Pn_Add = new GridPane();
		Pn_Add.setAlignment(Pos.CENTER);
		Pn_Add.setPadding(new Insets(15));
		Pn_Add.setHgap(5);
		Pn_Add.setVgap(5);
		//Adding everything to pane
		Pn_Add.add(lblNumProduct_Add, 0, 0, 2, 1);
		Pn_Add.add(new Label("Input:"), 0, 1);
		Pn_Add.add(txtNumProduct_Add, 1, 1);
		Pn_Add.add(btnNext_Add, 0, 4, 2, 1);
		GridPane.setHalignment(btnNext_Add, HPos.RIGHT);
		
		//Setting Scene Add
		Sc_Add = new Scene(Pn_Add);
		//Button Function - Validate integer input and change scene to let user choose product type and input product details
		btnNext_Add.setOnAction(
			e -> {
				if(checkInt(txtNumProduct_Add, "Number of Product", 0, Integer.MAX_VALUE)){
					no_product = Integer.parseInt(txtNumProduct_Add.getText());
					txtNoOfProduct.setText("Product remaining: " + no_product);
					
					if(no_product == 0) {
						Alert("Exit", "User ID: " + staff.getCode() + "\nStaff ID: " + staff.getID() + "\nGoodbye");
						window.close();
					}
					else {
						window.setScene(Sc_Product);
					}
				}	
			}
		);
		//Set to trigger next when enter is pressed
		Sc_Add.setOnKeyPressed(
			e -> {
				if(e.getCode() == KeyCode.ENTER) {
					btnNext_Add.fire();
					e.consume();
				}
			}
		);
		
		
		//==============================
		//No add product Scene
		//==============================
		//Setting label, text fields and buttons
		Label lblNumProduct_NoAdd = new Label("Enter 0 to exit");
		TextField txtNumProduct_NoAdd = new TextField();
		Button btnNext_NoAdd = new Button("Next");
		
		//Setting Pane
		Pn_No_Add = new GridPane();
		Pn_No_Add.setAlignment(Pos.CENTER);
		Pn_No_Add.setPadding(new Insets(15));
		Pn_No_Add.setHgap(5);
		Pn_No_Add.setVgap(5);
		//Adding everything to pane
		Pn_No_Add.add(lblNumProduct_NoAdd, 0, 0, 2, 1);
		Pn_No_Add.add(new Label("Input:"), 0, 1);
		Pn_No_Add.add(txtNumProduct_NoAdd, 1, 1);
		Pn_No_Add.add(btnNext_NoAdd, 0, 4, 2, 1);
		GridPane.setHalignment(btnNext_NoAdd, HPos.RIGHT);
		
		//Setting Screen no add product
		Sc_No_Add = new Scene(Pn_No_Add);
		//Button Function - Validate make sure user only enters 0 and prompt user ID and staff ID when user exits
		btnNext_NoAdd.setOnAction(
			e -> {
				if(checkInt(txtNumProduct_NoAdd, "Number of Product", 0, 0)){
					Alert("Exit", "User ID: " + staff.getCode() + "\nStaff ID: " + staff.getID() + "\nGoodbye");
					window.close();
				}	
			}
		);
		//Set to trigger next when enter is pressed
		Sc_No_Add.setOnKeyPressed(
			e -> {	
				if(e.getCode() == KeyCode.ENTER) {
					btnNext_NoAdd.fire();
					e.consume();
				}
			}
		);
		
		
		//==============================
		//Login Scene
		//==============================
		//Setting text fields, label and button
		TextField txtID_Login = new TextField();
		TextField txtName_Login = new TextField();
		Label lblDateTime = new Label("Current date time is : " + DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss").format(LocalDateTime.now()));
		Button btnLogin_Login = new Button("Login");
		
		//Setting Pane size
		Pn_Login = new GridPane();
		Pn_Login.setAlignment(Pos.CENTER);
		Pn_Login.setPadding(new Insets(15));
		Pn_Login.setHgap(5);
		Pn_Login.setVgap(5);
		//Adding everything to pane
		Pn_Login.add(new Label("Welcome to Stock Management System"), 0, 0, 2, 1);
		Pn_Login.add(new Label("Full Name:"), 0, 1);
		Pn_Login.add(txtName_Login, 1, 1);
		Pn_Login.add(new Label("Staff ID:"), 0, 2);
		Pn_Login.add(txtID_Login, 1, 2);
		Pn_Login.add(btnLogin_Login, 0, 5, 2, 1);
		Pn_Login.add(lblDateTime, 0, 6, 2, 1);
		GridPane.setHalignment(btnLogin_Login, HPos.RIGHT);
		GridPane.setHalignment(lblDateTime, HPos.RIGHT);
		
		//Setting Scene Login
		Sc_Login = new Scene(Pn_Login);
		//Button function - Validate both name and ID, then prompts whether user wanted to add any products.
		//Change scene to prompt user how many products if user wish to add products
		//Change scene to prompt user to enter 0 to exit if user do not wish to add any products
		btnLogin_Login.setOnAction(
			e -> {
				if(txtName_Login.getText().isEmpty())
					Alert("Name", "Please enter non-empty value.");
				else if (txtID_Login.getText().isEmpty())
					Alert("ID", "Please enter non-empty value.");
				else {
					//Setting staff 
					staff.setName(txtName_Login.getText());
					staff.setID(txtID_Login.getText());
					
					//Prompt user to enter 0 if do not wish to enter products
					if(Display("SMS","Do you wish to add any product(s)?"))
						window.setScene(Sc_Add);
					else
						window.setScene(Sc_No_Add);
				}		
			}
		);
		//Set to trigger login when enter is pressed
		Sc_Login.setOnKeyPressed(
			e -> {
				if(e.getCode() == KeyCode.ENTER) {
					btnLogin_Login.fire();
					e.consume();
				}
			}
		);
		

		//==============================
		//Start Window
		//==============================
		//Setting starting scene to Login scene
		window.setScene(Sc_Login);
		window.setTitle("SMS");
		
		//Set close to display exit message
		window.setOnCloseRequest(
			e -> {
				if(window.getScene() == Sc_Login)
					window.close();
				else {
					if(Display("Exit", "User ID: " + staff.getCode() + "\nStaff ID: " + staff.getID() + "\nAre you sure to exit?"))
						window.close();
					else
						e.consume();
				}	
			}
		);
		
		//Start Showing
		window.show();
	}
	
	private static void Alert(String title, String message) {
		Stage window = new Stage();
		
		//Window Layout
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle(title);
		window.setMinWidth(400);
		window.setMinHeight(120);
		window.setOnCloseRequest(e -> window.close());
		
		//Setting Label, Button and its function
		Label lblMessage = new Label(message);
		Button btnClose = new Button("Ok");
		btnClose.setOnAction(e -> window.close());	
		
		//Setting Vertical Layout
		VBox layout = new VBox();
		layout.setPadding(new Insets(15));
		layout.getChildren().addAll(lblMessage, btnClose);
		layout.setAlignment(Pos.CENTER);
		
		//Scene layout
		Scene scene = new Scene(layout);	
		scene.setOnKeyPressed(
			e -> {
				if(e.getCode() == KeyCode.ENTER) {
					btnClose.fire();
					e.consume();
				}
			}
		);
		
		//Set and start window
		window.setScene(scene);
		window.showAndWait();
	}
	
	private static boolean Display(String title, String message) {
		Stage window = new Stage();
		confirmation = false;

		//Window Layout
		window.initModality(Modality.APPLICATION_MODAL);	//window layout
		window.setTitle(title);
		window.setMinWidth(320);
		window.setMinHeight(150);
		window.setOnCloseRequest(e -> window.close());

		//Setting Label, Button and its function
		Button btnYes = new Button("Yes");
		btnYes.setOnAction(
			e -> {		
				window.close();
				confirmation = true;
			}
		);
		Button btnNo = new Button("No");
		btnNo.setOnAction(
			e -> {
				window.close();
				confirmation = false;
			}
		);
		
		//Adding everything to pane
		GridPane pane = new GridPane();	//pane and nodes layout
		pane.setHgap(5);
		pane.setVgap(5);
		pane.setPadding(new Insets(15));
		pane.add(new Label(message), 0, 0, 2, 1);
		pane.add(btnYes, 0, 3, 1, 1);
		pane.add(btnNo, 1, 3, 1, 1);
		pane.setAlignment(Pos.CENTER);
		GridPane.setHalignment(btnYes,HPos.LEFT);
		GridPane.setHalignment(btnNo,HPos.RIGHT);
		
		//Setting scene
		Scene scene = new Scene(pane);
		window.setScene(scene);
		window.showAndWait();
		
		return confirmation;
	}
	
	//Validate integer and whether it is in-range
	private boolean checkInt(TextField input, String fieldName, int min, int max) {
		int temp;
		try {
			temp = Integer.parseInt(input.getText());
			
			if((temp < min || temp > max)) {
				if(min == max) {
					Alert(fieldName, "Please enter only " + min + ".");
				}
				else {
					if (max == Integer.MAX_VALUE)
						Alert(fieldName, "Please enter a value of minimum " + min + ".");
					else
						Alert(fieldName, "Please enter a value between " + min + " and " + max + ".");
				}
				return false;
			}
			return true;
		}
		catch(NumberFormatException e){
			Alert(fieldName, "Invalid input! Please enter a valid integer value.");
			return false;
		}
	}
	
	//Validate double and whether it is in-range
	private boolean checkDouble(TextField input, String fieldName, double min, double max) {
		double temp;
		try {
			temp = Double.parseDouble(input.getText());
			
			if((temp < min || temp > max)) {
				if(min == max) {
					Alert(fieldName, "Please enter only " + min + ".");
				}
				else {
					if (max == Double.MAX_VALUE)
						Alert(fieldName, "Please enter a value of minimum " + min + ".");
					else
						Alert(fieldName, "Please enter a value between " + min + " and " + max + ".");
				}
				return false;
			}
			return true;
		}
		catch(NumberFormatException e){
			Alert(fieldName, "Invalid input! Please enter a valid double value.");
			return false;
		}
	}
}
