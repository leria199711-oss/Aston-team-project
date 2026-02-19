import java.util.Scanner;
import java.nio.file.Path;

public class ConsoleUI {
    private final Scanner scanner = new Scanner(System.in);
    private boolean isRunning = true;
    private boolean hasData = false;
    private Barrel[] barrels;
    private final ShellSortSelector selector = new ShellSortSelector();

    public void start() {
        while (isRunning) {
            showMainMenu();
        }
    }

    private int readInt() {
        while (true) {
            try {
                String input = scanner.nextLine();
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Ошибка! Введите целое число: ");
            }
        }
    }

    private void showMainMenu() {
        System.out.println("\n=== Главное меню ===");
        System.out.println("1. Заполнить массив бочек");
        System.out.println("2. Сортировать массив (Shell Sort + Strategy)");
        System.out.println("3. Выход");
        System.out.println("Выберите действие: ");
        int choice = readInt();
        switch (choice) {
            case 1 -> createBarrels();
            case 2 -> sortBarrels();
            case 3 -> exit();
            default -> System.out.println("Неверный выбор, попробуйте снова");
        }
    }

    private void createBarrels() {
        System.out.println("Введите желаемую длину массива: ");
        int length = readInt();
        if (length <= 0) {
            System.out.println("Длина должна быть больше 0!");
            return;
        }

        System.out.println("\n--- Способ заполнения ---");
        System.out.println("1. Из файла (с валидацией)");
        System.out.println("2. Случайная генерация");
        System.out.println("3. Ручной ввод (используя Builder)");
        int choice = readInt();

        InputSource source;
        switch (choice) {
            case 1 -> source = new FileInputSource(Path.of("src/main/resources/barrels.txt"));
            case 2 -> source = new RandomInputSource();
            case 3 -> source = new ManualInputSource(scanner);
            default -> {
                System.out.println("Отмена: неверный выбор способа.");
                return;
            }
        }

        try {
            java.util.List<Barrel> list = source.load(length);
            barrels = list.toArray(new Barrel[0]);
            hasData = true;
            System.out.println("Массив заполнен: " + barrels.length);
        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка при загрузке: " + e.getMessage());
        }
    }

    private void sortBarrels() {
        if (!hasData) {
            System.out.println("Ошибка: Сначала создайте массив данных!");
            return;
        }

        System.out.println("\n--- Параметры сортировки ---");
        System.out.println("1. Объем");
        System.out.println("2. Хранимый материал");
        System.out.println("3. Материал изготовления");

        int fieldChoice = readInt();

        String key;
        switch (fieldChoice) {
            case 1 -> key = "size";
            case 2 -> key = "filling";
            case 3 -> key = "material";
            default -> {
                System.out.println("Неверное поле.");
                return;
            }
        }

        ShellSortInterface strategy = selector.getStrategy(key);
        strategy.shellSort(barrels);

        System.out.println("Сортировка завершена!");
        printBarrels();
    }

    private void exit() {
        isRunning = false;
        System.out.println("Программа завершена. До свидания!");
    }

    private void printBarrels() {
        if (barrels == null || barrels.length == 0) {
            System.out.println("(массив пуст)");
            return;
        }
        for (Barrel b : barrels) {
            System.out.println(b);
        }
    }
}