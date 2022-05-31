import java.awt.Graphics2D;
import java.time.LocalTime;
import java.util.Date;
import restore.Coder;

//AP CS Project
//Alex, Johnny, Ethan, and Uday
//
//This is a template comment that you should paste verbatim above every class.
//Fill out any necessary information:
//
//File: Timer.java
//
//Add your name here if you work on this class:
/** @author Johnny */ 

public class Timer extends Thread {
	public static final Timer timer = new Timer();
	
	private long startTime;
	
	private long time;
	
	
	public Timer() {
		time = 0;
	}
	
	/**
	 * Starts the timer thread.
	 */
	public void start() {
		super.start();
		this.startTime = new Date().getTime();
	}
	
	/**
	 * Updates the timer.
	 */
	public void updateTime() {
		this.time = ((new Date().getTime() - startTime)/1000);
	}
	
	/**
	 * Gets the time from the timer.
	 * @return The time of the timer.
	 */
	public long getTime() {
		return time;
	}
	
}
