# Aston-team-project — Shell Sort + Strategy + Builder

Консольное приложение на Java, которое:
- создаёт массив объектов `Barrel` (бочка)
- заполняет массив 3 способами: вручную / случайно / из файла
- сортирует массив алгоритмом Shell sort по выбранному полю (Strategy)

## Запуск
Запустить класс `Main` (точка входа) в IDE.

## Меню
1. Заполнить массив бочек
2. Сортировать массив (Shell Sort + Strategy)
3. Выход

## Формат входного файла
Файл лежит: `src/main/resources/barrels.csv`

Формат строк: 
volume;material;storedMaterial
10;Сталь;Вода
5.5;Железо;Масло
20;Дерево;Песок

- Разделитель: `;`
- Пустые/некорректные строки пропускаются
- Объем должен быть > 0
- material и storedMaterial не должны быть пустыми

## Архитектура
- `Barrel` реализует паттерн Builder
- `ShellSortInterface` + `ShellSortSize/Material/Filling` реализуют Strategy
- `ShellSortSelector` выбирает стратегию сортировки

