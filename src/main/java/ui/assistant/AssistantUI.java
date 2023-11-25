package ui.assistant;

import business.orderProcessing.Cart;
import business.orderProcessing.Item;
import business.productCatalog.Category;
import business.productCatalog.Product;
import business.userAuth.SalesAssistant;
import dao.CategoryDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class AssistantUI extends JFrame {

    private JTextField searchField;
    private JTextField quantityField;
    private JButton searchButton;
    private JTable searchResultsTable;
    private JTable cartTable;
    private JLabel totalCostLabel;
    private JButton addToCartButton;
    private JButton processOrderButton;
    private JButton clearButton;
    private Cart cart;
    private JComboBox categoryComboBox;
    private SalesAssistant salesAssistant;

    public AssistantUI() {
        salesAssistant = new SalesAssistant();
        setTitle("Assistant Interface");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        JPanel topPanel = new JPanel(new GridLayout(1,2));
        JPanel bottomPanel = new JPanel(new GridLayout(2,2));
        JPanel middlePanel = new JPanel(new GridLayout(1,2));

        JPanel mainPanel = new JPanel(new BorderLayout());

        // Search Panel
        JPanel searchPanel = new JPanel(new GridLayout(1,3));
        JLabel searchLabel = new JLabel("Search Product:");
        searchField = new JTextField(20);
        searchButton = new JButton("Search");
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchProduct();
            }
        });

        categoryComboBox = new JComboBox<>(getCategoryNames());
        searchPanel.add(searchLabel);
        searchPanel.add(searchField);
        searchPanel.add(categoryComboBox);
        searchPanel.add(searchButton);

        bottomPanel.add(searchPanel);


        // Total Cost Label
        totalCostLabel = new JLabel("Total Cost: $0.00", JLabel.CENTER);
        bottomPanel.add(totalCostLabel);

        // Search Results Table
        String[] searchColumnNames = {"Product ID", "Name", "Description", "Quantity", "Price"};
        Object[][] searchData = new Object[0][4];
        DefaultTableModel searchModel = new DefaultTableModel(searchData, searchColumnNames);
        searchResultsTable = new JTable(searchModel);
        JScrollPane searchScrollPane = new JScrollPane(searchResultsTable);
        middlePanel.add(searchScrollPane);

        // labels for tables
        JLabel searchTableLabel = new JLabel("Search Results:", JLabel.CENTER);
        topPanel.add(searchTableLabel);
        JLabel cartTableLabel = new JLabel("Cart:", JLabel.CENTER);
        topPanel.add(cartTableLabel);

        // Quantity Panel
        JPanel quantityPanel = new JPanel(new FlowLayout());
        JLabel quantityLabel = new JLabel("Quantity:");
        JButton plusButton = new JButton("+");
        JButton minusButton = new JButton("-");
        quantityField = new JTextField("1", 5);
        quantityField.setEditable(false);

        quantityPanel.add(quantityLabel);
        quantityPanel.add(minusButton);
        quantityPanel.add(quantityField);
        quantityPanel.add(plusButton);

        minusButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int quantity = Integer.parseInt(quantityField.getText());
                if (quantity > 1) {
                    quantity--;
                    quantityField.setText(Integer.toString(quantity));
                }
            }
        });

        plusButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int quantity = Integer.parseInt(quantityField.getText());
                quantity++;
                quantityField.setText(Integer.toString(quantity));
            }
        });

        // Add to Cart Button
        addToCartButton = new JButton("Add to Cart");
        addToCartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addToCart();
            }
        });

        JPanel addToCartPanel = new JPanel();
        addToCartPanel.add(quantityPanel);
        addToCartPanel.add(addToCartButton);

        bottomPanel.add(addToCartPanel);

        // Cart Table
        String[] cartColumnNames = {"Product ID", "Name", "Quantity", "Price", "Total Price"};
        Object[][] cartData = new Object[0][4];
        DefaultTableModel cartModel = new DefaultTableModel(cartData, cartColumnNames);
        cartTable = new JTable(cartModel);
        JScrollPane cartScrollPane = new JScrollPane(cartTable);
        middlePanel.add(cartScrollPane);
        mainPanel.add(middlePanel, BorderLayout.CENTER);

        //clear button
        clearButton = new JButton("Clear");
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clear();
            }
        });
        JPanel processClearPanel = new JPanel();
        processClearPanel.add(clearButton);

        // Process Order Button
        processOrderButton = new JButton("Process Order");
        processOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                processOrder();
            }
        });
        processClearPanel.add(processOrderButton);
        bottomPanel.add(processClearPanel);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
        mainPanel.add(topPanel, BorderLayout.NORTH);
        add(mainPanel);
        cart = new Cart();
        updateResults();
        setVisible(true);
    }

    public void clear() {
        DefaultTableModel cartModel = (DefaultTableModel) cartTable.getModel();
        cartModel.setRowCount(0);
        totalCostLabel.setText("Total Cost: $0.00");
        cart.clear();
    }

    private void searchProduct() {
        if (searchField.getText().isEmpty()) {
            updateResults();
        }
        else {
            String searchText = searchField.getText();
            String categoryName = categoryComboBox.getSelectedItem().toString();
            List<Product> searchedProducts = cart.searchProducts(searchText, categoryName);
            DefaultTableModel searchModel = (DefaultTableModel) searchResultsTable.getModel();
            searchModel.setRowCount(0);
            for (Product p : searchedProducts) {
                searchModel.addRow(new Object[]{p.getCode(), p.getName(), p.getDescription(), p.getStockQuantity(), p.getPrice()});
            }
        }
    }
    public void updateResults() {
        String selectedCategory = categoryComboBox.getSelectedItem().toString();
        Product product = new Product();
        List<Product> products = product.getProductsByCategory(selectedCategory);

        DefaultTableModel searchModel = (DefaultTableModel) searchResultsTable.getModel();
        searchModel.setRowCount(0);

        for (Product p : products) {
            searchModel.addRow(new Object[]{p.getCode(), p.getName(), p.getDescription(), p.getStockQuantity(), p.getPrice()});
        }
    }

    private void displayCart() {
        List<Item> items = cart.getItemsList();
        DefaultTableModel cartModel = (DefaultTableModel) cartTable.getModel();
        cartModel.setRowCount(0);

        for (Item i : items) {
            Object[] itemDetails = {i.getProduct().getCode(), i.getProduct().getName(), i.getQuantityOrdered(), i.getProduct().getPrice(), i.total()};
            cartModel.addRow(itemDetails);
        }
    }

    private void addToCart() {
        // Get the selected product details
        int selectedRow = searchResultsTable.getSelectedRow();
        if (selectedRow != -1) {
            int productId = (int) searchResultsTable.getValueAt(selectedRow, 0);
            String productName = (String) searchResultsTable.getValueAt(selectedRow, 1);
            String productDescription = (String) searchResultsTable.getValueAt(selectedRow, 2);
            int productQuantity = (int) searchResultsTable.getValueAt(selectedRow, 3);
            double productPrice = (double) searchResultsTable.getValueAt(selectedRow, 4);
            int quantity;

            // Retrieve the quantity from the text field or use 1 as the default value
            try {
                quantity = Integer.parseInt(quantityField.getText());
            } catch (NumberFormatException ex) {
                quantity = 1;
            }
            Product product = new Product();
            product.setCode(productId);
            product.setName(productName);
            product.setDescription(productDescription);
            product.setStockQuantity(productQuantity);
            product.setPrice(productPrice);
            product.setCategoryCode(product.getCategoryCodeById(productId));
            Item item = new Item(product, quantity);
            cart.add(item);

            displayCart();

            // Update total cost
            updateTotalCost();
            JOptionPane.showMessageDialog(
                    this,
                    "Product added to cart.",
                    "Add to Cart",
                    JOptionPane.INFORMATION_MESSAGE
            );

            // Reset the quantity field
            quantityField.setText("1");
        } else {
            JOptionPane.showMessageDialog(
                    this,
                    "Please select a product to add to the cart.",
                    "Add to Cart Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
    private void processOrder() {
        // Display the order processing dialog
        OrderProcessingDialog orderProcessingDialog = new OrderProcessingDialog(this, cart, salesAssistant);
    }

    private void updateTotalCost() {
        double totalCost = cart.total();
        totalCostLabel.setText("Total Cost: $" + String.format("%.2f", totalCost));
    }

    private List<Category> getCategories() {
        CategoryDAO categoryDAO = new CategoryDAO();
        return categoryDAO.getAllCategories();
    }

    private String[] getCategoryNames() {
        List<Category> categories = getCategories();
        String[] categoryNames = new String[categories.size() + 1];
        categoryNames[0] = "All Categories";
        int i = 1;
        for (Category c : categories) {
            categoryNames[i] = c.getName();
            i++;
        }
        return categoryNames;
    }
}
