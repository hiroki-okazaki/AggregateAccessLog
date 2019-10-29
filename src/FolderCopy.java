import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class FolderCopy {
	
	/**
	 * 使用メソッド
	 * 
	 * searchHtmlFile　指定したディレクトリ以下にあるhtmlファイルを検索
	 * checkJs  htmlファイルの中にJSの構文があるファイルのみを検索
	 * loadFile 受け取ったファイルの中身を文字列(String型に変換)にして返す
	 * extractionJs  htmlファイルの中にある<Script>...</Script>構文のみを全て抽出
	 * copyFolder  フォルダーをコピーする
	 */
	
	public static void main(String[] args) {
		// コピー元のディレクトリ
		File srcFolder = new File("/Users/hirokiokazaki/testfolder");
		// コピー先のディレクトリ
		File destFolder = new File("/Users/hirokiokazaki/lesson");
		// 検索したいファイルの拡張子
		String extension = ".html";

		// 検索したhtmlファイルの絶対パスを詰めるリスト
		List<String> strlist = new ArrayList<>();
		// コピー元のディレクトリにあるhtmlファイルを検索
		searchHtmlFile(srcFolder.toString(), extension, strlist);

		// ファイル型の絶対パスを詰めるリスト
		List<File> filelist = new ArrayList<>();

		// htmlファイルの絶対パスが入ったリストを回し、そのhtmlファイルの中にJS構文が含まれるファイルのみを抽出.
		for (String checkStr : strlist) {
			File checkFile = new File(checkStr);
			// JS構文が含まれるhtmlファイルを検索しリストに格納
			checkJs(checkFile, filelist);
		}

		try {
			// 上記で取り出したfilelist中身をコピー先にコピーする(JSが含まれないファイルは除外)
			for (File file : filelist) {
				copyFolder(file, destFolder);
			}

		} catch (IOException e) {
			e.printStackTrace();
			System.exit(0);
		}
		System.out.println("Done");
	}

	// フォルダーをコピーするメソッド
	public static void copyFolder(File src, File dest) throws IOException {

		// 絶対パスを/区切りで分割し配列に格納
		String files[] = src.toString().split("/");
		for (int i = 4; i < files.length; i++) {

			// 配列のパスの拡張子がhtmlでなかった時に行う処理
			if (!files[i].endsWith(".html")) {
				// コピー先に配列のパス名がなかった時に行う処理
				if (!dest.getName().equals(files[i])) {
					// コピー先の既存のパスに配列のパス名を追加
					// 例 users/hiroki-okazaki/lesson + "/" + ec-ite
					dest = new File(dest + "/" + files[i]);
					// コピー先にディレクトリを作成
					dest.mkdir();
				}

				// 配列のパスの拡張子がhtmlだった時に行う処理
			} else {
				String newFileName = dest + "/" + files[i];
				extractionJs(src, newFileName);
			}
		}
	}

	// htmlのあるファイルを検索
	public static void searchHtmlFile(String path, String extension, List<String> list) {
		// Fileクラスのオブジェクトを生成する
		File dir = new File(path);
		File files[] = dir.listFiles();
		for (int i = 0; i < files.length; i++) {
			String file_name = files[i].getName();
			if (files[i].isDirectory()) { // ディレクトリなら再帰を行う
				searchHtmlFile(path + "/" + file_name, extension, list);
			} else {
				if (file_name.endsWith(extension)) { // file_nameの最後尾(拡張子)が指定のものならば出力
					list.add(path + "/" + file_name);
				}
			}
		}
	}

	// htmlファイルの中でJavaScript(<Script>が使われている)ファイルのみを検索しリストに格納
	public static void checkJs(File src, List<File> list) {
		// ファイルの拡張子がhtmlだった場合に行う処理
		if (src.getName().endsWith(".html")) {
			// htmlファイルの中身を文字列として取り出す.
			String str = loadFile(src);

					// 取り出された文字列の中にJavaScriptの構文が含まれていた時に行う処理
					if (str.contains("<script") && str.contains("</script>")) {
						list.add(src);
			}
		}
	}

	// htmlファイルの中にあるJSの構文だけを取り出すメソッド
	public static void extractionJs(File src, String newFileName) {

		String str = loadFile(src);

		StringBuilder jsStr = new StringBuilder();

		// htmlファイルの中にJSの構文がある時行う繰り返し処理
		while (str.contains("<script")) {
			int beginpoint = str.indexOf("<script");
			int endpoint = str.indexOf("</script>");

			jsStr.append(str.substring(beginpoint, endpoint + 9) + "\n");
			// 取り出したJS構文の場所以降の文字列だけを取り出す.
			str = str.substring(endpoint + 9);
		}

		try {
			// FileWriterクラスのオブジェクトを生成する(引数nameのファイルを作成)
			FileWriter file = new FileWriter(newFileName);
			// PrintWriterクラスのオブジェクトを生成する
			PrintWriter pw = new PrintWriter(new BufferedWriter(file));

			// ファイルに書き込む
			pw.println(jsStr);
			// ファイルを閉じる
			pw.close();

			System.out.println(jsStr);
		} catch (FileNotFoundException e) {
			System.out.println(e);
		} catch (IOException e) {
			System.out.println(e);
		}
	}

	// 受け取ったファイルの中身をString型にして返すメソッド
	public static String loadFile(File file) {
		String str = null;
		try {
			FileReader filereader = new FileReader(file);
			int ch = filereader.read();

			while ((ch = filereader.read()) != -1) {

				str = str + (char) ch;
			}
		} catch (FileNotFoundException e) {
			System.out.println(e);
		} catch (IOException e) {
			System.out.println(e);
		}
		return str;
	}
}
