package clfxjvs;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestStampCollection {

    // DO NOT CHANGE THIS PART
    protected StampCollection testStampCollection;
    protected Stamp stampA;
    protected Stamp stampB;
    protected Stamp stampC;
    protected Stamp stampD;

    @BeforeEach
    public void setUp() {
        // DO NOT CHANGE THIS PART
        this.testStampCollection = new StampCollection();
        this.stampA = new Stamp("1851 Vermillion", "Canada", 120);
        this.stampB = new Stamp("1851 Queen Victoria", "Canada", 225);
        this.stampC = new Stamp("1918 Inverted Jenny", "USA", 1350);
        this.stampD = new Stamp("1867 Abraham Lincoln", "USA", 1400);

        testStampCollection.addStamp(stampA);
        testStampCollection.addStamp(stampB);
        testStampCollection.addStamp(stampC);
        testStampCollection.addStamp(stampD);
    }

    @Test // Tests the getStamps method when it returns two distinct stamps
    public void testGetStampsTwoDistinctStampsReturned() {
        ArrayList<Stamp> stamps = (ArrayList<Stamp>)testStampCollection.getStamps(1350);
        Stamp s1 = stamps.get(0);
        Stamp s2 = stamps.get(1);
        if (s1.getValue() == 1350) {
            assertEquals(stampC, s1);
            assertEquals(stampD, s2);
        } else {
            assertEquals(stampD, s1);
            assertEquals(stampC, s2);
        }

    }

    // Tests the getStamps method when it returns the same stamp three times
    // and no other stamps
    @Test
    public void testGetStampsThreeDuplicateStampsReturned() {
        ArrayList<Stamp> stamps1 = (ArrayList<Stamp>)testStampCollection.getStamps(1400);
        ArrayList<Stamp> stamps2 = (ArrayList<Stamp>)testStampCollection.getStamps(1400);
        ArrayList<Stamp> stamps3 = (ArrayList<Stamp>)testStampCollection.getStamps(1400);
        assertEquals(stamps1.get(0), stamps2.get(0));
        assertEquals(stamps2.get(0), stamps3.get(0));
    }


}