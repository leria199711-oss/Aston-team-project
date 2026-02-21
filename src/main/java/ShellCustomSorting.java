import java.util.ArrayList;
import java.util.List;

public class ShellCustomSorting implements ShellSortInterface{
    public void shellSort(Barrel[] barrels){
        List<Barrel> onlyEvenBarrels = new ArrayList<>();
        List<Integer> originalIndexes = new ArrayList<>();
        for (int i = 0; i < barrels.length; i++) {
            if ((int)barrels[i].getVolume() % 2 == 0) {
                onlyEvenBarrels.add(barrels[i]);
                originalIndexes.add(i);
            }
        }
        Barrel[] onlyEvenBarrelsArray = new Barrel[onlyEvenBarrels.size()];
        for (int i = 0; i < onlyEvenBarrelsArray.length ; i++){
            onlyEvenBarrelsArray[i] = onlyEvenBarrels.get(i);
        }
        ShellSortInterface sorter = new ShellSortSize();
        sorter.shellSort(onlyEvenBarrelsArray);
        for (int i = 0; i < onlyEvenBarrelsArray.length; i++){
            barrels[originalIndexes.get(i)] = onlyEvenBarrelsArray[i];
        }
    }
}
