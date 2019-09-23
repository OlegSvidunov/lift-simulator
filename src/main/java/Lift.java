import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.ListIterator;

public class Lift {

    private static final int MILLIS_IN_A_SECOND = 1000;

    //TODO implement 'stop' button mechanism
    private boolean isStopped;

    private Building building;
    private int currentStage;
    private Direction currentDirection;
    private List<Passenger> passengersInLift;

    public Lift(Building building) {
        isStopped = false;
        this.building = building;
        this.currentStage = Building.FIRST_STAGE;
        this.currentDirection = Direction.UP;
        this.isStopped = true;
        passengersInLift = new ArrayList<>();
    }

    /**
     * Lift's main cyckle.
     */
    public void doJob() {
        while (areThereAnyPassengersToTransport()) {
            redefineDirection();
            if (isTimeToStop()) {
                printArriveMessage();
                openDoors();
                letOutPassengers();
                letInPassengers();
                closeDoors();
            }
            setDirectionForNextStep();
            moveForOneStage();
        }
        printSuccessMessage();
    }

    private void setDirectionForNextStep() {
        if (areAnyPeopleInsideLift()) {
            int maxStage = getMaxTargetStageForPassengerInsideLift();
            int minStage = getMinTargetStageForPassengersInsideLift();
            if (currentDirection == Direction.UP) {
                if (maxStage < currentStage) {
                    currentDirection = Direction.DOWN;
                }
            } else {
                if (minStage > currentStage) {
                    currentDirection = Direction.UP;
                }
            }
            redefineDirection();
        } else if (building.areAnyPassengersWaitingForLift()) {
            getDirectionToClosestPassengerOutsideLift();
        }
    }

    private int getMaxTargetStageForPassengerInsideLift() {
        return passengersInLift
                .stream()
                .mapToInt(Passenger::getTargetStage)
                .max().orElseThrow(IllegalArgumentException::new);
    }

    private int getMinTargetStageForPassengersInsideLift() {
        return passengersInLift
                .stream()
                .mapToInt(Passenger::getTargetStage)
                .min().orElseThrow(IllegalArgumentException::new);
    }


    private void getDirectionToClosestPassengerOutsideLift() {
        Passenger closestPassenger = building.getPassengersWaitingForLift()
                .stream()
                .min(Comparator.comparingInt(p -> Math.abs(currentStage - p.getInitialStage())))
                .orElseThrow(IllegalArgumentException::new);

        currentDirection = closestPassenger.getInitialStage() > currentStage
                ? Direction.UP
                : Direction.DOWN;
    }

    private void redefineDirection() {
        if (currentStage == Building.FIRST_STAGE) {
            currentDirection = Direction.UP;
        } else if (currentStage == Building.LAST_STAGE) {
            currentDirection = Direction.DOWN;
        }
    }

    private void letInPassengers() {
        ListIterator<Passenger> iter = building.getPassengersWaitingForLift().listIterator();
        while (iter.hasNext()) {
            Passenger passenger = iter.next();
            if (passenger.getInitialStage() == currentStage && passenger.getDesireDirection() == currentDirection) {
                iter.remove();
                passengersInLift.add(passenger);
                System.out.println(String.format("Пассажир c параметрами: %s вошел в лифт", passenger));
            }
        }
    }

    private void letOutPassengers() {
        ListIterator<Passenger> iter = passengersInLift.listIterator();
        while (iter.hasNext()) {
            Passenger passenger = iter.next();
            if (passenger.getTargetStage() == currentStage) {
                iter.remove();
                System.out.println(String.format("Пассажир c параметрами: %s вышел из лифта", passenger));
            }
        }
    }

    private void moveForOneStage() {
        System.out.println(String.format("Лифт проезжает %d этаж, направление: %s", currentStage, currentDirection));
        currentStage = currentDirection == Direction.UP
                ? ++currentStage
                : --currentStage;
    }

    private boolean isTimeToStop() {
        for (Passenger passenger : passengersInLift) {
            if (passenger.getTargetStage() == currentStage) {
                return true;
            }
        }
        for (Passenger passenger : building.getPassengersWaitingForLift()) {
            if (passenger.getInitialStage() == currentStage
                    && passenger.getDesireDirection() == currentDirection) {
                return true;
            }
        }
        return false;
    }

    private boolean areThereAnyPassengersToTransport() {
        return areAnyPeopleInsideLift() || building.areAnyPassengersWaitingForLift();
    }

    private boolean areAnyPeopleInsideLift() {
        return !passengersInLift.isEmpty();
    }

    private void closeDoors() {
        System.out.println("Двери закрылись");
    }

    private void openDoors() {
        System.out.println("Двери открылись");
    }

    private void printSuccessMessage() {
        System.out.println("Все пасажиры доставлены!");
    }

    private void printArriveMessage() {
        System.out.println(String.format("Лифт остановился на %d этаже", currentStage));
    }

    public void addDelay(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

