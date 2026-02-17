import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ShellSortTest {
    @Test
    public void testShellSortBySize() {
        Barrel[] barrels = {
                new Barrel.Builder().setVolume(9.0).setMaterial("A").setStoredMaterial("DA").build(),
                new Barrel.Builder().setVolume(12.0).setMaterial("B").setStoredMaterial("AA").build(),
                new Barrel.Builder().setVolume(11.0).setMaterial("A").setStoredMaterial("BA").build(),
                new Barrel.Builder().setVolume(14.0).setMaterial("C").setStoredMaterial("AS").build(),
                new Barrel.Builder().setVolume(8.0).setMaterial("C").setStoredMaterial("BB").build(),
                new Barrel.Builder().setVolume(14.0).setMaterial("B").setStoredMaterial("AS").build(),
                new Barrel.Builder().setVolume(10.0).setMaterial("D").setStoredMaterial("AR").build(),
                new Barrel.Builder().setVolume(16.0).setMaterial("A").setStoredMaterial("RA").build()
        };

        Barrel[] expected = {
                new Barrel.Builder().setVolume(8.0).setMaterial("C").setStoredMaterial("BB").build(),
                new Barrel.Builder().setVolume(9.0).setMaterial("A").setStoredMaterial("DA").build(),
                new Barrel.Builder().setVolume(10.0).setMaterial("D").setStoredMaterial("AR").build(),
                new Barrel.Builder().setVolume(11.0).setMaterial("A").setStoredMaterial("BA").build(),
                new Barrel.Builder().setVolume(12.0).setMaterial("B").setStoredMaterial("AA").build(),
                new Barrel.Builder().setVolume(14.0).setMaterial("B").setStoredMaterial("AS").build(),
                new Barrel.Builder().setVolume(14.0).setMaterial("C").setStoredMaterial("AS").build(),
                new Barrel.Builder().setVolume(16.0).setMaterial("A").setStoredMaterial("RA").build()
        };

        ShellSortInterface sorter = new ShellSortSize();
        sorter.shellSort(barrels);
        for (int i = 0; i < barrels.length; i++) {
            assertEquals(expected[i].getVolume(), barrels[i].getVolume(), "Неправильная сортировка по размеру");
        }
    }

    @Test
    public void testShellSortByFilling() {
        Barrel[] barrels = {
                new Barrel.Builder().setVolume(9.0).setMaterial("A").setStoredMaterial("DA").build(),
                new Barrel.Builder().setVolume(12.0).setMaterial("B").setStoredMaterial("AA").build(),
                new Barrel.Builder().setVolume(11.0).setMaterial("A").setStoredMaterial("BA").build(),
                new Barrel.Builder().setVolume(14.0).setMaterial("C").setStoredMaterial("AS").build(),
                new Barrel.Builder().setVolume(8.0).setMaterial("C").setStoredMaterial("BB").build(),
                new Barrel.Builder().setVolume(14.0).setMaterial("B").setStoredMaterial("AS").build(),
                new Barrel.Builder().setVolume(10.0).setMaterial("D").setStoredMaterial("AR").build(),
                new Barrel.Builder().setVolume(16.0).setMaterial("A").setStoredMaterial("RA").build()
        };

        Barrel[] expected = {
                new Barrel.Builder().setVolume(12.0).setMaterial("B").setStoredMaterial("AA").build(),
                new Barrel.Builder().setVolume(10.0).setMaterial("D").setStoredMaterial("AR").build(),
                new Barrel.Builder().setVolume(14.0).setMaterial("C").setStoredMaterial("AS").build(),
                new Barrel.Builder().setVolume(14.0).setMaterial("B").setStoredMaterial("AS").build(),
                new Barrel.Builder().setVolume(11.0).setMaterial("A").setStoredMaterial("BA").build(),
                new Barrel.Builder().setVolume(8.0).setMaterial("C").setStoredMaterial("BB").build(),
                new Barrel.Builder().setVolume(9.0).setMaterial("A").setStoredMaterial("DA").build(),
                new Barrel.Builder().setVolume(16.0).setMaterial("A").setStoredMaterial("RA").build()
        };

        ShellSortInterface sorter = new ShellSortFilling();
        sorter.shellSort(barrels);
        for (int i = 0; i < barrels.length; i++) {
            assertEquals(expected[i].getStoredMaterial(), barrels[i].getStoredMaterial(), "Неправильная сортировка по наполнению");
        }
    }
    @Test
    public void testShellSortByMaterial() {
        Barrel[] barrels = {
                new Barrel.Builder().setVolume(9.0).setMaterial("A").setStoredMaterial("DA").build(),
                new Barrel.Builder().setVolume(12.0).setMaterial("B").setStoredMaterial("AA").build(),
                new Barrel.Builder().setVolume(11.0).setMaterial("A").setStoredMaterial("BA").build(),
                new Barrel.Builder().setVolume(14.0).setMaterial("C").setStoredMaterial("AS").build(),
                new Barrel.Builder().setVolume(8.0).setMaterial("C").setStoredMaterial("BB").build(),
                new Barrel.Builder().setVolume(14.0).setMaterial("B").setStoredMaterial("AS").build(),
                new Barrel.Builder().setVolume(10.0).setMaterial("D").setStoredMaterial("AR").build(),
                new Barrel.Builder().setVolume(16.0).setMaterial("A").setStoredMaterial("RA").build()
        };

        Barrel[] expected = {
                new Barrel.Builder().setVolume(9.0).setMaterial("A").setStoredMaterial("DA").build(),
                new Barrel.Builder().setVolume(11.0).setMaterial("A").setStoredMaterial("BA").build(),
                new Barrel.Builder().setVolume(16.0).setMaterial("A").setStoredMaterial("RA").build(),
                new Barrel.Builder().setVolume(12.0).setMaterial("B").setStoredMaterial("AA").build(),
                new Barrel.Builder().setVolume(14.0).setMaterial("B").setStoredMaterial("AS").build(),
                new Barrel.Builder().setVolume(14.0).setMaterial("C").setStoredMaterial("AS").build(),
                new Barrel.Builder().setVolume(8.0).setMaterial("C").setStoredMaterial("BB").build(),
                new Barrel.Builder().setVolume(10.0).setMaterial("D").setStoredMaterial("AR").build()
        };

        ShellSortInterface sorter = new ShellSortMaterial();
        sorter.shellSort(barrels);
        for (int i = 0; i < barrels.length; i++) {
            assertEquals(expected[i].getMaterial(), barrels[i].getMaterial(), "Неправильная сортировка по материалу");
        }
    }
}

