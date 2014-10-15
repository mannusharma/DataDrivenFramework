package Rough;

public class RoughWork {
	
	public static void main(String[] args) {
		
		String s = "(-16.48%)";
		
		String d = s.split("\\(")[1].split("\\%")[0];
		
		System.out.println(d);
		//String f[]= d[1].split("\\%");
		//System.out.println(f[0]);
		
	}

}
