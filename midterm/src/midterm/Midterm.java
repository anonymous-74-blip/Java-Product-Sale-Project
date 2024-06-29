package midterm;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Midterm {
            
          int PID;
          String pName;
          int stockQTY;
          double cost;
          double price;
          
          public Midterm(int pID, String name, double price, double cost, int sQTY){
                    this.PID = pID;
                    this.pName = name;
                    this.price = price;
                    this.cost = cost;
                    this.stockQTY = sQTY;
          }
          
          int productID;
          String productName;
          int orderQty;
          double unitPrice;
          double itemTotalPrice;

          public Midterm(int productID, String productName, int orderQty, double unitPrice, double itemTotalPrice) {
                      this.productID = productID;
                      this.productName = productName;
                      this.orderQty = orderQty;
                      this.unitPrice = unitPrice;
                      this.itemTotalPrice = itemTotalPrice;
          }
          
          
         public int getProductID() {
                     return productID;
          }

          public String getProductName() {
                     return productName;
          }

          public int getOrderQty() {
                     return orderQty;
           }

          public double getUnitPrice() {
                    return unitPrice;
          }

          public double getItemTotalPrice() {
                      return itemTotalPrice;
          }
          
          @Override
          public String toString() {
              return PID + "\t\t" + pName + "\t\t" + price + "\t\t" + cost+ "\t\t" + stockQTY;
          }
          
          public static void storeProduct(HashMap<Integer, Midterm> products){
                    Scanner getProduct = new Scanner(System.in);
                    System.out.print("Enter Numbers of Product you want to add: ");
                    int getProductNumbers = getProduct.nextInt();
                    int getPsize = products.size();
                    for( int i=products.size() + 1; i<= getPsize + getProductNumbers; i++){
                               System.out.print("Product #"+i + "\n");
                               int productID = i;
                               
                               ProductName:
                               System.out.print("Enter Product Name: ");
                               String productName = getProduct.next();
                               
                                boolean duplicateFound = false;
                                for (Midterm product : products.values()) {
                                         if (product.pName.equalsIgnoreCase(productName)) {
                                                   duplicateFound = true;
                                                   System.out.println("Error: Product with name '" + productName + "' already exists. Please enter a unique name.");
                                                   i--;
                                                   break;
                                         }
                                }

                               if (duplicateFound) {
                                         continue; 
                               }
                               
                               System.out.print("Enter Product Unit Price: ");
                               double productPrice = getProduct.nextDouble(); 
                               System.out.print("Enter Product Import Cost: ");
                               double productCost = getProduct.nextDouble(); 
                               System.out.print("Enter Product Stock QTY: ");
                               int productStock = getProduct.nextInt();
                               
                               Midterm addProduct = new Midterm(productID, productName, productPrice, productCost ,productStock);
                               products.put(addProduct.PID, addProduct);
                    }
                    Scanner scanner = new Scanner(System.in);
                    scanner.useDelimiter(""); 
                    System.out.println("Press any key to go back to menu: ");
                    String key = scanner.next();
                    System.out.printf("-------------------------\n\n\n");
          }
          
          public static void viewStock(HashMap<Integer, Midterm> products){
                    
                    if (products.isEmpty()) {
                               System.out.println("No products currently in stock.");
                    } else {
                              System.out.print("PID\t\tName\t\tUnit Price\t\tCost\t\tStockQTY\n");
                              for (Midterm product : products.values()) {
                                         System.out.println(product.toString());
                              }
                    }
                    Scanner scanner = new Scanner(System.in);
                    scanner.useDelimiter(""); 

                    System.out.println("Press any key to go back to menu: ");
                    String key = scanner.next();
                    System.out.printf("-------------------------\n\n\n");

          }
          
          public static void createOrder(HashMap<Integer, Midterm> products) {
                    Scanner scanner = new Scanner(System.in);

                    List<Midterm> orderItems = new ArrayList<>();
                    double totalPrice = 0.0;

                    boolean keepAdding = true;
                    while (keepAdding) {
                              System.out.print("Enter product ID to add to order (or 0 to finish): ");
                              int productID = scanner.nextInt();

                              if (productID == 0) {
                                         break;
                              }

                              if (!products.containsKey(productID)) {
                                         System.out.println("Invalid product ID. Please try again.");
                                         continue;
                              }

                              Midterm product = products.get(productID);

                              System.out.print("Enter quantity for " + product.pName + ": ");
                              int orderQty = scanner.nextInt();

                              if (orderQty <= 0 || orderQty > product.stockQTY) {
                                         System.out.println("Invalid quantity. Please enter a positive value less than or equal to stock (" + product.stockQTY + ").");
                                         continue;
                              }

                             double itemTotalPrice = product.price * orderQty;

                             product.stockQTY -= orderQty;

                             Midterm orderProduct = new Midterm(productID, product.pName, orderQty, product.price, itemTotalPrice);
                             orderItems.add(orderProduct);

                             totalPrice += itemTotalPrice;

                             System.out.print("Do you want to add another item? (y/n): ");
                             String choice = scanner.next();
                             keepAdding = choice.equalsIgnoreCase("y");
                    }

                    
                    if (orderItems.isEmpty()) {
                             System.out.println("No items added to order.");
                    } else {
                               System.out.println("\n** Order Receipt **");
                            
                               System.out.println("-------------------------");
                               System.out.printf("Product ID\tName\tQty\tUnit Price\tTotal Price\n");
                               for (Midterm item : orderItems) {
                                         System.out.printf("%d\t%s\t%d\t%.2f\t%.2f\n", item.getProductID(), item.getProductName(), item.getOrderQty(), item.getUnitPrice(), item.getItemTotalPrice());
                               }
                              System.out.println("-------------------------");
                              System.out.printf("Grand Total: %.2f\n", totalPrice);
                    }
                    System.out.printf("-------------------------\n\n\n");
          }
          
          public static void main(String[] args) {
          
                        
                    boolean keepLooping = true;
                    HashMap<Integer, Midterm> productMap = new HashMap<>();
                    
                    while(keepLooping){          
                              Scanner sc = new Scanner(System.in);
                              System.out.print("1. Add to Inventory\n2. Create Order\n3. View Stock\n4. Exit\n--> Please select one process:");
                              int choice = sc.nextInt();
                              switch (choice) {
                                         case 1:
                                                   storeProduct(productMap);
                                         break;
                                         case 2:
                                                   createOrder(productMap);
                                         break;
                                         case 3:
                                                  viewStock(productMap);
                                         break;
                                         case 4:
                                                   keepLooping = false;
                                         break;
                                         default:
                                                   System.out.println("Invalid choice!");
                              }
                     }
          }
}
