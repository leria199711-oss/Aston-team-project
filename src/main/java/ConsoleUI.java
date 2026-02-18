import java.util.Scanner;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class ConsoleUI {
    private final Scanner scanner = new Scanner(System.in);
    private boolean isRunning = true;
    private boolean hasData = false;
    private Barrel[] barrels;
    private final ShellSortSelector selector = new ShellSortSelector();
    public void start(){
        while (isRunning){
            showMainMenu();
        }
    }
    private int readInt(){
        while (true){
            try {
                String input = scanner.nextLine();
                return Integer.parseInt(input);
            } catch (NumberFormatException e){
                System.out.println("Ошибка! Введите целое число: ");
            }
        }
    }
    private double readDoublePositive(){
        while (true){
            try {
                String input = scanner.nextLine().trim().replace(',', '.');
                double value = Double.parseDouble(input);
                if (value <= 0) {
                    System.out.println("Ошибка! Объем должен быть больше 0:");
                    continue;
                }
                return value;
            } catch (NumberFormatException e){
                System.out.println("Ошибка! Введите число (например 12.5):");
            }
        }
    }
    private String readNonEmptyString(String promt){
        while (true){
            System.out.println(promt);
            String s = scanner.nextLine().trim();
            if (s.isEmpty()){
                System.out.println("Ошибка! Строка не должна быть пустой.");
                continue;
            }
            return s;
        }
    }
    private void showMainMenu(){
        System.out.println("\n=== Главное меню ===");
        System.out.println("1. Заполнить массив бочек");
        System.out.println("2. Сортировать массив (Shell Sort + Strategy)");
        System.out.println("3. Выход");
        System.out.println("Выберите действие: ");
        int choice = readInt();
        switch (choice){
            case 1 -> createBarrels();
            case 2 -> sortBarrels();
            case 3 -> exit();
            default -> System.out.println("Неверный выбор, попробуйте снова");
        }
    }
    private void createBarrels(){
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
            case 1 -> source = new FileInputSource(java.nio.file.Path.of("src/main/resources/barrels.csv"));
            case 2 -> source = new RandomInputSource();
            case 3 -> source = new ManualInputSource(scanner);
            default -> {
                System.out.println("Отмена: неверный выбор способа.");
                return;
            }
        }
        java.util.List<Barrel> list = source.load(length);
        barrels = list.toArray(new Barrel[0]);
        hasData = true;
        System.out.println("Массив заполнен: " + barrels.length);
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
    private void exit(){
        isRunning = false;
        System.out.println("Программа завершена. До свидания!");
    }
    private void loadFromFile(int n) {
        String fileName = "barrels.csv";
        barrels = new Barrel[n];

        int filled = 0;

        try (InputStream is = getClass().getClassLoader().getResourceAsStream(fileName)) {
            if (is == null) {
                System.out.println("Файл не найден в resources: " + fileName);
                System.out.println("Проверь: src/main/resources/" + fileName);
                return;
            }
            try (BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
                String line;
                while ((line = br.readLine()) != null && filled < n) {
                    line = line.trim();
                    if (line.isEmpty()) continue;
                    if (line.toLowerCase().startsWith("volume")) continue;
                    Barrel barrel = parseBarrelLine(line);
                    if (barrel == null) {
                        System.out.println("Пропускаю некорректную строку: " + line);
                        continue;
                    }
                    barrels[filled++] = barrel;
                }
            }
            if (filled < n) {
                System.out.println("В файле мало корректных строк. Нужно " + n + ", получили " + filled);
                Barrel[] resized = new Barrel[filled];
                System.arraycopy(barrels, 0, resized, 0, filled);
                barrels = resized;
            }

            System.out.println("Загружено из файла: " + barrels.length);

        } catch (Exception e) {
            System.out.println("Ошибка чтения файла: " + e.getMessage());
        }
    }
    private Barrel parseBarrelLine(String line) {
        String[] parts = line.split(";");
        if (parts.length != 3) return null;
        String volStr = parts[0].trim().replace(',', '.');
        String material = parts[1].trim();
        String storedMaterial = parts[2].trim();
        if (material.isEmpty() || storedMaterial.isEmpty()) return null;
        double volume;
        try {
            volume = Double.parseDouble(volStr);
        } catch (NumberFormatException e) {
            return null;
        }
        if (volume <= 0) return null;
        return new Barrel.Builder()
                .setVolume(volume)
                .setMaterial(material)
                .setStoredMaterial(storedMaterial)
                .build();
    }
    private void generateRandom(int n) {
        barrels = new Barrel[n];
        for (int i = 0; i < n; i++){
            barrels[i] = new Barrel.Builder()
                    .setVolume(1 + Math.random() * 199)
                    .setMaterial("Сталь")
                    .setStoredMaterial("Дерево")
                    .build();
        }
        System.out.println("Генерируем " + n + " случайных бочек...");}
    private void manualInput(int n) {
        barrels= new Barrel[n];
        for (int i = 0; i < n; i++){
            System.out.println("Бочка #" + (i + 1));
            System.out.println("Введите объем: ");
            double volume = readDoublePositive();
            String material = readNonEmptyString("Введите материал: ");
            String storedMaterial = readNonEmptyString("Введите хранимый материал: ");
            barrels[i] = new Barrel.Builder()
                    .setVolume(volume)
                    .setMaterial(material)
                    .setStoredMaterial(storedMaterial)
                    .build();
        }
        System.out.println("Массив заполнен вручную.");
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
