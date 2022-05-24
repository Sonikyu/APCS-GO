import java.util.ArrayList;
import java.util.HashSet;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.text.AttributeSet.ColorAttribute;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.Color;
import java.awt.Font;

public class NPCGuard extends Entity{
	public static String TYPE = "NPCGuard";
	private static int MAX_HEALTH = 100;
	private static String IMAGE_FILE = "Enemy.png";
	
	private int range = 75;
	private boolean keyFound = false;
	private JFrame frame;
	
	public NPCGuard(JFrame frame){
		super(NPCGuard.TYPE, NPCGuard.MAX_HEALTH, NPCGuard.IMAGE_FILE);
		this.frame = frame;
		UIManager UI=new UIManager();
		UI.put("Panel.background", Color.black);
		UI.put("OptionPane.messageForeground", Color.yellow);
		UI.put("OptionPane.messageFont", new Font("Arial", Font.PLAIN, 14));
	}
	
	public void cycle(Level level, Game.GameInfo info) {
		ArrayList<Entity> visibleEntities = level.getCurrentRoom().getVisibleEntities();
		for (int i = 0; i < visibleEntities.size(); i++) {
			Entity entity = visibleEntities.get(i);
			if(entity.getType().equals(Player.TYPE)) {
				Player player = (Player) entity;
				double distance = Math.sqrt(Math.pow(entity.getX() - getX(), 2) + Math.pow(entity.getY() - getY(), 2));
				if (distance <= range) {
					HashSet<Integer> keysDown = info.getKeysDown();
					if (keysDown.contains(KeyEvent.VK_SPACE)) {
							keyFound = player.firstOccur("Key") >= 0;
							Debugger.main.print("" + player.firstOccur("Key"));
							if (keyFound) {
								JOptionPane.showMessageDialog(frame, "You have found the key!");
								keysDown.remove(KeyEvent.VK_SPACE);
							}
							else {
//								JLabel icon = new JLabel(message);
//								JPanel panel = new JPanel();
//								panel.setLayout(new BorderLayout());
//								panel.add(icon, BorderLayout.CENTER);
//								JOptionPane.showMessageDialog(frame, "", "Hint", JOptionPane.PLAIN_MESSAGE, message);
//								JOptionPane.showMessageDialog(frame, panel, "Display", JOptionPane.PLAIN_MESSAGE);
//								JOptionPane.showMessageDialog(frame, "You must find the key \n Trick the Enemy");
//								JOptionPane.showMessageDialog(frame, 
//								        "<html><p style='font-family: Calibri; font-size: 20pt; background-color: black, color: yellow'>You must find the key <br> Trick the enemy");
								JOptionPane op = new JOptionPane();
								op.setPreferredSize(new Dimension(300,200));
								op.showMessageDialog(frame, "You must find the key \n Trick the Enemy");
								keysDown.remove(KeyEvent.VK_SPACE);
							}
					}
				}	
			}
		}
	}
	
	
}
