import java.util.ArrayList;
import java.util.HashSet;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.event.KeyEvent;

public class NPCGuard extends Entity{
	public static String TYPE = "NPCGuard";
	private static int MAX_HEALTH = 100000;
	private static String IMAGE_FILE = "ParthInThePark.png";
	
	private int range = 75;
	ImageIcon image;
	private JFrame NPCDialogue = new JFrame();
	public NPCGuard(ImageIcon image) {
		super(NPCGuard.TYPE, NPCGuard.MAX_HEALTH, NPCGuard.IMAGE_FILE);
		this.image = image;
		JLabel label = new JLabel();
		label.setIcon(image);
		label.setBounds(0,0,300,200);
		NPCDialogue.setLayout(null);
		NPCDialogue.setSize(300,200);
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
