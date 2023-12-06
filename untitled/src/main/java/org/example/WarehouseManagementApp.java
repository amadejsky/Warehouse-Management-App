package com.example;

import com.google.common.collect.Maps;

import java.util.Map;
import java.util.Scanner;
import java.util.UUID;
import de.vandermeer.asciitable.AsciiTable;
import de.vandermeer.asciitable.CWC_FixedWidth;
import de.vandermeer.asciitable.TextAlignment;

public class WarehouseManagementApp {
    private static final Map<UUID, Product> products = Maps.newHashMap();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Wybierz akcję:");
            System.out.println("1. Dodaj produkt");
            System.out.println("2. Usuń produkt");
            System.out.println("3. Wyświetl produkt");
            System.out.println("4. Statystyki");
            System.out.println("5. Wyjście");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    addProduct(scanner);
                    break;
                case 2:
                    deleteProduct(scanner);
                    break;
                case 3:
                    showProduct(scanner);
                    break;
                case 4:
                    showStats();
                    break;
                case 5:
                    System.out.println("Dziękujemy. Do widzenia!");
                    System.exit(0);
                default:
                    System.out.println("Nieprawidłowy wybór. Spróbuj ponownie.");
            }
        }
    }

    private static void addProduct(Scanner scanner) {
        System.out.println("Podaj nazwę produktu:");
        String name = scanner.next();

        System.out.println("Podaj ilość produktu:");
        int quantity = scanner.nextInt();

        MoneyParser moneyParser = new MoneyParser();
        Money price;
        do {
            System.out.println("Podaj cenę produktu (format: <kwota> <waluta>):");
            String priceInput = scanner.next();
            try {
                price = moneyParser.parse(priceInput);
            } catch (IllegalArgumentException e) {
                System.out.println("Nieprawidłowa wartość, spróbuj jeszcze raz.");
                continue;
            }
            break;
        } while (true);

        UUID productId = UUID.randomUUID();
        Product product = new Product(productId, name, quantity, price);
        products.put(productId, product);

        System.out.println("Utworzono produkt o identyfikatorze: " + productId);
    }
    private static void deleteProduct(Scanner scanner) {
        System.out.println("Podaj identyfikator produktu do usunięcia:");
        String idString = scanner.next();

        try {
            UUID productId = UUID.fromString(idString);

            if (products.containsKey(productId)) {
                products.remove(productId);
                System.out.println("Usunięto produkt.");
            } else {
                System.out.println("Brak produktu o zadanym identyfikatorze.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Nieprawidłowy format identyfikatora. Spróbuj ponownie.");
        }
    }
    private static void showProduct(Scanner scanner) {
        System.out.println("Podaj identyfikator produktu do wyświetlenia:");
        String idString = scanner.next();

        try {
            UUID productId = UUID.fromString(idString);

            if (products.containsKey(productId)) {
                Product product = products.get(productId);
                System.out.println("Identyfikator produktu: " + product.getId());
                System.out.println("Nazwa produktu: " + product.getName());
                System.out.println("Sztuk na magazynie: " + product.getQuantity());
                System.out.println("Data dodania: " + product.getCreationDate());
            } else {
                System.out.println("Brak produktu o zadanym identyfikatorze.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Nieprawidłowy format identyfikatora. Spróbuj ponownie.");
        }
    }
    private static void showStats() {
        int totalProducts = products.size();
        int totalQuantity = 0;
        int maxQuantity = Integer.MIN_VALUE;
        String maxQuantityProductName = "";
        int minQuantity = Integer.MAX_VALUE;
        String minQuantityProductName = "";

        for (Product product : products.values()) {
            int quantity = product.getQuantity();
            totalQuantity += quantity;

            if (quantity > maxQuantity) {
                maxQuantity = quantity;
                maxQuantityProductName = product.getName();
            }

            if (quantity < minQuantity) {
                minQuantity = quantity;
                minQuantityProductName = product.getName();
            }
        }

        System.out.println("Produkty w magazynie: " + totalProducts);
        System.out.println("Sztuk: " + totalQuantity);
        System.out.println("Najwięcej sztuk produktu (" + maxQuantity + "): " + maxQuantityProductName);
        System.out.println("Najmniej sztuk (" + minQuantity + "): " + minQuantityProductName);
    }
    private static void updateProduct(Scanner scanner) {
        System.out.println("Podaj identyfikator produktu do aktualizacji:");
        String idString = scanner.next();

        try {
            UUID productId = UUID.fromString(idString);

            if (products.containsKey(productId)) {
                Product product = products.get(productId);

                System.out.println("Musisz określić, co chcesz zmodyfikować - cena, nazwa, ilość:");
                String fieldToUpdate = scanner.next().toLowerCase();

                switch (fieldToUpdate) {
                    case "cena":
                        updatePrice(scanner, product);
                        break;
                    case "nazwa":
                        updateName(scanner, product);
                        break;
                    case "ilość":
                        updateQuantity(scanner, product);
                        break;
                    default:
                        System.out.println("Nieprawidłowa opcja. Spróbuj ponownie.");
                }
            } else {
                System.out.println("Brak produktu o zadanym identyfikatorze.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Nieprawidłowy format identyfikatora. Spróbuj ponownie.");
        }
    }

    private static void updatePrice(Scanner scanner, Product product) {
        MoneyParser moneyParser = new MoneyParser();
        do {
            System.out.println("Podaj nową cenę (aktualna " + product.getPrice() + "):");
            String newPriceInput = scanner.next();
            try {
                Money newPrice = moneyParser.parse(newPriceInput);
                product.setPrice(newPrice);
                System.out.println("Zaktualizowano cenę.");
            } catch (IllegalArgumentException e) {
                System.out.println("Nieprawidłowa wartość, spróbuj jeszcze raz.");
                continue;
            }
            break;
        } while (true);
    }

    private static void updateName(Scanner scanner, Product product) {
        System.out.println("Podaj nową nazwę (aktualna " + product.getName() + "):");
        String newName = scanner.next();
        product.setName(newName);
        System.out.println("Zaktualizowano nazwę.");
    }

    private static void updateQuantity(Scanner scanner, Product product) {
        System.out.println("Podaj nową ilość (aktualna " + product.getQuantity() + "):");
        int newQuantity = scanner.nextInt();
        product.setQuantity(newQuantity);
        System.out.println("Zaktualizowano ilość.");
    }
    private static void showStats(String reportType) {
        switch (reportType) {
            case "łączna-wartość-produktów":
                showTotalValueReport();
                break;
            case "10-najbardziej-wartościowych-produktów":
                showTopProductsReport();
                break;
            default:
                showDefaultStats();
        }
    }

    private static void showTotalValueReport() {
        BigDecimal totalValue = BigDecimal.ZERO;

        for (Product product : products.values()) {
            BigDecimal pricePerUnit = product.getPrice().getAmount();
            BigDecimal quantity = BigDecimal.valueOf(product.getQuantity());
            totalValue = totalValue.add(pricePerUnit.multiply(quantity));
        }

        System.out.println("Łączna wartość wszystkich produktów wynosi: " + totalValue + " " + getCurrencyCode());
    }

    private static void showTopProductsReport() {
        List<Product> sortedProducts = products.values().stream()
                .sorted(Comparator.comparing(p -> p.getPrice().getAmount()).reversed())
                .limit(10)
                .collect(Collectors.toList());

        AsciiTable topProductsTable = new AsciiTable();
        topProductsTable.setTextAlignment(TextAlignment.LEFT);
        topProductsTable.addRule();
        topProductsTable.addRow("ID", "Nazwa produktu", "Cena");
        topProductsTable.addRule();

        for (Product product : sortedProducts) {
            topProductsTable.addRow(
                    product.getId(),
                    product.getName(),
                    product.getPrice().getAmount() + " " + getCurrencyCode()
            );
            topProductsTable.addRule();
        }

        topProductsTable.getRenderer().setCWC(new CWC_FixedWidth()
                .add(36)
                .add(40)
                .add(20)
        );

        System.out.println(topProductsTable.render());
    }

    private static void showDefaultStats() {
        // Kod dla aktualnej funkcjonalności STATS bez zmian
    }

    private static String getCurrencyCode() {
        // Pobierz domyślny kod waluty, można dostosować w zależności od potrzeb
        return Currency.getInstance(Locale.getDefault()).getCurrencyCode();
    }

}

}
}

