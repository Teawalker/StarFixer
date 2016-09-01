package source;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;

class StreamReader extends Thread {
	private InputStream is;
	private StringWriter sw = new StringWriter();
	
	public StreamReader(InputStream is){
		this.is= is;
	}
	
	public void run(){
		try {
			int c;
			while ((c = is.read()) != -1)
					sw.write(c);
			sw.flush();
			sw.close();
			is.close();
		} catch (IOException e){}
	}
	
	public String getResult() {
		return sw.toString();
	}
}