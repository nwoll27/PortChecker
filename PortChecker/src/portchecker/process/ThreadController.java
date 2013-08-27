package portchecker.process;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;

import portchecker.util.*;

/**
 * The thread controller manages the portTable object and the assignment of work to
 * threads. 
 * 
 * @author Nicholas Woll
 */
public class ThreadController {
	//Members
	private int maxThreads;
	private int chunkSize;
	
	//Constructors
	public ThreadController(){
		chunkSize = 15;
		maxThreads = 4;
	}
	
	public ThreadController(int chunkSize, int maxThreads){
		this.chunkSize = chunkSize;
		this.maxThreads = maxThreads;
	}
	
	//Methods
	public void processPortTable(Hashtable<String, List<Port>> portTable) throws Exception{
		//Local Variables
		Hashtable<String, List<int[]>> chunkTable;
		List<WorkThread> threadList;
		int currentChunkIndex;
		Enumeration<String> ipAddresses;
		String currentIP;
				
		//Statements
		threadList = new ArrayList<WorkThread>();
		
		chunkTable = buildChunkTable(portTable);
		
		for(int i = 0; i < maxThreads; i++){
			threadList.add(new WorkThread());
		}
		
		currentChunkIndex = 0;
		ipAddresses = chunkTable.keys();
		currentIP = ipAddresses.nextElement();
		while(!threadList.isEmpty()){
			for(WorkThread thread : threadList) {
				if(thread.isIdle){
					if(currentChunkIndex >= chunkTable.get(currentIP).size() && ipAddresses.hasMoreElements()){
						currentIP = ipAddresses.nextElement();
						currentChunkIndex = 0;
					} 
					if (currentChunkIndex < chunkTable.get(currentIP).size()){
						thread.assignTask(currentIP, chunkTable.get(currentIP).get(currentChunkIndex));
						currentChunkIndex++; 						
					} else if(currentChunkIndex >= chunkTable.get(currentIP).size() && !ipAddresses.hasMoreElements()){
						threadList.remove(thread); //Remove current thread if no work is left
					} else {
						throw new Exception("ERROR! Thread was handled improperly.");
					}
				}
			}
		}
		
		System.out.println("Test");
	}
	
	private Hashtable<String, List<int[]>> buildChunkTable(Hashtable<String, List<Port>> portTable){
		//Local Variables
		Hashtable<String, List<int[]>> chunkTable;
		Enumeration<String> ipAddresses;
		String currentIP;
		
		//Statements
		chunkTable = new Hashtable<String, List<int[]>>();
		ipAddresses = portTable.keys();
		
		while(ipAddresses.hasMoreElements()){
			currentIP = ipAddresses.nextElement();
			chunkTable.put(currentIP, calculateChunks(portTable.get(currentIP)));
		}
		
		return chunkTable;
	}
	
	private List<int[]> calculateChunks(List<Port> portList){
		//Local Variables
		List<int[]> chunkList = new ArrayList<int[]>();
		int wholeDivisions;
		int startIndex = 0;
		int endIndex = 0;
		
		//Statements
		wholeDivisions = portList.size() / chunkSize;
		
		for(int i = 0; i < wholeDivisions; i++) {
			startIndex = endIndex;
			endIndex += chunkSize;
			chunkList.add(new int[]{startIndex, endIndex});
		}
		
		if(endIndex < portList.size()){
			chunkList.add(new int[]{endIndex, portList.size()});
		}
		
		return chunkList;
	}
	
	private static class WorkThread implements Runnable {
		private boolean isIdle;
		private boolean isFinished;
		private int startIndex;
		private int endIndex;
		private String ipAddress;
		
		private WorkThread(){
			isIdle = true;
			isFinished = false;
		}
		
		public void assignTask(String ipAddress, int[] chunk){
			this.ipAddress = ipAddress;
			startIndex = chunk[0];
			endIndex = chunk[1];			
		}
		
		public void run() {
			// TODO Build workThread class and process
			
		}
		
	}

}
