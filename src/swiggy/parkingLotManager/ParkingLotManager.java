package swiggy.parkingLotManager;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.Iterator;
import swiggy.parkingLotManager.pojos.Car;
import swiggy.parkingLotManager.pojos.Slot;
import swiggy.parkingLotManager.utils.InputParserConstants;


public class ParkingLotManager {
    SlotManager slotManager = null;

    public ParkingLotManager(int numberOfSlots) {
        slotManager = new SlotManager();
        slotManager.createSlots(numberOfSlots);
        System.out.println("Created a parking lot with " + numberOfSlots + " slots");
    }

    public void park(String registrationNumber, String color) {
        int response = slotManager.allocateSlot(new Car(registrationNumber, color));
        if (response > 0) {
            System.out.println("Allocated Slot Number " + response);
            return;
        }
        System.out.println("Sorry, Parking lot is full");
    }

    public void leave(int number) {
        int response = slotManager.vacateSlot(number);
        if (response > 0) {
            System.out.println("Slot Number " + response + " is freed");
            return;
        }
        System.out.println("Requested slot is already free");
    }

    public void printOccupiedSlots() {
        System.out.println("Slot No\t" + "Registration\t" + "Color");
        Collection<Slot> occupiedSlots = slotManager.getOccupiedSlots();
        Iterator<Slot> itr = occupiedSlots.iterator();
        while (itr.hasNext()) {
            Slot slot = itr.next();
            System.out.println(slot.getNumber() + "\t" + slot.getCar().getRegistrationNumber() + "\t"
                    + slot.getCar().getColor());
        }
    }

    public void printFreeSlots() {
        Collection<Slot> freeSlots = slotManager.getFreeSlots();
        System.out.println("Slots");
        Iterator<Slot> itr = freeSlots.iterator();
        while (itr.hasNext()) {
            Slot slot = itr.next();
            System.out.println(slot.getNumber());
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader reader;
        ParkingLotManager parkingManager = null;
        if (args.length > 0) {
            // Input from file
            reader = new BufferedReader(new FileReader(args[0]));
        } else {
            // Input from System.in
            reader = new BufferedReader(new InputStreamReader(System.in));
        }

        while (true) {
            String s = reader.readLine();
            if (s == null) {
                break;
            }
            String[] tokenize = s.split(InputParserConstants.SPLIT_TOKEN);
            if (tokenize[0].equals(InputParserConstants.CREATE_PARKING_LOT)) {
                parkingManager = new ParkingLotManager(Integer.parseInt(tokenize[1]));
            } else if (tokenize[0].equals(InputParserConstants.PARK)) {
                parkingManager.park(tokenize[1], tokenize[2]);
            } else if (tokenize[0].equals(InputParserConstants.LEAVE)) {
                parkingManager.leave(Integer.parseInt(tokenize[1]));
            } else if (tokenize[0].equals(InputParserConstants.STATUS)) {
                if (tokenize[1].equals(InputParserConstants.FREE)) {
                    parkingManager.printFreeSlots();
                } else if (tokenize[1].equals(InputParserConstants.ALLOCATED)) {
                    parkingManager.printOccupiedSlots();
                }
            } else {
                System.out.println("Invalid Input");
                break;
            }
        }

    }
}
