import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.Scanner;
import static org.junit.jupiter.api.Assertions.*;

public class ManualInputSourceTest {

    @Test
    void testLoadValidInput() {
        String input = "10\nСталь\nВода\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
        ManualInputSource source = new ManualInputSource(scanner);
        List<Barrel> barrels = source.load(1);

        assertEquals(1, barrels.size());
        Barrel b = barrels.get(0);
        assertEquals(10.0, b.getVolume());
        assertEquals("Сталь", b.getMaterial());
        assertEquals("Вода", b.getStoredMaterial());
    }

    @Test
    void testLoadMultipleValidInputs() {
        String input = "10\nСталь\nВода\n20\nЖелезо\nМасло\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
        ManualInputSource source = new ManualInputSource(scanner);
        List<Barrel> barrels = source.load(2);

        assertEquals(2, barrels.size());
        Barrel b1 = barrels.get(0);
        assertEquals(10.0, b1.getVolume());
        assertEquals("Сталь", b1.getMaterial());
        assertEquals("Вода", b1.getStoredMaterial());
        Barrel b2 = barrels.get(1);
        assertEquals(20.0, b2.getVolume());
        assertEquals("Железо", b2.getMaterial());
        assertEquals("Масло", b2.getStoredMaterial());
    }

    @Test
    void testLoadWithInvalidThenValidInput() {
        String input = "-5\n10\nСталь\nВода\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
        ManualInputSource source = new ManualInputSource(scanner);
        List<Barrel> barrels = source.load(1);

        assertEquals(1, barrels.size());
        Barrel b = barrels.get(0);
        assertEquals(10.0, b.getVolume());
    }

    @Test
    void testLoadZeroSize() {
        Scanner scanner = new Scanner(new ByteArrayInputStream("".getBytes()));
        ManualInputSource source = new ManualInputSource(scanner);
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> source.load(0));
        assertEquals("Размер должен быть положительным, получено: 0", ex.getMessage());
    }
}