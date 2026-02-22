import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.IntStream;

/**
 * Источник данных, читающий бочки из текстового файла.
 * Формат файла: каждая строка должна содержать три поля,
 * разделённых точкой с запятой: объём;хранимый_материал;материал.
 * Пустые строки игнорируются.
 * Выполняется валидация каждой строки.
 */
public class FileInputSource implements InputSource {
    private final Path path;

    /**
     * Создаёт источник для указанного файла.
     *
     * @param path путь к файлу с данными
     */
    public FileInputSource(Path path) {
        this.path = path;
    }

    /**
     * Загружает бочки из файла.
     *
     * @param size максимальное количество строк для чтения.
     *             Если size = 0, читаются все строки.
     * @return список загруженных бочек
     * @throws IllegalArgumentException при ошибках:
     * <ul>
     *  <li>файл не найден или недоступен</li>
     *  <li>неверный формат строки</li>
     *  <li>ошибка валидации данных</li>
     *  <li>файл не содержит корректных данных</li>
     * </ul>
     */
    @Override
    public List<Barrel> load(int size) {
        try {
            List<String> lines = Files.readAllLines(path);
            BarrelList barrels = BarrelList.fromStream(
                    IntStream.range(0, lines.size())
                            .filter(i -> !lines.get(i).isBlank())
                            .limit(size > 0 ? size : Integer.MAX_VALUE)
                            .mapToObj(i -> parseLine(lines.get(i), i + 1))
            );
            if (barrels.isEmpty()) {
                throw new IllegalArgumentException("В файле нет корректных данных");
            }
            return barrels;
        } catch (IOException e) {
            throw new IllegalArgumentException("Ошибка чтения файла: " + path, e);
        }
    }

    /**
     * Разбирает строку файла и создаёт объект Barrel.
     *
     * @param line       строка из файла
     * @param lineNumber номер строки (для сообщений об ошибках)
     * @return объект Barrel
     * @throws IllegalArgumentException если строка имеет неверный формат
     *                                  или данные не проходят валидацию
     */
    private Barrel parseLine(String line, int lineNumber) {
        String[] parts = line.trim().split(";");
        if (parts.length != 3) {
            throw new IllegalArgumentException(
                    "Строка " + lineNumber + ": ожидается 3 поля, разделенных ';'");
        }
        double volume = parseVolume(parts[0].trim(), lineNumber);
        String material = parts[1].trim();
        String storedMaterial = parts[2].trim();

        if (volume <= 0) {
            throw new IllegalArgumentException("Строка " + lineNumber +
                    ": объём должен быть положительным, получено: " + volume);
        }
        if (Double.isNaN(volume) || Double.isInfinite(volume)) {
            throw new IllegalArgumentException("Строка " + lineNumber +
                    ": объём должен быть конечным числом");
        }
        if (storedMaterial == null || storedMaterial.isBlank()) {
            throw new IllegalArgumentException("Строка " + lineNumber +
                    ": хранимый материал не может быть пустым");
        }
        if (!storedMaterial.matches("[\\p{L}\\s-]+")) {
            throw new IllegalArgumentException("Строка " + lineNumber +
                    ": хранимый материал должен содержать только буквы, пробелы и дефисы");
        }
        if (material == null || material.isBlank()) {
            throw new IllegalArgumentException("Строка " + lineNumber +
                    ": материал изготовления не может быть пустым");
        }
        if (!material.matches("[\\p{L}\\s-]+")) {
            throw new IllegalArgumentException("Строка " + lineNumber +
                    ": материал изготовления должен содержать только буквы, пробелы и дефисы");
        }

        return new Barrel.Builder()
                .setVolume(volume)
                .setMaterial(material)
                .setStoredMaterial(storedMaterial)
                .build();
    }

    /**
     * Преобразует строку в число с плавающей точкой.
     * Заменяет запятую на точку.
     *
     * @param s          строковое представление числа
     * @param lineNumber номер строки (для сообщений об ошибках)
     * @return значение объёма
     * @throws IllegalArgumentException если строка пуста или не является числом
     */
    private double parseVolume(String s, int lineNumber) {
        if (s.isEmpty()) {
            throw new IllegalArgumentException("Строка " + lineNumber + ": объём не задан");
        }
        try {
            return Double.parseDouble(s.replace(',', '.'));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Строка " + lineNumber + ": неверный формат объёма: " + s, e);
        }
    }
}