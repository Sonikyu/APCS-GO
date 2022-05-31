package restore;
import java.util.ArrayList;

//AP CS Project
//Alex, Johnny, Ethan, and Uday
//
//This is a template comment that you should paste verbatim above every class.
//Fill out any necessary information:
//
//File: Coder.java
//
// VERY IMPORTANT: This class does not work properly in our current version of the game. If the class was working properly,
// we could save the game into a string and paste it into terminal to return to the saved game. As a result, all
// constructors and methods that use coder (such as encode()) should be ignored. 
//
//Add your name here if you work on this class:
/** @author Ethan */ 
public class Coder {
	private ArrayList<String> tokens;
	int index;
	
	public Coder() {
		this.tokens = new ArrayList<String>();
		this.index = 0;
	}
	
	public Coder(String data) {	
		this.tokens = new ArrayList<String>();
		this.index = 0;
		
		// Parse string into tokens
		String acc = "";
		boolean inString = false;
		for (int i = 0; i <= data.length(); i++) {
			if (i == data.length()) {
				if (!acc.isEmpty()) {
					tokens.add(acc);
				}
				break;
			}
			
			char cur = data.charAt(i);
			if (cur == '\"') {
				inString = !inString;
			} else if (cur == ' ') {
				if (inString) {
					acc += cur;
				} else {
					if (!acc.isEmpty()) {
						tokens.add(acc);
					}
					acc = "";
				}
			} else {
				acc += cur;
			}
		}
		tokens.add("");
	}
	
	public String result() {
		String r = "";
		System.out.println("calculating result with tokens: " + tokens.size());
		for (int i = 0; i < tokens.size(); i++) {
			if (i > 0) {
				r += " ";
			}
			r += tokens.get(i);
		}
		System.out.println("Printing result");
		return r;
	}
	
	private String getErrorEnd() {
		return " at token " + (index + 1) + ", '" + tokens.get(0) + "'";
	}
	
	public String decodeString() throws CoderException {
		if (tokens.isEmpty()) {
			throw new CoderException("Unexpected end of string: " + getErrorEnd());
		}
		index++;
		return tokens.remove(0);
	}
	
	public boolean decodeBoolean() throws CoderException {
		if (tokens.isEmpty()) {
			throw new CoderException("Unexpected end of string" + getErrorEnd());
		}
		int i = decodeInt();
		if (i != 0 && i != 1) {
			throw new CoderException("Invalid boolean" + getErrorEnd());
		}
		index++;
		return i == 0 ? false : true;
	}
	
	
	public int decodeInt() throws CoderException {
		if (tokens.isEmpty()) {
			throw new CoderException("Unexpected end of string" + getErrorEnd());
		}
		try {
			int i = Integer.parseInt(decodeString());
			index++;
			return i;
		}
		catch(Exception e) {
			throw new CoderException("Invalid integer" + getErrorEnd());
		}
	}
	
	public long decodeLong() throws CoderException {
		if (tokens.isEmpty()) {
			throw new CoderException("Unexpected end of string" + getErrorEnd());
		}
		try {
			long l = Long.parseLong(decodeString());
			index++;
			return l;
		}
		catch(Exception e) {
			throw new CoderException("Invalid long integer" + getErrorEnd());
		}
	}
	
	public double decodeDouble() throws CoderException {
		if (tokens.isEmpty()) {
			throw new CoderException("Unexpected end of string" + getErrorEnd());
		}
		try {
			double d = Double.parseDouble(decodeString());
			index++;
			return d;
		}
		catch(Exception e) {
			throw new CoderException("Invalid double" + getErrorEnd());
		}
	}
	
	public void encode(String str) {
		tokens.add("\"" + str + "\"");
	}
	
	public void encode(boolean bool) {
		tokens.add("" + (bool ? 1 : 0));
	}
	
	public void encode(int num) {
		tokens.add("" + num);
	}
	
	public void encode(long num) {
		tokens.add("" + num);
	}
	
	public void encode(double num) {
		tokens.add("" + num);
	}
	
	public void encode(Encodable encodable) {
		encodable.encode(this);
	}
	
	public void putBack(String str) {
		tokens.add(0, str);
	}
}
