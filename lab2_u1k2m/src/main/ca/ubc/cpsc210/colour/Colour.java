package ca.ubc.cpsc210.colour;

// Represents a colour having a red, green and blue component,
// each of which is in the range [0, 255]
public class Colour {

    int r, g, b;
    // EFFECTS: constructs the colour (r, g, b)
    Colour(int r, int g, int b) {
        this.r = r;
        this.g = g;
        this.b = b;
    }

    // EFFECTS: returns true if this colour is on the grey scale,
    // false otherwise
    boolean isGreyScale() {
        if (r == g && r == b)
            return true;
        return false;
    }

    // EFFECTS: returns the hexadecimal representation of this colour
    String toHex() {
        int hex_int = (r * 256 + g) * 256 + b;
        String hex = Integer.toHexString(hex_int);
        return hex;
    }
}
