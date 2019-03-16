//讀app mainActivity的功能
//Class Command_Function run
//Line17-26:註解掉，因為他只是看錯誤訊息報告，不一定需要
//Line14:增加string result為了解決mainActivity偵測不到之問題
//Line35-49:讀取error訊息看有沒有發生讀不到的問題
//Line15:void>>String
//Line53-55:return result;
//Class Command_Funtion openTargetApp
//Line91-101:判斷是否有沒有沒有成功打開MainActivity的情形
//Class Command_Function allopenTargetApp
//Line110-122: 判斷是否有沒有沒有成功打開MainActivity的情形
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Command {
	private String[] emulators;
	private boolean emulator_using[];
	public String run(String command)
	{
		String result="";
		try {
            Runtime rt = Runtime.getRuntime ();
            Process proc = rt.exec (command);
            //System.out.println(command);
//            StreamConsumer errorConsumer = new
//                StreamConsumer (proc.getErrorStream(), "error");           
//             
//            StreamConsumer outputConsumer = new
//                StreamConsumer (proc.getInputStream(), "output");
//                 
//            errorConsumer.start ();
//            outputConsumer.start ();
//                                     
//            int exitVal = proc.waitFor ();
//			  System.out.println ("ExitValue: " + exitVal);
            
            //讀取error訊息看有沒有發生讀不到的問題 solving:有些package偵測不到的問題--------------------------------------------------------
            InputStream is = proc.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String line = null;
            while ((line = br.readLine()) != null) {
              //System.out.println(line);
              //if(line.indexOf("Error") != -1 && line.indexOf("Activity class") != -1 && line.indexOf("does not exist") != -1)
			   // {
			    	//System.out.println("!!!!!!!!!!!!!"+line);
            	  	result+=line;
			    	//System.out.println("stream:"+result);
			    //}
            }
            int r = proc.waitFor(); // Let the process finish. 
            //--------------------------------------------------------------------
 
		} catch (Exception e) {
			e.printStackTrace();
		}
		//solving:有些package偵測不到的問題--------------------------------------------------------
		return result;
		//--------------------------------------------------------
	}
	
//	public String GetPid(){
//		return pid;
//	}
	public void install_apk(String apk,int emulator_n){
		String command_content="adb -s "+emulators[emulator_n]+" install "+apk;
		Command cmd = new Command();
		cmd.run(command_content);
	}
	public void uninstall_apk(String package_name,int emulator_n){
		String command_content="adb -s "+emulators[emulator_n]+" uninstall "+package_name;
		Command cmd = new Command();
		cmd.run(command_content);
	}
	public void stopApp(String mpName , int emulator_number){
		String command_content="adb -s "+emulators[emulator_number]+" shell am force-stop "+mpName;
		Command cmd = new Command();
		cmd.run(command_content);
	}
	
	public void stopAllApp(String mpName){
		for(int i=0;i<emulators.length;i++){
			String command_content="adb -s "+emulators[i]+" shell am force-stop "+mpName;
			Command cmd = new Command();
			cmd.run(command_content);
		}
	}
	public void openTargetApp(String mpName,String mpMainActivity,int emulator_number){
		String command_content="adb -s "+emulators[emulator_number]+" shell am start "+mpName+"/"+mpName+"."+mpMainActivity;
		Command cmd = new Command();
		//cmd.run(command_content);
		
		//Line91: add 判斷是否有沒有沒有成功打開MainActivity的情形
		String result=cmd.run(command_content);
		//System.out.println("openTargetApp\n");
		if(result.indexOf("Error") != -1 && result.indexOf("Activity class") != -1 && result.indexOf("does not exist") != -1)
        {
			command_content="adb -s "+emulators[emulator_number]+" shell am start "+mpName+"/"+mpMainActivity;
			cmd = new Command();
			result=cmd.run(command_content);
        }
		//-------------------------------------------------------------------
	}
	public void allOpenTargetApp(String mpName,String mpMainActivity){
		for(int i=0;i<emulators.length;i++){
			String command_content="adb -s "+emulators[i]+" shell am start "+mpName+"/"+mpName+"."+mpMainActivity;
			Command cmd = new Command();
			//cmd.run(command_content);

			//Line110: 判斷是否有沒有沒有成功打開MainActivity的情形
			//System.out.println("allopenTargetApp:"+result+"\n");
			String result=cmd.run(command_content);
			if(result.indexOf("Error") != -1 && result.indexOf("Activity class") != -1 && result.indexOf("does not exist") != -1)
            {
				//System.out.println("allopenTargetApp\n");
				command_content="adb -s "+emulators[i]+" shell am start "+mpName+"/"+mpMainActivity;
				cmd = new Command();
				cmd.run(command_content);
            }
			//-------------------------------------------------------------------
		}
	}
	
	public void setEmulator(int emulatorCount){
		emulators=new String[emulatorCount];
		for(int i=0;i<emulators.length;i++){
			int temp=101+i;
			emulators[i]="192.168.56."+Integer.toString(temp)+":5555";
		}
		emulator_using=new boolean[emulatorCount];
		for(int i=0;i<emulator_using.length;i++){
			emulator_using[i]=false;
		}
	}
	public String getEmulator(int emulator_number){
		return emulators[emulator_number];
	}
	public void mkdir(String name){
		Command cmd = new Command();
		cmd.run("mkdir "+name);
	}
}



class StreamConsumer extends Thread {
    InputStream is;
    String type;
     
    StreamConsumer (InputStream is, String type) {
        this.is = is;
        this.type = type;
    }
     
    public void run () {
        InputStreamReader isr = new InputStreamReader (is);
		BufferedReader br = new BufferedReader (isr);
		String line = null;
		//while ((line = br.readLine()) != null)
		   // System.out.println (type + ">" + line);   
    }
}