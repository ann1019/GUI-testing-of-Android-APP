import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;


public class CroppedText {
	static int threshold=0;
	File [] layout1;
	File [] layout2;
	File [] ocr1;
	File [] ocr2;
	File [] edit1;
	File [] edit2;
	public void run()
	{
		
		try{
			
			layout1=LoadallFile("./layout_1");
			layout2=LoadallFile("./layout_2");
			
			if(layout1.length!=layout2.length)
			{
				System.out.println("layout1.length!=layout2.length\n");
				//return;
			}
			for(int i=0;i<layout1.length;i++)
			{
				for(int a=0;a<layout2.length;a++)
				{
					
					//System.out.println("layout1:"+layout1[i].getName());
					//System.out.println("layout2:"+layout2[a].getName());
					if(layout1[i].getName().equals(layout2[a].getName()))
					{
						if(layout1[i].isDirectory())
						{
							edit1=LoadallFile("./layout_1/"+layout1[i].getName()+"/EditDistance/");
							edit2=LoadallFile("./layout_2/"+layout2[a].getName()+"/EditDistance/");
							System.out.println(edit1.length);
							System.out.println(edit2.length);
							
							for(int j=0;j<edit1.length;j++)
							{
								
								for(int b=0;b<edit2.length;b++)
								{
									//System.out.println("edit1:"+edit1[j].getName());
									//System.out.println("edit2:"+edit2[b].getName());
									if(edit1[j].getName().equals(edit2[b].getName()))
									{
										int ed_ori=LoadEDFile(1,"./layout_1/"+layout1[i].getName()+"/EditDistance/"+edit1[j].getName());
										int ed_another=LoadEDFile(1,"./layout_2/"+layout2[a].getName()+"/EditDistance/"+edit2[b].getName());
										
										int ed_abs=Math.abs(ed_another-ed_ori);
										
										if(ed_abs>threshold)
										{
											String ocr_ori=LoadOCRFile(1,"./layout_1/"+layout1[i].getName()+"/OCR/"+edit1[j].getName());
											String ocr_ano=LoadOCRFile(2,"./layout_2/"+layout2[a].getName()+"/OCR/"+edit2[b].getName());
											ReportGen(edit1[j].getName(),ed_ori,ed_another,ocr_ori,ocr_ano);
											
										}
										break;
									}
								}
							}
						}
						break;
					}
				}
			}
			//layout2=LoadallFile("./layout_2");
			
		}
		catch(Exception e)
		{
			//e.printStackTrace(); 
			System.out.println("LoadallFile()");
		}
	}
	public File LoadFolder(int index,File all)
	{
		File[] secFolder=all.listFiles();
		int count=0;
		for(int i=0;i<secFolder.length;i++)
		{
			if(secFolder[i].isDirectory())
			{
				count++;
				if(count==index)
					return secFolder[i];
			}
		}
		return null;
		
		
		
	}
	public File[] LoadallFile(String path)
	{
		File file=new File(path);
		File[] allfile=file.listFiles();
		return allfile;
	}
	public int LoadEDFile(int emulator,String filename)
	{
		int nodenum=-1; 
		try {
			 //System.out.println("filename: "+filename+"\n");
			FileReader fr = new FileReader(filename);
			 //System.out.println("!!!!"+"\n");
			BufferedReader br = new BufferedReader(fr);
			// System.out.println("!!!!!!!!!!"+"\n");
	        String line = br.readLine();
	        nodenum=Integer.parseInt(line);
	        
			System.out.println(filename+" edit distance:"+line+"\n");
//	        while (line != null) {
//	            line = br.readLine();
//	            System.out.println("4321"+line+"\n");
//	        }
	        
	        br.close();
	    }
		catch(Exception e)
		{
			System.out.println("LoadEDFile()");
		}
		return nodenum;
	}
	public String LoadOCRFile(int emulator,String filename)
	{
		String result = "";
		try
		{
			//System.out.println(filename);
			FileReader fr = new FileReader(filename);
			BufferedReader br = new BufferedReader(fr);
			result="";
			String line = br.readLine();
	        while (line != null) {
	        	result+=line;
	            line = br.readLine();
	            //System.out.println("4321:"+line+"\n");
	        }
	        //System.out.println("4321:"+result+"\n");
	        
		}catch(Exception e)
		{
			System.out.println("LoadFile error");
		}
		return result;
	}
	public void ReportGen(String filename,int node1,int node2,String ocr_ori,String ocr_ano)
	{
		try{
			//FileWriter file = new FileWriter("./NumOfNodes/" + Layoutname + ".txt", false);
			FileWriter file = new FileWriter("./comparebug/" +  "Cropping Text"+filename+ ".txt", false);
			file.write(filename+"\n The standard text: "+ocr_ori+", and "+" the standard edit distance："+node1+"\n\n");
			file.write(filename+"\n Another display text: "+ocr_ano+", and "+"the edit distance: "+node2+"\n");
			file.write("This layout maybe has layout chaos: cropped text on components."+"\n");
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
