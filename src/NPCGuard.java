import java.util.ArrayList;
import java.util.HashSet;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import restore.Coder;
import restore.CoderException;
import restore.Encodable;

import java.awt.event.KeyEvent;

//AP CS Project
//Alex, Johnny, Ethan, and Uday
//
//This is a template comment that you should paste verbatim above every class.
//Fill out any necessary information:
//
//File: NPCGuard.java
//
//Add your name here if you work on this class:
/** @author Uday */ 

public class NPCGuard extends Entity implements Encodable {
	public static String TYPE = "NPCGuard";
	private static int MAX_HEALTH = 100000;
	private static String IMAGE_FILE = "HelpfulNPC.png";
	
	private int range = 150;
	private int width, height;
	ImageIcon image;
	private String imageString;
	private JFrame NPCDialogue = new JFrame();
	private JLabel label;
	
	
	public NPCGuard(String imageString, int width, int height) {
		super(NPCGuard.TYPE, NPCGuard.MAX_HEALTH, NPCGuard.IMAGE_FILE);
		this.width = width;
		this.height = height;
		this.imageString = imageString;
		makeImage();
		
	}
	
	public NPCGuard(Coder coder) throws CoderException {
		super(coder);
		this.imageString = coder.decodeString();
		makeImage();
		JLabel label = new JLabel();
		label.setIcon(image);
		label.setBounds(0,0,300,200);
		NPCDialogue.setLayout(null);
		NPCDialogue.setSize(300,200);
		NPCDialogue.add(label);
		NPCDialogue.setVisible(false);
	}
	
	public void encode(Coder coder) {
		super.encode(coder);
	}
	
	public void makeImage() {
		image = new ImageIcon(imageString);
		label = new JLabel();
		label.setIcon(image);
		label.setBounds(0,0,width,height);
		NPCDialogue.setLayout(null);
		NPCDialogue.setSize(width + 18,height + 19);
		NPCDialogue.add(label);
		NPCDialogue.setVisible(false);
	}
	
	public void cycle(Level level, Game.GameInfo info) {
		ArrayList<Entity> visibleEntities = level.getCurrentRoom().getVisibleEntities();
		for(int i = 0; i < visibleEntities.size(); i++) {
			Entity entity = visibleEntities.get(i);
			if (entity.getType().equals(Player.TYPE)) {
				double distance = Math.sqrt(Math.pow(entity.getX() - getX(), 2) + Math.pow(entity.getY() - getY(), 2));
				if (distance <= range) {
					HashSet<Integer>keysDown = info.getKeysDown();
					if (keysDown.contains(KeyEvent.VK_X)) {
						NPCDialogue.setVisible(true);
						keysDown.remove(KeyEvent.VK_X);
					}
				}
			}
		}
	}
}
