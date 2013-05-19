package com.playtomic.tests;

import java.util.ArrayList;
import android.util.Log;

public class PTest
{
	protected static ArrayList<String> successes;
	protected static ArrayList<String> failures;
	protected static ArrayList<String> results;
	
	public static void Setup() {
		successes = new ArrayList<String>();
		failures = new ArrayList<String>();
		results = new ArrayList<String>();
	}
	
	public static Boolean assertEquals(String section, String name, Boolean expected, Boolean received) {
		if (expected == received) {
			Record(true, section, name, expected, received);
			return true;
		}
		
		Record(false, section, name, expected, received);
		return false;
	}
	
	public static Boolean assertEquals(String section, String name, int expected, int received) {
		if (expected == received) {
			Record(true, section, name, expected, received);
			return true;
		}
		
		Record(false, section, name, expected, received);
		return false;
	}
	
	public static Boolean assertEquals(String section, String name, java.util.Date date, java.util.Date date2) {
		if (date.equals(date2)) {
			Record(true, section, name, date, date2);
			return true;
		}
		
		Record(false, section, name, date, date2);
		return false;
	}
	
	public static Boolean assertEquals(String section, String name, String expected, String received) {
		if (expected.equals(received)) {
			Record(true, section, name, expected, received);
			return true;
		}
		
		Record(false, section, name, expected, received);
		return false;
	}
	
	public static Boolean assertTrue(String section, String name, Boolean value) {
		return assertEquals(section, name, value, true);
	}
	
	public static Boolean assertFalse(String section, String name, Boolean value) {
		return assertEquals(section, name, value, false);
	}
	
	public static Boolean assertNotNull(String section, String name, String value) {
		return assertTrue(section, name, value != null) && assertTrue(section, name, !value.equals(""));
	}
	
	public static void fail(String section, String message) {
		failures.add("[" + section + "] " + message);
	}
	
	private static void Record(Boolean success, String section, String message, Object expected, Object received) {
		
		String m = "[" + section + "] " + message;
		
		if (success) {
			successes.add (m);
		} else {
			m += " (" + expected + " vs " + received + ")";
			failures.add (m);
		}
		
		results.add (m);
	}
	
	public static void render() 
	{
		if(failures.size() > 0) {
			Log.d ("Playtomic.PTest", " ----------------      errors      ---------------- ");
			
			for(String failure : failures) {
				Log.d ("Playtomic.PTest", failure);
			}
		}
		
		if(failures.size() == 0) {
			Log.d ("Playtomic.PTest", successes.size() + " tests passed out of " + results.size() + " total");
		}
	}
}