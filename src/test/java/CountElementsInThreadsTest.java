import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CountElementsInThreadsTest {
    private List<Barrel> barrels;
    @BeforeEach
    public void createBarrelsList() {
        barrels = new ArrayList<>();
        Barrel barrel1 = new Barrel.Builder()
                .setVolume(10.5)
                .setMaterial("дерево")
                .setStoredMaterial("вино")
                .build();
        Barrel barrel2 = new Barrel.Builder()
                .setVolume(50.5)
                .setMaterial("сталь")
                .setStoredMaterial("золото")
                .build();
        Barrel barrel3 = new Barrel.Builder()
                .setVolume(23.5)
                .setMaterial("пластик")
                .setStoredMaterial("запрещенное вещество")
                .build();
        Barrel barrel4 = new Barrel.Builder()
                .setVolume(10.5)
                .setMaterial("дерево")
                .setStoredMaterial("вино")
                .build();
        Barrel barrel5 = new Barrel.Builder()
                .setVolume(60.5)
                .setMaterial("пластик")
                .setStoredMaterial("сухари")
                .build();
        Barrel barrel6 = new Barrel.Builder()
                .setVolume(10.5)
                .setMaterial("сталь")
                .setStoredMaterial("семечки")
                .build();
        Barrel barrel7 = new Barrel.Builder()
                .setVolume(11.5)
                .setMaterial("дерево")
                .setStoredMaterial("вино")
                .build();
        Barrel barrel8 = new Barrel.Builder()
                .setVolume(50.5)
                .setMaterial("сталь")
                .setStoredMaterial("золото")
                .build();
        Barrel barrel9 = new Barrel.Builder()
                .setVolume(60.5)
                .setMaterial("дерево")
                .setStoredMaterial("золото")
                .build();
        Barrel barrel10 = new Barrel.Builder()
                .setVolume(10.5)
                .setMaterial("дерево")
                .setStoredMaterial("вино")
                .build();
        Barrel barrel11 = new Barrel.Builder()
                .setVolume(13.5)
                .setMaterial("картон")
                .setStoredMaterial("банан")
                .build();

        for (int i = 0; i < 10; i++) {
            barrels.add(barrel1);
            barrels.add(barrel2);
            barrels.add(barrel3);
            barrels.add(barrel4);
            barrels.add(barrel5);
            barrels.add(barrel6);
            barrels.add(barrel7);
            barrels.add(barrel8);
            barrels.add(barrel9);
            barrels.add(barrel10);
            barrels.add(barrel11);
        }
    }

    @Test
    public void countingBarrelsInCollection() {
        int result = CountElementsInThreads.countSimilarBarrelsInThreads(new Barrel.Builder()
                .setVolume(50.5)
                .setMaterial("сталь")
                .setStoredMaterial("золото")
                .build(), barrels);
        assertEquals(20, result);
    }
    @Test
    public void countingByVolumeInCollection(){
       int result = CountElementsInThreads.countSimilarVolumeInThreads(10.5, barrels);
       assertEquals(40, result);
    }
    @Test
    public void countingByMaterialInCollection(){
        int result = CountElementsInThreads.countSimilarMaterialInThreads("дерево", barrels);
        assertEquals(50, result);
    }
    @Test
    public void countingByStoredMaterialInCollection(){
        int result = CountElementsInThreads.countSimilarStoredMaterialInThreads("вино", barrels);
        assertEquals(40, result);
    }
    @Test
    public void countingBarrelsInEmptyCollection(){
        barrels.clear();
        int result = CountElementsInThreads.countSimilarBarrelsInThreads(new Barrel.Builder()
                .setVolume(50.5)
                .setMaterial("сталь")
                .setStoredMaterial("золото")
                .build(), barrels);
        assertEquals(0, result);
    }
    @Test
    void countingWithNoMatches() {
        int result = CountElementsInThreads.countSimilarMaterialInThreads("квас", barrels);
        assertEquals(0, result);
    }
}