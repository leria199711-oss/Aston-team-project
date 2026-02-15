import java.util.Scanner;

public class ConsoleUI {
    private int readInt(){
        if (scanner.hasNextInt()){
            int value = scanner.nextInt();
            scanner.nextLine();
            return value;
        } else {
            System.out.println("Введите число!");
            scanner.nextLine();
            return -1;
        }
    }
    private final Scanner scanner = new Scanner(System.in);
    private boolean isRunning = true;
    public void start(){
        while (isRunning){
            showMainMenu();
        }
    }
    private void showMainMenu(){
        System.out.println("\n=== Меню ===");
        System.out.println("1. Создать массив бочек");
        System.out.println("2. Отсортировать");
        System.out.println("3. Выход");
        int choice  = readInt();
        switch (choice){
            case 1 -> createBarrels();
            case 2 -> sortBarrels();
            case 3 -> exit();
            default -> System.out.println("Неверный выбор");
        }
    }
    private void createBarrels(){
        System.out.println("Создание массива...");
        System.out.println("\nВыберите способ заполнения:");
        System.out.println("1. Из файла");
        System.out.println("2. Случайная генерация");
        System.out.println("3. Вручную");
        int choice = readInt();
        switch (choice){
            case 1 -> System.out.println("Выбарана загрузка из файла");
            case 2 -> System.out.println("Выбрана случайная генерация");
            case 3 -> System.out.println("Выбран ручной ввод");
            default -> System.out.println("Неверный выбор");
        }
    }
    private void sortBarrels(){
        System.out.println("Сортировка...");
        System.out.println("\nВыберите поле для сортировки:");
        System.out.println("1. Объем");
        System.out.println("2. Хранимый материал");
        System.out.println("3. Материал изготовления");
        int fieldChoice = readInt();
        if (fieldChoice < 1 || fieldChoice > 3){
            System.out.println("Неверный выбор поля!");
            return;
        }
        System.out.println("\nВыберите алгоритм сортировки:");
        System.out.println("1. Shell sort");
        int algorithmChoice = readInt();
        if (algorithmChoice != 1){
            System.out.println("Неверный выбор алгоритма!");
            return;
        }
        System.out.println("Сортировка запущена...");
    }
    private void exit(){
        isRunning = false;
        System.out.println("Программа завершена.");
    }
}
