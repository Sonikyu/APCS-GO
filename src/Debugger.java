import java.util.ArrayList;

// AP CS Project
// Alex, Johnny, Ethan, and Uday
//
// This is a template comment that you should paste verbatim above every class.
// Fill out any necessary information:
//
// File: Debugger.java
//
// Add your name here if you work on this class:
/** @author Ethan */ 
public class Debugger extends Thread {
	public static final Debugger main = new Debugger();
	
	private ArrayList<String> backlog;
	private boolean printDebugs;
	private boolean mutex;
	
	public Debugger() {
		this.backlog = new ArrayList<String>();
		this.printDebugs = false;
		this.mutex = true;
	}
	
	public void print(String str) {
		this.mutex = false;
		backlog.add(str);
		this.mutex = true;
	}
	
	public void showDebug(boolean show) {
		printDebugs = show;
	}
	
	public boolean isShowing() {
		return printDebugs;
	}
	
	public void run() {
		while (true) {
			System.out.print("");
			if (printDebugs) {
				String last = null;
				while (backlog.size() > 0) {
					while (!this.mutex);
					String current = backlog.remove(0);
					if (last == null || !last.equals(current)) {
						System.out.println(current);
						last = current;
					}
				}
	            try {
	                Thread.sleep(100);
	            } catch (InterruptedException e) {
	                e.printStackTrace();
	            }
			}
		}
	}
}