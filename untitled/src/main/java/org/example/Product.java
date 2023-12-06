import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Currency;
import java.util.UUID;

public class Product {
    private UUID id;
    private String name;
    private int quantity;
    private LocalDate creationDate;
    private Money price;

    public Product(UUID id, String name, int quantity, Money price) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.creationDate = LocalDate.now();
        this.price = price;
    }

    // Dodaj gettery do nowych pól
    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public Money getPrice() {
        return price;
    }
}