
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class select_customer {
	public static void main(String[] args) {
		LocalDateTime startdate = LocalDateTime.of(2012, 1, 1, 0, 0, 0);
		LocalDateTime enddate = LocalDateTime.of(2012, 4, 1, 0, 0, 0);
		
		try {
			String filepath = "/Users/hirokiokazaki/sample_log.csv";
			
			//filepathに指定されたファイルを読み込み文字列をリストに格納
			ArrayList<String> list = new ArrayList<>();
            loadfile(list,filepath);
			
			List<List<String>> allList = new ArrayList<>();
			// ユーザーID毎のリストを作成
			addList(allList, list);

			StringBuilder corce = new StringBuilder();
			User user = new User();
			List<User> userList = new ArrayList<>();
			
			String[] log ;
			String ABC ;
			LocalDateTime date ;
			String joinOrExit ;

			for (int i = 0; i < allList.size(); i++) {

				for (int j = 0; j < allList.get(i).size(); j++) {
					log = allList.get(i).get(j).split(",", 0);
					ABC = log[1];
					date = toLocalDateTime(log[3], "yyyy-MM-dd HH:mm:ss");
					joinOrExit = log[2];

					//コースが空で2012年1月1日以降に契約した場合(対象外の為addしない)
					if ("".equals(corce.toString()) && date.isAfter(startdate)) {
						user = new User();
						corce = new StringBuilder();
						break;
					}

					// 日付が２０１２年４月１日以前だったら処理を行う
					if (date.isBefore(enddate)) {
						if (joinOrExit.equals("1")) {

							user.setId(Integer.parseInt(log[0]));
							corce.append(ABC);
						} else {// 退会だった場合の処理
							corce.replace(corce.indexOf(ABC), corce.indexOf(ABC) + 1, "");
							}
						}
					// キャンペーン期間でコースが空の場合
					if (date.isAfter(startdate) && date.isBefore(enddate) && "".equals(corce.toString())) {
						user = new User();
						corce = new StringBuilder();
						break;
					} 

				}
				//コースの中身が空でない場合リストに格納する
				if (!corce.toString().isEmpty()) {
					userList.add(user);
				}

				user = new User();
				corce = new StringBuilder();
			}

			//優良顧客を表示
			for (User ss : userList) {
				System.out.println(ss.getId());
			}

		} catch (FileNotFoundException e) {
			System.out.println(e);
		} catch (IOException e1) {
			System.out.println(e1);
		}
	}

	// 引数に入れたString型の文字列をLocalDateTimeに変換する処理を行うメソッド
	public static LocalDateTime toLocalDateTime(String date, String format) {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern(format);
		return LocalDateTime.parse(date, dtf);
	}

	//ユーザー毎にリストに格納するメソッド
	public static void addList(List<List<String>> allList, List<String> list) {
		int preId = Integer.parseInt(list.get(0).split(",", 0)[0]);
		List<String> userList = new ArrayList<>();
		for (String users : list) {
			if (preId == Integer.parseInt(users.split(",", 0)[0])) {
				userList.add(users);
			} else {
				allList.add(userList);
				userList = new ArrayList<String>();
				userList.add(users);
			}
			preId = Integer.parseInt(users.split(",", 0)[0]);
		}
		allList.add(userList);
	}
	
	//filepathに指定されたファイルを読み込み文字列としてリストに格納するメソッド
	public static void loadfile(List<String> list ,String filepath) throws IOException {
		
		File file = new File(filepath);
		FileReader fileReader = new FileReader(file);
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		String data = null;

		while ((data = bufferedReader.readLine()) != null) {
			list.add(data);
		}
	}
	
	//これだったら短いソースコードで読み込める！
	   private List<String> read(String filename) throws IOException {
	        return Files.readAllLines(Paths.get(filename), StandardCharsets.UTF_8);
	    }
}
