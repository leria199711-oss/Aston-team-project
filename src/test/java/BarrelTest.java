import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BarrelTest {
    @Test
    void testBuilderCreation() {
        Barrel barrel = new Barrel.Builder()
                .setVolume(10.5)
                .setMaterial("дерево")
                .setStoredMaterial("килька")
                .build();
        assertEquals(10.5, barrel.getVolume());
        assertEquals("дерево", barrel.getMaterial());
        assertEquals("килька", barrel.getStoredMaterial());
    }
    @Test
    void testNegativeVolumeThrowsException() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Barrel.Builder()
                        .setVolume(-5.0)
                        .setMaterial("дерево")
                        .setStoredMaterial("вино")
                        .build()
        );

        assertEquals("Volume must be greater than 0", exception.getMessage());
    }

    @Test
    void testIncorrectMaterialThrowsException() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Barrel.Builder()
                        .setVolume(10.0)
                        .setMaterial(null)
                        .setStoredMaterial("вино")
                        .build()
        );
        assertEquals("Material cannot be null or empty", exception.getMessage());
    }

    @Test
    void testIncorrectStoredMaterialThrowsException() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Barrel.Builder()
                        .setVolume(10.0)
                        .setMaterial("дерево")
                        .setStoredMaterial(null)
                        .build()
        );
        assertEquals("Stored material cannot be null or empty", exception.getMessage());
    }

}

