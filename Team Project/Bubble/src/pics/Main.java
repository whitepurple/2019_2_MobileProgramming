package pics;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("asd");
		MPImage img1 = new MPImage("C:\\Users\\user\\Desktop\\pics\\IMG_2776.JPG");
		
		img1.getExifTagsForDate()
						.entrySet()
						.stream()
						.sorted((i,j)->i.getKey().compareTo(j.getKey()))
						.forEach((e)->{System.out.println(e.getKey()+" : "+e.getValue());});
	
//		for(String s :a) {
//			System.out.println(s); 
//		}
	}

}
