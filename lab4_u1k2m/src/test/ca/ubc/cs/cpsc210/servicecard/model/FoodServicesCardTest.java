package ca.ubc.cs.cpsc210.servicecard.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// Unit tests for FoodServiceCard class
class FoodServicesCardTest {
    @Test
    void testConstructor() {
        FoodServicesCard card = new FoodServicesCard(-10);
        assertEquals(card.getBalance(), -10);
        assertEquals(card.getRewardPoints(), 0);
    }

    @Test
    void testReload() {
        FoodServicesCard card = new FoodServicesCard(0);
        card.reload(10);
        assertEquals(card.getBalance(), 10);
    }

    @Test
    void testMakePurchase() {
        FoodServicesCard card = new FoodServicesCard(4500);
        card.makePurchase(4100);
        assertEquals(card.getBalance(), 400 + 40);
        assertEquals(card.getRewardPoints(), 100);
    }
}