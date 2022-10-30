package nasa.rover.dto;

public class Rover {
    private Character orientation;
    private int x;
    private int y;
    private String instructions;

    public Rover() {
    }


    public Character getOrientation() {
        return orientation;
    }

    public void setOrientation(Character orientation) {
        this.orientation = orientation;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Rover rover = (Rover) o;

        if (x != rover.x) return false;
        if (y != rover.y) return false;
        if (!orientation.equals(rover.orientation)) return false;
        return instructions.equals(rover.instructions);
    }

    @Override
    public int hashCode() {
        int result = orientation.hashCode();
        result = 31 * result + x;
        result = 31 * result + y;
        result = 31 * result + instructions.hashCode();
        return result;
    }
}
