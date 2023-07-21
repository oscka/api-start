package com.osckorea.openmsa.global.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import org.springframework.stereotype.Component;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Component
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DateUtil {

	
	/**
	 * <pre>]
     * 
	 *<b>현재일자 조회 </b>
	 * </pre>
	 *
	 * method : getDateNow
	 * 
	 * @param obj
	 *            Model Object
	 * @return String 날짜
	 * 
	 * yyyyMMdd, yyyy-MM-dd HH:mm:ss 
	 */
	public static String getDateNow(String formatter) {

		LocalDate currentDate = LocalDate.now(); 
		return currentDate.format(DateTimeFormatter.ofPattern(formatter));
	}
	
	/**
	 * <pre>
	 *<b>일자 ADD </b>
	 * </pre>
	 *
	 * method : getDateNow
	 * 
	 * @param obj
	 *            Model Object
	 * @return String 날짜
	 * 
	 * yyyyMMdd, yyyy-MM-dd HH:mm:ss 
	 */
	public static String getAddDay(String date, long addDay, String formatter) {

		LocalDate currentDate = LocalDate.parse(date,DateTimeFormatter.ofPattern(formatter)); 
		
		LocalDate returnvalue  =  currentDate.plusDays(addDay);
		
		return returnvalue.format(DateTimeFormatter.ofPattern(formatter));
	}
	
	/**
	 * <pre>
	 *<b>일자 ADD </b>
	 * </pre>
	 *
	 * method : getDateNow
	 * 
	 * @param obj
	 *            Model Object
	 * @return String 날짜
	 * 
	 * yyyyMMdd, yyyy-MM-dd HH:mm:ss 
	 */
	public static String getAddDay(String date, long addDay) {
		
		LocalDate currentDate = LocalDate.parse(date,DateTimeFormatter.ofPattern("yyyyMMdd")); 
		
		LocalDate returnvalue  =  currentDate.plusDays(addDay);
		
		return returnvalue.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
	}
	
	/**
	 * <pre>
	 *<b>WEEK ADD </b>
	 * </pre>
	 *
	 * method : getDateNow
	 * 
	 * @param obj
	 *            Model Object
	 * @return String 날짜
	 * 
	 * yyyyMMdd, yyyy-MM-dd HH:mm:ss 
	 */
	public static String getAddWeek(String date, long addDay, String formatter) {

		LocalDate currentDate = LocalDate.parse(date,DateTimeFormatter.ofPattern(formatter)); 
		
		LocalDate returnvalue  =  currentDate.plusWeeks(addDay);
		
		return returnvalue.format(DateTimeFormatter.ofPattern(formatter));
	}
	
	/**
	 * <pre>
	 *<b>WEEK ADD </b>
	 * </pre>
	 *
	 * method : getDateNow
	 * 
	 * @param obj
	 *            Model Object
	 * @return String 날짜
	 * 
	 * yyyyMMdd, yyyy-MM-dd HH:mm:ss 
	 */
	public static String getAddWeek(String date, long addWeek) {

		LocalDate currentDate = LocalDate.parse(date,DateTimeFormatter.ofPattern("yyyyMMdd")); 
		
		LocalDate returnvalue  =  currentDate.plusWeeks(addWeek);
		
		return returnvalue.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
	}
	
	/**
	 * <pre>
	 *<b>MONTH ADD </b>
	 * </pre>
	 *
	 * method : getDateNow
	 * 
	 * @param obj
	 *            Model Object
	 * @return String 날짜
	 * 
	 * yyyyMMdd, yyyy-MM-dd HH:mm:ss 
	 */
	public static String getAddMonth(String date, long addMonth, String formatter) {

		LocalDate currentDate = LocalDate.parse(date,DateTimeFormatter.ofPattern(formatter)); 
		
		LocalDate returnvalue  =  currentDate.plusMonths(addMonth);
		
		return returnvalue.format(DateTimeFormatter.ofPattern(formatter));
	}
	
	/**
	 * <pre>
	 *<b>MONTH ADD </b>
	 * </pre>
	 *
	 * method : getDateNow
	 * 
	 * @param obj
	 *            Model Object
	 * @return String 날짜
	 * 
	 * yyyyMMdd, yyyy-MM-dd HH:mm:ss 
	 */
	public static String getAddMonth(String date, long addMonth) {

		LocalDate currentDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyyMMdd")); 
		
		LocalDate returnvalue  =  currentDate.plusMonths(addMonth);
		
		return returnvalue.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
	}
	
	
	/**
	 * <pre>
	 *<b>isDateValidateFormat </b>
	 * </pre>
	 *
	 * method : date, format
	 * 
	 * @param obj
	 *            Model Object
	 * @return String 날짜
	 * 
	 * yyyyMMdd, yyyy-MM-dd HH:mm:ss 
	 */
	public static Boolean isDateValidateFormat(String date, String formatter) {
		
		DateFormat sdf = new SimpleDateFormat(formatter,Locale.KOREA);
        sdf.setLenient(false);
        try {
            sdf.parse(date);
        } catch (ParseException e) {
            return false;
        }
        return true;
		
		
	}
	
	

}