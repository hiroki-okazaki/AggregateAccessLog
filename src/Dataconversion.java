import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


//　tsvファイルから入手したデータを各従業員ごとに振り分けtxtファイルを出力する処理を行うクラス.
public class Dataconversion {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		try {

			//ファイル情報を入手しBufferedReaderに入れる
			File file = new File("/Users/hirokiokazaki/data.tsv");
			FileReader filereader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(filereader);

			
			//bufferedReader.readLine()で入手したデータを1行ずつリストに格納
			String data = null;
			ArrayList<String> dataList = new ArrayList<>();
			while ((data = bufferedReader.readLine()) != null) {
                dataList.add(data);
            }
			
			
			//誤った出退勤データの要素番号が入ったリスト
			ArrayList<Integer> deleteList = new ArrayList<>();
			//map<従業員名, "出勤"or"退勤" > 
			Map<String,String> map = new HashMap<>();
			//idMap<従業員名,　直前にkeyに入れた従業員名が入ったリストの番号>
			Map<String,Integer> idMap = new HashMap<>();
			
			
			for(int i = 0;i < dataList.size(); i ++) {
				String name =dataList.get(i).substring(17,19);
				String work =dataList.get(i).substring(20,22);
				
				String checkWork = map.get(name);
				
				//checkWork("出勤"、"退勤")がnullの場合はmapに格納
				if(checkWork == null) {
					map.put(name, work);
					idMap.put(name, i);
				}else if(work.equals(checkWork)) {//checkWork("出勤"、"退勤")がnullの場合はmapに格納
					
							//2回連続出勤、もしくは退勤した際の処理
					        //"出勤"(もしくは"退勤")が2回連続になるのは誤ったデータであるため、誤ったデータが見つかった場合
					        //"出勤"　→　1回目、　"退勤" →　2回目のデータを削除する
							if("出勤".equals(work)) {//mapに"出勤"が入っており、今回も"出勤"を取ってきた場合
								
								//前回"出勤"を入れたリスト番号をdeleteList(削除リスト)に格納
								deleteList.add(idMap.get(name));
								map.put(name, work);
								idMap.put(name, i);
							}else{//mapに"退勤"が入っており、今回も"退勤"を取ってきた場合
								
								//今回"退勤"を入れたリスト番号をdeleteList(削除リスト)に格納
								deleteList.add(i);
								map.put(name, work);
								idMap.put(name, i);
							}
						
					}else {//前回と今回で出退勤が異なる場合
							map.put(name, work);
							idMap.put(name, i);
					
				}
				
			}
			
			
			//誤った出勤、退勤のデータを削除(今回の場合、鈴木、佐藤が該当)
			for(int i = deleteList.size() -1;i >= 0; i --)  {
				int delete = deleteList.get(i);
				dataList.remove(delete);
			}
			
	 		//時間をリストに格納(String型)
			 ArrayList<String> strTimeList = new ArrayList<>();
			 for(int i = 0;i < dataList.size(); i ++) {
				 String str2 = dataList.get(i).substring(0, 16);
				 strTimeList.add(str2);
			 }
			 
			 //String型で格納したstrTimeListをLocalDateTimeに変換してリストに格納
			 ArrayList<LocalDateTime> timeList = new ArrayList<>();
	 		 for(int i = 0;i < strTimeList.size(); i ++) {
	 			LocalDateTime dtf = toLocalDateTime(strTimeList.get(i), "yyyy/MM/dd HH:mm");
	 			timeList.add(dtf);
			 }
			
	 		//mapに入った全従業員名をリストに格納
			Set<String> employeeNameList = map.keySet();

			List<Employee> employeeTimeList = new ArrayList<>();
			Employee employee = new Employee();

			//mapに入った従業員名のリストをfor文で回す
			for (String employeeName : employeeNameList) {

				//出退勤リストをfor文でまわす
				for (int i = 0; i < dataList.size(); i++) {
					String name = dataList.get(i).substring(17, 19);

					//mapに入った名前と出退勤リストから取り出した名前が一致した時の処理
					if (employeeName.equals(name)) {

						//もしスタートタイムがnullだった場合、出勤情報を従業員オブジェクトにセット
						if (employee.getStartTime() == null) {
							employee.setName(employeeName);
							employee.setDateTime(timeList.get(i));
							employee.setStartTime(timeList.get(i));
							
						}else {//スタートタイムがnullではない場合、出勤情報は入力されているので退勤情報を入力し
							   //従業員リスト(employeeList)にセット。最後に従業員オブジェクトの中身をリセットする.
							
							employee.setEndTime(timeList.get(i));
							employee.setMinute(
									ChronoUnit.MINUTES.between(employee.getStartTime(),employee.getEndTime()));
							employeeTimeList.add(employee);
							
							employee = new Employee();

						} 
							

					}

				}
			}


			//中身のデータを記述するようのStringBuilderクラス
			StringBuilder text = new StringBuilder();

			//mapに入っている従業員名をfor文で回す
			for (String employeeName : employeeNameList) {
				
				//各従業員の出退勤情報リスト
				for (Employee emp : employeeTimeList) {

					//mapに入れた名前と各従業員の出退勤情報リストの名前が一致した時の処理
					//
					if(employeeName.equals(emp.getName())) {
						int year = emp.getDateTime().getYear();
						int month = emp.getDateTime().getMonthValue();
						int day = emp.getDateTime().getDayOfMonth();

						
						//出退勤の年、日、時分をStringBuilder型のtxtに追加
						text.append(year + "/"  + month + "/" + day + " " );
   					    text.append(emp.getStartTime().toLocalTime() + " ");	
						text.append(emp.getEndTime().toLocalTime()+ " ");
						text.append(emp.getMinute());					
						text.append("\n");
					}
					
				}
				//一人の名前の繰り返し処理が終わった段階でtxtファイルを作成.出力後txtを初期化
				streamTest4(employeeName,text.toString());
				text = new StringBuilder();
			}
			
			
			
			//処理終了
			filereader.close();
		} catch (FileNotFoundException e) {
			System.out.println(e);
		} catch (IOException e) {
			System.out.println(e);
		}
	}
	
	//txtファイルに出力するためのメソッド.
	//name = 従業員名  各従業員名のファイルを作成
	//txt = 出勤データ  引数で受け取った文字列をファイルに入力
	public static void streamTest4(String name, String txt) {
		
		    try{
		    	
		      FileWriter fw = new FileWriter("/Users/hirokiokazaki/" + name + ".txt", false);  //※１
	          PrintWriter pw = new PrintWriter(new BufferedWriter(fw));
		      
	          pw.println("日付 出勤 退勤 分");
	          pw.println(txt);

	            pw.close();
	            System.out.println("出力が完了しました。");

		    }catch(IOException e){
		      System.out.println(e);
		    }
		  }

	//引数に入れたString型の文字列をLocalDateTimeに変換する処理を行うメソッド
	public static LocalDateTime toLocalDateTime(String date, String format) {

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(format);
        return LocalDateTime.parse(date, dtf);
    }
}

