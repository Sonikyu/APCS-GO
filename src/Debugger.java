import java.util.ArrayList;
import java.time.LocalTime;
import java.util.Date;

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
	private boolean hasStarted;
	private boolean printDebugs;
	private long startTime;
	private long lastTime;
	
	public Debugger() {
		this.backlog = new ArrayList<String>();
		this.hasStarted = false;
		this.printDebugs = false;
	}
	
	public void print(String str) {
		if (!hasStarted) return;
		long timestamp = (new Date().getTime() - startTime) / 1000;		
		if (lastTime == timestamp) {
			backlog.add("        " + str);
		} else {
			String timeStr = "" + timestamp;
			while (timeStr.length() < 4) {
				timeStr = "0" + timeStr;
			}
			backlog.add("<" + timeStr + "s> " + str);
			lastTime = timestamp;
		}
	}
	
	public void showDebug(boolean show) {
		printDebugs = show;
	}
	
	public boolean isShowing() {
		return printDebugs;
	}
	
	public void start() {
		super.start();
		this.startTime = new Date().getTime();
		this.lastTime = -1;
		this.hasStarted = true;
	}
	
	public void run() {
		while (true) {
			if (printDebugs) {
				String last = null;
				while (backlog.size() > 0) {
					String current = backlog.remove(0);
					if (last == null || !last.equals(current)) {
						System.out.println(current);
						last = current;
					}
				}
			}
		}
	}
	
	
}