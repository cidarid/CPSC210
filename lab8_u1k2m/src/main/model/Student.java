package model;

// Represents a student with an id, name, the grade in which the student is registered and bus to which
// student is assigned to travel
public class Student {
    private int id;
    private int grade;
    private String name;
    private Bus assignedBus;

    // EFFECTS: constructs student with id, name and registered grade, and with no bus assigned
    public Student(int id, String name, int grade) {
        this.id = id;
        this.name = name;
        this.grade = grade;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getGrade() {
        return grade;
    }

    public Bus getAssignedBus() {
        return assignedBus;
    }

    // EFFECTS: returns true if student is assigned to a bus, false otherwise
    public boolean isAssignedToBus() {
        return assignedBus != null;
    }

    // REQUIRES: !bus.isFull()
    // MODIFIES: this, bus
    // EFFECTS: assigns student to travel on bus
    public void assignToBus(Bus bus) {
        if (bus == null || bus.isFull()) {
            return;
        }
        if (this.assignedBus == null || !this.assignedBus.equals(bus)) {
            if (this.assignedBus != null) {
                this.assignedBus.removeStudent(this);
            }
            this.assignedBus = bus;
            bus.addStudent(this);
        }
    }

    // MODIFIES: this, Bus b = getAssignedBus()
    // EFFECTS: if student is assigned to a bus, removes student from assigned bus;
    // otherwise has no effect
    public void removeFromBus() {
        if (assignedBus != null) {
            Bus temp = assignedBus;
            assignedBus = null;
            temp.removeStudent(this);
        }
    }
}
