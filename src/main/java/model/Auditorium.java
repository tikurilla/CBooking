package model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Auditorium {

    private final String ID;

    private final String auditoriumName;

    private final int maxCapacity;

    private int freeSeatsAmount;

    private Set<Integer> occupiedSeatsSet;

    private Map<Integer, String> occupiedSeatsMap;

    public Auditorium(String ID, String auditoriumName, int maxCapacity, Set<Integer> occupiedSeatsSet) {
        this.ID = ID;
        this.auditoriumName = auditoriumName;
        this.maxCapacity = maxCapacity;
        this.occupiedSeatsSet = occupiedSeatsSet;
    }

    public String getID() {
        return ID;
    }

    public String getAuditoriumName() {
        return auditoriumName;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public int getFreeSeatsAmount() {
        return freeSeatsAmount;
    }

    public Set<Integer> getOccupiedSeats() {
        return occupiedSeatsSet;
    }

    public void setFreeSeatsAmount(int freeSeatsAmount) {
        this.freeSeatsAmount = freeSeatsAmount;
    }

    public void occupyOneSeat(int seatNumber) {
        occupiedSeatsSet.add(seatNumber);
    }

    public boolean releaseOneSeat(int seatNumber) {
        if (occupiedSeatsSet.contains(seatNumber)) {
            return occupiedSeatsSet.remove(seatNumber);

        }
        else
            return false;
    }

    public void occupySeveralSeats(Set<Integer> seatNumbers) {
        occupiedSeatsSet.addAll(seatNumbers);
    }

    public boolean releaseSeveralSeats(Set<Integer> seatNumbers) {
        if (occupiedSeatsSet.containsAll(seatNumbers)) {
            return occupiedSeatsSet.removeAll(seatNumbers);
        }
        else
            return false;
    }

    public void makeMapFromSet() {
        Map<Integer, String> returnedMap = new HashMap<>();
        for(int i=1; i<=maxCapacity;i++) {
            if (occupiedSeatsSet.contains(i))
                returnedMap.put(i, "checked");
            else
                returnedMap.put(i, "unchecked");
        }
        occupiedSeatsMap = returnedMap;
    }

    public Map<Integer, String> getOccupiedSeatsMap() {
        return occupiedSeatsMap;
    }

    public static Set<Integer> makeSeatsSetFromQerry(String querry) {
        String[] queryArray = querry.split("seats=");
        Set<Integer> seatsSet = new HashSet<>();
        for (String item: queryArray) {
            if (item.length() > 1 & item.contains("&"))
                seatsSet.add(Integer.parseInt(item.substring(0, item.length() - 1)));
            else if (item.length() > 1)
                seatsSet.add(Integer.parseInt(item));
        }
        return seatsSet;
    }

}