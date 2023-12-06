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
            System.out.println("3. Wyjście");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    addProduct(scanner);
                    break;
                case 2:
                    deleteProduct(scanner);
                    break;
                case 3:
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
}
}

class Product {
    private UUID id;
    private String name;
    private int quantity;

    public Product(UU
