package eu.ensup.my_resto.model;

public enum Category {
    Beverage("boisson"),
    Appetizer("entrée"),
    Dish("plat"),
    Dessert("dessert");

    private String category;
    Category(String category) {
        this.category = category;
    }
}
