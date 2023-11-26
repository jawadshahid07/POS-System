package productTests;

import business.productCatalog.Category;
import business.productCatalog.Product;
import dao.CategoryDAO;
import dao.ProductDAO;
import org.junit.Test;

import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.*;

public class CategoryTest {

    @Test
    public void testAddCategory() {
        Category category = new Category("TestCategory", "CategoryDescription");

        assertTrue(category.addCategory(category));

        int categoryCode = category.getCategoryCode("TestCategory");
        CategoryDAO categoryDAO = new CategoryDAO();
        categoryDAO.removeCategory(categoryCode);
    }

    @Test
    public void testAddCategoryWithExistingName() {
        Category category1 = new Category("TestCategory", "CategoryDescription");
        Category category2 = new Category("TestCategory", "AnotherCategoryDescription");

        assertTrue(category1.addCategory(category1));
        assertFalse(category2.addCategory(category2));

        int categoryCode = category1.getCategoryCode("TestCategory");
        CategoryDAO categoryDAO = new CategoryDAO();
        categoryDAO.removeCategory(categoryCode);
    }

    @Test
    public void testRemoveCategory() {
        Category category = new Category("TestCategory", "CategoryDescription");

        assertTrue(category.addCategory(category));
        int categoryCode = category.getCategoryCode("TestCategory");
        category.setCode(categoryCode);
        category.removeCategory(category);

        CategoryDAO categoryDAO = new CategoryDAO();
        assertEquals(0 ,categoryDAO.getCategoryCodeByName(category.getName()));
    }

    @Test
    public void testAddProduct() {
        Category category = new Category("TestCategory", "CategoryDescription");
        category.addCategory(category);
        int categoryCode = category.getCategoryCode("TestCategory");
        Product product = new Product("TestProduct", "ProductDescription", 10, 20.0, categoryCode);

        assertTrue(category.addProduct(product));

        CategoryDAO categoryDAO = new CategoryDAO();
        categoryDAO.removeCategory(categoryCode);
    }

    @Test
    public void testAddProductWithExistingName() {
        Category category = new Category("TestCategory", "CategoryDescription");
        category.addCategory(category);
        int categoryCode = category.getCategoryCode("TestCategory");
        Product product1 = new Product("TestProduct", "ProductDescription", 10, 20.0, categoryCode);
        Product product2 = new Product("TestProduct", "AnotherProductDescription", 15, 25.0, categoryCode);

        assertTrue(category.addProduct(product1));
        assertFalse(category.addProduct(product2));

        CategoryDAO categoryDAO = new CategoryDAO();
        categoryDAO.removeCategory(categoryCode);
    }

    @Test
    public void testRemoveProduct() {
        Category category = new Category("TestCategory", "CategoryDescription");
        category.addCategory(category);
        int categoryCode = category.getCategoryCode("TestCategory");
        Product product = new Product("TestProduct", "ProductDescription", 10, 20.0, categoryCode);

        assertTrue(category.addProduct(product));
        ProductDAO productDAO = new ProductDAO();
        try {
            product = productDAO.getProductByName("TestProduct");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        category.removeProduct(product.getCode());

        assertNull(productDAO.getProductById(product.getCode()));

        CategoryDAO categoryDAO = new CategoryDAO();
        categoryDAO.removeCategory(categoryCode);
    }

    @Test
    public void testEditProduct() {
        Category category = new Category("TestCategory", "CategoryDescription");
        category.addCategory(category);
        int categoryCode = category.getCategoryCode("TestCategory");
        Product product = new Product("TestProduct", "ProductDescription", 10, 20.0, categoryCode);

        assertTrue(category.addProduct(product));

        ProductDAO productDAO = new ProductDAO();
        try {
            product = productDAO.getProductByName("TestProduct");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        product.updateStock(5);
        category.editProduct(product);
        assertEquals(15, product.getStockQuantity());


        CategoryDAO categoryDAO = new CategoryDAO();
        categoryDAO.removeCategory(categoryCode);
    }

    @Test
    public void testGetCategoryCode() {
        Category category = new Category("TestCategory", "CategoryDescription");

        assertTrue(category.addCategory(category));
        int code = category.getCategoryCode("TestCategory");

        assertNotEquals(0, code);

        CategoryDAO categoryDAO = new CategoryDAO();
        categoryDAO.removeCategory(code);
    }

    @Test
    public void testLoadCategories() {
        Category category1 = new Category("TestCategory1", "CategoryDescription1");


        CategoryDAO categoryDAO = new CategoryDAO();
        category1.addCategory(category1);

        List<Category> categories = category1.loadCategories();
        boolean flag = false;
        for (Category c : categories) {
            if (c.getName().equals("TestCategory1")) {
                flag = true;
                categoryDAO.removeCategory(c.getCode());
                break;
            }
        }
        assertTrue(flag);
    }
}

