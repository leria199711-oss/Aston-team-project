import java.util.ArrayList;
import java.util.stream.Stream;

/**
 * Кастомная коллекция бочек. Заполнение осуществляется через стрим.
 */
public class BarrelList extends ArrayList<Barrel> {

    /**
     * Создаёт и заполняет список элементами из стрима.
     *
     * @param stream стрим бочек
     * @return новый BarrelList
     */
    public static BarrelList fromStream(Stream<Barrel> stream) {
        BarrelList list = new BarrelList();
        stream.forEach(list::add);
        return list;
    }
}
