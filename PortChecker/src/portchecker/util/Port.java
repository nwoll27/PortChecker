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
	
	public String toString(){
		String status = "CLOSED";
		if(isOpen){
			status = "OPEN";
		}
		return "Port " + port + " is " + status;
	}
}
