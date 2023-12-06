package com.example;

import com.google.common.collect.Maps;

import java.util.Map;
import java.util.Scanner;
import java.util.UUID;

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

        UUID productId = UUID.randomUUID();
        Product product = new Product(productId, name, quantity);
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
}

}
}

