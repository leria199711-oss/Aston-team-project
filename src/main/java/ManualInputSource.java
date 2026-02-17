import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Источник данных для ручного ввода бочек с консоли.
 * Пользователь последовательно вводит объём, хранимый материал и материал изготовления.
 * Каждое поле проверяется сразу; при ошибке ввод повторяется для этого же поля.
 */
public class ManualInputSource implements InputSource {
    private final Scanner scanner;

    /**
     * Создаёт источник с заданным сканером для чтения ввода.
     *
     * @param scanner сканер, связанный с консолью (обычно System.in)
     */
    public ManualInputSource(Scanner scanner) {
        this.scanner = scanner;
    }

    /**
     * Запрашивает у пользователя указанное количество бочек.
     *
     * @param size количество бочек для ввода (должно быть положительным)
     * @return список введённых бочек
     * @throws IllegalArgumentException если size <= 0
     */
    @Override
    public List<Barrel> load(int size) {
        if (size <= 0) {
            throw new IllegalArgumentException("Размер должен быть положительным, получено: " + size);
        }
        List<Barrel> barrels = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            barrels.add(readOneBarrel(i + 1));
        }
        return barrels;
    }

    /**
     * Вводит одну бочку с порядковым номером.
     *
     * @param index номер бочки (для вывода пользователю)
     * @return созданный объект Barrel
     */
    private Barrel readOneBarrel(int index) {
        System.out.println("--- Бочка " + index + " ---");
        double volume = readVolume();
        String storedMaterial = readStoredMaterial();
        String material = readMaterial();
        return new Barrel.Builder()
                .setVolume(volume)
                .setMaterial(material)
                .setStoredMaterial(storedMaterial)
                .build();
    }

    /**
     * Запрашивает и валидирует объём.
     *
     * @return введённое положительное конечное число
     */
    private double readVolume() {
        while (true) {
            System.out.print("Объём (положительное число): ");
            String line = scanner.nextLine();
            if (line.isBlank()) {
                System.out.println("Введите число.");
                continue;
            }
            try {
                double value = Double.parseDouble(line.trim().replace(',', '.'));
                if (value <= 0 || Double.isNaN(value) || Double.isInfinite(value)) {
                    System.out.println("Объём должен быть положительным конечным числом.");
                    continue;
                }
                return value;
            } catch (NumberFormatException e) {
                System.out.println("Неверный формат числа. Попробуйте снова.");
            }
        }
    }

    /**
     * Запрашивает и валидирует хранимый материал.
     *
     * @return непустая строка, содержащая только буквы, пробелы и дефисы
     */
    private String readStoredMaterial() {
        while (true) {
            System.out.print("Хранимый материал (только буквы, пробелы, дефисы): ");
            String line = scanner.nextLine();
            if (line.isBlank()) {
                System.out.println("Значение не может быть пустым.");
                continue;
            }
            String value = line.trim();
            if (!value.matches("[\\p{L}\\s-]+")) {
                System.out.println("Хранимый материал должен содержать только буквы, пробелы и дефисы.");
                continue;
            }
            return value;
        }
    }

    /**
     * Запрашивает и валидирует материал изготовления.
     *
     * @return непустая строка, содержащая только буквы, пробелы и дефисы
     */
    private String readMaterial() {
        while (true) {
            System.out.print("Материал изготовления (только буквы, пробелы, дефисы): ");
            String line = scanner.nextLine();
            if (line.isBlank()) {
                System.out.println("Значение не может быть пустым.");
                continue;
            }
            String value = line.trim();
            if (!value.matches("[\\p{L}\\s-]+")) {
                System.out.println("Материал изготовления должен содержать только буквы, пробелы и дефисы.");
                continue;
            }
            return value;
        }
    }
}