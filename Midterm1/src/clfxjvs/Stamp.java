package clfxjvs;

public class Stamp {
    public String getTitle() {
        return title;
    }

    public String getCountry() {
        return country;
    }

    public int getValue() {
        return value;
    }

    String title;
    String country;
    int value;

    Stamp(String title, String country, int value) {
        this.title = title;
        this.country = country;
        this.value = value;
    }


}