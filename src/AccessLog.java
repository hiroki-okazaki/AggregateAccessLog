import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class AccessLog {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		try{
		
		File file = new File("/Users/hirokiokazaki/アクセスログ.txt");
		FileReader filereader = new FileReader(file);
		
		int ch = filereader.read();
		String str = null;
		
		while((ch = filereader.read()) != -1){
//		    System.out.print((char)ch);
		    
		    str = str + (char)ch;
		    
		  }
		
		String afterstr = str.replace("2012/04/07","");
	
 		String removestr = afterstr.replace("null時	ページ	応答時間(ms)","");
 		String fixstr = removestr.substring(3);
 		fixstr = fixstr.replace("\r", "").replace("\n", "");
 		
 		String[] strList = fixstr.split(" ");
 		
// 		for(String sss : strList) {
// 			System.out.println(sss);
// 		}
 		
 		
 		//時間をリストに格納
		 ArrayList<String> timeList = new ArrayList<>();
		 for(int i = 0;i < strList.length; i ++) {
			 String str2 = "2012/04/07" + " " + strList[i].substring(0, 8);
			 timeList.add(str2);
		 }
		 
//	 		for(String date :timeList) {
//			System.out.println(date);
//		}
//	 		System.out.println(timeList.size());
	 
	 		
	 		ArrayList<LocalDateTime> dateTimeList = new ArrayList<>();
	 		 for(int i = 0;i < timeList.size(); i ++) {
	 			LocalDateTime dtf = toLocalDateTime(timeList.get(i), "yyyy/MM/dd HH:mm:ss");
	 			dateTimeList.add(dtf);
			 }

//		 		for(LocalDateTime date :dateTimeList) {
//		 			System.out.println(date);
//		 		}
	 		 
			
	 		 //平均応答時間を算出するのに使うデータをリストに格納
	 		ArrayList<String> avaragelist = new ArrayList<>();
	 		for(int i = 0;i < strList.length; i ++) {
//	 			System.out.println(strList[i].length());
	 			if(strList[i].length() == 19) {
	 				avaragelist.add(strList[i].substring(strList[i].length() - 2));
	 			}else if(strList[i].length() == 21) {
	 				avaragelist.add(strList[i].substring(strList[i].length() - 4));
	 			}else if(strList[i].length() == 18){
	 				avaragelist.add(strList[i].substring(strList[i].length() - 1));
	 			}else {
	 			avaragelist.add(strList[i].substring(strList[i].length() - 3));
			 }
	 		}
	 		
	 		//[Integer型]平均応答時間を算出するのに使うデータをリストに格納
	 		ArrayList<Integer> avarageIntlist = new ArrayList<>();
	 		for(int i = 0;i < avaragelist.size(); i ++) {
	 			avarageIntlist.add(Integer.parseInt(avaragelist.get(i)));
	 		}
	 		
//	 		for(Integer ava :avarageIntlist) {
//	 			System.out.println(ava);
//	 		}
	 		
	 		 
	 		 List<GetterSetter> hogeList = new ArrayList<>();
	 		 
 	 		 LocalDateTime preTime = LocalDateTime.of(2012,04,07,0,05,0);
 	 		 GetterSetter hoge = new GetterSetter();
 	 		 hoge.setLessthan500(0);
 	 		 hoge.setLessthan2000(0);
 	 		 hoge.setMorethan2001(0);
 	 		 hoge.setAvarage(0);
 	 		 hoge.setDateTime(LocalDateTime.of(2012,04,07,0,0,0));
 	 		 
	 		 for(int i = 0; i < dateTimeList.size();i ++) {

	 			 
	 			 if(dateTimeList.get(i).isBefore(preTime)){
//	 				System.out.println(dateTimeList.get(i) + "  / " + i);
	 				 
	 				
	 				 if(avarageIntlist.get(i) <= 500 ) {//500以下の場合にオブジェクトに格納
	 					

	 					hoge.setAvarage(hoge.getAvarage() + avarageIntlist.get(i));
	 					hoge.setLessthan500(hoge.getLessthan500() + 1);
	 					
	 				 }else if(avarageIntlist.get(i) <= 2000) {//2000以下の場合にオブジェクトに格納
	 

		 					hoge.setAvarage(hoge.getAvarage() + avarageIntlist.get(i));
		 					hoge.setLessthan2000(hoge.getLessthan2000() + 1);
	 					 
	 				 }else {//2000以上の場合にオブジェクトに格納
	 					hoge.setAvarage(hoge.getAvarage() + avarageIntlist.get(i));
	 					hoge.setMorethan2001(hoge.getMorethan2001() + 1);
	 				 }
	 				  
	 			 }else {
	 				preTime = preTime.plusMinutes(5);
	 				hogeList.add(hoge);
	 				hoge = new GetterSetter();
	 				
	 		 		 hoge.setLessthan500(0);
	 	 	 		 hoge.setLessthan2000(0);
	 	 	 		 hoge.setMorethan2001(0);
	 	 	 		hoge.setAvarage(0);
	 				hoge.setDateTime(preTime.plusMinutes(-5));
//	 				System.out.println(dateTimeList.get(i) + "  / " + i);
	 				i -- ;
	 				
	 			 }
	 		
	 		 }
	 		hogeList.add(hoge);
	 		hoge = new GetterSetter();
	 		hoge.setDateTime(hogeList.get(hogeList.size() - 1).getDateTime());
	 		
	 		LocalDateTime lastTime = LocalDateTime.of(2012,04,07,23,55,0);
	 		
//	 		System.out.println(hoge.getDateTime() + "////" + preTime);
	 		if(hoge.getDateTime().isBefore(lastTime)) {
	 			for( hoge.getDateTime(); hoge.getDateTime().isBefore(lastTime) ;) {
	 				hoge.setDateTime(hoge.getDateTime().plusMinutes(5));
	 		 		 hoge.setLessthan500(0);
	 	 	 		 hoge.setLessthan2000(0);
	 	 	 		 hoge.setMorethan2001(0);
	 	 	 		hoge.setAvarage(0);
	 	 	 		hogeList.add(hoge);
	 			}
	 		}
	 		 
	 		 
	 		 
	 		System.out.println("時刻    	               500ms以下	       2000ms以下	      2001ms以上	       平均応答時間（ms）");
	 		for(GetterSetter list : hogeList){
	 			
	 			
	 			
	 			System.out.print(list.getDateTime());
	 			System.out.print("       ");
	 			System.out.print(list.getLessthan500());
	 			System.out.print("                       ");
	 			System.out.print(list.getLessthan2000());
	 			System.out.print("                          ");
	 			System.out.print(list.getMorethan2001());
	 			System.out.print("                            ");
	 			System.out.print(list.avarage());
	 			
	 			System.out.println();
	 		}
		 
		
		filereader.close();
	}catch(FileNotFoundException e) {
		System.out.println(e);
	}catch(IOException e){
		  System.out.println(e);
	}

}
	
	 public static LocalDateTime toLocalDateTime(String date, String format) {

	        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(format);
	        return LocalDateTime.parse(date, dtf);
	    }
}