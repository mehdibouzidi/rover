package nasa.rover.exception;

public class RoverException extends Throwable{
    public static final String ROVER_POS_OUT = "LES POSITIONS(%o,%o) DU ROVER N°%o EN DEHORS DU PLATEAU";
    public static final String ROVER_NO_INSTUCTION = "LE ROVER N°%o N'A PAS D'INSTRUCTIONS";

    public RoverException() {
        super();
    }

    public RoverException(String message) {
        super(message);
    }
}
