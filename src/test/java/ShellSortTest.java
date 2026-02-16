import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ShellSortTest {
    @Test
    public void testShellSortBySize() {
        Barrel[] barrels = {
                new Barrel(9.0, "A", "DA"),
                new Barrel(12.0, "B", "AA"),
                new Barrel(11.0, "A", "BA"),
                new Barrel(14.0, "C", "AS"),
                new Barrel(8.0, "C", "BB"),
                new Barrel(14.0, "B", "AS"),
                new Barrel(10.0, "D", "AR"),
                new Barrel(16.0, "A", "RA")
        };

        Barrel[] expected = {
                new Barrel(8.0, "C", "BB"),
                new Barrel(9.0, "A", "DA"),
                new Barrel(10.0, "D", "AR"),
                new Barrel(11.0, "A", "BA"),
                new Barrel(12.0, "B", "AA"),
                new Barrel(14.0, "B", "AS"),
                new Barrel(14.0, "C", "AS"),
                new Barrel(16.0, "A", "RA")
        };

        ShellSortInterface sorter = new ShellSortSize();
        sorter.shellSort(barrels);
        for (int i = 0; i < barrels.length; i++) {
            assertEquals(expected[i].size, barrels[i].size, "Неправильная сортировка по размеру");
        }
    }

    @Test
    public void testShellSortByFilling() {
        Barrel[] barrels = {
                new Barrel(9.0, "A", "DA"),
                new Barrel(12.0, "B", "AA"),
                new Barrel(11.0, "A", "BA"),
                new Barrel(14.0, "C", "AS"),
                new Barrel(8.0, "C", "BB"),
                new Barrel(14.0, "B", "AS"),
                new Barrel(10.0, "D", "AR"),
                new Barrel(16.0, "A", "RA")
        };

        Barrel[] expected = {
                new Barrel(12.0, "B", "AA"),
                new Barrel(10.0, "D", "AR"),
                new Barrel(14.0, "C", "AS"),
                new Barrel(14.0, "B", "AS"),
                new Barrel(11.0, "A", "BA"),
                new Barrel(8.0, "C", "BB"),
                new Barrel(9.0, "A", "DA"),
                new Barrel(16.0, "A", "RA")
        };

        ShellSortInterface sorter = new ShellSortFilling();
        sorter.shellSort(barrels);
        for (int i = 0; i < barrels.length; i++) {
            assertEquals(expected[i].filling, barrels[i].filling, "Неправильная сортировка по наполнению");
        }
    }
    @Test
    public void testShellSortByMaterial() {
        Barrel[] barrels = {
                new Barrel(9.0, "A", "DA"),
                new Barrel(12.0, "B", "AA"),
                new Barrel(11.0, "A", "BA"),
                new Barrel(14.0, "C", "AS"),
                new Barrel(8.0, "C", "BB"),
                new Barrel(14.0, "B", "AS"),
                new Barrel(10.0, "D", "AR"),
                new Barrel(16.0, "A", "RA")
        };

        Barrel[] expected = {
                new Barrel(9.0, "A", "DA"),
                new Barrel(11.0, "A", "BA"),
                new Barrel(16.0, "A", "RA"),
                new Barrel(12.0, "B", "AA"),
                new Barrel(14.0, "B", "AS"),
                new Barrel(14.0, "C", "AS"),
                new Barrel(8.0, "C", "BB"),
                new Barrel(10.0, "D", "AR")
        };

        ShellSortInterface sorter = new ShellSortMaterial();
        sorter.shellSort(barrels);
        for (int i = 0; i < barrels.length; i++) {
            assertEquals(expected[i].material, barrels[i].material, "Неправильная сортировка по материалу");
        }
    }
}

