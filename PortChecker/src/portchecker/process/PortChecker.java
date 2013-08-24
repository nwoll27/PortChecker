package portchecker.process;

import java.util.Hashtable;
import portchecker.util.*;

/**
 * PortChecker contains all of the vital process methods of the application. 
 * Additional utility methods and objects are contained in the portchecker.util package. 
 * 
 * @author Nicholas Woll
 */
public class PortChecker {
	
	private Hashtable<String, Port> portList;
	
	public void checkPorts(String[] args){
		portList = new Hashtable<String, Port>();
		if(args.length >= 2){
			portList = cacheFromArgs(args);
		} else {
			cacheCSV(portList);
		}			
		
		
		buildReport();
	}
	
	/**
	 * Reads the contents of ports.csv into a Hashtable<String, Port>
	 * containing the ports to be checked. Each IP address in the file 
	 * serves as a key.
	 * 
	 * @param portList
	 */
	public void cacheCSV(Hashtable<String, Port> portList){
		
	}
	
	/**
	 * Uses String[] args to directly build a Hashtable<String, Port> containing 
	 * the ports to be checked.
	 *  
	 * @param args
	 */
	public Hashtable<String, Port> cacheFromArgs(String[] args){
		Hashtable<String, Port> portList = new Hashtable<String, Port>();
		
		return portList;
	}
	
	/**
	 * Builds a report into an output file from the results of the PortChecker.
	 */
	public void buildReport(){
		
	}
	
}
