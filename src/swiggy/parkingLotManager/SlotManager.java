package swiggy.parkingLotManager;

import java.util.Collection;
import java.util.HashMap;
import java.util.TreeSet;
import swiggy.parkingLotManager.pojos.Car;
import swiggy.parkingLotManager.pojos.Slot;


public class SlotManager {

    private TreeSet<Slot> vacantSlots = null;
    private HashMap<Integer, Slot> occupiedSlots = null;

    public SlotManager() {
        vacantSlots = new TreeSet<Slot>();
        occupiedSlots = new HashMap<Integer, Slot>();
    }

    public int createSlots(int number) {
        try {
            for (int i = 0; i < number; i++) {
                vacantSlots.add(new Slot(i + 1));
            }
            return 0;
        } catch (Exception e) {
            return 1;
        }
    }

    public int allocateSlot(Car car) {
        if (vacantSlots.size() > 0) {
            // Should be atomic Operation // Can be made thread safe by using synchronized on Slot
            Slot s = vacantSlots.first();
            if (s != null && s.isVacant()) {
                vacantSlots.remove(s);
                s.setCar(car);
                s.setVacant(false);
                occupiedSlots.put(s.getNumber(), s);
                return s.getNumber();
            }
        }
        return -1;
    }

    public int vacateSlot(int number) {
        if (occupiedSlots.size() > 0) {
            // Should be atomic Operation // Can be made thread safe by using synchronized on Slot
            Slot s = occupiedSlots.get(number);
            if (s != null && !s.isVacant()) {
                occupiedSlots.remove(number);
                s.setVacant(true);
                vacantSlots.add(s);
                return s.getNumber();
            }
        }
        return -1;
    }

    public Collection<Slot> getOccupiedSlots() {
        return occupiedSlots.values();
    }

    public Collection<Slot> getFreeSlots() {
        return vacantSlots;
    }

}
