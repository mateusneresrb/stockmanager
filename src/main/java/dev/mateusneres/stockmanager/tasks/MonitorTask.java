package dev.mateusneres.stockmanager.tasks;

import dev.mateusneres.stockmanager.controllers.StockController;
import dev.mateusneres.stockmanager.models.Product;
import dev.mateusneres.stockmanager.models.Purchase;
import dev.mateusneres.stockmanager.models.PurchaseProduct;
import dev.mateusneres.stockmanager.models.Supplier;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * This class is responsible for monitoring the files.
 */
public class MonitorTask implements Runnable {

    private final StockController stockController;
    private final String[] tables = {"users", "products", "suppliers", "purchases_product"};
    private final Path currentPath = Paths.get("");

    /**
     * Constructor of MonitorTask.
     * @param stockController StockController
     */
    public MonitorTask(StockController stockController) {
        this.stockController = stockController;
    }

    @Override
    public void run() {
        String currentDirectory = currentPath.toAbsolutePath().toString();
        File[] files = getInsertionsFiles(currentDirectory);

        for (File file : files) {
            if (file == null || !file.exists() || !file.isFile() || file.length() == 0) continue;

            List<String> invalidLines = new ArrayList<>();
            String line;
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                while ((line = reader.readLine()) != null) {
                    if (readLineAndInsert(file.getName().toLowerCase(), line.replace(" ", ""))) continue;
                    invalidLines.add(line);
                }
            } catch (IOException e) {
                Logger.getGlobal().warning("[MonitorTask] Occurred an error while reading the file: " + file.getName());
            }

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                writer.flush();
                for (String invalidLine : invalidLines) {
                    writer.write(invalidLine + "\n");
                }
            } catch (IOException e) {
                Logger.getGlobal().warning("[MonitorTask] Occurred an error while writing the file: " + file.getName());
            }
        }
    }

    private boolean readLineAndInsert(String fileName, String line) {
        String[] data = line.split(",");

        if (fileName.equals("insere_users.txt") && data.length == 3) {
            return stockController.getUserRepository().register(data[0], data[1], data[2].toCharArray());
        }

        if (fileName.equals("insere_products.txt") && data.length == 3) {
            return stockController.createProduct(Product.builder().
                    name(data[0]).price(Double.parseDouble(data[1]))
                    .amountAvailable(Integer.parseInt(data[2])).build());
        }

        if (fileName.equals("insere_suppliers.txt") && data.length == 2) {
            return stockController.createSupplier(Supplier.builder().
                    name(data[0]).address(data[1]).build());
        }

        if (fileName.equals("insere_purchases_product.txt") && data.length == 3) {
            Product product = stockController.getProductList().stream().filter(product1 -> product1.getId() == Integer.parseInt(data[0])).findFirst().orElse(null);
            Supplier supplier = stockController.getSupplierList().stream().filter(supplier1 -> supplier1.getId() == Integer.parseInt(data[1])).findFirst().orElse(null);

            int quantity = Integer.parseInt(data[2]);
            if (product == null || supplier == null) return false;

            double totalPrice = product.getPrice() * quantity;
            Purchase purchase = new Purchase(Instant.now(), totalPrice, supplier);

            return stockController.createPurchaseProduct(PurchaseProduct.builder()
                    .product(product).purchase(purchase)
                    .supplier(supplier).quantity(quantity).build());
        }

        return false;
    }

    private File[] getInsertionsFiles(String directory) {
        File[] files = new File[tables.length];

        for (int i = 0; i < tables.length; i++) {
            files[i] = new File(directory + "/insere_" + tables[i] + ".txt");
        }

        return files;
    }

}
