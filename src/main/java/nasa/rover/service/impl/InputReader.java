package nasa.rover.service.impl;

import nasa.rover.dto.Rover;
import nasa.rover.exception.InputFormatException;
import nasa.rover.service.contract.IInputReader;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The Single Responsibility of this Class is to Handle the Input file, by:
 * - Reading and Checking it's format
 * - Initialize Rovers and Plateau
 * */
@Service
public class InputReader implements IInputReader {

    public static final Logger LOGGER = Logger.getLogger(InputReader.class);

    public int xLimit = 0;
    public int yLimit = 0;
    public List<Rover> roverList = new ArrayList<>();
    private static Rover rover=null;

    public void readInputFile(String fileName) throws InputFormatException {
        File file =  new File(fileName);

        try (InputStream inputStream = new FileInputStream(file)) {
            BufferedReader br
                    = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            int ligneNumber = 0;
            while ((line = br.readLine()) != null) {
                if(ligneNumber==0){
                    readingPlateauDimensions(line);
                }else{
                    readingRoverInfo(line, ligneNumber);
                }
                ligneNumber++;
            }
            if(ligneNumber==0){
                throw new InputFormatException(InputFormatException.EMPTY_INPUT);
            }
            LOGGER.info("FILE PROCESSED WITH SUCCESS");
        }  catch (IOException e) {
            throw new InputFormatException(InputFormatException.FILE_NOT_FOUND);
        }
    }

    private void readingRoverInfo(String line, int ligneNumber) throws InputFormatException{

        int roverId = roverList.size() + 1;
        if(ligneNumber%2>0){
            rover = new Rover();
            String[] positionOrientation = line.split("\\s+");
            if(positionOrientation.length>2){
                try{
                    int[] positions = new int[2];
                    positions[0] = Integer.valueOf(positionOrientation[0]);
                    positions[1] = Integer.valueOf(positionOrientation[1]);

                    if(positions[0]<0 || positions[1]<0){
                        throw new InputFormatException(String.format(InputFormatException.NEGATIVE_POS, roverId));
                    }else if(positions[0]>xLimit || positions[1]>yLimit){
                        throw new InputFormatException(String.format(InputFormatException.ROVER_POS_OUT, roverId));
                    }else{
                        rover.setX(positions[0]);
                        rover.setY(positions[1]);
                        if(isOrientation(positionOrientation[2])){
                            rover.setOrientation(positionOrientation[2].charAt(0));
                        }else {
                            throw new InputFormatException(String.format(InputFormatException.ROVER_NOT_DIRECTION, roverId));
                        }
                    }

                }catch (NumberFormatException e){
                    throw new InputFormatException(String.format(InputFormatException.ROVER_POSITIONS_NOT_NUMBER, roverId));
                }

            }else {
                throw new InputFormatException(String.format(InputFormatException.ROVER_POS_ORIENT_ABS, roverId));
            }
        }else{
            if(isInstruction(line) && rover!=null){
                rover.setInstructions(line);
                roverList.add(rover);
            }else{
                throw new InputFormatException(String.format(InputFormatException.ROVER_NOT_INSTRUCTION, roverId));
            }
        }
    }


    private void readingPlateauDimensions(String line) throws InputFormatException {
        String[] dimensions = line.split("\\s+");
        if(dimensions.length>1){
            try{
                xLimit = Integer.valueOf(dimensions[0]);
                yLimit = Integer.valueOf(dimensions[1]);

            }catch (NumberFormatException e){
                throw new InputFormatException(InputFormatException.PLATEAU_DIMENSIONS_NOT_NUMBER);
            }

            if(xLimit<0 || yLimit<0){
                throw new InputFormatException(InputFormatException.NEGATIVE_PLATEAU_DIMENSIONS);
            }
        }else{
            throw new InputFormatException(InputFormatException.NOT_ALL_DIMENSIONS);
        }

    }

    private boolean isInstruction(String line) {
        if(line!=null && !line.isEmpty()){
            long count = line.chars()
                    .filter(c -> c!='M' && c!='L' && c!='R')
                    .count();
            return count==0;
        }
        return true;
    }

    private boolean isOrientation(String orientation) {
        return orientation.equals("S") || orientation.equals("W") || orientation.equals("N") || orientation.equals("E");
    }

    public int getXLimit() {
        return xLimit;
    }

    public int getYLimit() {
        return yLimit;
    }

    public List<Rover> getRoverList() {
        return roverList;
    }

    @Override
    public void clearRoverList() {
        roverList = new ArrayList<>();
    }
}
