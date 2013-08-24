package portchecker;

import portchecker.process.*;

public class Main {

	public static void main(String[] args) {
		
		PortChecker process = new PortChecker();

		process.checkPorts(args);

	}
}