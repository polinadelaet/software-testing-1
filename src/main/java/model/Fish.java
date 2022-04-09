package model;

public class Fish {
    private String description;

    public Fish(String description) {
        this.description = description;
    }

    public void swim() {
        System.out.print("плавала, переливаясь, " + description + " рыбка.");
    }
}
