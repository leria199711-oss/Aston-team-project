public class ShellCustomSorting implements ShellSortInterface{
    public void shellSort(Barrel[] barrels){
        for (int gap = barrels.length / 2; gap > 0; gap /= 2) {
            for (int i = gap; i < barrels.length; i++){
                if ((int)barrels[i].getVolume() % 2 == 0){
                    int j = i - gap;
                    boolean sortedPartFound = false;
                    while (j >= 0 && !sortedPartFound) {
                        if ((int) barrels[j].getVolume() % 2 == 0){
                            if (barrels[j].getVolume() > barrels[j + gap].getVolume()) {
                                Barrel temp = barrels[j];
                                barrels[j] = barrels[j + gap];
                                barrels[j + gap] = temp;
                                j = j - gap;
                            } else sortedPartFound = true;
                        } else {
                            int k = j - gap;
                            boolean evenNumberFound = false;
                            while (k >= 0 && !evenNumberFound) {
                                if ((int)barrels[k].getVolume() % 2 == 0){
                                    evenNumberFound = true;
                                    if (barrels[k].getVolume() > barrels[j+gap].getVolume()){
                                        Barrel temp = barrels[k];
                                        barrels[k] = barrels[j + gap];
                                        barrels[j + gap] = temp;
                                        j = k - gap;
                                    }
                                } else {
                                    k = k - gap;
                                }
                            }
                            sortedPartFound = true;
                        }
                    }
                }
            }
        }
    }
}
