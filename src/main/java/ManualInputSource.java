import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.IntStream;

/**
 * Источник данных для ручного ввода бочек с консоли.
 * Пользователь последовательно вводит объём, материал изготовления и хранимый материал.
 * Каждое поле проверяется сразу; при ошибке ввод повторяется для этого же поля.
 */
public class ManualInputSource implements InputSource {
    private final Scanner scanner;
    private static final int MAX_ATTEMPTS = 100;

    public ManualInputSource(Scanner scanner) {
        this.scanner = scanner;
    }

    @Override
    public List<Barrel> load(int size) {
        if (size <= 0) {
            throw new IllegalArgumentException("Размер должен быть положительным, получено: " + size);
        }
        return BarrelList.fromStream(
                IntStream.range(0, size).mapToObj(i -> readOneBarrel(i + 1))
        );
    }

    private Barrel readOneBarrel(int index) {
        System.out.println("--- Бочка " + index + " ---");
        double volume = readVolume();
        String material = readMaterial();
        String storedMaterial = readStoredMaterial();
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
        for (int attempt = 1; attempt <= MAX_ATTEMPTS; attempt++) {
            Optional<Double> result = tryReadVolume();
            if (result.isPresent()) {
                return result.get();
            }
        }
        throw new IllegalArgumentException("Слишком много неудачных попыток ввода объёма");
    }

    private Optional<Double> tryReadVolume() {
        System.out.print("Объём (положительное число): ");
        String line = scanner.nextLine();
        if (line.isBlank()) {
            System.out.println("Введите число.");
            return Optional.empty();
        }
        try {
            double value = Double.parseDouble(line.trim().replace(',', '.'));
            if (value <= 0 || Double.isNaN(value) || Double.isInfinite(value)) {
                System.out.println("Объём должен быть положительным конечным числом.");
                return Optional.empty();
            }
            return Optional.of(value);
        } catch (NumberFormatException e) {
            System.out.println("Неверный формат числа. Попробуйте снова.");
            return Optional.empty();
        }
    }

    /**
     * Запрашивает и валидирует хранимый материал.
     *
     * @return непустая строка, содержащая только буквы, пробелы и дефисы
     */
    private String readStoredMaterial() {
        for (int attempt = 1; attempt <= MAX_ATTEMPTS; attempt++) {
            Optional<String> result = tryReadStoredMaterial();
            if (result.isPresent()) {
                return result.get();
            }
        }
        throw new IllegalArgumentException("Слишком много неудачных попыток ввода хранимого материала");
    }

    private Optional<String> tryReadStoredMaterial() {
        System.out.print("Хранимый материал (только буквы, пробелы, дефисы): ");
        String line = scanner.nextLine();
        if (line.isBlank()) {
            System.out.println("Значение не может быть пустым.");
            return Optional.empty();
        }
        String value = line.trim();
        if (!value.matches("[\\p{L}\\s-]+")) {
            System.out.println("Хранимый материал должен содержать только буквы, пробелы и дефисы.");
            return Optional.empty();
        }
        return Optional.of(value);
    }

    /**
     * Запрашивает и валидирует материал изготовления.
     *
     * @return непустая строка, содержащая только буквы, пробелы и дефисы
     */
    private String readMaterial() {
        for (int attempt = 1; attempt <= MAX_ATTEMPTS; attempt++) {
            Optional<String> result = tryReadMaterial();
            if (result.isPresent()) {
                return result.get();
            }
        }
        throw new IllegalArgumentException("Слишком много неудачных попыток ввода материала изготовления");
    }

    private Optional<String> tryReadMaterial() {
        System.out.print("Материал изготовления (только буквы, пробелы, дефисы): ");
        String line = scanner.nextLine();
        if (line.isBlank()) {
            System.out.println("Значение не может быть пустым.");
            return Optional.empty();
        }
        String value = line.trim();
        if (!value.matches("[\\p{L}\\s-]+")) {
            System.out.println("Материал изготовления должен содержать только буквы, пробелы и дефисы.");
            return Optional.empty();
        }
        return Optional.of(value);
    }
}