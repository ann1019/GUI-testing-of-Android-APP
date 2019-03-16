import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;



public class Resumer extends Thread{
			Command ci = new Command();

			private String scriptName;
			private String[] emulators = new String[4];
			private int emulator_n;
			public Resumer(String scriptName, int emulator_n){
				this.scriptName=scriptName;
				this.emulator_n=emulator_n;
			}
			public void run(){
				emulators[0]="192.168.56.101:5555";
				emulators[1]="192.168.56.102:5555";
				emulators[2]="192.168.56.103:5555";
				emulators[3]="192.168.56.104:5555";
				if(scriptName!=null){
				try {
					FileReader fr =new FileReader(scriptName);
					BufferedReader br =new BufferedReader(fr);
					try {
						while(br.ready()){
							
							String []events = br.readLine().split(" ");
							//[adb,shell,input,touchscreen,tap/swipe,x,y]
							//[ 0 ,  1  ,  2  ,     3     ,    4    ,5,6]
							String event=events[0]+" -s "+emulators[emulator_n];
							for(int i=1;i<events.length;i++){
								event=event+" "+events[i];
							}
							//+" "+events[1]+" "+events[2]+" "+events[3]+" "+events[4]+" "+events[5]+" "+events[6];
							ci.run(event);
							ThreadWait(1);
						}
					} catch (IOException e) {
						System.out.println("Command read error");
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} catch (FileNotFoundException e) {
					System.out.println("File can't be found");
					e.printStackTrace();
				}
				}
				else{
					
				}
			}
			private void ThreadWait(int time){
				try {
					Thread.sleep(time*1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}