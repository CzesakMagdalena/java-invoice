package pl.edu.agh.mwo.invoice;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import pl.edu.agh.mwo.invoice.product.Product;

public class Invoice {
    private Map<Product, Integer> products = new HashMap<>();
    private static int nextNumber = 0;
    private final int number = ++nextNumber;

    public void addProduct(Product product) {
        addProduct(product, 1);
    }

    public void addProduct(Product product, Integer quantity) {
        if (product == null || quantity <= 0) {
            throw new IllegalArgumentException();
        }
        if (products.get(product) == null) {
            products.put(product, quantity);
        } else {
            products.put(product, products.get(product) + quantity);
        }
    }

    public BigDecimal getNetTotal() {
        BigDecimal totalNet = BigDecimal.ZERO;
        for (Product product : products.keySet()) {
            BigDecimal quantity = new BigDecimal(products.get(product));
            totalNet = totalNet.add(product.getPrice().multiply(quantity));
        }
        return totalNet;
    }

    public BigDecimal getTaxTotal() {
        return getGrossTotal().subtract(getNetTotal());
    }

    public BigDecimal getGrossTotal() {
        BigDecimal totalGross = BigDecimal.ZERO;
        for (Product product : products.keySet()) {
            BigDecimal quantity = new BigDecimal(products.get(product));
            totalGross = totalGross.add(product.getPriceWithTax().multiply(quantity));
        }
        return totalGross;
    }

    public void printInvoice() {
        System.out.println("Invoice number: " + this.getNumber());
        for (Product product : products.keySet()) {
            System.out.println("Product: " + product.getName() + "; quantity: " + products.get(product)
                    + "; unit price: " + product.getPriceWithTax());
        }

        System.out.println("Number of items: " + products.size());

    }

    public int getNumber() {
        return number;
    }

    public Map<Product, Integer> getProducts() {
        return products;
    }

}
