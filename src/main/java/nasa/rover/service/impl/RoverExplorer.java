package nasa.rover.service.impl;

import nasa.rover.dto.Rover;
import nasa.rover.exception.RoverException;
import nasa.rover.service.contract.IRovereExplorer;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * The Single Responsibility of this class is to manage the Rover inside the Plateau
 * */
@Service
public class RoverExplorer implements IRovereExplorer {
    public  final Logger LOGGER = Logger.getLogger(InputReader.class);

    private final List<Character> orientations = Arrays.asList('S','W','N','E');

    private int xLimit;
    private int yLimit;

    @Override
    public List<Rover> explore(List<Rover> roverList) throws RoverException{
        LOGGER.info("ROVERS EXPLORATION BEGIN ! ");
        for(int i=0;i<roverList.size();i++){
            Rover currentRover = roverList.get(i);
            if (currentRover != null) {
                Character orientation = currentRover.getOrientation();
                int orientationIndex = getOrientationIndex(orientation);

                currentRover = applyInstructions(i, currentRover, orientation, orientationIndex);
                roverList.set(i, currentRover);

                System.out.println(currentRover.getX() + " " + currentRover.getY() + " " + currentRover.getOrientation());
            }
        }
        LOGGER.info("ROVERS EXPLORATION END ! ");
        return roverList;
    }

    private  int getOrientationIndex(Character orientation) {
        switch (orientation) {
            case 'S':
                return 0;
            case 'W':
                return 1;
            case 'N':
                return 2;
            case 'E':
                return 3;
            default:
                return -1;
        }
    }

    private  Rover applyInstructions(int i, Rover currentRover, Character orientation, int orientationIndex) throws RoverException {
        int x = currentRover.getX();
        int y = currentRover.getY();
        String instructions = currentRover.getInstructions();
        if(instructions.length()==0){
            LOGGER.warn(RoverException.ROVER_NO_INSTUCTION);
        }else{
            for (int j = 0; j < instructions.length(); j++) {
                Character instruction = instructions.charAt(j);
                if (instruction == 'L') {
                    orientationIndex = moveLeft(orientationIndex);
                }else if (instruction == 'R') {
                    orientationIndex = moveRight(orientationIndex);
                } else if (instruction == 'M') {
                    switch (orientation) {
                        case 'S':
                            y--;
                            break;
                        case 'W':
                            x--;
                            break;
                        case 'N':
                            y++;
                            break;
                        case 'E':
                            x++;
                            break;
                        default:
                            break;
                    }
                }

                if (x < 0 || x > xLimit || y < 0 || y > yLimit) {
                    throw new RoverException(String.format(RoverException.ROVER_POS_OUT, x, y, i +1));
                }
                orientation = orientations.get(orientationIndex);
            }
            currentRover.setX(x);
            currentRover.setY(y);
            currentRover.setOrientation(orientations.get(orientationIndex));
        }
        
        return currentRover;
    }

    private  int moveLeft(int orientationIndex) {
        orientationIndex--;
        orientationIndex = (orientationIndex == -1) ? orientations.size() - 1 : orientationIndex;
        return orientationIndex;
    }

    private  int moveRight(int orientationIndex) {
        orientationIndex++;
        orientationIndex = (orientationIndex == orientations.size()) ? 0 : orientationIndex;
        return orientationIndex;
    }

    @Override
    public void setPlateauDimensions(int xLimitExt, int yLimitExt){
        xLimit = xLimitExt;
        yLimit = yLimitExt;
    }

    @Override
    public int getXLimit() {
        return xLimit;
    }

    @Override
    public int getYLimit() {
        return yLimit;
    }
}
