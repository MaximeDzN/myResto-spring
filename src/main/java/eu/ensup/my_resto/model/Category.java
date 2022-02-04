package eu.ensup.my_resto.model;

/**
 * The enum Category.
 */
public enum Category {
    /**
     * Beverage category.
     */
    BEVERAGE("boisson"),
    /**
     * Appetizer category.
     */
    APPETIZER("entrée"),
    /**
     * Dish category.
     */
    DISH("plat"),
    /**
     * Dessert category.
     */
    DESSERT("dessert");


    private String cat;
    Category(String category) {
        this.cat = category;
    }
}
