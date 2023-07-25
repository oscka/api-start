package com.osckorea.openmsa.global.util;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

@Component
public class MaskingUtil {
	
	private static final String ENG_NAME_PATTERN = "(?<=.{4}).";						// 이름
	// private static final String EMAIL_PATTERN = "(?<=.{2}).";							// 이메일
	private static final String EMAIL_PATTERN = "([\\w.])(?:[\\w.]*)(@.*)";				// 이메일
	private static final String LAST_6_CHAR_PATTERN = "(.{6}$)";						// 주민번호 & 여권번호
	private static final String PHONE_PATTERN = "(\\d{2,3})(\\d{3,4})(\\d{4})$";		// 전화번호
	private static final String PHONE_PATTERN2 = "^(\\d{3})?(\\d{1,2})\\d{2}?\\d{2}(\\d{2})$";	// 전화번호
	private static final String CARD_PATTERN = "(\\d{4})(\\d{2})\\d{2}(\\d{4})(\\d{4})";		// 카드번호
	private static final String BIZ_PATTERN = "(\\d{3})(\\d{2})(\\d{5})";				// 사업자번호
	private static final String CAR_NUMBER_PATTERN = "([0-9]{4})$";						// 자동차번호
	private static final String ACCOUNT_PATTERN = "(\\d{3})(\\d{2})(\\d{2})\\d{2}(\\d{3})";	// 계좌번호
	private static final String BIRTHDAY_PATTERN = "(\\d{4})(\\d{2})(\\d{2})";	// 생년월일
	
	private MaskingUtil() {
	}

	/**
	 * 
	 * 
	 *<pre>
	 *<b>개인정보 마스킹 처리 </b>
	 *</pre>
	 *
	 * method  : getMasking
	 * 
	 * @param txt 마스킹처리 할 텍스트
	 * @param num 마스킹 타입
	 * 			  1:이름, 2:이메일
	 * 			  , 3:주민번호, 4:전화번호
	 * 			  , 5:카드번호, 6:사업자번호
	 *            , 7:자동차번호, 8:계좌번호
	 * @return  String
	 */
	public static String getMasking(String txt, int num) {
		
		String mask = "";
		String str = "";
		
		if(txt == null || txt.isEmpty()) {
			return txt;
		}
		
	    switch (num) {
			case 1 :	// 이름
				if(txt.matches(".*[ㄱ-ㅎㅏ-ㅣ가-힣]+.*")) {
					mask = maskingName(txt);
				} else {
					mask = txt.replaceAll(ENG_NAME_PATTERN , "*");
				}
				break;
			case 2 :	// 이메일
				mask = txt.split("@")[0].replaceAll(EMAIL_PATTERN, "*") + "@" + txt.split("@")[1];
				break;
			case 3 :	// 주민번호
				mask = txt.replaceAll(LAST_6_CHAR_PATTERN, "******");
				break;
			case 4 :	// 전화번호
				str = txt.replaceAll("-", "");
				mask = str.replaceAll(PHONE_PATTERN2, "$1-$2**-**$3");
				break;
			case 5 :	// 카드 번호
				str = txt.replaceAll("-", "");
				mask = str.replaceAll(CARD_PATTERN, "$1-$2**-****-$4");
				break;
			case 6 :	// 사업자 번호
				str = txt.replaceAll("-", "");
				mask = str.replaceAll(BIZ_PATTERN, "$1-$2-*****");
				break;
			case 7 :	// 자동차 번호
				mask = txt.replaceAll(CAR_NUMBER_PATTERN, "****");
				break;
			case 8 :	// 계좌번호
				str = txt.replaceAll("-", "");
				mask = str.replaceAll(ACCOUNT_PATTERN, "$1-$2-$3**-****");
				break;
			case 9 :
				mask = txt.replaceAll(BIRTHDAY_PATTERN, "****년**월**일");
				break;
			case 10 :
				str = txt.replaceAll("-", "");
				mask = str.replaceAll(PHONE_PATTERN, "$1-****-****");
				break;
			default :
				mask = "";
				break;
		}
		
		return mask;
	}
	
	/**
	 * maskingName
	 *  
	 * @param str
	 * @return String
	 */
	private static String maskingName(String str) {
		String replaceString = str;

		String pattern = "";
		if(str.length() == 2) {
			pattern = "^(.)(.+)$";
		} else {
			pattern = "^(.)(.+)(.)$";
		}

		Matcher matcher = Pattern.compile(pattern).matcher(str);

		if(matcher.matches()) {
			replaceString = "";

			for(int i=1;i<=matcher.groupCount();i++) {
				String replaceTarget = matcher.group(i);
				if(i == 2) {
					char[] c = new char[replaceTarget.length()];
					Arrays.fill(c, '*');

					replaceString = replaceString + String.valueOf(c);
				} else {
					replaceString = replaceString + replaceTarget;
				}

			}
		}
		return replaceString;
	}
	
}
