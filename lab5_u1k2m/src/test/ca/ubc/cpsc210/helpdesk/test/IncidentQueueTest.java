package ca.ubc.cpsc210.helpdesk.test;


import ca.ubc.cpsc210.helpdesk.model.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

class IncidentQueueTest {
    private IncidentQueue iq;
    @BeforeEach
    void beforeEach() {
        iq = new IncidentQueue();
    }

    @Test
    void testConstructor() {
        assert(iq.isEmpty());
    }

    @Test
    void testAddIncident() {
        Incident i = new Incident(32, "Hello");
        iq.addIncident(i);
        assertTrue(iq.contains(i));
    }

    @Test
    void testAddTooManyIncidents() {
        Incident i1 = new Incident(20, "9");
        for (int i = 0; i < 10; i++) {
            iq.addIncident(i1);
        }
        assertFalse(iq.addIncident(i1));
    }

    @Test
    void testGetNextIncident() {
        Incident i = new Incident(20, "Deutsch");
        iq.addIncident(i);
        assertEquals(i, iq.getNextIncident());
        assertEquals(iq.length(), 0);
    }

    @Test
    void testGetPositionInQueueOfCaseNumber() {
        iq.addIncident(new Incident(20, "Deutsch"));
        iq.addIncident(new Incident(32, "Hello"));
        iq.addIncident(new Incident(42, "John"));
        iq.addIncident(new Incident(8123, "o"));
        iq.addIncident(new Incident(5, "q"));
        assertEquals(2, iq.getPositionInQueueOfCaseNumber(32));
        assertEquals(-1, iq.getPositionInQueueOfCaseNumber(22));
        assertEquals(3, iq.getPositionInQueueOfCaseNumber(42));
        assertEquals(5, iq.getPositionInQueueOfCaseNumber(5));
    }

    @Test
    void testGetPositionInQueueOfCaseNumberWithRemoval() {
        Incident i1 = new Incident(20, "Deutsch");
        Incident i2 = new Incident(32, "Hello");
        Incident i3 = new Incident(42, "John");
        iq.addIncident(i1);
        iq.addIncident(i2);
        iq.addIncident(i3);
        iq.getNextIncident();
        assertEquals(-1, iq.getPositionInQueueOfCaseNumber(20));
        assertEquals(1, iq.getPositionInQueueOfCaseNumber(32));
        assertEquals(2, iq.getPositionInQueueOfCaseNumber(42));
    }

    @Test
    void testLength() {
        assertEquals(0, iq.length());
        Incident i1 = new Incident(20, "Deutsch");
        Incident i2 = new Incident(32, "Hello");
        iq.addIncident(i1);
        iq.addIncident(i2);
        assertEquals(2, iq.length());
    }

    @Test
    void testIsEmpty() {
        assertTrue(iq.isEmpty());
    }

    @Test
    void testIsFull() {
        Incident i1 = new Incident(20, "9");
        for (int i = 0; i < 10; i++) {
            iq.addIncident(i1);
        }
        assertTrue(iq.isFull());
    }

    @Test
    void testIsNotFull() {
        Incident i1 = new Incident(20, "9");
        for (int i = 0; i < 8; i++) {
            iq.addIncident(i1);
        }
        assertFalse(iq.isFull());
    }
}