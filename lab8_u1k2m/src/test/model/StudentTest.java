package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class StudentTest {
    Bus b1, b2;
    Student s1, s2, s3, s4;
    @BeforeEach
    void beforeEach() {
        b1 = new Bus(10, 3);
        b2 = new Bus(203, 2);
        s1 = new Student(1, "Jim", 10);
        s2 = new Student(2, "Jessica", 12);
        s3 = new Student(3, "Ken", 11);
        s4 = new Student(4, "John", 13);
    }

    @Test
    void testGetters() {
        assertEquals(1, s1.getId());
        assertEquals(10, s1.getGrade());
        assertEquals("Jim", s1.getName());
    }

    @Test
    void testAddingBus() {
        s1.assignToBus(b1);
        assertEquals(b1, s1.getAssignedBus());
        assertTrue(b1.getStudents().contains(s1));
    }

    @Test
    void testAddingToMultipleBuses() {
        s1.assignToBus(b1);
        s1.assignToBus(b2);
        assertEquals(b2, s1. getAssignedBus());
        assertTrue(b2.getStudents().contains(s1));
        assertFalse(b1.getStudents().contains(s1));
    }

    @Test
    void testRemovingBus() {
        s1.assignToBus(b1);
        s1.removeFromBus();
        assertFalse(b1.getStudents().contains(s1));
        assertNull(s1.getAssignedBus());
    }

    @Test
    void testAssigningToNullBus() {
        s1.assignToBus(null);
        assertFalse(s1.isAssignedToBus());
    }
}