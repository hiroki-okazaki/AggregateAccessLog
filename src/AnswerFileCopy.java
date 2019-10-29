import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class AnswerFileCopy {

	private static final String FROM_DIR = "C:\\IntelliJ_work\\";
	private static final String TO_DIR = "C:\\tmp\\FileCopy\\";
	private static final String TO_DIR2 = "C:\\tmp\\FileCopy2\\";

	private static final Pattern pattern = Pattern.compile("<script.+?</script>", Pattern.DOTALL);

	public static void main(String[] args) {
		try {
			AnswerFileCopy fileCopy = new AnswerFileCopy();
			fileCopy.execute2();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void execute2() throws IOException {
		Files.walk(Paths.get(FROM_DIR)).forEach(file -> {
			String absolutePathStr = file.toString();
			String workStr = absolutePathStr.replace(FROM_DIR, "");
			if (workStr.indexOf("\\") >= 0) {
				String projectName = workStr.substring(0, workStr.indexOf("\\"));
				if (workStr.indexOf(projectName + "\\src") >= 0 && workStr.endsWith("html")) {
					try {
						String text = Files.lines(file, StandardCharsets.UTF_8)
								.collect(Collectors.joining(System.getProperty("line.separator")));
						text = filterOnlyScript(text);
						if (!"".equals(text)) {
							Path targetPath = Paths.get(TO_DIR2 + workStr);
							Files.createDirectories(targetPath.getParent());
//                            Files.copy(file, targetPath, StandardCopyOption.REPLACE_EXISTING);
							// 抽出した文字列でコピー先にファイルを作成
							List<String> outputList = new ArrayList<>();
							outputList.add(text);
							Path r = Files.write(targetPath, outputList, StandardOpenOption.TRUNCATE_EXISTING);
						}
					} catch (IOException e) {
						e.printStackTrace();
					}

				}
			}
		});
	}

	private String filterOnlyScript(String text) {
		StringBuilder sb = new StringBuilder();
		Matcher matcher = pattern.matcher(text);
		while (matcher.find()) {
			sb.append(matcher.group() + System.getProperty("line.separator"));
		}
		return sb.toString();
	}

	public void execute1() throws IOException {
		Files.walk(Paths.get(FROM_DIR)).forEach(file -> {
			String absolutePathStr = file.toString();
			String workStr = absolutePathStr.replace(FROM_DIR, "");
			if (workStr.indexOf("\\") >= 0) {
				String projectName = workStr.substring(0, workStr.indexOf("\\"));
				if (workStr.indexOf(projectName + "\\src") >= 0
						&& (workStr.endsWith("application.yml") || workStr.endsWith("application.properties"))) {
					Path targetPath = Paths.get(TO_DIR + projectName + "_" + file.getFileName());
					try {
						Files.copy(file, targetPath);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		});
	}
}