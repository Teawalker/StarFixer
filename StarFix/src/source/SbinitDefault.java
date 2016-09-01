package source;

import java.util.ArrayList;

public class SbinitDefault {
	private static ArrayList<String> arr = new ArrayList<>();
	
	private static void fillArray(){
		arr.add("{");
		arr.add("  \"assetDirectories\" : [");
		arr.add("    \"..\\\\assets\\\\\",");
		arr.add("    \"..\\\\mods\\\\\"");
		arr.add("  ],");
		arr.add(" ");
		arr.add("  \"storageDirectory\" : \"..\\\\storage\\\\\",");
		arr.add("");
		arr.add("  \"defaultConfiguration\" : {");
		arr.add("    \"gameServerBind\" : \"*\",");
		arr.add("    \"queryServerBind\" : \"*\",");
		arr.add("    \"rconServerBind\" : \"*\"");
		arr.add("  }");
		arr.add("}");
	}
	
	public static void setArray(){
		fillArray();
	}
	
	public static ArrayList<String> getArray(){
		return arr;
	}
}
