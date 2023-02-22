package clfxjvs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// Represents a collection of stamps.
public class StampCollection {

    private List<Stamp> stamps;

    // EFFECTS: constructs a stamp collection without any stamps present.
    public StampCollection() {
        this.stamps = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: adds the given stamp to the collection. Insertion order is
    //  maintained. Duplicate stamps are allowed, so stamps with the same
    //  title/country/value can be added multiple times.
    public void addStamp(Stamp stamp) {
        this.stamps.add(stamp);
    }

    // REQUIRES: minValue >= 0
    // EFFECTS: returns a list of all stamps that have a value higher or equal
    //  the provided minValue. Duplicate stamps are allowed. No order is
    //  guaranteed among the returned stamps.
    public List<Stamp> getStamps(int minValue) {
        ArrayList<Stamp> temp = new ArrayList<>();
        if (minValue <= 0) {
            System.out.println("minValue must be higher than or equal to 0.");
            return null;
        }
        for (Stamp s : stamps) {
            if (s.getValue() >= minValue) {
                temp.add(s);
            }
        }
        Collections.shuffle(temp);
        return temp;
    }

}