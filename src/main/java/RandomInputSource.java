import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Источник данных, генерирующий случайные бочки.
 * Объём генерируется в диапазоне [MIN_VOLUME, MAX_VOLUME].
 * Материалы выбираются случайно из предопределённых списков.
 * Все сгенерированные данные проходят валидацию.
 */
public class RandomInputSource implements InputSource {
    private static final double MIN_VOLUME = 1.0;
    private static final double MAX_VOLUME = 1000.0;
    private static final String[] STORED_MATERIALS = {
            "Вода", "Масло", "Вино", "Мёд", "Зерно", "Бензин", "Молоко", "Краска",
            "Сок", "Сироп", "Пиво", "Квас", "Рассол", "Керосин", "Спирт", "Нефть",
            "Кислота", "Щёлочь", "Растворитель", "Цемент", "Песок", "Гравий", "Уголь",
            "Удобрения", "Антифриз", "Пропан", "Кислород", "Зола", "Глина", "Известь",
            "Мел", "Гипс", "Пегас"
    };
    private static final String[] MATERIALS = {
            "Дуб", "Металл", "Пластик", "Нержавейка", "Сталь", "Керамика",
            "Алюминий", "Медь", "Латунь", "Бронза", "Титан", "Чугун",
            "Стеклопластик", "Углепластик", "Резина", "Кожа", "Бамбук", "Пробка",
            "Бетон", "Фарфор", "Эмалированная сталь", "Оцинковка"
    };
    private final Random random = new Random();

    /**
     * Генерирует указанное количество случайных бочек.
     *
     * @param size количество бочек (должно быть положительным)
     * @return список сгенерированных бочек
     * @throws IllegalArgumentException если size <= 0 или сгенерированные данные не проходят валидацию
     */
    @Override
    public List<Barrel> load(int size) {
        if (size <= 0) {
            throw new IllegalArgumentException("Размер должен быть положительным, получено: " + size);
        }
        List<Barrel> barrels = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            barrels.add(createRandomBarrel());
        }
        return barrels;
    }

    /**
     * Создаёт одну случайную бочку.
     *
     * @return новый объект Barrel
     */
    private Barrel createRandomBarrel() {
        double volume = MIN_VOLUME + (MAX_VOLUME - MIN_VOLUME) * random.nextDouble();
        String storedMaterial = STORED_MATERIALS[random.nextInt(STORED_MATERIALS.length)];
        String material = MATERIALS[random.nextInt(MATERIALS.length)];

        validateVolume(volume);
        validateStoredMaterial(storedMaterial);
        validateMaterial(material);

        return new Barrel.Builder()
                .setVolume(volume)
                .setMaterial(material)
                .setStoredMaterial(storedMaterial)
                .build();
    }

    /**
     * Проверяет корректность объёма.
     *
     * @param volume проверяемое значение
     * @throws IllegalArgumentException если объём не положительный или не конечный
     */
    private void validateVolume(double volume) {
        if (volume <= 0) {
            throw new IllegalArgumentException("Объём должен быть положительным, получено: " + volume);
        }
        if (Double.isNaN(volume) || Double.isInfinite(volume)) {
            throw new IllegalArgumentException("Объём должен быть конечным числом");
        }
    }

    /**
     * Проверяет корректность хранимого материала.
     *
     * @param s строка с материалом
     * @throws IllegalArgumentException если строка пуста или содержит недопустимые символы
     */
    private void validateStoredMaterial(String s) {
        if (s == null || s.isBlank()) {
            throw new IllegalArgumentException("Хранимый материал не может быть пустым");
        }
        if (!s.matches("[\\p{L}\\s-]+")) {
            throw new IllegalArgumentException("Хранимый материал должен содержать только буквы, пробелы и дефисы");
        }
    }

    /**
     * Проверяет корректность материала изготовления.
     *
     * @param s строка с материалом
     * @throws IllegalArgumentException если строка пуста или содержит недопустимые символы
     */

    private void validateMaterial(String s) {
        if (s == null || s.isBlank()) {
            throw new IllegalArgumentException("Материал изготовления не может быть пустым");
        }
        if (!s.matches("[\\p{L}\\s-]+")) {
            throw new IllegalArgumentException("Материал изготовления должен содержать только буквы, пробелы и дефисы");
        }
    }
}