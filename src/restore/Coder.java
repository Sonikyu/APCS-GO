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
//Add your name here if you work on this class:
/** @author Ethan */ 
public class Coder {
	private ArrayList<String> tokens;
	private String error;
	private String message;
	int index;
	boolean errored;
	
	public Coder() {
		this.tokens = new ArrayList<String>();
		this.error = "";
		this.message = "";
		this.errored = false;
	}
	
	public Coder(String data) {	
		this.tokens = new ArrayList<String>();
		this.error = "";
		this.message = "";
		this.errored = false;
		
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
	
	public String getError() {
		String errStr = "";
		if (!message.isEmpty()) {
			errStr += message + " (";
		}
		if (!error.isEmpty()) {
			errStr += "(" + error + " ";
		}
		errStr += "at token " + (index + 1) + ", '" + tokens.get(0) + "')";
		return errStr;
	}
	
	public boolean hasError() {
		return errored;
	}
	
	public void setErrorMsg(String message) {
		this.message = message;
		errored = true;
	}
	
	private void setError(String error) {
		this.error = error;
		errored = true;
	}
	
	public String decodeString() {
		if (tokens.isEmpty()) {
			setError("Unexpected end of string");
			return null;
		}
		index++;
		return tokens.remove(0);
	}
	
	public Boolean decodeBoolean() {
		if (tokens.isEmpty()) {
			setError("Unexpected end of string");
			return null;
		}
		Integer i = decodeInt();
		if (i == null || (i != 0 && i != 1)) {
			setError("Invalid boolean");
			return null;
		}
		index++;
		return i == 0 ? false : true;
	}
	
	
	public Integer decodeInt() {
		if (tokens.isEmpty()) {
			setError("Unexpected end of string");
			return null;
		}
		try {
			Integer i = Integer.parseInt(decodeString());
			index++;
			return i;
		}
		catch(Exception e) {
			setError("Invalid integer");
			return null;
		}
	}
	
	public Long decodeLong() {
		if (tokens.isEmpty()) {
			setError("Unexpected end of string");
			return null;
		}
		try {
			Long l = Long.parseLong(decodeString());
			index++;
			return l;
		}
		catch(Exception e) {
			setError("Invalid long integer");
			return null;
		}
	}
	
	public Double decodeDouble() {
		if (tokens.isEmpty()) {
			setError("Unexpected end of string");
			return null;
		}
		try {
			Double d = Double.parseDouble(decodeString());
			index++;
			return d;
		}
		catch(Exception e) {
			setError("Invalid double");
			return null;
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
