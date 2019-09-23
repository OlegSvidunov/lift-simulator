public enum Direction {
    UP("вверх"),
    DOWN("вниз");

    private String alias;

    Direction(String alias) {
        this.alias = alias;
    }

    @Override
    public String toString() {
        return alias;
    }
}
