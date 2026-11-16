package service;

/**
 * Calculates Delhi Metro fares based on official DMRC distance-based fare slabs.
 *
 * <h3>Fare Structure (2024)</h3>
 * <table>
 *   <tr><th>Distance</th><th>Token</th><th>Smart Card (10% off)</th><th>Sunday/Holiday</th></tr>
 *   <tr><td>0–2 km</td><td>₹11</td><td>₹9.9</td><td>₹10</td></tr>
 *   <tr><td>2–5 km</td><td>₹21</td><td>₹18.9</td><td>₹15</td></tr>
 *   <tr><td>5–12 km</td><td>₹32</td><td>₹28.8</td><td>₹25</td></tr>
 *   <tr><td>12–21 km</td><td>₹43</td><td>₹38.7</td><td>₹35</td></tr>
 *   <tr><td>21–32 km</td><td>₹54</td><td>₹48.6</td><td>₹40</td></tr>
 *   <tr><td>&gt;32 km</td><td>₹64</td><td>₹57.6</td><td>₹50</td></tr>
 * </table>
 */
public class FareCalculator {

    private FareCalculator() {} // utility class

    /**
     * Calculates token fare based on DMRC distance slabs.
     *
     * @param distanceKm total journey distance in kilometres
     * @return token fare in rupees
     */
    public static int calculateTokenFare(double distanceKm) {
        if (distanceKm <= 2)  return 11;
        if (distanceKm <= 5)  return 21;
        if (distanceKm <= 12) return 32;
        if (distanceKm <= 21) return 43;
        if (distanceKm <= 32) return 54;
        return 64;
    }

    /**
     * Calculates Smart Card fare (10% discount on token fare).
     *
     * @param distanceKm total journey distance in kilometres
     * @return smart card fare in rupees (with decimal)
     */
    public static double calculateSmartCardFare(double distanceKm) {
        return Math.round(calculateTokenFare(distanceKm) * 0.90 * 10.0) / 10.0;
    }

    /**
     * Calculates Sunday/Holiday discounted fare.
     *
     * @param distanceKm total journey distance in kilometres
     * @return Sunday/holiday fare in rupees
     */
    public static int calculateSundayFare(double distanceKm) {
        if (distanceKm <= 2)  return 10;
        if (distanceKm <= 5)  return 15;
        if (distanceKm <= 12) return 25;
        if (distanceKm <= 21) return 35;
        if (distanceKm <= 32) return 40;
        return 50;
    }
}
