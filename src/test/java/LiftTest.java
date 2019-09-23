import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class LiftTest {
    private Lift lift;

    @Before
    public void init() {
        List<Passenger> passengersToTransport = new ArrayList<>();
        passengersToTransport.add(new Passenger(1, Direction.UP, 4));
        passengersToTransport.add(new Passenger(3, Direction.DOWN, 2));
        passengersToTransport.add(new Passenger(4, Direction.DOWN, 1));

        Building building = new Building(passengersToTransport);
        lift = new Lift(building);
    }

    @Test
    public void testLiftJob() {
        lift.doJob();
    }
}
