import java.awt.Image;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.List;
import org.opencv.core.*;
import org.opencv.imgcodecs.*;
import org.opencv.imgproc.Imgproc;
import org.opencv.core.Rect;

public class CutElement {
	String layoutname="";
	List<UIComponentNodes> nodes;
	Mat img; 
	Command cmd;
	String [] text;
	public CutElement(String _layoutname, List<UIComponentNodes> _nodes)
	{
		layoutname=_layoutname;
		nodes=_nodes;
		System.load("/home/testing1/opencv/build/lib/libopencv_java300.so");
		cmd=new Command();
		cmd.run("mkdir "+"./layout/"+layoutname);
		cmd.run("mkdir "+"./layout/"+layoutname+"/OCR");
		cmd.run("mkdir "+"./layout/"+layoutname+"/EditDistance");
		//System.out.println(re);
	}
	public void run()
	{
		img =  Imgcodecs.imread("./layout/"+"0_"+layoutname+".png");
		//System.out.println(img.cols()+","+img.height());
		for (int i = 0; i < nodes.size(); i++)
		 {
			
			 if(!nodes.get(i).text.isEmpty())
			 {
				 System.out.println("element: "+nodes.get(i).resourceID+"-"+nodes.get(i).text);
				 int [][] bound=nodes.get(i).bounds;
				 System.out.println(bound[0][0]+","+bound[0][1]+","+bound[1][0]+","+bound[1][1]);
				 
				 Rect rec=new Rect(bound[0][0],bound[0][1],bound[1][0]-bound[0][0],bound[1][1]-bound[0][1]);
				 int x=rec.x+rec.width;
				 int y=rec.y+rec.height;
				// System.out.println(x+","+y);
				 //Scalar color=new Scalar(255);
				 Mat result=new Mat();
				 Mat dst=new Mat(1500,1500,img.type());
				 Mat Roi=new Mat(bound[1][1]-bound[0][1],bound[1][0]-bound[0][0],img.type());
				
				 img.submat(rec).copyTo(Roi);
				 //Imgcodecs.imwrite(nodes.get(i).resourceID+".jpg", Roi);
				 //Mat Roi=new Mat(img,rec);
				 Imgproc.cvtColor(Roi,Roi,Imgproc.COLOR_RGB2GRAY);
				 Imgproc.threshold(Roi, Roi, 150, 255, Imgproc.THRESH_BINARY);
				 Size size=new Size(Roi.cols()*2,Roi.rows()*2);
				 Roi.copyTo(dst);
				// Roi.copyTo(dst.rowRange(1,Roi.rows()).colRange(1,Roi.cols()));
				 Imgproc.resize(dst,dst,size);
				 
				  //GaussianBlur(dst, result, cv::Size(0, 0), 3);//提升精準度
				// Imgproc.addWeighted(dst, 6.8, result, 2.69, 0, result); //提升精準度
				 String imgname="";
				 if(nodes.get(i).resourceID.isEmpty())
					 imgname="Null"+i;
				 else
					 imgname=nodes.get(i).resourceID;
				 if(nodes.get(i).resourceID.indexOf('/')!=-1)
					 imgname=nodes.get(i).resourceID.replace('/','_');
				 boolean bo=Imgcodecs.imwrite("./layout/"+layoutname+"/"+imgname+".bmp", dst);
				 System.out.println(Boolean.toString(bo));
				// Imgcodecs.imwrite("/layout/"+layoutname+"/"+nodes.get(i).resourceID+".bmp",  Roi);
				// Imgcodecs.imwrite(nodes.get(i).resourceID+".jpg", Roi);
				 cmd=new Command();
				 cmd.run("tesseract "+"./layout/"+layoutname+"/"+imgname+".bmp "+"./layout/"+layoutname+"/OCR/"+layoutname+"_"+imgname+" -psm 6");
				 
				 String txt=LoadFile("./layout/"+layoutname+"/OCR/"+layoutname+"_"+imgname+".txt");
				 //System.out.println("txt: "+txt);
				 String handletxt=handleString(txt);
				 SaveFile("./layout/"+layoutname+"/OCR/"+layoutname+"_"+imgname+".txt",handletxt);
				 String ori=handleString(nodes.get(i).text);
				// System.out.println(handletxt+"\n"+ori);
				 int dis=editDist(handletxt,ori ,handletxt.length(),ori.length());
				// dis=editDist("AB","AEFG" ,2,4);
				 System.out.println("edit distance:"+dis);
				 SaveFile("./layout/"+layoutname+"/EditDistance/"+layoutname+"_"+imgname+".txt",Integer.toString(dis));
			 }
	     }
			 //waitKey(1000);
	}
	
	public int editDist(String str1,String str2,int m, int n)
	{
		// Create a table to store results of subproblems
        int dp[][] = new int[m+1][n+1];
      
        // Fill d[][] in bottom up manner
        for (int i=0; i<=m; i++)
        {
            for (int j=0; j<=n; j++)
            {
                // If first string is empty, only option is to
                // isnert all characters of second string
                if (i==0)
                    dp[i][j] = j;  // Min. operations = j
      
                // If second string is empty, only option is to
                // remove all characters of second string
                else if (j==0)
                    dp[i][j] = i; // Min. operations = i
      
                // If last characters are same, ignore last char
                // and recur for remaining string
                else if (str1.charAt(i-1) == str2.charAt(j-1))
                    dp[i][j] = dp[i-1][j-1];
      
                // If last character are different, consider all
                // possibilities and find minimum
                else
                    dp[i][j] = 1 + minthree(dp[i][j-1],  // Insert
                                       dp[i-1][j],  // Remove
                                       dp[i-1][j-1]); // Replace
            }
        }
  
        return dp[m][n];
//		// If first string is empty, the only option is to
//	    // insert all characters of second string into first
//	    if (m == 0) return n;
//	 
//	    // If second string is empty, the only option is to
//	    // remove all characters of first string
//	    if (n == 0) return m;
//	 
//	    // If last characters of two strings are same, nothing
//	    // much to do. Ignore last characters and get count for
//	    // remaining strings.
//	    System.out.println((m-1)+str1+"\n"+(n-1)+str2+"\n" );
//	    System.out.println(str1.charAt(m-1)+"\n" +str2.charAt(n-1)+"\n\n" );
//	    try {
//			Thread.sleep(1000);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	    if (str1.charAt(m-1) ==str2.charAt(n-1))
//	        return editDist(str1, str2, m-1, n-1);
//	   
//	    // If last characters are not same, consider all three
//	    // operations on last character of first string, recursively
//	    // compute minimum cost for all three operations and take
//	    // minimum of three values.
//	    return 1 + minthree ( editDist(str1,  str2, m, n-1),    // Insert
//	                     editDist(str1,  str2, m-1, n),   // Remove
//	                     editDist(str1,  str2, m-1, n-1) // Replace
//	                   );
	}

	public int mintwo(int x,int y)
	{
		return (x<=y)?x:y;
	}
	public int minthree(int x,int y,int z)
	{
		return mintwo(mintwo(x,y),z);
	}
	
	public String handleString(String str)
	{
		while(str.contains(" ")||str.contains("\n"))
	    {
	      	str=str.replace(" ","");
	       	str=str.replace("\n","");
	    }
		return str;
	}
	public String LoadFile(String filename)
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
	public void SaveFile(String filename,String str)
	{
		try
		{
			FileWriter file = new FileWriter(filename, false);
			file.write(str);
			file.close();
		}
		catch(Exception e)
		{
			System.out.println("result:"+str);
		}
	}
	
	
}
