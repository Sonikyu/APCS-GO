import java.awt.Graphics2D;
import java.time.LocalTime;
import java.util.Date;
import restore.Coder;

public class Timer extends Thread {
	public static final Timer timer = new Timer();
	
	private long startTime;
	
	private long time;
	
	
	public Timer() {
		time = 0;
	}
	

	public void start() {
		super.start();
		this.startTime = new Date().getTime();
	}
	
	public void updateTime() {
		this.time = ((new Date().getTime() - startTime)/1000);
	}
	
	public long getTime() {
		return time;
	}
	
}
