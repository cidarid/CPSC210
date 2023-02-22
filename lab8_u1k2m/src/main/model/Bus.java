package model;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

// Represents a bus having an id, capacity, chaperone and set of students assigned to travel on bus
public class Bus {

    private Chaperone chaperone;
    private Set<Student> students;
    private int id;
    private int capacity;

    // EFFECTS: constructs an empty bus with id and capacity that has no chaperone
    public Bus(int id, int capacity) {
        this.id = id;
        this.capacity = capacity;
        this.students = new HashSet<>();
    }

    public int getId() {
        return id;
    }

    public int getCapacity() {
        return capacity;
    }

    public Chaperone getChaperone() {
        return chaperone;
    }

    // EFFECTS: returns true if bus has a chaperone assigned, false otherwise
    public boolean hasChaperone() {
        return chaperone != null;
    }

    // EFFECTS: returns an unmodifiable set of students assigned to travel on this bus
    public Set<Student> getStudents() {
        return Collections.unmodifiableSet(students);
    }

    // MODIFIES: this
    // EFFECTS: assigns the chaperone for this bus
    public void setChaperone(Chaperone chaperone) {
        this.chaperone = chaperone;
    }

    // EFFECTS: returns true if bus is full, false otherwise
    public boolean isFull() {
        return students.size() == capacity;
    }

    // REQUIRES: !isFull()
    // MODIFIES: this, student
    // EFFECTS: adds student to this bus
    public void addStudent(Student student) {
        if (isFull()) {
            return;
        }
        if (!students.contains(student)) {
            students.add(student);
            student.assignToBus(this);
        }
    }

    // MODIFIES: this, student
    // EFFECTS: removes student from this bus
    public void removeStudent(Student student) {
        if (student != null && this.students.contains(student)) {
            students.remove(student);
            student.removeFromBus();
        }
    }
}
