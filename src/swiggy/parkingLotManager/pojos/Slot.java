package swiggy.parkingLotManager.pojos;

public class Slot implements Comparable<Slot> {
    
    private int number;
    private Car car;
    private volatile boolean isVacant;

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    synchronized public boolean isVacant() {
        return isVacant;
    }

    synchronized public void setVacant(boolean isVacant) {
        this.isVacant = isVacant;
    }

    public Slot(int number) {
        this.number = number;
        this.isVacant = true;
    }

    @Override
    public int compareTo(Slot o) {
        if (this.getNumber() > o.getNumber()) {
            return 1;
        } else if (this.getNumber() == o.getNumber())
            return 0;
        else
            return -1;
    }

}
