import java.awt.Font;
import java.awt.Graphics2D;
import restore.Coder;
import restore.CoderException;

//AP CS Project
//Alex, Johnny, Ethan, and Uday
//
//This is a template comment that you should paste verbatim above every class.
//Fill out any necessary information:
//
//File: TimerDisplay.java
//
//Add your name here if you work on this class:
/** @author Johnny */ 

public class TimerDisplay extends Entity {

	public static String TYPE = "TimerDisplay";
	private static String IMAGE_FILE =  "TimerDisplay.png";
	
	/**
	 * Initializes a TimerDisplay entity.
	 */
	public TimerDisplay() {
		super(TYPE, 0, IMAGE_FILE);
	}
	
	public TimerDisplay(Coder coder) throws CoderException {
		super(coder);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Sets the position of the TimerDisplay.
	 * @param time The current time of the timer.
	 */
	public void setPosition(long time) {
		int digits = 0;
		if (time == 0) {
			digits = 1;
		}
		while (time > 0) {
			time /= 10;
			digits++;
		}
		super.setPosition(790 - (20 * digits), 30);
	}
	
	/**
	 * Paints the TimerDisplay object.
	 * @param g The graphics object the entity is painted to.
	 */
	@Override
	public void paint(Graphics2D g) {
		super.paint(g);
		String time = "" + Timer.timer.getTime();
		setPosition(Timer.timer.getTime());
		Font currentFont = g.getFont();
		Font newFont = currentFont.deriveFont(currentFont.getSize() * 3.0F);
		g.setFont(newFont);
		g.drawString(time, getX(), getY());
	}

	/**
	 * Cycles the entity.
	 * @param level The current level.
	 * @param info The game information.
	 */
	@Override
	public void cycle(Level level, Game.GameInfo info) {
	}
	
}
