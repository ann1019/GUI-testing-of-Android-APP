//Line 209-213: 加break

//元件個數減少
//Class LayoutReader
//Line 293-308:add function " getNodesNumber"-------
//Class getLayout Function run
//Line 89: 元件個數減少-輸出node number存檔到NumofNodes
//Class getLayout
//Line110-128:元件個數減少-增加輸出檔案function

//layout name>resource-id
//Class LayoutReader Function getNodesbyXml
//Line217:add"resource-id getAttribute"
//Class Layout Reader
//Line310: layoutname>>resource-id 增加setName()
//Line40: String Layoutname;
//Line319: layoutname>>resource-id 增加getName()
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;
import java.util.StringTokenizer;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


public class LayoutReader{
	//Line40: String Layoutname;
	String Layoutname="";
	String layoutDir="Test";
	String[] layoutName = new String[4];
	//String []attributeType=new String[4];

	List<UIComponentNodes> nodes0=new ArrayList<UIComponentNodes>();
	List<UIComponentNodes> nodes1=new ArrayList<UIComponentNodes>();
	List<UIComponentNodes> nodes2=new ArrayList<UIComponentNodes>();
	List<UIComponentNodes> nodes3=new ArrayList<UIComponentNodes>();
	
	int NumofNode=0;
	int allnodenum=0;
	LayoutReader(String Path)
	{
//		for(int i=0;i<attributeType.length;i++){
//			attributeType[i]="";
//		}
		layoutDir=Path;
	}
	private void RipperWait(int time){
		try {
			Thread.sleep(time*1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public class getLayout extends Thread{
		private String[] emulators = new String[4];
		private String name[]=new String[4];
		private int emulator_n;
		
		public getLayout(int emulator_n){
			emulators[0]="192.168.56.101:5555";
			emulators[1]="192.168.56.102:5555";
			emulators[2]="192.168.56.103:5555";
			emulators[3]="192.168.56.104:5555";
			this.emulator_n=emulator_n;
		}
		public void run(){
			Command cmd=new Command();
			for(int i=0;i<name.length;i++){
				name[i]="temp"+Integer.toString(i);
			}
			cmd.run("adb -s "+emulators[emulator_n]+" shell uiautomator dump "+"/sdcard"+"/"+name[emulator_n]+".xml");
			ThreadWait(1);
			//System.out.println("Uiautomator dump, done!\n");
			
			cmd.run("adb -s "+emulators[emulator_n]+" pull "+"/sdcard"+"/"+name[emulator_n]+".xml "+layoutDir);
			ThreadWait(1);
			
			cmd.run("adb -s "+emulators[emulator_n]+" shell screencap -p /sdcard/"+name[emulator_n]+".png");
			ThreadWait(1);

			cmd.run( "adb -s "+emulators[emulator_n]+" pull  /sdcard/"+name[emulator_n]+".png "+layoutDir);
			ThreadWait(1);
			getNodesbyXml(layoutDir+"/"+name[emulator_n]+".xml",emulator_n);
			layoutName[emulator_n]=LayoutReader.this.getName(emulator_n);
			String temp="/temp"+Integer.toString(emulator_n);
			//Line 87: 元件個數減少-輸出node number存檔到NumofNodes
			
			displayNumofNodes();
			
			//=====================================
			cmd.run("mv "+layoutDir+temp+".xml "+layoutDir+"/"+emulator_n+"_"+layoutName[emulator_n]+".xml");
			cmd.run("mv "+layoutDir+temp+".png "+layoutDir+"/"+emulator_n+"_"+layoutName[emulator_n]+".png");
			
			//getNodesbyXml(layoutDir+"/"+emulator_n+"_"+layoutName[emulator_n]+".xml",emulator_n);
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
	//public void getNodebyXml(int emulator_n){
	//	getNodesbyXml(layoutDir+"/"+emulator_n+"_"+layoutName[emulator_n]+".xml",emulator_n);
	//}
	
	//Line104-122:元件個數減少-增加輸出檔案function
	public void displayNumofNodes()
	{
		int node=getNodesNumber();	
		//Layoutname=LayoutReader.this.getName(emulator_n);
		try{
			//FileWriter file = new FileWriter("./NumOfNodes/" + Layoutname + ".txt", false);
			FileWriter file = new FileWriter("./bugreport/" +  Layoutname + ".txt", false);
			file.write(Integer.toString(node));
			file.close();
		}
		catch (IOException e){
			StringWriter sw = new StringWriter();  
            PrintWriter pw = new PrintWriter(sw);  
            e.printStackTrace(pw);  
            System.out.println(sw.toString().toUpperCase()); 
		}
	}
	//===============================================
	
	public void getCurrentLayout(int emulator_n) {
		getLayout CL= new getLayout(emulator_n);
		CL.start();
		try{
			CL.join();
		}catch(InterruptedException e){
			e.printStackTrace();
		}
		
		//layoutNames.add(name);
	}
	
	public void getAllCurrentLayout(int emulatorCount)
	{	
		String[] emulators = new String[4];
		emulators[0]="192.168.56.101:5555";
		emulators[1]="192.168.56.102:5555";
		emulators[2]="192.168.56.103:5555";
		emulators[3]="192.168.56.104:5555";
		String name[]=new String[4];
		for(int i=0;i<name.length;i++){
			name[i]="temp"+Integer.toString(i);
		}
		Command cmd=new Command();
		for(int i=0;i<emulatorCount;i++){
			cmd.run("adb -s "+emulators[i]+" shell uiautomator dump "+"/sdcard"+"/"+name[i]+".xml");	
			RipperWait(1);
		}
		//System.out.println("Uiautomator dump, done!\n");
		for(int i=0;i<emulatorCount;i++){
			cmd.run("adb -s "+emulators[i]+" pull "+"/sdcard"+"/"+name[i]+".xml "+layoutDir);
			RipperWait(1);
		}
		for(int i=0;i<emulatorCount;i++){
			//System.out.println("adb -s "+emulators[i]+" pull "+"/sdcard"+"/"+name[i]+".xml "+layoutDir);
			//System.out.println("adb -s "+emulators[i]+" pull layout, done!\n");
		}
		for(int i=0;i<emulatorCount;i++){
			cmd.run("adb -s "+emulators[i]+" shell screencap -p /sdcard/"+name[i]+".png");
			RipperWait(1);
		}
		for(int i=0;i<emulatorCount;i++){
			cmd.run( "adb -s "+emulators[i]+" pull  /sdcard/"+name[i]+".png "+layoutDir);
			RipperWait(1);
		}
		for(int i=0;i<emulatorCount;i++){
			layoutName[i]=getName(i);
			getNodesbyXml(layoutDir+"/"+name[i]+".xml",i);
			
			String temp="/temp"+Integer.toString(i);
			cmd.run("mv "+layoutDir+temp+".xml "+layoutDir+"/"+i+"_"+layoutName[i]+".xml");
			cmd.run("mv "+layoutDir+temp+".png "+layoutDir+"/"+i+"_"+layoutName[i]+".png");
			
			displayNumofNodes();
			 //System.out.println("LayoutName:"+Layoutname+"\n");
			CutElement cutelement=new CutElement(Layoutname,nodes0);
			cutelement.run();
		}
	}
	
	
	private void getNodesbyXml(String Path,int number)
	{
		try{
			File fXmlFile = new File(Path);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			
			doc.getDocumentElement().normalize();
			NodeList nList = doc.getElementsByTagName("node");
			//20161025 add three line 判斷是否在listview or gridview 裡面，並且數有幾個node
			boolean isClickable=false;
			boolean cancount=true;
			NodeList listviewnode;
			int listviewnodenum=0,countlistviewnode=1;
			int maxlistviewnodenum=0,countlistviewnode2=1;
			//------------------------------------------------
			
			//System.out.println(nList.getLength());
			allnodenum=nList.getLength();
			 for (int temp = 0; temp < nList.getLength(); temp++) {
				 
				  Node nNode = nList.item(temp);
				  UIComponentNodes tempNode=new UIComponentNodes();
				 // System.out.println("\nCurrent Element :" + nNode.getNodeName());
				 
				  if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				 
					   Element eElement = (Element) nNode;
					   tempNode.index = Integer.valueOf(eElement.getAttribute("index"));
					   
					   //======Line217: add"resource-id getAttribute"
					   tempNode.resourceID=eElement.getAttribute("resource-id");
					   if(eElement.getAttribute("resource-id").equals(""))
						   tempNode.resourceID=eElement.getAttribute("class")+temp;
					   //================================================
					   tempNode.text=eElement.getAttribute("text");
					   tempNode.className =  eElement.getAttribute("class");
					   tempNode.packageName= eElement.getAttribute("package");
					   tempNode.contentDesc= eElement.getAttribute("content-desc");
					   tempNode.checkable = Boolean.valueOf(eElement.getAttribute("checkable"));
					   tempNode.checked = Boolean.valueOf(eElement.getAttribute("checked"));
					  
					 //20161025 add if(isClickable){...}判斷這個node是不是屬於listview並且可以點擊
					   if(isClickable)
					   {
						   tempNode.clickable = true;
						   if(countlistviewnode<listviewnodenum)
							   countlistviewnode++;
						   else
						   {
							   isClickable=false;
							   countlistviewnode=1;
						   }
					   }
					   else
						   tempNode.clickable = Boolean.valueOf(eElement.getAttribute("clickable"));
//					   
					   tempNode.clickable = false;
					   
					   tempNode.enabled = Boolean.valueOf(eElement.getAttribute("enabled"));
					   tempNode.focusable = Boolean.valueOf(eElement.getAttribute("focusable"));
					   tempNode.focused = Boolean.valueOf(eElement.getAttribute("focused"));
					   tempNode.scrollable = Boolean.valueOf(eElement.getAttribute("scrollable"));
					   tempNode.longClickable = Boolean.valueOf(eElement.getAttribute("longClickable"));
					   tempNode.password = Boolean.valueOf(eElement.getAttribute("password"));
					   tempNode.selected = Boolean.valueOf(eElement.getAttribute("selected"));
					   tempNode.bounds = getBoundsFromString(eElement);
					   if(!cancount)
					   {
						   if(countlistviewnode2<maxlistviewnodenum)
						   {
							   countlistviewnode2++;
						   }
							   
						   else
						   {
							   cancount=true;
							   countlistviewnode2=1;
						   } 
					   }
					   else
					    NumofNode++;
					   
					   
					 //20161025 add 判斷此class 是不是listview or gridview
					   if(eElement.getAttribute("class").equals("android.widget.ListView")&&Boolean.valueOf(eElement.getAttribute("clickable")))
					   {
						  listviewnode=eElement.getChildNodes();
						  isClickable=true;
						  listviewnodenum=countChildNode(listviewnode,0);
						  countlistviewnode=1;
						  //System.out.println("ListView:"+listviewnodenum);
						  
					   }
					   else if(eElement.getAttribute("class").equals("android.widget.GridView")&&Boolean.valueOf(eElement.getAttribute("clickable")))
					   {
						  listviewnode=eElement.getChildNodes();
						  isClickable=true;
						  listviewnodenum=countChildNode(listviewnode,0);
						  countlistviewnode=1;
						 // System.out.println("GridView:"+listviewnodenum);
					   }
					   else if(eElement.getAttribute("class").equals("android.widget.ScrollView")&&Boolean.valueOf(eElement.getAttribute("clickable")))
					   {
						  listviewnode=eElement.getChildNodes();
						  isClickable=true;
						  listviewnodenum=countChildNode(listviewnode,0);
						  countlistviewnode=1;
						 //System.out.println("ScrollView:"+listviewnodenum);
					   }
					 //line335: 20161125針對listview會發生偵測不完所產生的腳本
					   if(listviewnodenum>=3)
					   {
						   listviewnodenum=3;
					   }
					   
					   if(eElement.getAttribute("class").equals("android.widget.ScrollView")||eElement.getAttribute("class").equals("android.widget.GridView")
							   ||eElement.getAttribute("class").equals("android.widget.ListView"))
					   {
						   listviewnode=eElement.getChildNodes();
						   maxlistviewnodenum=countChildNode(listviewnode,0);
						  // System.out.println("maxlistviewnodenum:"+maxlistviewnodenum);
						   cancount=false;
					   }
					   
					  // System.out.println("listviewnodenum:"+listviewnodenum);
					   //System.out.println("maxlistviewnodenum:"+maxlistviewnodenum);
					   //-----------------------------------------------------------------
					   switch (number){
					   	case 0:	nodes0.add(tempNode); break;
					   	case 1:	nodes1.add(tempNode); break;
					   	case 2:	nodes2.add(tempNode); break;
					   	case 3:	nodes3.add(tempNode); break;
					   }
				   }
			 }
			
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		
	}
	private int countChildNode(NodeList nodelist,int num)
	{
		//int temp=0;
		num+=nodelist.getLength();
		System.out.println("num:"+num);
		//System.out.println("length:"+nodelist.getLength());
		for(int i=0;i<nodelist.getLength();i++)
		{
			Node node=nodelist.item(i);
			Element e=(Element)node;
			//System.out.println("i:"+i+e.getAttribute("class"));
			if(nodelist.item(i).hasChildNodes())
			{
				//System.out.println("true"+i);
				Element echild=(Element)nodelist.item(i);
				NodeList childlist=echild.getChildNodes();
				num=countChildNode(childlist,num);
				//System.out.println("temp:"+num);
			}
		}
		//num+=temp;
		
		//System.out.println("all:"+num);
		return num;
	}
	private int[][] getBoundsFromString(Element eElement)
	{
		int[][] result =new int[2][2];
		   StringTokenizer st = new StringTokenizer(eElement.getAttribute("bounds"),"[,]");
	        for(int i=0;i<2;i++)
	        {
	        	for(int j=0;j<2;j++)
	        	{
	        		String integer=st.nextToken();
	        		//System.out.println(integer);
	        		result[i][j] = Integer.valueOf(integer);
	        	}
	        }
		return result;
	}
	
	
//	private String getName(int emulator_n)
//	{
//        MessageDigest md = null;
//		try {
//			md = MessageDigest.getInstance("MD5");
//		} catch (NoSuchAlgorithmException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//        md.update(attributeType[emulator_n].getBytes());
//        
//        byte byteData[] = md.digest();
// 
//        //convert the byte to hex format method 1
//        StringBuffer sb = new StringBuffer();
//        for (int i = 0; i < byteData.length; i++) {
//         sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
//        }
//        
//        //convert the byte to hex format method 2
//        StringBuffer hexString = new StringBuffer();
//    	for (int i=0;i<byteData.length;i++) {
//    		String hex=Integer.toHexString(0xff & byteData[i]);
//   	     	if(hex.length()==1) hexString.append('0');
//   	     	hexString.append(hex);
//    	}
//    	return  hexString.toString();
//	}
	//Line310: layoutname>>resource-id 增加setName()
	public void setName(String name)
	{
		name=name.replace("/", "");
		Layoutname=name;
	}
	//-------------------------------
	//Line319: layoutname>>resource-id 增加getName()
	private String getName(int emulator_n)
	{
		return Layoutname;
	}
	//---------------------------------
	public String getLayoutDir()
	{
		return layoutDir;
	}
	
	public String getLayoutName(int number)
	{
		return layoutName[number];
	}

	public List<UIComponentNodes> GetNodes(int number)
	{
		switch (number){
			case 0: return nodes0;
			case 1: return nodes1;
			case 2: return nodes2;
			case 3: return nodes3;
			default: return nodes0;
		}
	}
	
	//-----20161024 21:48 add function " getNodesNumber"-------
		public int getNodesNumber()
		{
//			int i=0;
//			if(emulator_n==0)
//				i=nodes0.size();
//			else if(emulator_n==1)
//				i=nodes1.size();
//			else if(emulator_n==2)
//				i=nodes2.size();
//			else if(emulator_n==3)
//				i=nodes3.size();
//			System.out.println("The number of nodes:"+i+"!!!!!!!!!!!\n");
//			return i;
			System.out.println("All NumofNode:"+allnodenum);
			System.out.println("NumofNode:"+NumofNode);
			return NumofNode;
		}
		//-------------------------------------
	
}



