package calculator;

import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import camp.nextstep.edu.missionutils.Console;

public class Application {
	public static void main(String[] args) {
		String input = Console.readLine();

		try {
			// 기본 구분자 확인 (입력값의 처음 시작값이 //로 시작하지 않으면 기본 구분자 or 잘못된 값)
			if (!input.startsWith("//")) {
				String[] nums = input.split("[,:]");
				int sum = 0;
				for (String num : nums) {
					sum += Integer.parseInt(num);
				}

				System.out.println(sum);
			} else {
				Map<String, String> map = getCustomSeperator(input);
				String[] nums = map.get("input").split(map.get("seperator"));
				int sum = 0;
				for (String num : nums) {
					sum += Integer.parseInt(num);
				}

				System.out.println(sum);
			}
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException("잘못된 값을 입력하였습니다.");
		}
	}

	public static Map<String, String> getCustomSeperator(String input) {
		Map<String, String> map = new HashMap<>();
		StringTokenizer st = new StringTokenizer(input, "\\n");
		map.put("seperator", st.nextToken().replaceAll("//", ""));
		map.put("input", st.nextToken());
		return map;
	}
}