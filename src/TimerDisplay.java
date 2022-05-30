import java.awt.Font;
import java.awt.Graphics2D;

import restore.Coder;

public class TimerDisplay extends Entity {

	public static String TYPE = "TimerDisplay";
	private static String IMAGE_FILE =  "TimerDisplay.png";
	
	public TimerDisplay() {
		super(TYPE, 0, IMAGE_FILE);
	}
	
	public TimerDisplay(Coder coder) {
		super(coder);
		// TODO Auto-generated constructor stub
	}
	
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

	@Override
	public void cycle(Level level, Game.GameInfo info) {
	}
	
}
