import java.awt.Dimension;
import java.awt.Image;

import restore.Coder;
import restore.Restorer;

//AP CS Project
//Alex, Johnny, Ethan, and Uday
//
//This is a template comment that you should paste verbatim above every class.
//Fill out any necessary information:
//
//File: EntityRestorer.java
//
//Add your name here if you work on this class:
/** @author Ethan */ 
public class EntityRestorer extends Restorer {
	@Override
	public Object decode(Coder coder) {
		String type = coder.decodeString();
		int health = coder.decodeInt();
		int maxHealth = coder.decodeInt();
		int x = coder.decodeInt();
		int y = coder.decodeInt();
		
		int imageCount = coder.decodeInt();
		String[] imageNames = new String[imageCount];
		coder.encode(imageNames.length);
		for (int i = 0; i < imageCount; i++) {
			imageNames[i] = coder.decodeString();
		}
		
		int currentImage = coder.decodeInt();
		boolean isVisible = coder.decodeBoolean();

		GenericEntity entity = new GenericEntity(type, maxHealth, imageNames);
		entity.__setHealth(health);
		entity.setPosition(x, y);
		entity.setImageAtIndex(currentImage);
		if (isVisible) {
			entity.show();
		} else {
			entity.hide();
		}
		
		return entity;
	}

	@Override
	public void encode(Object object, Coder coder) {
		Entity entity = (Entity)object;
		
		coder.encode(entity.getType());
		coder.encode(entity.getHealth());
		coder.encode(entity.getMaxHealth());
		coder.encode(entity.getX());
		coder.encode(entity.getY());
		
		String[] imageNames = entity.__getImageNames();
		coder.encode(imageNames.length);
		for (String imageName: imageNames) {
			coder.encode(imageName);
		}
		
		coder.encode(entity.__getCurrentImage());
		coder.encode(entity.isVisible());
	}
}
/*
private final String id;
private String type;
private int health;
private final int maxHealth;
private int x, y;
private String[] imageNames;
private Image[] images;
private int currentImage;
private Dimension size;
private boolean visible;
*/