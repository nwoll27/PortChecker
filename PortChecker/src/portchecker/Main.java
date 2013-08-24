package portchecker;

import java.net.*;
import java.io.*;
import java.util.*;

import portchecker.process.*;
import portchecker.util.*;

public class Main {

	public static void main(String[] args) {
		/*int startPortRange = 0;
		int stopPortRange = 0;
		boolean hasArgs = false;
		String defaultIP = "171.74.200.217";

		if (args.length >= 2) {
			hasArgs = true;
			startPortRange = Integer.parseInt(args[0]);
			stopPortRange = Integer.parseInt(args[1]);
			if (args.length > 2) {
				defaultIP = args[2];
			}
		}

		if (hasArgs) {
			ConnectFromArgs(startPortRange, stopPortRange, defaultIP);
		} else {
			File inputFile = new File("ports.csv");
			File outputFile = new File("port_log.txt");
			Logger logger = new Logger(outputFile);
			ArrayList2D<String> portList = new ArrayList2D<String>();

			CacheCSV(portList, inputFile);
			ProcessPortList(portList, logger);
			logger.closeWriter();
		}*/
		
		PortChecker process = new PortChecker();
		
		process.checkPorts(args);
		

	}

	
	/**
	 * Reads the contents of ports.csv into a Hashtable<String, Port>
	 * portList. Each IP address in the file is a hashtable key.
	 * 
	 * @param portList
	 */
	public static void CacheCSV(ArrayList2D<String> portList, File inputFile) {
		// Local Variables
		String currentLine = null;
		int row = 0;
		int column = 0;

		// Statements
		try {
			BufferedReader br = new BufferedReader(new FileReader(inputFile));

			while ((currentLine = br.readLine()) != null) {
				currentLine = currentLine.replaceAll("\"", "");
				StringTokenizer st = new StringTokenizer(currentLine, ",");

				while (st.hasMoreTokens()) {
					portList.Add(st.nextToken(), row);
					System.out.println(portList.get(row, column));
					column++;
				}

				row++;
				column = 0;
			}
			System.out.println(portList.toString());
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
	 * Processes the contents of the cached portList ArrayList. Each row of
	 * portList contains an IP address and the corresponding ports to be
	 * connected to. A connection is established to each port in turn, and
	 * failed connections are logged to port_log.txt via logger.
	 * 
	 * @param portList
	 * @param logger
	 */
	public static void ProcessPortList(ArrayList2D<String> portList,
			Logger logger) {
		// Local Variables
		String currentIP = null;
		int row = 0;
		int column = 0;
		int rowCount = portList.getNumRows();

		// Statements
		for (row = 0; row < rowCount; row++) {

			for (column = 0; column < portList.getNumCols(row); column++) {
				if (column == 0) {
					currentIP = (String) portList.get(row, column);
					logger.println("\n" + currentIP + " - "
							+ portList.getNumCols(row) + "ports");
				} else {
					System.out.println("\nAttempting connection to Port "
							+ currentIP + ":" + portList.get(row, column));
					try {
						Socket ServerSok = new Socket(currentIP, Integer
								.parseInt((String) portList.get(row, column)));

						System.out.println("Connected to Port: " + currentIP
								+ ":" + portList.get(row, column));

						ServerSok.close();
					} catch (Exception e) {
						logger.println("Error connecting to Port "
								+ portList.get(row, column));
					}
				}
			}
		}
	}

	/**
	 * If arguments are passed into the program on execution, this method will
	 * run in place of the CSV processor. args[0] and args[1] are startPortRange
	 * and stopPortRange args[2] specifies an IP to connect to. If none is
	 * supplied, the program executes with the defaultIP defined in main.
	 * 
	 * @param startPortRange
	 * @param stopPortRange
	 * @param defaultIP
	 */
	public static void ConnectFromArgs(int startPortRange, int stopPortRange,
			String defaultIP) {
		// Statements
		for (int i = startPortRange; i <= stopPortRange; i++) {
			System.out.println("\nAttempting connection to Port " + i);
			try {
				Socket ServerSok = new Socket(defaultIP, i);
				System.out.println("Connected to Port: " + i);
				ServerSok.close();
			} catch (Exception e) {
				System.out.println("Error connecting to Port " + i);
			}
		}
	}
}