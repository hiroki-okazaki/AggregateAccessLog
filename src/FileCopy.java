import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FileCopy {

	public static void main(String[] args) throws Exception {

		String src = "/Users/hirokiokazaki/testfolder";
		
		File newdir = new File("/Users/hirokiokazaki/lesson");
		newdir.mkdir();

		String extension = ".yml"; // 検索したいファイルの拡張子

		//拡張子が.ymlであるファイルがある絶対パスを詰めるリスト
		List<String> strlist = new ArrayList<>();
		// src:コピー元のパス　extension:拡張子　strlist:リスト検索でたどり着いたymlファイルの絶対パスのリスト
		file_search(src, ".yml", strlist);
		file_search(src, ".properties", strlist);
		
		
		
		for (String str : strlist) {
			try {
				String[] bits = str.split("/");
				Path sourcePath = Paths.get(str);
				Path targetPath = Paths.get(newdir.toString() + "/" + bits[4] + "_(" + bits[bits.length - 2] + ")" + bits[bits.length - 1]);
				
				Files.copy(sourcePath, targetPath);
				System.out.println("コピーが成功しました");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	
	//ファイルを検索するメソッド
	public static void file_search(String path, String extension, List<String> list) {
		// Fileクラスのオブジェクトを生成する
		File dir = new File(path);
		// ディレクトリ直下にあるファイルを全て取り出し配列に詰める
		File files[] = dir.listFiles();
		
		for (int i = 0; i < files.length; i++) {
			String file_name = files[i].getName();
			
			if (files[i].isDirectory()) { // ディレクトリなら再帰を行う
				file_search(path + "/" + file_name, extension, list);
				
			} else {
				if (file_name.endsWith(extension)) { // file_nameの最後尾(拡張子)が指定のものならば出力
					System.out.println(path + "/" + file_name);
					list.add(path + "/" + file_name);
				}
			}

		}
	}
}
