
public class Main {
		
		public static void main(String argv[]) 
		{
			long time = System.currentTimeMillis();
			Ripper rp = new Ripper();
			if(argv.length == 2){
				rp.run(argv,1,time);
				//(apk file)
			}
			else if(argv.length == 3){
				rp.run(argv,1,time);
				//(apk file , script file)
			}
			else
				System.out.println("Error input value.");
		}
}
