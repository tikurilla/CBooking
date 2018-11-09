import model.Auditorium;

import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class AuditoriumTest {

    Auditorium purpleHall, blackAndWhiteHall, redHall;

    @Before
    public void setUp() throws Exception {
        Set<Integer> purpleHallSeats = new HashSet<>();
        purpleHallSeats.add(1);
        purpleHallSeats.add(23);
        purpleHallSeats.add(45);
        purpleHallSeats.add(67);
        purpleHall = new Auditorium("tb82sd", "Purple Hall", 85, purpleHallSeats);

        Set<Integer> blackAndWhiteHallSeats = new HashSet<>();
        blackAndWhiteHallSeats.add(2);
        blackAndWhiteHallSeats.add(32);
        blackAndWhiteHallSeats.add(71);
        blackAndWhiteHall = new Auditorium("d8lsna", "Black and White Hall", 72, blackAndWhiteHallSeats);

        Set<Integer> redHallSeats = new HashSet<>();
        redHallSeats.add(1);
        redHallSeats.add(5);
        redHallSeats.add(6);
        redHall = new Auditorium("25hjd7", "Red Hall", 7, redHallSeats);
    }

    @Test
    public void testOccupyOneSeat() {
        purpleHall.occupyOneSeat(5);
        purpleHall.occupyOneSeat(10);
        Set<Integer> purpleHallSeats = new HashSet<>();
        purpleHallSeats.addAll(new ArrayList<>(Arrays.asList(1, 23, 45, 67 ,5, 10)));
        assertEquals(purpleHallSeats, purpleHall.getOccupiedSeats());

        blackAndWhiteHall.occupyOneSeat(3);
        blackAndWhiteHall.occupyOneSeat(69);
        blackAndWhiteHall.occupyOneSeat(43);
        Set<Integer> blackAndWhiteHallSeats = new HashSet<>();
        blackAndWhiteHallSeats.addAll(new ArrayList<>(Arrays.asList(2, 32, 71, 3, 69, 43)));
        assertEquals(blackAndWhiteHallSeats, blackAndWhiteHall.getOccupiedSeats());
    }

    @Test
    public void testMakeMapFromSet() {
        Map<Integer, String> testMap = new HashMap<>();
        testMap.put(1, "checked");
        testMap.put(2, "unchecked");
        testMap.put(3, "unchecked");
        testMap.put(4, "unchecked");
        testMap.put(5, "checked");
        testMap.put(6, "checked");
        testMap.put(7, "unchecked");
        redHall.makeMapFromSet();
        assertEquals(testMap, redHall.getOccupiedSeatsMap());
        testMap.replace(4, "checked");
        redHall.occupyOneSeat(4);
        redHall.makeMapFromSet();
        assertEquals(testMap, redHall.getOccupiedSeatsMap());
    }

    @Test
    public void testMakeSeatsSetFromQerry() {
        String body1 = "seats=10&seats=13&seats=17";
        Set<Integer> set1 = new HashSet<>();
        set1.add(10);set1.add(13);set1.add(17);
        String body2 = "seats=1&seats=9";
        Set<Integer> set2 = new HashSet<>();
        set2.add(1);set2.add(9);
        String body3 = "seats=9";
        Set<Integer> set3 = new HashSet<>();
        set3.add(9);
        String body4 = "seats=21&seats=49";
        Set<Integer> set4 = new HashSet<>();
        set4.add(21);set4.add(49);
        String body5 = "";
        Set<Integer> set5 = new HashSet<>();
        assertEquals(set1, Auditorium.makeSeatsSetFromQerry(body1));
        assertEquals(set2, Auditorium.makeSeatsSetFromQerry(body2));
        assertEquals(set3, Auditorium.makeSeatsSetFromQerry(body3));
        assertEquals(set4, Auditorium.makeSeatsSetFromQerry(body4));
        assertEquals(set5, Auditorium.makeSeatsSetFromQerry(body5));
    }

}
