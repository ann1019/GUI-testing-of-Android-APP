import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ScriptGen {
	FileWriter fw;
	String disPath;
	String layoutName;
	String logHistory;
	int lastNodeSize = -2;


	ScriptGen(String dis,String ln,String lh) {
		disPath = dis;
		layoutName =ln;
		logHistory = lh;
	}
	
	
	public void startGenScript(ArrayList<String> event_sequences){
		try {	
			if(logHistory==null){
				fw = new FileWriter(disPath + "/"+layoutName+".sh", false);
				}
			
			else {
					fw = new FileWriter(disPath + "/"+layoutName+".sh", true);
					FileReader fr = new FileReader(logHistory);
					String line = null;
	            	// Always wrap FileReader in BufferedReader.
	            	BufferedReader bufferedReader = new BufferedReader(fr);
	            	while((line = bufferedReader.readLine()) != null) {
	                	fw.write(line+"\n");
	            	}	
	            	// Always close files.
	            	bufferedReader.close();	
				}
				for(int i=0;i<event_sequences.size();i++){
					fw.write(event_sequences.get(i));
				}
				fw.flush();
				fw.close();
		} 
		catch (IOException e) {
				System.out.println("Script generate failed");
				// TODO Auto-generated catch block
				e.printStackTrace();
		}
}
}