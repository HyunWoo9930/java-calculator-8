package calculator;

import java.util.Arrays;
import java.util.Scanner;

public class Application {
    public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		String input = sc.next();

		// 기본 구분자 확인 (입력값의 처음 시작값이 //로 시작하지 않으면 기본 구분자 or 잘못된 값)
		if(!input.startsWith("//")) {
			String[] nums = input.split(",:");
		}

    }
}
