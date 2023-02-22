package model;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class BusTest {
    Bus b;
    Student s1, s2, s3, s4;
    @BeforeEach
    void beforeEach() {
        b = new Bus(10, 3);
        s1 = new Student(1, "Jim", 10);
        s2 = new Student(2, "Jessica", 12);
        s3 = new Student(3, "Ken", 11);
        s4 = new Student(4, "John", 13);
    }

    @Test
    void testGetters() {
        assertEquals(10, b.getId());
        assertEquals(3, b.getCapacity());
        assertEquals(new HashSet<Student>(), b.getStudents());
    }

    @Test
    void testChaperone() {
        Chaperone c = new Chaperone("Tim");
        assertNull(b.getChaperone());
        assertFalse(b.hasChaperone());
        b.setChaperone(c);
        assertEquals(c, b.getChaperone());
        assertTrue(b.hasChaperone());
    }

    @Test
    void testAddingStudent() {
        b.addStudent(s1);
        assertTrue(b.getStudents().contains(s1));
        assertEquals(1, b.getStudents().size());
        assertTrue(s1.isAssignedToBus());
        assertEquals(s1.getAssignedBus(), b);
    }

    @Test
    void testAddingMultipleStudents() {
        b.addStudent(s1);
        b.addStudent(s2);
        assertTrue(b.getStudents().contains(s1) && b.getStudents().contains(s2));
        assertEquals(2, b.getStudents().size());
        assertTrue(s1.isAssignedToBus() && s2.isAssignedToBus());
        assertEquals(s1.getAssignedBus(), b);
        assertEquals(s2.getAssignedBus(), b);
    }

    @Test
    void testRemovingStudent() {
        b.addStudent(s1);
        b.removeStudent(s1);
        assertFalse(b.getStudents().contains(s1));
        assertEquals(0, b.getStudents().size());
        assertNull(s1.getAssignedBus());
    }

    @Test
    void testAddingToCapacity() {
        b.addStudent(s1);
        b.addStudent(s2);
        b.addStudent(s3);
        b.addStudent(s4);
        Set<Student> students = b.getStudents();
        assertTrue(students.contains(s1) && students.contains(s2) && students.contains(s3));
        assertFalse(students.contains(s4));
    }

    @Test
    void testRemovingNullStudent() {
        b.removeStudent(null);
        // pass
    }

    @Test
    void testUnmodifiableSet() {
        b.addStudent(s1);
        assertEquals(Collections.unmodifiableSet(new HashSet<Student>()).getClass(), b.getStudents().getClass());
    }
}