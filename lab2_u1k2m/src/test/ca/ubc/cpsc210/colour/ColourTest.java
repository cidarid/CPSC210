package ca.ubc.cpsc210.colour;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ColourTest {

    Colour a, b, c;
    @BeforeEach
    void runBefore() {
        a = new Colour(0, 0, 0);
        b = new Colour (255, 1, 0);
        c = new Colour (0, 255, 255);
    }

    @Test
    void testIsGreyScale() {
        assertTrue(a.isGreyScale());
        assertFalse(b.isGreyScale());
        assertFalse(c.isGreyScale());
    }

    @Test
    void testToHex() {
        assertEquals("0", a.toHex());
        assertEquals("ff0100", b.toHex());
        assertEquals("00FFFF", c.toHex());
    }
}