import java.util.HashMap;

// AP CS Project
// Alex, Johnny, Ethan, and Uday
//
// This is a template comment that you should paste verbatim above every class.
// Fill out any necessary information:
//
// File: IDGen.java
//
// Add your name here if you work on this class:
/** @author Ethan */ 
public class IDGen {
	private static HashMap<String, Integer> idMap = new HashMap<String, Integer>();
	
	public static String make(String type) {
		Integer count = idMap.get(type);
		if (count == null) {
			idMap.put(type, 1);
			return type + "_0";
		} else {
			idMap.put(type, count + 1);
			return type + "_" + count;
		}
	}
	
	private IDGen() {}
}
