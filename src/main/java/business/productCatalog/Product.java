package business.productCatalog;

import dao.CategoryDAO;
import dao.ProductDAO;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Product {
    private int code;
    private String name;
    private String description;
    private int stockQuantity;
    private double price;
    private int categoryCode;
    private int alertQuantity;
    private String expirationDate;


    public Product(String name, String description, int stockQuantity, double price, int categoryCode) {
        this.name = name;
        this.description = description;
        this.stockQuantity = stockQuantity;
        this.price = price;
        this.categoryCode = categoryCode;
        this.alertQuantity = -1;
    }

    public Product() {
        this.alertQuantity = -1;
    }

    public Product(int code, String name, String description, int stockQuantity, double price, int categoryCode) {
        this.code = code;
        this.name = name;
        this.description = description;
        this.stockQuantity = stockQuantity;
        this.price = price;
        this.categoryCode = categoryCode;
        this.alertQuantity = -1;
    }

    public int getAlertQuantity() {
        return alertQuantity;
    }

    public void setAlertQuantity(int alertQuantity) {
        this.alertQuantity = alertQuantity;
    }
    public String getExpirationDate() {
        return expirationDate;
    }
    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    public int getCode() {
        return code;
    }
    public void setCode(int code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }
    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public double getPrice() {
        return price;
    }
    public int getCategoryCode() {return categoryCode;}
    public void setCategoryCode(int categoryCode) {
        this.categoryCode = categoryCode;
    }
    public int getCategoryCodeById(int code) {
        ProductDAO productDAO = new ProductDAO();
        Product product = productDAO.getProductById(code);
        return product.getCategoryCode();
    }

    public Product getProductById(int code) {
        ProductDAO productDAO = new ProductDAO();
        return productDAO.getProductById(code);
    }

    public void updateStock(int quantity) {
        this.stockQuantity += quantity;
    }

    public double getCurrentPrice() {
        return price;
    }

    public List<Product> getProductsByCategory(String categoryName) {
        ProductDAO productDAO = new ProductDAO();
        if (categoryName.equals("All Categories")) {
            return productDAO.getAllProducts();
        }
        else {
            CategoryDAO categoryDAO = new CategoryDAO();
            int catCode = categoryDAO.getCategoryCodeByName(categoryName);
            return productDAO.getProductsByCategoryCode(catCode);
        }
    }

    public void setName(String productName) {
        this.name = productName;
    }

    public void setDescription(String productDescription) {
        this.description = productDescription;
    }
    public void setPrice(double price) {
        this.price = price;
    }

    public void deleteProducts(List<Product> products) {
        ProductDAO productDAO = new ProductDAO();
        for (Product product : products) {
            productDAO.removeProduct(product.getCode());
        }
    }

    public ArrayList<Product> filterExpiredProducts() {
        ArrayList<Product> expiredProducts = new ArrayList<>();
        List<Product> products = getProductsByCategory("All Categories");

        Calendar currentDate = Calendar.getInstance();
        currentDate.setTime(new Date());

        for (Product p : products) {
            String expirationDateString = p.getExpirationDate();

            if (expirationDateString != null) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    Date expirationDate = sdf.parse(expirationDateString);

                    Calendar expirationCalendar = Calendar.getInstance();
                    expirationCalendar.setTime(expirationDate);

                    if (expirationCalendar.get(Calendar.YEAR) < currentDate.get(Calendar.YEAR) ||
                            (expirationCalendar.get(Calendar.YEAR) == currentDate.get(Calendar.YEAR) &&
                                    expirationCalendar.get(Calendar.MONTH) < currentDate.get(Calendar.MONTH)) ||
                            (expirationCalendar.get(Calendar.YEAR) == currentDate.get(Calendar.YEAR) &&
                                    expirationCalendar.get(Calendar.MONTH) == currentDate.get(Calendar.MONTH) &&
                                    expirationCalendar.get(Calendar.DAY_OF_MONTH) < currentDate.get(Calendar.DAY_OF_MONTH))) {
                        expiredProducts.add(p);
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }

        return expiredProducts;
    }


    public void restockItems() {
        try {
            Scanner scanner = new Scanner(new File("restock.txt"));
            if (scanner.hasNextLine()) {
                scanner.nextLine();
            }
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] values = line.split(",");

                if (values.length == 2) {
                    int code = Integer.parseInt(values[0].trim());
                    int quantity = Integer.parseInt(values[1].trim());
                    if (quantity > 0) {
                        Product product = getProductById(code);
                        if (product != null) {
                            product.updateStock(quantity);
                            product.updateProductInDB(product);
                        }
                    }

                } else {
                    System.err.println("Invalid line: " + line);
                }
            }

            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    private void updateProductInDB(Product product) {
        ProductDAO productDAO = new ProductDAO();
        productDAO.editProduct(product);
    }
}


