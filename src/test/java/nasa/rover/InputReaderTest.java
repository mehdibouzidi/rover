package nasa.rover;

import nasa.rover.dto.Rover;
import nasa.rover.exception.InputFormatException;
import nasa.rover.service.contract.IInputReader;
import org.apache.log4j.BasicConfigurator;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
public class InputReaderTest {

    @Autowired
    private IInputReader inputReader;

    @Before
    public void setup(){
        //Config Logger
        BasicConfigurator.configure();
    }

    @BeforeEach
    public void setupEach(){
        System.out.println("-----------------------");
        inputReader.clearRoverList();
    }


    @Test
    public void readInputFile_empty() {
        //Given
        ClassLoader classLoader = InputReaderTest.class.getClassLoader();
        File file = new File(classLoader.getResource("input_empty.txt").getFile());

        //When
        final Throwable raisedException = Assertions.catchThrowable(() -> inputReader.readInputFile(file.getAbsoluteFile().toString()));

        //Then
        Assertions.assertThat(raisedException).isInstanceOf(InputFormatException.class)
                .hasMessageContaining(InputFormatException.EMPTY_INPUT);
    }

    @Test
    public void readInputFile_negative_limits() {
        //Given
        ClassLoader classLoader = InputReaderTest.class.getClassLoader();
        File file = new File(classLoader.getResource("input_negative_limits.txt").getFile());

        //When
        final Throwable raisedException = Assertions.catchThrowable(() -> inputReader.readInputFile(file.getAbsoluteFile().toString()));

        //Then
        Assertions.assertThat(raisedException).isInstanceOf(InputFormatException.class)
                .hasMessageContaining(InputFormatException.NEGATIVE_PLATEAU_DIMENSIONS);
    }

    @Test
    public void readInputFile_negative_positions() {
        //Given
        ClassLoader classLoader = InputReaderTest.class.getClassLoader();
        File file = new File(classLoader.getResource("input_negative_positions.txt").getFile());

        //When
        final Throwable raisedException = Assertions.catchThrowable(() -> inputReader.readInputFile(file.getAbsoluteFile().toString()));

        //Then
        Assertions.assertThat(raisedException).isInstanceOf(InputFormatException.class)
                .hasMessageContaining(String.format(InputFormatException.NEGATIVE_POS, 1));
    }

    @Test
    public void readInputFile_not_direction() {
        //Given
        ClassLoader classLoader = InputReaderTest.class.getClassLoader();
        File file = new File(classLoader.getResource("input_not_direction.txt").getFile());

        //When
        final Throwable raisedException = Assertions.catchThrowable(() -> inputReader.readInputFile(file.getAbsoluteFile().toString()));

        //Then
        Assertions.assertThat(raisedException).isInstanceOf(InputFormatException.class)
                .hasMessageContaining(String.format(InputFormatException.ROVER_NOT_DIRECTION, 1));
    }

    @Test
    public void readInputFile_not_all_dimensions() {
        //Given
        ClassLoader classLoader = InputReaderTest.class.getClassLoader();
        File file = new File(classLoader.getResource("input_not_all_dimensions.txt").getFile());

        //When
        final Throwable raisedException = Assertions.catchThrowable(() -> inputReader.readInputFile(file.getAbsoluteFile().toString()));

        //Then
        Assertions.assertThat(raisedException).isInstanceOf(InputFormatException.class)
                .hasMessageContaining(InputFormatException.NOT_ALL_DIMENSIONS);
    }

    @Test
    public void readInputFile_dimensions_not_number() {
        //Given
        ClassLoader classLoader = InputReaderTest.class.getClassLoader();
        File file = new File(classLoader.getResource("input_dim_not_number.txt").getFile());

        //When
        final Throwable raisedException = Assertions.catchThrowable(() -> inputReader.readInputFile(file.getAbsoluteFile().toString()));

        //Then
        Assertions.assertThat(raisedException).isInstanceOf(InputFormatException.class)
                .hasMessageContaining(InputFormatException.PLATEAU_DIMENSIONS_NOT_NUMBER);
    }

    @Test
    public void readInputFile_rover_position_not_number() {
        //Given
        ClassLoader classLoader = InputReaderTest.class.getClassLoader();
        File file = new File(classLoader.getResource("input_rover_pos_not_number.txt").getFile());

        //When
        final Throwable raisedException = Assertions.catchThrowable(() -> inputReader.readInputFile(file.getAbsoluteFile().toString()));

        //Then
        Assertions.assertThat(raisedException).isInstanceOf(InputFormatException.class)
                .hasMessageContaining(String.format(InputFormatException.ROVER_POSITIONS_NOT_NUMBER, 1));
    }

    @Test
    public void readInputFile_rover_out() {
        //Given
        ClassLoader classLoader = InputReaderTest.class.getClassLoader();
        File file = new File(classLoader.getResource("input_rover_out.txt").getFile());

        //When
        final Throwable raisedException = Assertions.catchThrowable(() -> inputReader.readInputFile(file.getAbsoluteFile().toString()));

        //Then
        Assertions.assertThat(raisedException).isInstanceOf(InputFormatException.class)
                .hasMessageContaining(String.format(InputFormatException.ROVER_POS_OUT, 1));
    }

    @Test
    public void readInputFile_rover_not_instruction() {
        //Given
        ClassLoader classLoader = InputReaderTest.class.getClassLoader();
        File file = new File(classLoader.getResource("input_rover_not_instruction.txt").getFile());

        //When
        final Throwable raisedException = Assertions.catchThrowable(() -> inputReader.readInputFile(file.getAbsoluteFile().toString()));

        //Then
        Assertions.assertThat(raisedException).isInstanceOf(InputFormatException.class)
                .hasMessageContaining(String.format(InputFormatException.ROVER_NOT_INSTRUCTION, 1));
    }


    @Test
    public void readInputFile_file_not_found() {
        //Given
        String fileName = "input_not_existant.txt";

        //When
        final Throwable raisedException = Assertions.catchThrowable(() -> inputReader.readInputFile(fileName));

        //Then
        Assertions.assertThat(raisedException).isInstanceOf(InputFormatException.class)
                .hasMessageContaining(InputFormatException.FILE_NOT_FOUND);
    }

    @Test
    public void readInputFile() throws InputFormatException {
        //Given
        ClassLoader classLoader = InputReaderTest.class.getClassLoader();
        File file = new File(classLoader.getResource("input.txt").getFile());

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
        List<Rover> expectedRovers = Arrays.asList(rover_1, rover_2);

        //When
        inputReader.readInputFile(file.getAbsoluteFile().toString());
        List<Rover> roverList = inputReader.getRoverList();

        //Then
        Assert.assertEquals(5, inputReader.getXLimit());
        Assert.assertEquals(5, inputReader.getYLimit());
        Assert.assertTrue(expectedRovers.containsAll(roverList));
    }

}
