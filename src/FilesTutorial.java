import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FilesTutorial {

	//コピー元のパス
	private static final String FROM_DIR = "/Users/hirokiokazaki/testfolder";
	//コピー先のパス
	private static final String TO_DIR = "/Users/hirokiokazaki/lesson";
	private static final String TO_DIR2 = "C:\\tmp\\FileCopy2\\";
	
	
	
	
    public static void execute1() throws IOException{
    	Files.walk(Paths.get(FROM_DIR)).forEach(file -> {
    		
    		
    	});
    }
}
