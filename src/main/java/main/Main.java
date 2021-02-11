package main;

import entities.Order;
import entities.OrderedItems;
import entities.Product;
import services.OrderService;
import services.ProductService;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;


public class Main {



    static Scanner input = new Scanner(System.in);

    public static void main(String[] args) throws SQLException, IOException {
        OrderService orderService=new OrderService();
        ProductService productService=new ProductService();
        System.out.println("Welcome to this Demo, Here are all available commands: \n" +
                "Get all products: -products\n" +
                "Get all orders: -orders\n" +
                "create a product: -cp\n" +
                "create an order: -co\n" +
                "retrieve product by id: -p\n" +
                "retrieve product that has been ordered before: -po\n" +
                "retrieve order by id: -o\n" +
                "update product by id: -up\n" +
                "delete product by id: -dp\n" +
                "delete all products: -deleteProducts\n");
        String command = input.nextLine();
        while (!command.equals("-exit")) {
            switch (command) {
                case "-products":
                    getAllProducts(productService);
                    break;
                case "-orders":
                    getAllOrders(orderService);
                    break;
                case "-cp":
                    createProduct(productService);
                    break;
                case "-co":
                    createOrder(productService, orderService);
                    break;
                case "-p":
                    getProductById(productService);
                    break;
                case "-o":
                    getOrderById(orderService);
                    break;
                case "-po":
                    getOrderedProducts(productService);
                    break;
                case "-up":
                    updateProductById(productService);
                    break;
                case "-dp":
                    deleteProductById(productService);
                    break;
                case "-deleteProducts":
                    deleteAllProducts(productService);
                    break;
                default:
                    System.err.println("wrong command\n");
                    break;
            }
            command = input.nextLine();
        }
    }

    private static void getAllOrders(OrderService orderService){
        List<Order> orders = orderService.getAllOrders();
        if (!orders.isEmpty()) {
            System.out.println("| Order ID | Products total Price | Products Quantity |\n");
            orders.forEach((order) -> {
                int totalPrice=0;
                int totalQuantity=0;
                for (OrderedItems orderedItem:order.getOrderedItems()){
                    totalPrice+=orderedItem.getProduct().getPrice()*orderedItem.getQuantity();
                    totalQuantity+=orderedItem.getQuantity();
                }
                System.out.printf("| %d | %d | %s |%n", order.getId(),totalPrice, totalQuantity);
            });
        } else {
            System.out.println("no orders available\n");
        }
    }
    private static void getAllProducts(ProductService productService){
        List<Product> products = productService.getAllProducts();
        if (!products.isEmpty()) {
            System.out.println("| Product Name | Product Price | Product Status |\n");
            products.forEach((product) -> {
                System.out.printf("| %s | %d | %s |%n", product.getName(), product.getPrice(), product.getStatus());
            });
        } else {
            System.out.println("no products available\n");
        }
    }

    private static void getProductById(ProductService productService) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("enter product id: \n");
        try {
            int productId = scanner.nextInt();
            Product product = productService.getProduct(productId);
            if (product != null) {
                System.out.println("| Product Name | Product Price | Product Status |\n");
                System.out.printf("| %s | %d | %s |%n", product.getName(), product.getPrice(), product.getStatus());
            } else {
                System.out.printf("no product with id %d %n", productId);
            }
        } catch (InputMismatchException ex) {
            System.err.println("product id must be an integer");
        }
    }

    private static void createProduct(ProductService productService) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("creating a new product\n");
        try {
            System.out.println("enter product name:\n");
            String productName = scanner.nextLine();
            System.out.println("enter product status:\n");
            String productStatus = scanner.nextLine();
            System.out.println("enter product price:\n");
            int productPrice = scanner.nextInt();
            if (!productName.isEmpty() && productStatus.matches("(?i)in_stock|out_of_stock|running_low") && productPrice > 0) {
                Product product = new Product(productName, productPrice, productStatus);
                boolean insertDone = productService.insertProduct(product);
                if (insertDone) {
                    System.out.println("product inserted successfully");
                } else System.err.println("something went wrong!");
            } else System.err.println("incorrect inputs");
        } catch (InputMismatchException ex) {
            System.err.println("invalid inputs");
        }
    }
    private static void getOrderById(OrderService orderService) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("enter order id: \n");
        try {
            int orderId = scanner.nextInt();
            Order order = orderService.getOrder(orderId);
            if (order != null) {
                int totalPrice=0;
                int totalQuantity=0;
                for (OrderedItems orderedItem:order.getOrderedItems()){
                    totalPrice+=orderedItem.getProduct().getPrice()*orderedItem.getQuantity();
                    totalQuantity+=orderedItem.getQuantity();
                }

                System.out.println("| Order ID | Products total Price | Products Quantity |\n");
                System.out.printf("| %d | %d | %d |%n", order.getId(), totalPrice,totalQuantity);
            } else {
                System.out.printf("no order with id %d %n", orderId);
            }
        } catch (InputMismatchException ex) {
            System.err.println("order id must be an integer");
        }
    }

    private static void createOrder(ProductService productService, OrderService orderService) {
        Scanner scanner = new Scanner(System.in);
        List<Product> products = productService.getAllProducts();
        if (!products.isEmpty()) {
            HashMap<Product,Integer> orderProducts = new HashMap<>();
            System.out.println("choose products to order and quantity: <Product Id> <Quantity> then press f to finish\n");
            products.forEach((product) -> {
                System.out.printf("| %d | %s | %d | %s |%n", product.getId(), product.getName(), product.getPrice(), product.getStatus());
            });
            String[] productsQuantity = scanner.nextLine().split("\\s+");
            while (!productsQuantity[0].equals("f")) {
                try {
                    int pId = Integer.parseInt(productsQuantity[0]);
                    int pQuantity = Integer.parseInt(productsQuantity[1]);
                    Product product = productService.getProduct(pId);
                    if (product != null) {
                        orderProducts.put(product,pQuantity);
                    } else System.err.println("invalid order id");
                } catch (NumberFormatException ex) {
                    System.err.println("id and quantities are numbers!");
                } catch (IndexOutOfBoundsException ex) {
                    System.err.println("specify product id and quantity!");
                }
                productsQuantity = scanner.nextLine().split("\\s+");
            }
            Order order=new Order();
            boolean done=orderService.insertOrder(order,orderProducts);
            if (done){
                System.out.println("order added successfully");
            }else System.err.println("something went wrong!");
        } else System.err.println("no product yet to create orders");
    }
    public static void getOrderedProducts(ProductService productService){
        productService.getOrderedProducts();
    }

    public static void updateProductById(ProductService productService) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("enter product id to update\n");
        try {
            int productId=Integer.parseInt(scanner.nextLine());
            Product product=productService.getProduct(productId);
            if (product!=null){
            System.out.println("enter product name:\n");
            String productName = scanner.nextLine();
            System.out.println("enter product status:\n");
            String productStatus = scanner.nextLine();
            System.out.println("enter product price:\n");
            int productPrice = scanner.nextInt();
            if (!productName.isEmpty() && productStatus.matches("(?i)in_stock|out_of_stock|running_low") && productPrice > 0) {
                product.setName(productName);
                product.setPrice(productPrice);
                product.setStatus(productStatus);
                boolean updateDone = productService.updateProduct(product);
                if (updateDone) {
                    System.out.println("product updated successfully");
                } else System.err.println("something went wrong!");
            } else System.err.println("incorrect inputs");
            }
            else System.err.println("no product with id: "+productId);
        } catch (InputMismatchException ex) {
            System.err.println("invalid inputs");
        }
        catch (NumberFormatException ex) {
            System.err.println("enter a valid product id");
        }
    }

    public static void deleteProductById(ProductService productService){
        Scanner scanner = new Scanner(System.in);
        System.out.println("enter product id to delete:\n");
        try {
            int productId = scanner.nextInt();
            Product product = productService.getProduct(productId);
            if (product != null) {
                boolean done=productService.deleteProduct(productId);
                if (done) System.out.println("product deleted\n");
                else System.err.println("something went wrong\n");
            } else {
                System.out.printf("no product with id %d %n", productId);
            }
        } catch (InputMismatchException ex) {
            System.err.println("product id must be an integer");
        }
    }
    public static void deleteAllProducts(ProductService productService){
        Scanner scanner=new Scanner(System.in);
        System.err.println("deleting all products\n");
        System.out.println("enter a password to confirm deletion\n");
        String delete=scanner.nextLine();
        if (!delete.isEmpty()){
            productService.deleteAllProducts();
        }
    }
}
