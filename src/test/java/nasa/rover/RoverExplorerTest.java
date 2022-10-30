package nasa.rover;

import nasa.rover.dto.Rover;
import nasa.rover.exception.RoverException;
import nasa.rover.service.contract.IRovereExplorer;
import org.apache.log4j.BasicConfigurator;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
public class RoverExplorerTest {
    @Autowired
    private IRovereExplorer rovereExplorer;

    @Before
    public void setup(){
        //Config Logger
        BasicConfigurator.configure();
    }

    @Test
    public void testExplore() throws RoverException {
        //Given
        rovereExplorer.setPlateauDimensions(5,5);

        Rover rover_1 = new Rover();
        rover_1.setOrientation('N');
        rover_1.setX(1);
        rover_1.setY(2);
        rover_1.setInstructions("LMLMLMLMM");
        Rover rover_2 = new Rover();
        rover_2.setOrientation('E');
        rover_2.setX(3);
        rover_2.setY(3);
        rover_2.setInstructions("MMRMMRMRRM");

        //When
        List<Rover> result = rovereExplorer.explore(Arrays.asList(rover_1,rover_2));

        //Then
        Assert.assertEquals(rover_1.getX(), result.get(0).getX());
        Assert.assertEquals(rover_1.getY(), result.get(0).getY());
        Assert.assertEquals(rover_1.getOrientation(), result.get(0).getOrientation());
        Assert.assertEquals(rover_2.getX(), result.get(1).getX());
        Assert.assertEquals(rover_2.getY(), result.get(1).getY());
        Assert.assertEquals(rover_2.getOrientation(), result.get(1).getOrientation());
    }

    @Test
    public void testExplore_Exception() throws RoverException {
        //Given
        rovereExplorer.setPlateauDimensions(5,5);

        Rover rover = new Rover();
        rover.setOrientation('N');
        rover.setX(1);
        rover.setY(2);
        rover.setInstructions("MMMMMMMM");

        //When
        final Throwable raisedException = Assertions.catchThrowable(() -> rovereExplorer.explore(Arrays.asList(rover)));

        //Then
        Assertions.assertThat(raisedException).isInstanceOf(RoverException.class);
    }

    @Test
    public void testSetPlateauSize(){
        //Given
        int xLimit = 5;
        int yLimit = 6;

        //When
        rovereExplorer.setPlateauDimensions(xLimit,yLimit);

        //Then
        Assert.assertEquals(xLimit,rovereExplorer.getXLimit());
        Assert.assertEquals(yLimit,rovereExplorer.getYLimit());
    }
}
