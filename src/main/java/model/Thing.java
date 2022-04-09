package model;

public class Thing {
    private final String name;
    private final String owner;

    public Thing(String name, String owner) {
        this.name = name;
        this.owner = owner;
    }

    public String getName() {
        return name;
    }

    public String getOwner() {
        return owner;
    }
}
