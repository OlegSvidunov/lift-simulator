import lombok.Getter;

@Getter
public class Passenger {

    private int initialStage;
    private Direction desireDirection;
    private int targetStage;

    public Passenger(int initialStage, Direction desireDirection, int targetStage) {
        this.initialStage = initialStage;
        this.desireDirection = desireDirection;
        this.targetStage = targetStage;
    }

    @Override
    public String toString() {
        return String.format("[начальный этаж: %d, конечный этаж: %d]", initialStage, targetStage);
    }
}
