import java.util.Scanner;
public class ConsoleUI {
    private final Scanner scanner = new Scanner(System.in);
    private boolean isRunning = true;
    private boolean hasData = false;
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
        if (length <= 0){
            System.out.println("Длина должна быть больше 0!");
            return;
        }
        System.out.println("\n--- Способо заполнения ---");
        System.out.println("1. Из файла (с валидацией)");
        System.out.println("2. Случайная генерация");
        System.out.println("3. Ручной ввод (используя Builder)");
        int choice = readInt();
        switch (choice){
            case 1 -> loadFromFile(length);
            case 2 -> generateRandom(length);
            case 3 -> manualInput(length);
            default -> {
                System.out.println("Отмена: неверный выбор способа.");
                return;
            }
        }
        hasData = true;
    }
    private void sortBarrels(){
        if (!hasData){
            System.out.println("Ошибка: Сначала создайте массив данных!");
            return;
        }
        System.out.println("\n--- Параметры сортировки ---");
        System.out.println("Выберите поле:");
        System.out.println("1. Объем\n2. Хранимый материал\n3. Материал изготовления");
        int fieldChoice = readInt();
        if (fieldChoice < 1 || fieldChoice > 3){
            System.out.println("Неверное поле.");
            return;
        }
        System.out.println("Запуск Shell Sort по выбранному полю...");
    }
    private void exit(){
        isRunning = false;
        System.out.println("Программа завершена. До свидания!");
    }
    private void loadFromFile(int n) { System.out.println("Читаем " + n + " элементов из файла...");}
    private void generateRandom(int n) { System.out.println("Генерируем " + n + " случайных бочек...");}
    private void manualInput(int n) { System.out.println("Введите данные для " + n + " бочек...");}
}
