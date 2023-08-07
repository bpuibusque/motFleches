package motFlechés;

public class Mot {
    private String mot;
    private Position position;
    private Direction direction;
    

    public Mot(String mot, Position position, Direction direction) {
        this.mot = mot;
        this.position = position;
        this.direction = direction;
    }

    public String getMot() {
        return mot;
    }

    public void setMot(String mot) {
        this.mot = mot;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

	public int getTaille() {
        return mot.length();
    }
}
