package battleship;
public enum Ship {
    AIRCRAFT_CARRIER(5, "Aircraft Carrier"),
    BATTLESHIP(4, "Battleship"),
    SUBMARINE(3, "Submarine"),
    CRUISER(3, "Cruiser"),
    DESTROYER(2, "Destroyer");
    final int numberOfCells;
    final String name;
    Ship(int numberOfCells, String name) {
        this.numberOfCells = numberOfCells;
        this.name = name;
    }
}
