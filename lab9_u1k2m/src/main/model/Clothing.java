package model;

import ui.Printer;

import java.util.ArrayList;

public class Clothing extends Wearable {
    ArrayList<Wearable> items;

    public Clothing(String name) {
        super(name);
        items = new ArrayList<>();
    }

    public void add(Wearable w) {
        items.add(w);
    }

    @Override
    public void display(String onTopOf) {
        super.display(onTopOf);
        for (Wearable w : items) {
            w.display(this.name);
        }
    }
}
