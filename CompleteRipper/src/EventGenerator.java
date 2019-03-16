//layout name>resource-id
//Class EventGenerator
//Line38: layout name>resource-id 計數null元件數量命名
//Line41:layout name>resource-id 用來存點擊元件的順序resource-id

//Class EventGenerator Function parsingSymbolData
//Line47-55: 點擊元件的順序resource-id

//Class Executor
//Line319: ID_sequence傳進executor
//Class Executor Function executer
//Line283:executor constructer參數改變
//Line297:放入id_sequence

//Class EventGenerator Function run
//Line341: 算是第幾個layout
//Line350: 存算是第幾個layout：countlayout++
//Line356:  layout setName

//Class EventGenerator Function parsingSymbolData
//Line 90: case ScrollView;
					
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Handler;

//public class SynchronizedArrayList
public class EventGenerator {
	Command ci = new Command();
	String apk;
	String packageName;
	String MainActivity;
	String CurrentLayout;
	String resultDir;
	String historyScript;
	String[] emulators;
	int emulatorCount;
	ArrayList<ArrayList<String>> event_sequences = new ArrayList<ArrayList<String>>();
	long time1;
	EventGenerator(int emulatorCount,String packageName,String MainActivity,String CurrentLayout,String resultDir,String historyScript,long time1,String apk){
		emulators=new String[emulatorCount];
		for(int i=0;i<emulatorCount;i++){
			int temp=101;
			emulators[i]="192.168.56."+(temp+i)+":5555";
		}
		this.time1=time1;
		this.packageName=packageName;
		this.MainActivity=MainActivity;
		this.CurrentLayout=CurrentLayout;
		this.resultDir=resultDir;
		this.historyScript=historyScript;
		this.emulatorCount=emulatorCount;
		this.apk=apk;
		ci.setEmulator(emulatorCount);
	}
	
	//Line38: layout name>resource-id 計數null元件數量命名
	int countNull=0;
	//Line41:layout name>resource-id 用來存點擊元件的順序resource-id
	ArrayList<String> ID_sequences=new ArrayList<String>();
	//============================================
	public void parsingSymbolData(List<UIComponentNodes> nodes) {
			for(int j=0;j<nodes.size();j++){
				UIComponentNodes element = nodes.get(j);
				switch (element.className) {
					case "android.widget.Button":
						symbol_button(element);
						break;
					case "android.widget.ImageButton":
						symbol_button(element);
						break;
					case "android.widget.TextView":
						symbol_button(element);
						break;
					case "android.widget.ImageView":
						symbol_button(element);
						break;
					case "android.widget.RelativeLayout":
						symbol_button(element);
						break;
						
					//Line 90: case ScrollView;
					case "android.widget.ListView":
						symbol_listview(element);
						break;
					case "android.widget.ScrollView":
						symbol_listview(element);
						break;
					case "android.widget.GridView":
						symbol_listview(element);
						break;
					//===============================
					case "android.widget.LinearLayout":
						symbol_linearLayout(element);
						break;
					case "android.widget.CheckBox":
						symbol_toggle(element);
						break;
					case "android.widget.Switch":
						symbol_toggle(element);
						break;
					case "android.widget.ToggleButton":
						symbol_toggle(element);
						break;
					case "android.widget.RadioButton":
						symbol_button(element);
						break;
				}
			
		}
		
		long time2 = System.currentTimeMillis();
		//System.out.println("BeforeThreadTime"+((time2-time1)/1000)+"EndTime");
		if(emulatorCount==1){
			executor exe1 = new executor(packageName,MainActivity,CurrentLayout,resultDir,historyScript,event_sequences,apk,ID_sequences);
			Thread thread1=new Thread(exe1);
			thread1.start();
			//=======================================
			try{
				thread1.join();
			}catch(InterruptedException e){
				e.printStackTrace();
			}
			//=======================================
		}
		else if(emulatorCount==2){
		executor exe1 = new executor(packageName,MainActivity,CurrentLayout,resultDir,historyScript,event_sequences,apk,ID_sequences);
			Thread thread1=new Thread(exe1);
			Thread thread2=new Thread(exe1);
			thread1.start();
			thread2.start();
		}
		else if(emulatorCount==3){
		executor exe1 = new executor(packageName,MainActivity,CurrentLayout,resultDir,historyScript,event_sequences,apk,ID_sequences);
			Thread thread1=new Thread(exe1);
			Thread thread2=new Thread(exe1);
			Thread thread3=new Thread(exe1);
			thread1.start();
			thread2.start();
			thread3.start();
		}
	}

	private void symbol_listview(UIComponentNodes u) {

		if (u.scrollable) {
			System.out.println("Scollable: "+u.resourceID);
			int loopTimes =1;
			int x_center = (u.bounds[0][0] + u.bounds[1][0]) / 2;
			int y_center = (u.bounds[0][1] + u.bounds[1][1]) / 2;
			ArrayList<String> temp1=new ArrayList<String>();
			event_sequences.add(temp1);
			event_swipe_right(u.bounds[0][0], u.bounds[1][0], y_center,event_sequences.size()-1);
//			for (int i = 0; i < loopTimes; i++){
//				event_swipe_right(u.bounds[0][0], u.bounds[1][0], y_center,event_sequences.size()-1);
//			}
			ArrayList<String> temp2=new ArrayList<String>();
			event_sequences.add(temp2);
			event_swipe_left(u.bounds[0][0], u.bounds[1][0], y_center,event_sequences.size()-1);
//			for (int i = 0; i < loopTimes; i++){
//				event_swipe_left(u.bounds[0][0], u.bounds[1][0], y_center,event_sequences.size()-1);
//			}
			ArrayList<String> temp3=new ArrayList<String>();
			event_sequences.add(temp3);
			event_swipe_up(x_center, u.bounds[0][1], u.bounds[1][1],event_sequences.size()-1);
//			for (int i = 0; i < loopTimes; i++){
//				event_swipe_up(x_center, u.bounds[0][1], u.bounds[1][1],event_sequences.size()-1);
//			}
			ArrayList<String> temp4=new ArrayList<String>();
			event_sequences.add(temp4);
			event_swipe_down(x_center, u.bounds[0][1], u.bounds[1][1],event_sequences.size()-1);
//			for (int i = 0; i < loopTimes; i++){
//				event_swipe_down(x_center, u.bounds[0][1], u.bounds[1][1],event_sequences.size()-1);
//			}
			//Line213: button add to list
			String element_ID=u.resourceID;
			if(element_ID.equals(""))
			{
				element_ID="NULL"+Integer.toString(countNull);
				countNull++;
			}
			ID_sequences.add(element_ID+"_right");
			ID_sequences.add(element_ID+"_left");
			ID_sequences.add(element_ID+"_up");
			ID_sequences.add(element_ID+"_down");
			//=========================================
			
		}
	}

	/*private void symbol_ZoomControls(UIComponentNodes u, int emulator_n) {
		int m = (u.bounds[0][0] + u.bounds[1][0]) / 2;
		if (u.clickable) {
			event_tap((u.bounds[0][0] + m )/ 2, (u.bounds[0][1] + u.bounds[1][1]) / 2, 0,emulator_n);
			event_tap((u.bounds[0][0] + m )/ 2, (u.bounds[0][1] + u.bounds[1][1]) / 2, 0,emulator_n);
			event_tap((m + u.bounds[1][0]) / 2, (u.bounds[0][1] + u.bounds[1][1]) / 2, 0,emulator_n);
			event_tap((m + u.bounds[1][0]) / 2, (u.bounds[0][1] + u.bounds[1][1]) / 2, 0,emulator_n);
		}
	}*/
	private void symbol_toggle(UIComponentNodes u) {
		if (u.checkable) {
			ArrayList<String> temp=new ArrayList<String>();
			event_sequences.add(temp);
			//Line213: button add to list
			String element_ID=u.resourceID;
			if(element_ID.equals(""))
			{
				element_ID="NULL"+Integer.toString(countNull);
				countNull++;
			}
			ID_sequences.add(element_ID);
			//=========================================
			event_tap((u.bounds[0][0] + u.bounds[1][0]) / 2, (u.bounds[0][1] + u.bounds[1][1]) / 2, 0,event_sequences.size()-1);
			
	
		}
		if (u.longClickable){
			ArrayList<String> temp=new ArrayList<String>();
			event_sequences.add(temp);
			//Line213: button add to list
			String element_ID=u.resourceID;
			if(element_ID.equals(""))
			{
				element_ID="NULL"+Integer.toString(countNull);
				countNull++;
			}
			ID_sequences.add(element_ID);
			//=========================================
			event_tap((u.bounds[0][0] + u.bounds[1][0]) / 2, (u.bounds[0][1] + u.bounds[1][1]) / 2, 1,event_sequences.size()-1);
		}
	}
	
	
	private void symbol_button(UIComponentNodes u) {
		if (u.clickable){
			ArrayList<String> temp=new ArrayList<String>();
			event_sequences.add(temp);
			//Line198: button add to list
			String element_ID=u.resourceID;
			if(element_ID.equals(""))
			{
				element_ID="NULL"+Integer.toString(countNull);
				countNull++;
			}
			ID_sequences.add(element_ID);
			//=========================================
			event_tap((u.bounds[0][0] + u.bounds[1][0]) / 2, (u.bounds[0][1] + u.bounds[1][1]) / 2, 0,event_sequences.size()-1);
			
		}
		if (u.longClickable){
			ArrayList<String> temp=new ArrayList<String>();
			event_sequences.add(temp);
			//Line213: button add to list
			String element_ID=u.resourceID;
			if(element_ID.equals(""))
			{
				element_ID="NULL"+Integer.toString(countNull);
				countNull++;
			}
			ID_sequences.add(element_ID);
			//=========================================
			event_tap((u.bounds[0][0] + u.bounds[1][0]) / 2, (u.bounds[0][1] + u.bounds[1][1]) / 2, 1,event_sequences.size()-1);
		}
	}
/*Linear Layout*/
	private void symbol_linearLayout(UIComponentNodes u) {
		if (u.clickable){
			ArrayList<String> temp=new ArrayList<String>();
			event_sequences.add(temp);
			//Line213: button add to list
			String element_ID=u.resourceID;
			if(element_ID.equals(""))
			{
				element_ID="NULL"+Integer.toString(countNull);
				countNull++;
			}
			ID_sequences.add(element_ID);
			//=========================================
			event_tap((u.bounds[0][0] + u.bounds[1][0]) / 2, (u.bounds[0][1] + u.bounds[1][1]) / 2, 0,event_sequences.size()-1);
		}
		if (u.longClickable){
			ArrayList<String> temp=new ArrayList<String>();
			event_sequences.add(temp);
			//Line213: button add to list
			String element_ID=u.resourceID;
			if(element_ID.equals(""))
			{
				element_ID="NULL"+Integer.toString(countNull);
				countNull++;
			}
			ID_sequences.add(element_ID);
			//=========================================
			event_tap((u.bounds[0][0] + u.bounds[1][0]) / 2, (u.bounds[0][1] + u.bounds[1][1]) / 2, 1,event_sequences.size()-1);
		}

	}
	/* Click: mode = 0, LongClick: mode = 1 */
	private void event_tap(int x, int y, int mode,int list_x) {
		// fw.open();
		switch (mode) {
		case 0:
			touch(x, y,list_x);
			break;
		case 1:
			touch(x, y,list_x);
			touch(x, y, 1000,list_x);
			
			break;
		default:
			;
		}
		
	}
	/*Swioe Left*/
	private void event_swipe_left(int r, int l, int y,int list_x) {
		swipe(r, y, l, y, 1000,list_x);
	}
	/*Swipe Right*/
	private void event_swipe_right(int r, int l, int y,int list_x) {
		swipe(l, y, r, y, 1000,list_x);
	}
	/*Swipe Up */
	private void event_swipe_up(int x, int u, int d,int list_x) {
		// fw.open();
		swipe(x, d-20, x, u+20, 1000,list_x);
	}
	/*Swipe Down*/
	private void event_swipe_down(int x, int u, int d,int list_x) {
		// fw.open();
		swipe(x, u+20, x, d-20, 500,list_x);
	}
	@Override
	protected Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return super.clone();
	}
	private void touch(int x,int y,int delaytime,int list_x){
		//cmd.run("adb -s "+emulators[emulator_n]+" shell input touchscreen swipe "+Integer.toString(x)+" "+Integer.toString(y)+" "+Integer.toString(x)+" "+Integer.toString(y)+Integer.toString(delaytime));
		event_sequences.get(list_x).add("adb shell input touchscreen swipe "+Integer.toString(x)+" "+Integer.toString(y)+" "+Integer.toString(x)+" "+Integer.toString(y)+Integer.toString(delaytime));
	}
	private void touch(int x,int y,int list_x){
		//cmd.run("adb -s "+emulators[emulator_n]+" shell input touchscreen tap "+Integer.toString(x)+" "+Integer.toString(y));
		event_sequences.get(list_x).add("adb shell input touchscreen tap "+Integer.toString(x)+" "+Integer.toString(y));
		//LayoutChangeSwitch("adb -s "+emulators[emulator_n]+" shell input touchscreen tap "+Integer.toString(x)+" "+Integer.toString(y)+"\n",emulator_n);
		}
	private void swipe(int xStart,int yStart,int xEnd,int yEnd,int delayTime,int list_x){
		//cmd.run("adb -s "+emulators[emulator_n]+" shell input touchscreen swipe "+Integer.toString(xStart)+" "+Integer.toString(yStart)+" "+Integer.toString(xEnd)+" "+Integer.toString(yEnd)+" "+Integer.toString(delayTime));
		event_sequences.get(list_x).add("adb shell input touchscreen swipe "+Integer.toString(xStart)+" "+Integer.toString(yStart)+" "+Integer.toString(xEnd)+" "+Integer.toString(yEnd)+" "+Integer.toString(delayTime));
		
		//LayoutChangeSwitch("adb -s "+emulators[emulator_n]+" shell input touchscreen swipe "+Integer.toString(xStart)+" "+Integer.toString(yStart)+" "+Integer.toString(xEnd)+" "+Integer.toString(yEnd)+" "+Integer.toString(delayTime)+"\n",emulator_n);
	}

	
	public class executor extends Thread{
		private String packageName;
		private String MainActivity;
		private String CurrentLayout;
		private String resultScriptDir;
		private String historyScript;
		private String apk;
		private ArrayList<ArrayList<String>> event_sequences;
		private boolean[] emulator_using;
		//Line319: ID_sequence傳進executor
		private ArrayList<String> ID_sequences;
		private String temp_ID="";
		//Line283:executor constructer參數改變
		public executor(String packageName,String MainActivity,String CurrentLayout,String resultScriptDir,String historyScript,ArrayList<ArrayList<String>> event_sequences,String apk
				,ArrayList<String> ID_seq){
			this.packageName=packageName;
			this.MainActivity=MainActivity;
			this.CurrentLayout=CurrentLayout;
			this.resultScriptDir=resultScriptDir;
			this.historyScript=historyScript;
			this.event_sequences=event_sequences;
			this.apk=apk;
			emulator_using=new boolean[emulatorCount];
			for(int i=0;i<emulator_using.length;i++){
				emulator_using[i]=true;
			}
			//Line297:放入id_sequence
			this.ID_sequences=ID_seq;
		}
		public void run(){
				int emulator_n=findIdleEmulator();
				ArrayList<String> fire_event_sequences=next_event_sequences(emulator_n);
				//Line341: 存算是第幾個layout----------------------------
				int countlayout=0;
				int countCloseApp=0;
				//======================================
				while(fire_event_sequences!=null){
					for(int i=0;i<fire_event_sequences.size();i++){
						ci.run(fire_event_sequences.get(i));	
					}
					//Line350: 算是第幾個layout countlayout++----------------------------
					//countlayout++;
					ThreadWait(1);
					LayoutReader lr =new LayoutReader("./layout");
					//Line356:  layout setName
					//System.out.println("Layout Name: "+"Close:"+Integer.toString(countCloseApp)+"_"+ID_sequence.get(countlayout));
					//lr.setName("Close:"+Integer.toString(countCloseApp)+"_"+temp_ID);
					lr.setName(temp_ID);
					lr.getCurrentLayout(emulator_n);
					
					String ChangingLayout = lr.getLayoutName(emulator_n);
					String uiStatePakname = lr.GetNodes(emulator_n).get(0).packageName;
					if(uiStatePakname.equalsIgnoreCase(packageName)){
						if(!CurrentLayout.equals(ChangingLayout)){
							ci.uninstall_apk(packageName,emulator_n);
							ThreadWait(1);
							fire_event_sequences=LayoutChangeSwitch(fire_event_sequences,MainActivity,ChangingLayout,CurrentLayout,packageName,historyScript,emulator_n);
						}
						else{
							emulator_using[emulator_n]=true;
							fire_event_sequences=next_event_sequences(emulator_n);
						}
						//System.out.println("uiStatePakname.equalsIgnoreCase(packageName)");
					}
					else{
						ci.stopApp(packageName,emulator_n);
						ThreadWait(1);
						ci.openTargetApp(packageName, MainActivity, emulator_n);
						ThreadWait(1);
						Thread resumer = new Resumer(historyScript,emulator_n);
						resumer.start();
						try{
							resumer.join();
						}catch(InterruptedException e){
							e.printStackTrace();
						}
						emulator_using[emulator_n]=true;
						fire_event_sequences=next_event_sequences(emulator_n);
						//System.out.println("else");
					}
				}
		}
		public int findIdleEmulator(){
			synchronized(this){
				for(int i=0;i<emulator_using.length;i++){
					if(emulator_using[i]){
						emulator_using[i]=false;
						return i;
					}
				}
				return -1;
			}
		}
		public ArrayList<String> next_event_sequences(int emulator_n){
			synchronized(this){
				if(event_sequences.size()>0){
					ArrayList<String> temp = completeEvent(event_sequences.get(0),emulator_n);
					event_sequences.remove(0);
					//Line439: add 
					
					temp_ID=ID_sequences.get(0);
					System.out.println("next_event_sequences: "+ID_sequences.get(0));
					ID_sequences.remove(0);
					return temp;
				}
			return null;
			}
		}
		public ArrayList<String> completeEvent(ArrayList<String> event_sequences,int emulator_n){
			ArrayList<String> complete_event_sequences = new ArrayList<String>();
			for(int i=0;i<event_sequences.size();i++){
				String []events = event_sequences.get(i).split(" ");
				//[adb,shell,input,touchscreen,tap/swipe,x,y]
				//[ 0 ,  1  ,  2  ,     3     ,    4    ,5,6]
				String event=events[0]+" -s "+emulators[emulator_n];
				for(int j=1;j<events.length;j++){
					event=event+" "+events[j];
				}
				complete_event_sequences.add(event);
			}
			return complete_event_sequences;
		}
		private ArrayList<String> LayoutChangeSwitch(ArrayList<String> event_sequences,String MainActivity,String ChangingLayout,String CurrentLayout,String packageName,String historyScript,int emulator_n)
		{
			ci.install_apk(apk,emulator_n);
			ThreadWait(1);
			ci.stopApp(packageName,emulator_n);
			ThreadWait(1);
			ci.openTargetApp(packageName, MainActivity, emulator_n);
			ThreadWait(1);
				if(historyScript!=null){
					Thread resumer = new Resumer(historyScript,emulator_n);
					resumer.start();
					try{
						resumer.join();
					}catch(InterruptedException e){
						e.printStackTrace();
					}
				}
				else{
				}
				ArrayList<String> record_event_sequences = new ArrayList<String>();
				for(int i=0;i<event_sequences.size();i++){
					String[] event=event_sequences.get(i).split(" ");
					String temp="";
					for(int j=0;j<event.length;j++){//
						if(!event[j].contains("-s")&&!event[j].contains("192.168")){
							temp=temp+event[j]+" ";
						}
						else
							i++;
					}
					temp=temp+"\n";
					record_event_sequences.add(temp);
				}
				ScriptGen sc =new ScriptGen(resultScriptDir,ChangingLayout,historyScript);
				sc.startGenScript(record_event_sequences);
				record_event_sequences.clear();
				emulator_using[emulator_n]=true;
				return next_event_sequences(emulator_n);
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
}
