package nasa.rover.exception;

public class InputFormatException extends Throwable {
    public static final String EMPTY_INPUT = "FICHIER NE CONTENANT AUCUNE DONNES, VEUILLEZ FOURNIR: \n- LA TAILLE DE LA GRILLE \n- LES DONNEES D'AU MOINS UN ROVER ";
    public static final String FILE_NOT_FOUND = "FICHIER INTROUVABLE";
    public static final String NOT_ALL_DIMENSIONS = "DIMENSION(S) MANQUANTE(S)";
    public static final String NEGATIVE_PLATEAU_DIMENSIONS = "DIMENSIONS DU PLATEAU NEGATIVES";
    public static final String PLATEAU_DIMENSIONS_NOT_NUMBER = "DIMENSIONS DU PLATEAU DOIVENT ETRE UN NOMBRE";
    public static final String ROVER_POSITIONS_NOT_NUMBER = "DIMENSIONS DU ROVER N°%o DOIVENT ETRE UN NOMBRE";
    public static final String ROVER_NOT_DIRECTION = "LA LETTRE INDIQUEE APRES LES DIMENSIONS N'EST PAS UNE DIRECTION POUR LE ROVER N°%o";
    public static final String ROVER_NOT_INSTRUCTION = "LA LETTRE INDIQUEE N'EST PAS UNE INSTRUCTION POUR LE ROVER N°%o";
    public static final String NEGATIVE_POS = "POSITION DU ROVER N°%o AVEC COORDONNEES NEGATIVES";
    public static final String ROVER_POS_OUT = "LES POSITIONS DU ROVER N°%o EN DEHORS DU PLATEAU";
    public static final String ROVER_POS_ORIENT_ABS = "UNE POSITION OU UNE DIRECTION EST MANQUANTE POUR LE ROVER N°%o";

    public InputFormatException() {
        super();
    }

    public InputFormatException(String message) {
        super(message);
    }
}
