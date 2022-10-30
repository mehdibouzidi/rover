package nasa.rover;

import nasa.rover.exception.InputFormatException;
import nasa.rover.exception.RoverException;
import nasa.rover.service.contract.IInputReader;
import nasa.rover.service.contract.IRovereExplorer;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RoverApplication implements ApplicationRunner {
	public static final Logger LOGGER = Logger.getLogger(RoverApplication.class);

	@Autowired
	private IInputReader inputReader;
	@Autowired
	private IRovereExplorer rovereExplorer;

	public static void main(String[] args) {
		SpringApplication.run(RoverApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args)  {
		//Config Logger
		BasicConfigurator.configure();

		LOGGER.info("NASA ROVER PROGRAM IS RUNNING....");
		if(args!=null && args.getSourceArgs().length>0){
			String fileName = args.getSourceArgs()[0];
			LOGGER.info("PROCESSING FILE: "+ fileName);
			try {
				inputReader.readInputFile(fileName);

				rovereExplorer.setPlateauDimensions(inputReader.getXLimit(),inputReader.getYLimit());
				rovereExplorer.explore(inputReader.getRoverList());
			} catch (InputFormatException e){
				LOGGER.error(e.getMessage());
			} catch (RoverException e){
				LOGGER.error(e.getMessage());
			}
		}else{
			LOGGER.error("PLEASE INTRODUCE INPUT FILE");
		}
	}
}
