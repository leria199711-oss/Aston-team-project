import java.util.HashMap;
import java.util.Map;

public class ShellSortSelector {
    private final Map<String, ShellSortInterface> strategies = new HashMap<>();

    public ShellSortSelector() {
        strategies.put("size", new ShellSortSize());
        strategies.put("material", new ShellSortMaterial());
        strategies.put("filling", new ShellSortFilling());
        strategies.put("custom", new ShellCustomSorting());
    }

    public ShellSortInterface getStrategy(String fieldName){
        ShellSortInterface strategy = strategies.get(fieldName);
        if (strategy == null) {
            throw new IllegalArgumentException("Нет алгоритма для поля: " + fieldName);
        }
        return strategy;
    }
}
