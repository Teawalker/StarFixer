package source;

public class WinRegistryWorker {
	public static final String readRegistry(String location, String key){
		try {
			Process process = Runtime.getRuntime().exec("reg query " + '"' + location + "\" /v " + key + '"');
		    StreamReader reader = new StreamReader(process.getInputStream());
		    reader.start();
		    process.waitFor();
		    reader.join();
		    String output = reader.getResult();
		    
//		    if(!output.contains("\t")){
//		    	return null;
//		    }
		    
		    String[] parsed = output.split("\t");
		    return parsed[parsed.length-1];
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return null;
		}
	}
}
