import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ManifestParser {
	private static Document doc;
	private static String MainActivity;
	private static String PkgName;
	private static ArrayList<String> ActivityNameList;
	ManifestParser(String APKName) {
		Command cmd = new Command();
		cmd.run("java -jar apktool.jar d "+APKName+" -o "+"reverse");
		
		String FileDir = "./"+"reverse"+"/AndroidManifest.xml";
		parse(FileDir);
		ArrayList<String> a = getActivityList();
		for (String str : a) {
			System.out.println(str);
		}
		System.out.println("MainActivity = "+getMainActivity());
		System.out.println("PackageName = "+getPkgName());
		System.out.println("LongActivityName = "+longActivityName());
	}


	private static void parse(String FileDir) {
		MainActivity="";
		PkgName="";
		ActivityNameList = new ArrayList<String>();
		File input = new File(FileDir);
		try {
			doc = Jsoup.parse(input, "UTF-8", "");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("No parsing data, the  fake apk."); 
			e.printStackTrace();
			System.exit(1);
		}
		if (doc != null) {
			Elements activity = doc.getElementsByTag("activity");

			for (Element e : activity) {
				String ActivityTag = e.outerHtml().toString();
				int front = ActivityTag.indexOf("android:name")+14;//14 = length of ' android:name=" '
				int back = front;
				if(front != -1)	{
					for( ; back < ActivityTag.length() ; back++)
						if(ActivityTag.charAt(back) == '"')
							break;
					//System.out.println(front+", "+back);
					String str = ActivityTag.subSequence(front, back).toString();
					if(str.charAt(0) == '.')
						str = str.replace(".", "");
					ActivityNameList.add(str);
					if (e.toString().indexOf("android.intent.action.MAIN") != -1) {
						MainActivity = str;
					}
				}
			}
			Elements intentFilter = doc.getElementsByTag("manifest");
			String iF = intentFilter.toString();
			int front = iF.indexOf("package=")+9;//14 = length of ' android:name=" '
			int back = front;
			if(front != -1)	{
				for( ; back < iF.length() ; back++)
					if(iF.charAt(back) == '"')
						break;
				//System.out.println(front+", "+back);
				PkgName = iF.subSequence(front, back).toString();
			}
		}
	}

	public static ArrayList<String> getShortActivityList() {
		ArrayList<String> shortList = new ArrayList<String>();
		for(String a: ActivityNameList){
			shortList.add(a.subSequence(PkgName.length()+1, a.length()).toString());
		}
		return shortList;
	}
	
	public static ArrayList<String> getActivityList() {
		if(longActivityName())
			return getShortActivityList();
		return ActivityNameList;
	}

	public static String getMainActivity() {
		if(MainActivity.indexOf(getPkgName()) != -1)
			return MainActivity.subSequence(PkgName.length()+1, MainActivity.length()).toString();
		return MainActivity;
	}

	public static String getPkgName() {
		return PkgName;
	}

	public static boolean longActivityName() {
		if (MainActivity.indexOf(getPkgName()) == -1)
			return false;
		return true;
	}
}
