//元件個數減少
//Class ripper_Function run
////Line69:get and display the number of nodes
////Line99:get and display the number of nodes
import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Ripper {
	String package_name="";
	String MainActivity="";
	private String resultScriptDir;
	Command ci = new Command();
	long time;
	public int run(String argv[] ,int emulatorCount,long time) 
	{
		this.time=time;
		
		ci.setEmulator(emulatorCount);
		for(int i=0;i<emulatorCount;i++){
			ci.install_apk(argv[0],i);
		}
		//install app
		RipperWait(1);
		ManifestParser mp = new ManifestParser(argv[0]);
		//get app's package name
		package_name=mp.getPkgName();
		MainActivity=mp.getMainActivity();
		if(argv.length==2){	
			/** stop running app **/
			ci.stopAllApp(package_name);
			RipperWait(1);
			/** open target app**/
			ci.allOpenTargetApp(package_name,MainActivity);
			RipperWait(1);	
			
		}
		else{
			/** stop running app**/
			ci.stopAllApp(package_name);
			RipperWait(1);
			/** open target app**/
			ci.allOpenTargetApp(package_name,MainActivity);
			RipperWait(1);
			
		}

		
		/**Coordinator analysis root UI-state**/
		if(argv.length==2)
		{
			setScriptDirPath("./waiting");
			LayoutReader lr =new LayoutReader("./layout");
			lr.setName("FirstLayout");
			lr.getAllCurrentLayout(emulatorCount);
			
//			for(int i=0;i<emulatorCount;i++){
//				try{
//					FileWriter localFileWriter = new FileWriter("./waiting/" + lr.getLayoutName(i) + ".sh", false);
//				}
//				catch (IOException e){
//					e.printStackTrace();
//				}
//			}
//			for(int i=0;i<emulatorCount;i++){
//				LogListening loglisten = new LogListening(lr.getLayoutName(i),i);
//				loglisten.run(); 
//			}
			String CurrentLayout = lr.getLayoutName(0);
			//Line69:get and display the number of nodes
			//int node=lr.getNodesNumber(0);
			//==========================================
			EventGenerator event_generator=new EventGenerator(emulatorCount,package_name,MainActivity,CurrentLayout,resultScriptDir,null,time,argv[0]);
			event_generator.parsingSymbolData(lr.GetNodes(0));
			
			
		}
		/**Slave analysis**/
		else if(argv.length>2){	
			//Slave analysis current UI-state
			setScriptDirPath("./tempscript");
			String historyScript = argv[1];
			/**the first time resume all emulator**/
			
			for(int i=0;i<emulatorCount;i++){
				Thread resumer = new Resumer(historyScript,i);
				resumer.start();
				try{
					resumer.join();
				}catch(InterruptedException e){
					e.printStackTrace();
				}
			}
			/**set loglistener to all emulator**/
			LayoutReader lr =new LayoutReader("./layout");
			lr.setName("FirstLayout_"+argv[1]);
			lr.getAllCurrentLayout(emulatorCount);
			String uiStatePakname = lr.GetNodes(0).get(0).packageName;
			//Line99:get and display the number of nodes
			//int node=lr.getNodesNumber(0);
			//==========================================
			if(uiStatePakname.equalsIgnoreCase(package_name)){
//				for(int i=0;i<emulatorCount;i++){
//					LogListening loglisten = new LogListening(lr.getLayoutName(i),i);
//					loglisten.run();
//				}
				String CurrentLayout = lr.getLayoutName(0);
				
				EventGenerator event_generator=new EventGenerator(emulatorCount,package_name,MainActivity,CurrentLayout,resultScriptDir,historyScript,time,argv[0]);
				event_generator.parsingSymbolData(lr.GetNodes(0));
				
			}
		}
		return 1;
	}


	private void RipperWait(int time){
		try {
			Thread.sleep(time*1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void setScriptDirPath(String path){
		resultScriptDir = path;
	}

	
}
