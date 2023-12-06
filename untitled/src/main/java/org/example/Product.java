class Product {
    private UUID id;
    private String name;
    private int quantity;
    private LocalDate creationDate;

    public Product(UUID id, String name, int quantity) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.creationDate = LocalDate.now();
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
}