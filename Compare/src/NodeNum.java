import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

public class NodeNum {
	
	String layoutname="";
	int NumallLayout_1;
	int NumallLayout_2;
	int countEmulator=0;
	String [] bug1;
	String [] bug2;
	public void run()
	{
		try{
			LoadallFile();
		}
		catch(Exception e)
		{
			//e.printStackTrace();
			System.out.println("LoadallFile()");
		}
		if(bug1.length!=bug2.length)
		{
			ReportDiffNumLayout();
			//return;
		}
		//int filelength=(bug1.length>=bug2.length)?bug1.length:bug2.length;
		for(int i=0;i<bug1.length;i++)
		{
			for(int j=0;j<bug2.length;j++)
			{
				if(bug1[i].equals(bug2[j]))
				{
					int layout1=LoadoneFile(1,bug1[i]);
					int layout2=LoadoneFile(2,bug2[j]);
					if(layout1!=layout2)
						ReportDiffNode(bug1[i],layout1,layout2);
					break;
				}
			}
		}
	}
	public void LoadallFile()
	{
		File bug1_file=new File("./bugreport_1");
		bug1=bug1_file.list();
		System.out.println(bug1.length);
		
		File bug2_file=new File("./bugreport_2");
		bug2=bug2_file.list();
		System.out.println(bug2.length);
		
	}
	
	public int LoadoneFile(int emulator,String filename)
	{
		int nodenum=-1; 
		try {
			 System.out.println("filename: "+filename+"\n");
			 FileReader fr = new FileReader("./bugreport_"+emulator+"/"+filename);
			 //System.out.println("!!!!"+"\n");
			 BufferedReader br = new BufferedReader(fr);
			 // System.out.println("!!!!!!!!!!"+"\n");
	         String line = br.readLine();
	         nodenum=Integer.parseInt(line);
			 //System.out.println("4321:"+line+"\n");
//	         while (line != null) {
//	            line = br.readLine();
//	            System.out.println("4321"+line+"\n");
//	         }
	        
	         br.close();
	    }
		catch(Exception e)
		{
			System.out.println("LoadoeneFile()");
		}
		return nodenum;
	}
	
	public void ReportDiffNumLayout()
	{
		try{
			//FileWriter file = new FileWriter("./NumOfNodes/" + Layoutname + ".txt", false);
			FileWriter file = new FileWriter("./comparebug/" +  "NumofComponentReduction"+ ".txt", false);
			file.write("In the standard layout, the number of cliclable components:"+bug1.length+"\n");
			file.write("In another layout, the number of clickable components: "+bug2.length+"\n");
			file.write("The number of layouts are different. It maybe has layout chaos: The reduction in the number of total componenets."+"\n");
			file.close();
		}
		catch (IOException e){
			StringWriter sw = new StringWriter();  
            PrintWriter pw = new PrintWriter(sw);  
            e.printStackTrace(pw);  
            System.out.println(sw.toString().toUpperCase()); 
		}
	}
	
	public void ReportDiffNode(String layoutname,int node1,int node2)
	{
		try{
			//FileWriter file = new FileWriter("./NumOfNodes/" + Layoutname + ".txt", false);
			FileWriter file = new FileWriter("./comparebug/" + layoutname +"NumofComponentReduction2"+ ".txt", false);
			file.write(layoutname+" The number of standard layout components: "+node1+"\n");
			file.write(layoutname+" The number of another screen resolution layout components:"+node2+"\n");
			file.write("The number of layouts are different. It maybe has layout chaos: The reduction in the number of total componenets."+"\n");
			file.close();
		}
		catch (IOException e){
			StringWriter sw = new StringWriter();  
            PrintWriter pw = new PrintWriter(sw);  
            e.printStackTrace(pw);  
            System.out.println(sw.toString().toUpperCase()); 
		}
	}
	

}
