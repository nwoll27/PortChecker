package portchecker.util;

public class Port {
	private int port;
	private boolean isOpen;
	
	public Port(int port){
		this.port = port;
		isOpen = true;
	}
	
	public Port(int port, boolean isOpen){
		this.port = port;
		this.isOpen = isOpen;
	}
	
	public void openPort(){
		isOpen = true;
	}
	
	public void closePort(){
		isOpen = false;
	}
	
	public int getPort(){
		return port;
	}
	
	public String getStatus(){
		String result;
		if(isOpen){
			result = "OPEN";
		} else {
			result = "CLOSED";
		}
		return result;
	}
	
	public String toString(){
		
		return "Port " + getPort() + " is " + getStatus();
	}
}
