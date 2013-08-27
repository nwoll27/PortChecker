package portchecker.process;

import java.util.Hashtable;
import java.util.List;

import portchecker.util.*;

/**
 * The thread controller manages the portTable object and the assignment of work to
 * threads. Results are then collated back into the order in which they were assigned.
 * 
 * @author Nicholas Woll
 */
public class ThreadController {
	private int minThreads;
	private int maxThreads;
	private int minPorts;
	private int maxPorts;
	private List<List<Port>> workList;
	
	public ThreadController(){
		
	}
	
	public void processPortTable(Hashtable<String, List<Port>> portTable){
		
	}
	
	private static class workThread implements Runnable {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			
		}
		
	}

}
