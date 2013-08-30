package portchecker.process;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.Hashtable;
import java.util.concurrent.TimeUnit;
import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.lang.System;

import portchecker.util.*;

/**
 * PortChecker contains all of the vital process methods of the application.
 * Additional utility methods and objects are contained in the portchecker.util
 * package.
 * 
 * @author Nicholas Woll
 */
public class PortChecker {

	private Hashtable<String, List<Port>> portTable;
	private File inputFile;
	private File outputFile;
	private Logger logger;
	private int socketTimeout = 200;

	public void checkPorts(String[] args) {
		// Local Variables
		portTable = new Hashtable<String, List<Port>>();
		inputFile = new File("ports.csv");
		outputFile = new File("port_log.txt");
		logger = new Logger(outputFile);
		ThreadController threadController = new ThreadController();
		long startTime;
		long elapsedTime;

		// Statements
		startTime = System.nanoTime();
		if (args.length >= 2) {
			portTable = cacheFromArgs(args);
		} else {
			cacheCSV();
		}
		elapsedTime = System.nanoTime();

		logger.println("\nCaching completed in "
				+ TimeUnit.NANOSECONDS.toMillis(elapsedTime - startTime)
				+ " milliseconds");

		startTime = System.nanoTime();
		try {
			threadController.processPortTable(portTable);
		} catch (Exception e) {
			e.printStackTrace();
		}
		elapsedTime = System.nanoTime();
		logger.println("Process completed in "
				+ TimeUnit.NANOSECONDS.toSeconds(elapsedTime - startTime)
				+ " seconds");
		buildReport();

		logger.closeWriter();
	}

	/**
	 * Reads the contents of ports.csv into a Hashtable<String, Port> containing
	 * the ports to be checked. Each IP address in the file serves as a key.
	 * 
	 * @param portTable
	 */
	public void cacheCSV() {
		// Local Variables
		String currentLine = null;
		String currentIP = null;
		List<Port> currentPorts;

		// Statements
		try {
			logger.println("Caching ports from file...\n");

			BufferedReader br = new BufferedReader(new FileReader(inputFile));

			while ((currentLine = br.readLine()) != null) {
				if (currentLine.contains(",")) {
					currentPorts = new ArrayList<Port>();
					currentLine = currentLine.replaceAll("\"", "");
					StringTokenizer st = new StringTokenizer(currentLine, ",");
					if (st.hasMoreTokens())
						currentIP = st.nextToken();
					while (st.hasMoreTokens()) {
						addPortsFromToken(currentPorts, st.nextToken());
					}
					portTable.put(currentIP, currentPorts);
					logger.println(currentIP + " - " + currentPorts.size()
							+ " ports");
				}
			}
			br.close();
			System.out.println(portTable.toString());
		} catch (FileNotFoundException fnf) {
			System.out.println("Error! File " + inputFile.getName()
					+ " not found!");
			fnf.printStackTrace();
		} catch (Exception e) {
			System.out.println("Error!");
			e.printStackTrace();
		}
	}

	/**
	 * Uses String[] args to directly build a Hashtable<String, Port> containing
	 * the ports to be checked.
	 * 
	 * If arguments are passed into the program on execution, this method will
	 * run in place of the CSV processor. args[0] and args[1] are startPortRange
	 * and stopPortRange args[2] specifies an IP to connect to. If none is
	 * supplied, the program executes with the defaultIP defined in main.
	 * 
	 * @param args
	 */
	// TODO Add support for command line arguments
	public Hashtable<String, List<Port>> cacheFromArgs(String[] args) {
		Hashtable<String, List<Port>> portTable = new Hashtable<String, List<Port>>();

		logger.println("WARNING: Command line args are not currently supported.");
		logger.println("Please use the ports.csv file in the application directory.");
		/*
		 * if (args.length >= 2) { hasArgs = true; startPortRange =
		 * Integer.parseInt(args[0]); stopPortRange = Integer.parseInt(args[1]);
		 * if (args.length > 2) { defaultIP = args[2]; } }
		 */
		return portTable;
	}

	/**
	 * This method parses a given token to determine if it represents a single
	 * port or a range of ports. A range of ports is indicated by a hyphen (-)
	 * between two port numbers. The method then adds the port or range of ports
	 * to the portList collection.
	 * 
	 * @param portList
	 * @param token
	 */
	public void addPortsFromToken(List<Port> portList, String token) {
		// Local Variables
		String[] rangeValues;
		int rangeStart;
		int rangeEnd;

		// Statements
		if (token.contains("-")) {
			rangeValues = token.split("-");
			rangeStart = Integer.parseInt(rangeValues[0]);
			rangeEnd = Integer.parseInt(rangeValues[1]);
		} else {
			rangeStart = Integer.parseInt(token);
			rangeEnd = rangeStart;
		}

		for (int i = rangeStart; i <= rangeEnd; i++) {
			portList.add(new Port(i));
		}
	}
	
	/**
	 * Builds a report into an output file from the results of the PortChecker. The
	 * contents of portTable are iterated through and ports are used to build the
	 * StringBuffers which are then fed to the logger. Results go to port_log.txt.
	 */
	// TODO: Condense results to ranges to improve readability.
	// (Requires preprocessing of list to split by status and sort numerically)
	public void buildReport() {
		//Local Variables
		StringBuffer openPortBuffer = new StringBuffer();
		StringBuffer closedPortBuffer = new StringBuffer();		
				
		//Statements
		openPortBuffer.append("\nThe following ports are OPEN:\n");
		closedPortBuffer.append("\nThe following ports are CLOSED:\n");		
		
		for(String ipAddress : portTable.keySet()){
			openPortBuffer.append(ipAddress + " - ");
			closedPortBuffer.append(ipAddress + " - ");			
			for(Port currentPort : portTable.get(ipAddress)){
				if(currentPort.getStatus().equals("OPEN")){
					openPortBuffer.append(currentPort.getPort() + ", ");
				} else {
					closedPortBuffer.append(currentPort.getPort() + ", ");
				}
			}
			openPortBuffer.deleteCharAt(openPortBuffer.length()-2);			
			openPortBuffer.append("\n");
			closedPortBuffer.deleteCharAt(closedPortBuffer.length()-2);
			closedPortBuffer.append("\n");	
		}
		logger.println(openPortBuffer.toString());
		logger.println(closedPortBuffer.toString());
	}

	/**
	 * (DEPRECATED) Processes the contents of the cached portTable. Each entry
	 * in the table contains an IP address and the corresponding ports to be
	 * connected to. A connection is established to each port in turn, and
	 * failed connections are logged to port_log.txt via logger.
	 * 
	 * @param portTable
	 */
	@Deprecated
	public void processPortTable(Map<String, List<Port>> portTable) {
		// Local Variables
		List<Port> currentPorts;

		// Statements
		for (String currentIP : portTable.keySet()) {
			currentPorts = portTable.get(currentIP);
			logger.println("\n" + currentIP + " - " + currentPorts.size()
					+ " ports");
			for (Port currentPort : currentPorts) {
				System.out.println("\nAttempting connection to Port "
						+ currentIP + ":" + currentPort.getPort());
				try {
					Socket serverSock = new Socket();
					serverSock.connect(new InetSocketAddress(currentIP,
							currentPort.getPort()), socketTimeout);

					System.out.println("Connected to Port: "
							+ currentPort.getPort());

					serverSock.close();
				} catch (Exception e) {
					logger.println("Error connecting to Port "
							+ currentPort.getPort());
					currentPort.closePort();
				}

			}
		}
	}
}
