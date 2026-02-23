import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.function.Predicate;


public class CountElementsInThreads {
    private final static int NUMBER_OF_THREADS = 4;
    private final static int NUMBER_IN_COLLECTION_COUNT_WITHOUT_THREADS = 100;

    public static int countSimilarBarrelsInThreads(Barrel targetBarrel, List<Barrel> barrels) {
        return countByCondition(barrel -> barrel.equals(targetBarrel), barrels);
    }

    public static int countSimilarVolumeInThreads(double volume, List<Barrel> barrels) {
        double roundedInput = Math.round(volume * 100.0) / 100.0;
        Predicate<Barrel> condition = barrel -> {
            double roundedBarrel = Math.round(barrel.getVolume() * 100.0) / 100.0;
            return roundedBarrel == roundedInput;
        };

        return countByCondition(condition, barrels);
    }

    public static int countSimilarMaterialInThreads(String material, List<Barrel> barrels) {
        Predicate<Barrel> condition = barrel -> barrel.getMaterial().equals(material);
        return countByCondition(condition, barrels);
    }

    public static int countSimilarStoredMaterialInThreads(String storedMaterial, List<Barrel> barrels) {
        Predicate<Barrel> condition = barrel -> barrel.getStoredMaterial().equals(storedMaterial);
        return countByCondition(condition, barrels);
    }

    private static List<List<Barrel>> getChunks(List<Barrel> barrels) {
        List<List<Barrel>> chunks = new ArrayList<>();
        int chunksSize = (int) Math.ceil((double) barrels.size() / NUMBER_OF_THREADS);
        for (int i = 0; i < barrels.size(); i += chunksSize) {
            int end = Math.min(i + chunksSize, barrels.size());
            chunks.add(barrels.subList(i, end));
        }
        return chunks;
    }
    public static int countByCondition(Predicate<Barrel> condition, List<Barrel> barrels) {
        int count = 0;
        if (barrels.size() <= NUMBER_IN_COLLECTION_COUNT_WITHOUT_THREADS) {
            for(Barrel barrel : barrels) {
                if (condition.test(barrel)) {
                    count++;
                }
            }
            return count;
        }

        List<List<Barrel>> chunks = getChunks(barrels);
        if (chunks.isEmpty()) {
            System.out.println("Это пустая коллекция.");
            return count;
        }

        ExecutorService executorService = Executors.newFixedThreadPool(NUMBER_OF_THREADS);
        List<Future<Integer>> futures = new ArrayList<>();

        for(List<Barrel> chunk : chunks) {
            Future<Integer> future = executorService.submit(() -> {
                int countInChunk = 0;
                for (Barrel barrel : chunk) {
                    if (condition.test(barrel)) {
                        countInChunk++;
                    }
                }
                return countInChunk;
            });
            futures.add(future);
        }
        for (Future<Integer> future : futures){
            try {
                count += future.get();
            } catch (InterruptedException| ExecutionException e) {
                throw new RuntimeException("Ошибка в потоке подсчета" + e);
            }
        }
        executorService.shutdown();
        System.out.println("Колличество совпадений: " + count);
        return count;
    }
}