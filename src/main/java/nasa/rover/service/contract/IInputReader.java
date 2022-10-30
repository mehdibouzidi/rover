package nasa.rover.service.contract;

import nasa.rover.dto.Rover;
import nasa.rover.exception.InputFormatException;

import java.util.List;

public interface IInputReader {
    void readInputFile(String fileName) throws InputFormatException;

    int getXLimit();
    int getYLimit();
    List<Rover> getRoverList();

    void clearRoverList();
}
