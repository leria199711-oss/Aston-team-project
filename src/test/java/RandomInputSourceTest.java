import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class RandomInputSourceTest {

    @Test
    void testLoadPositiveSize() {
        RandomInputSource source = new RandomInputSource();
        List<Barrel> barrels = source.load(5);
        assertEquals(5, barrels.size());
        for (Barrel b : barrels) {
            assertNotNull(b);
            assertTrue(b.getVolume() > 0);
            assertFalse(b.getMaterial().isBlank());
            assertFalse(b.getStoredMaterial().isBlank());
        }
    }

    @Test
    void testLoadZeroSize() {
        RandomInputSource source = new RandomInputSource();
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> source.load(0));
        assertEquals("Размер должен быть положительным, получено: 0", ex.getMessage());
    }

    @Test
    void testLoadNegativeSize() {
        RandomInputSource source = new RandomInputSource();
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> source.load(-5));
        assertTrue(ex.getMessage().contains("положительным"));
    }
}