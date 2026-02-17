public class ShellSortMaterial implements ShellSortInterface{

    public void shellSort(Barrel[] barrels) {
        int n = barrels.length;
        int j;
        for (int gap = n / 2; gap > 0; gap /= 2){
            for (int i = gap; i < n; i++) {
                Barrel temp = barrels[i];
                for (j = i; j >= gap && barrels[j-gap].getMaterial().compareTo(temp.getMaterial()) > 0; j-= gap) {
                    barrels[j] = barrels[j - gap];
                }
                barrels[j] = temp;
            }
        }
    }
}
