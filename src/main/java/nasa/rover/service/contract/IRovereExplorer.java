package nasa.rover.service.contract;

import nasa.rover.dto.Rover;
import nasa.rover.exception.RoverException;

import java.util.List;

public interface IRovereExplorer {
    List<Rover> explore(List<Rover> roverList)  throws RoverException;
    void setPlateauDimensions(int xLimitExt, int yLimitExt);

    int getXLimit();

    int getYLimit();
}
