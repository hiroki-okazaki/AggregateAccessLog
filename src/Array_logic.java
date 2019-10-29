import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Array_logic {

	public static void main(String[] args) {
		
		int[] num = { 1, 2, 3, 6, 7, 8, 9, 11, 13 };

		int i = 1;
		Map<Integer, Integer> map = new HashMap<>();
		int start = num[0];
		int count = 1;

		do {

			if ((num[i] - num[i - 1]) == 1) {
				count++;
			} else {
				map.put(start, count);

				start = num[i];
				count = 1;
			}
			i++;
		} while (i < num.length);
		map.put(start, count);

		Set<Integer> set = map.keySet();
		
		for(Integer startNum :set) {
		System.out.println(startNum + "から始まる連続する数は" + map.get(startNum) + "つ");
		}
	}
}
