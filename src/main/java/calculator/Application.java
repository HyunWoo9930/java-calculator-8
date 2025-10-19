package calculator;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

import camp.nextstep.edu.missionutils.Console;

public class Application {
	public static void main(String[] args) {
		String input = Console.readLine();

		// 요구사항 명세에 따라 빈 문자열 또는 null 입력 시 0을 출력하고 종료합니다.
		if (input == null || input.isEmpty()) {
			System.out.println(0);
			return;
		}

		try {
			// 기본 구분자 확인 (입력값의 처음 시작값이 //로 시작하지 않으면 기본 구분자 or 잘못된 값)
			if (!input.startsWith("//")) {
				String[] nums = input.split("[,:]");
				int sum = 0;
				for (String num : nums) {
					sum += parseAndValidateNumber(num);
				}

				System.out.println(sum);
			} else {
				Map<String, String> map = getCustomSeperator(input);
				// 커스텀 구분자가 정규식의 특수문자일 경우를 대비하여 quote 처리합니다.
				String separator = Pattern.quote(map.get("seperator"));
				String[] nums = map.get("input").split(separator);
				int sum = 0;
				for (String num : nums) {
					sum += parseAndValidateNumber(num);
				}

				System.out.println(sum);
			}
		} catch (IllegalArgumentException e) {
			System.err.println("오류 원인: " + e.getMessage());
			throw new IllegalArgumentException("잘못된 값을 입력하였습니다.");
		}
	}

	/**
	 * 문자열을 정수로 변환하되, 유효성 검사를 수행합니다.
	 * @param numStr 변환할 숫자 문자열
	 * @return 변환된 양의 정수
	 * @throws IllegalArgumentException 음수 또는 숫자가 아닌 문자가 포함된 경우
	 */
	private static int parseAndValidateNumber(String numStr) {
		// split의 결과로 빈 문자열이 생성될 수 있습니다. (예: "1,,2")
		// 이는 유효하지 않은 값으로 간주합니다.
		if (numStr == null || numStr.trim().isEmpty()) {
			throw new IllegalArgumentException("입력값에 빈 문자열이 포함되어 있습니다.");
		}

		int number;
		try {
			number = Integer.parseInt(numStr);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("입력값에 숫자가 아닌 문자('" + numStr + "')가 포함되어 있습니다.");
		}

		if (number < 0) {
			throw new IllegalArgumentException("음수('" + number + "')는 계산할 수 없습니다.");
		}

		return number;
	}

	/**
	 * 커스텀 구분자 형식의 문자열을 파싱하여 구분자와 숫자 부분으로 분리합니다.
	 * @param input 전체 입력 문자열
	 * @return 구분자(seperator)와 숫자 문자열(input)을 담은 Map
	 * @throws IllegalArgumentException 커스텀 구분자 형식이 잘못된 경우
	 */
	public static Map<String, String> getCustomSeperator(String input) {
		try {
			Map<String, String> map = new HashMap<>();
			StringTokenizer st = new StringTokenizer(input, "\n");

			String separatorPart = st.nextToken().replaceAll("//", "");
			if (separatorPart.isEmpty()) {
				throw new IllegalArgumentException("커스텀 구분자가 비어있습니다.");
			}
			map.put("seperator", separatorPart);

			if (!st.hasMoreTokens()) {
				 throw new IllegalArgumentException("구분자만 명시되고 숫자 부분은 비어있습니다.");
			}
			map.put("input", st.nextToken());
			return map;
		} catch (NoSuchElementException e) {
			// StringTokenizer에서 nextToken() 호출 시 토큰이 더 이상 없는 경우 발생
			throw new IllegalArgumentException("커스텀 구분자 형식이 잘못되었습니다. (//구분자\n숫자 형식)");
		}
	}
}
