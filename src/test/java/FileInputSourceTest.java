import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import java.nio.file.Files;
import java.nio.file.Path;
import java.io.IOException;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class FileInputSourceTest {

    @TempDir
    Path tempDir;

    @Test
    void testLoadValidFile() throws IOException {
        Path file = tempDir.resolve("valid.txt");
        Files.write(file, List.of(
                "10;Сталь;Вода",
                "5;Железо;Масло",
                "20;Дерево;Песок"
        ));

        FileInputSource source = new FileInputSource(file);
        List<Barrel> barrels = source.load(0);

        assertEquals(3, barrels.size());
        Barrel b1 = barrels.get(0);
        assertEquals(10.0, b1.getVolume());
        assertEquals("Сталь", b1.getMaterial());
        assertEquals("Вода", b1.getStoredMaterial());
    }

    @Test
    void testLoadWithSizeLimit() throws IOException {
        Path file = tempDir.resolve("valid.txt");
        Files.write(file, List.of(
                "10;Сталь;Вода",
                "5;Железо;Масло",
                "20;Дерево;Песок"
        ));

        FileInputSource source = new FileInputSource(file);
        List<Barrel> barrels = source.load(2);

        assertEquals(2, barrels.size());
    }

    @Test
    void testLoadInvalidFormat() throws IOException {
        Path file = tempDir.resolve("invalid.txt");
        Files.write(file, List.of("10;Сталь"));

        FileInputSource source = new FileInputSource(file);
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> source.load(0));
        assertTrue(ex.getMessage().contains("ожидается 3 поля"));
    }

    @Test
    void testLoadNonNumericVolume() throws IOException {
        Path file = tempDir.resolve("bad.txt");
        Files.write(file, List.of("десять;Сталь;Вода"));

        FileInputSource source = new FileInputSource(file);
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> source.load(0));
        assertTrue(ex.getMessage().contains("неверный формат объёма"));
    }

    @Test
    void testLoadInvalidMaterialChars() throws IOException {
        Path file = tempDir.resolve("bad.txt");
        Files.write(file, List.of("10;Сталь123;Вода"));

        FileInputSource source = new FileInputSource(file);
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> source.load(0));
        assertTrue(ex.getMessage().contains("только буквы, пробелы и дефисы"));
    }

    @Test
    void testLoadEmptyFile() throws IOException {
        Path file = tempDir.resolve("empty.txt");
        Files.createFile(file);

        FileInputSource source = new FileInputSource(file);
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> source.load(0));
        assertEquals("В файле нет корректных данных", ex.getMessage());
    }

    @Test
    void testLoadNonexistentFile() {
        Path file = tempDir.resolve("nonexistent.txt");
        FileInputSource source = new FileInputSource(file);
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> source.load(0));
        assertTrue(ex.getMessage().contains("Ошибка чтения файла"));
    }
}