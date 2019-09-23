import lombok.Getter;

import java.util.List;

@Getter
public class Building {

    public static final int FIRST_STAGE = 1;
    public static final int LAST_STAGE = 4;
    public static final int STAGE_HEIGHT = 4;

    private List<Passenger> passengersWaitingForLift;

    public Building(List<Passenger> passengers) {
        passengersWaitingForLift = passengers;
    }

    boolean areAnyPassengersWaitingForLift() {
        return !passengersWaitingForLift.isEmpty();
    }
}
