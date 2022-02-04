package eu.ensup.my_resto.model;

public enum Category {
    BEVERAGE("boisson"),
    APPETIZER("entrée"),
    DISH("plat"),
    DESSERT("dessert");

    private String cat;
    Category(String category) {
        this.cat = category;
    }
}
