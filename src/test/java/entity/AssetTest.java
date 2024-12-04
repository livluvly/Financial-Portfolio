package entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AssetTest {

    @Test
    void testConstructorAndGetters() {
        // Testing the main constructor
        Asset asset = new Asset("AAPL", 10, 150.0, 1500.0, 5.0, 0.03);
        assertEquals("AAPL", asset.getSymbol());
        assertEquals(10, asset.getQuantity());
        assertEquals(150.0, asset.getValuePerUnit());
        assertEquals(1500.0, asset.getTotalValue());
        assertEquals(5.0, asset.getDailyGain());
        assertEquals(0.03, asset.getDailyGainPercentage());
    }

    @Test
    void testConstructorPolymorphism() {
        // Testing constructor polymorphism
        Asset asset = new Asset("GOOGL", 5, 2800.0, 100.0);
        assertEquals("GOOGL", asset.getSymbol());
        assertEquals(5, asset.getQuantity());
        assertEquals(2800.0, asset.getValuePerUnit());
        assertEquals(14000.0, asset.getTotalValue()); // 5 * 2800
        assertEquals(100.0, asset.getDailyGain());
        assertEquals(0.0, asset.getDailyGainPercentage()); // Default value
    }

    @Test
    void testSettersAndGetters() {
        Asset asset = new Asset("AAPL", 10, 150.0, 1500.0, 5.0, 0.03);

        // Update values using setters
        asset.setSymbol("TSLA");
        asset.setQuantity(20);
        asset.setValuePerUnit(700.0);
        asset.setTotalValue(14000.0);
        asset.setDailyGain(200.0);
        asset.setDailyGainPercentage(0.014);

        // Verifying updates through getters
        assertEquals("TSLA", asset.getSymbol());
        assertEquals(20, asset.getQuantity());
        assertEquals(700.0, asset.getValuePerUnit());
        assertEquals(14000.0, asset.getTotalValue());
        assertEquals(200.0, asset.getDailyGain());
        assertEquals(0.014, asset.getDailyGainPercentage());
    }

    @Test
    void testGetTotalValue() {
        // Validate total value calculation
        Asset asset = new Asset("AAPL", 15, 200.0, 10.0);
        assertEquals(3000.0, asset.getTotalValue()); // 15 * 200
    }

    @Test
    void testSetTotalValue() {
        Asset asset = new Asset("AAPL", 10, 150.0, 5.0);
        asset.setTotalValue(5000.0);
        assertEquals(5000.0, asset.getTotalValue());
    }

    @Test
    void testDefaultValues() {
        // Checking default values for daily gain percentage
        Asset asset = new Asset("AAPL", 0, 0.0, 0.0);
        assertEquals(0.0, asset.getQuantity());
        assertEquals(0.0, asset.getValuePerUnit());
        assertEquals(0.0, asset.getDailyGain());
        assertEquals(0.0, asset.getDailyGainPercentage());
    }

    @Test
    void testNegativeValues() {
        // Ensuring the entity handles negative values correctly
        Asset asset = new Asset("AAPL", -5, -100.0, -10.0);
        assertEquals(-5, asset.getQuantity());
        assertEquals(-100.0, asset.getValuePerUnit());
        assertEquals(500.0, asset.getTotalValue()); // -5 * -100
    }

    @Test
    void testZeroValues() {
        // Verifying behavior with zero values
        Asset asset = new Asset("AAPL", 0, 0.0, 0.0);
        assertEquals(0.0, asset.getQuantity());
        assertEquals(0.0, asset.getValuePerUnit());
        assertEquals(0.0, asset.getTotalValue());
    }
}
