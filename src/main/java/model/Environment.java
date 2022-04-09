package model;

import java.util.ArrayList;
import java.util.List;

public class Environment {
    private Ford ford;
    private Artur artur;
    private List<Thing> things = new ArrayList<>();
    private boolean isEmpty = true;
    private boolean isThereSimpleThing = false;

    public Environment() {
        initEnvironment();
    }

    public Ford getFord() {
        return ford;
    }

    public Artur getArtur() {
        return artur;
    }

    public boolean isThereSimpleThing() {
        return isThereSimpleThing;
    }

    public void initEnvironment() {
        if (isEmpty) {
            ford = new Ford();
            artur = new Artur();
            addStoryThings();
            isEmpty = false;
        }
        ford.tryTakeBottle(); //1
        artur.doWatchAction();
        if (!isThereSimpleThing) {
            artur.setConfident(false);
            printThings();
            ford.printBirthPlace();
            ford.doOffer();
            printSimpleThing();
        }
    }

    private void addStoryThings() {
        Thing underWears = new Thing("нижним бельем ", "дентрасси");
        Thing mattresses = new Thing("матрацами ", " скворншельскими ");
        things.add(underWears);
        things.add(mattresses);
    }

    private void printThings() {
        System.out.println(things.get(0).getName() + things.get(0).getOwner() +
                "," + things.get(1).getOwner() + things.get(1).getName());
    }

    private void printSimpleThing() {
        System.out.print(" он увидел, к примеру, пакет кукурузных хлопьев");
    }

    public boolean isEmpty() {
        return isEmpty;
    }

    public int getNumberOfThings() {
        return things.size();
    }
}
