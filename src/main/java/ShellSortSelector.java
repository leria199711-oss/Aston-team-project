import java.util.HashMap;
import java.util.Map;

public class ShellSortSelector {
    private Map<String, ShellSortInterface> strategies = new HashMap<>();

    public ShellSortSelector() {
        strategies.put("size", new ShellSortSize());
        strategies.put("material", new ShellSortMaterial());
        strategies.put("filling", new ShellSortFilling());
    }

    public ShellSortInterface getStrategy(String fieldName){
        ShellSortInterface strategy = strategies.get(fieldName);
        if (strategy == null) {
            throw new IllegalArgumentException("Нет алгоритма для поля: " + fieldName);
        }
        return strategy;
    }
}
