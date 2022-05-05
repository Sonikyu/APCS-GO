import java.awt.Dimension;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import java.lang.Thread;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.JFrame;

// AP CS Project
// Alex, Johnny, Ethan, and Uday
//
// This is a template comment that you should paste verbatim above every class.
// Fill out any necessary information:
//
// File: GameView.java
//
// Add your name here if you work on this class:
/** @author Ethan */ 
public class GameView extends JComponent implements Runnable {
    public static final int DELAY = 5;
	private Dimension size;
    private Game game;
    private Thread animator;
        
	public GameView(Game game) {
		this.game = game;
        this.size = game.getSize();
        
        setup();
	}

	private void setup() {
        setBackground(Color.BLACK);
        setPreferredSize(size);
	}
	
	public Dimension getSize() {
		return size;
	}
	
    public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D)g;
        game.paint(g2d);
        //Toolkit.getDefaultToolkit().sync();
    }
    
    private void cycle() {
    	game.cycle();
    }
    
    // These two functions are taken from the website ZetCode:
    
    public void run() {
        long beforeTime, timeDiff, sleep;

        beforeTime = System.currentTimeMillis();

        while (true) {
            cycle();
            repaint();

            timeDiff = System.currentTimeMillis() - beforeTime;
            sleep = DELAY - timeDiff;

            if (sleep < 0) {
                sleep = 2;
            }

            try {
                Thread.sleep(sleep);
            } catch (InterruptedException e) {
                
                String msg = String.format("Thread interrupted: %s", e.getMessage());
                
                JOptionPane.showMessageDialog(this, msg, "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }

            beforeTime = System.currentTimeMillis();
        }
    }
    
    public void addNotify() {
        super.addNotify();

        animator = new Thread(this);
        animator.start();
    }
}
