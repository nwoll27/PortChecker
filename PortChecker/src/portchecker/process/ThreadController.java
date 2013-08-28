package portchecker.process;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Hashtable;

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
		maxThreads = 8;
	}
	
	public ThreadController(int chunkSize, int maxThreads){
		this.chunkSize = chunkSize;
		this.maxThreads = maxThreads;
	}
	
	//Methods
	public void processPortTable(Hashtable<String, List<Port>> portTable, Logger logger) throws Exception{
		//Local Variables
		Map<String, List<int[]>> chunkTable;
		List<WorkThread> threadList;
		int currentChunkIndex;
		Iterator<String> ipAddresses;
		Iterator<WorkThread> threads;
		String currentIP;
		
		//Statements
		threadList = new ArrayList<WorkThread>();
		
		chunkTable = buildChunkTable(portTable);
		
		for(int i = 0; i < maxThreads; i++){
			threadList.add(new WorkThread(portTable, logger));
		}
		
		currentChunkIndex = 0;
		ipAddresses = chunkTable.keySet().iterator();
		currentIP = ipAddresses.next();
		while(!threadList.isEmpty()){
			threads = threadList.iterator();
			while(threads.hasNext()){
				WorkThread thread = threads.next();
				if(thread.isIdle){
					if(currentChunkIndex >= chunkTable.get(currentIP).size() && ipAddresses.hasNext()){
						currentIP = ipAddresses.next();
						currentChunkIndex = 0;
					} 
					if (currentChunkIndex < chunkTable.get(currentIP).size()){
						thread.assignTask(currentIP, chunkTable.get(currentIP).get(currentChunkIndex));
						currentChunkIndex++; 						
					} else if(currentChunkIndex >= chunkTable.get(currentIP).size() && !ipAddresses.hasNext()){
						thread.finishWork();
						threadList.remove(thread); //Remove current thread if no work is left
						threads = threadList.iterator();
					} else {
						throw new Exception("ERROR! Thread was handled improperly.");
					}
				}
			}
		}
		
		System.out.println("Test");
	}
	
	private Hashtable<String, List<int[]>> buildChunkTable(Map<String, List<Port>> portTable){
		//Local Variables
		Hashtable<String, List<int[]>> chunkTable;
		
		//Statements
		chunkTable = new Hashtable<String, List<int[]>>();
				
		for(String currentIP : portTable.keySet()){
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
		Map<String, List<Port>> portTable;
		private boolean isIdle;
		private boolean isFinished;
		private int startIndex;
		private int endIndex;
		private String ipAddress;
		private int socketTimeout = 200;
		Logger logger;
		Thread t;
		
		private WorkThread(Map<String, List<Port>> portTable, Logger logger){
			isIdle = true;
			isFinished = false;
			this.portTable = portTable;
			this.logger = logger;
			t = new Thread(this);
			t.start();
		}
		
		public void assignTask(String ipAddress, int[] chunk){
			this.ipAddress = ipAddress;
			startIndex = chunk[0];
			endIndex = chunk[1];
			resumeWork();			
		}
		
		public void finishWork(){
			ipAddress = null;
			isFinished = true;
			resumeWork();
		}
		
		synchronized void resumeWork(){
			isIdle = false;
			notify();
		}
		
		public void run() {
			try {
				while(!isFinished){
					synchronized(this) {
						while(isIdle) {
							wait();
						}
					}
					if(!isFinished){						
						for (int i = startIndex; i < endIndex; i++) {
							System.out.println("\nAttempting connection to Port "
									+ ipAddress + ":" + portTable.get(ipAddress).get(i).getPort());
							try {
								Socket serverSock = new Socket();
								serverSock.connect(new InetSocketAddress(ipAddress,
										portTable.get(ipAddress).get(i).getPort()), socketTimeout);
	
								System.out.println("Connected to Port: "
										+ portTable.get(ipAddress).get(i).getPort());
	
								serverSock.close();
							} catch (Exception e) {
								portTable.get(ipAddress).get(i).closePort();
							}
						}
						
						isIdle = true;
					}
				}
			} catch (InterruptedException e) {
				System.out.println("Thread interrupted");
			}
			
		}
		
	}

}
