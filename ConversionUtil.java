/**
 * 文件名称:ConversionUtil.java
 * 作者姓名:邓珑
 * 创建时间:2007-10-01 下午02:18:35
 * 完成时间:2007-10-13 下午02:18:35
 * 修改人员:邓珑
 * 修改时间:2007-11-03 下午02:50:55
 * 修改原因:
 * 当前版本:1.0.0 Alpha
 * 未 完 成:TODO
 */
package com.ccue.dispatch.util;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


/**
 * Conversion Util (类型转换工具类)
 * fph
 */
@SuppressWarnings("serial")
public class ConversionUtil<T extends Object> implements Serializable{
	public static final String DEFAULT_DATE_MONTH="yyyy-MM";
	
	public static final String DEFAULT_DATE_PATTERN="yyyy-MM-dd";
	
	public static final String DEFAULT_DATETIME_PATTERN="yyyy-MM-dd HH:mm:ss";
	
	public static final String DATETIME_EXACT_PATTERN="yyyy-MM-dd HH:mm:ss.SSS";
	
	public static final String DATETIME_WHOLE_PATTERN="yyyy-MM-dd HH:mm:ss.SSSZ";
	
	/**
	 * 把 java.util.Date 转换成字符串形式
	 * 
	 * @param date(java.util.Date)
	 * @return (String)
	 */
	public String date2String(Date date){
		return date2String(date,null);
	}
	
	/**
	 * 把 java.util.Date 转换成字符串形式
	 * 
	 * @param date(java.util.Date)
	 * @param pattern(String)
	 * @return (String)
	 */
	public static String date2String(Date date,String pattern){
		if(date == null){
			return null;
		}
		if(pattern==null || pattern.equals("")){
			pattern=DEFAULT_DATETIME_PATTERN;
		}
		SimpleDateFormat format=new SimpleDateFormat(pattern);
		return format.format(date);
	}
	
	/**
	 * 把日期字符串转换成 java.util.Date 
	 * 
	 * @param dateString(String)
	 * @return (java.util.Date)
	 */
	public static Date string2Date(String dateString){
		if(dateString == null){
			return null;
		}
		int length = dateString.length();
		if(length > 10){
			return string2Date(dateString,DEFAULT_DATETIME_PATTERN);
		}
		return string2Date(dateString,null);
	}
	
	/**
	 * 把日期字符串转换成 java.util.Date 
	 * 
	 * @param dateString(String)
	 * @param pattern(String)
	 * @return (java.util.Date)
	 */
	public static Date string2Date(String dateString,String pattern){
		if(dateString == null){
			return null;
		}
		if(pattern==null || pattern.equals("")){
			pattern=DEFAULT_DATE_PATTERN;
		}
		SimpleDateFormat format=new SimpleDateFormat(pattern);
		Date result=null;
		try {
			result = format.parse(dateString);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 把日期字符串转换成 java.sql.Timestamp 
	 * 
	 * @param dateString(String)
	 * @return (java.sql.Timestamp)
	 */
	public java.sql.Timestamp string2Timestamp(String dateString){
		if(dateString == null){
			return null;
		}
		
		java.sql.Timestamp result = null;
		try{
			result = java.sql.Timestamp.valueOf(date2String(string2Date(dateString)));
		}catch(IllegalArgumentException e){
			System.err.println(e.getMessage() + "Need date string."); 
		}
		return result;
	}
	
	//获取今天的日期
	public static String getYesterday(){
		Calendar   cal   =   Calendar.getInstance(); 
		cal.add(Calendar.DATE,0); 
		String   yesterday   =   new   SimpleDateFormat(DEFAULT_DATE_PATTERN).format(cal.getTime());
		return yesterday;
	}
	//获取本月
	public static String getMonth(){
		Calendar   cal   =   Calendar.getInstance(); 
		cal.add(Calendar.DATE,   0); 
		String   yesterday   =   new   SimpleDateFormat(DEFAULT_DATE_MONTH).format(cal.getTime());
		return yesterday;
	}
	
	//获取下月
	public static String getNextMonth(){
		Times tt = new Times();
//		tt.getNextMonthEnd();
//		Calendar   cal   =   Calendar.getInstance(); 
//		cal.add(Calendar.DATE,   0); 
		String   yesterday   =   new   SimpleDateFormat(DEFAULT_DATE_MONTH).format(string2Date(tt.getNextMonthEnd()));
		//System.out.println("yesterday"+yesterday);
		return yesterday;
	}
	
	
	
	public static String getTimeStamp(){
		return date2String(new Date(),DATETIME_EXACT_PATTERN);
	}
	
	public static void main(String args[]){
		System.out.println(getTimeStamp());
	}
	
	
	/**
	 * 
	 * @param date
	 * @return
	 * @throws ParseException
	 * 根据日期得到这周的日期
	 */
	public static String[] getStringDate(String date) throws ParseException{
		String[] weeks = new String[7];//返回的这周的日期
		String[] a = date.split("-");
		int week = getDayOfWeek(a[0], a[1], a[2]);//获取周几
		int minWeek = 0;
		int maxWeek = 7;
		String format = "yyyy-MM-dd";
		
		if(week == 1){//如果是周日（老外是从周日开始算一周，所以有点恶心）
			weeks[6] = date;
			for(int i = 5; i >= 0; i--){
				weeks[i] = getFormatDateAdd(getStrToDate(date, format), -1, format);
				date = weeks[i];
			}
		}else{
			int temp = week - 2;
			weeks[temp] = date;
			for(int i = temp - 1; i >= minWeek; i--){
				weeks[i] = getFormatDateAdd(getStrToDate(date, format), -1, format);
				date = weeks[i];
			}
			date = weeks[temp];
			for(int i = temp + 1; i < maxWeek; i++){
				weeks[i] = getFormatDateAdd(getStrToDate(date, format), 1, format);
				date = weeks[i];
			}
		}
		
		return weeks;
	}
	/**
	 * 根据指定的年、月、日返回当前是星期几。1表示星期天、2表示星期一、7表示星期六。
	 * 
	 * @param year
	 * @param month
	 *            month是从1开始的12结束
	 * @param day
	 * @return 返回一个代表当期日期是星期几的数字。1表示星期天、2表示星期一、7表示星期六。
	 */
	public static int getDayOfWeek(String year, String month, String day) {
		Calendar cal = new GregorianCalendar(new Integer(year).intValue(),
				new Integer(month).intValue() - 1, new Integer(day).intValue());
		return cal.get(Calendar.DAY_OF_WEEK);
	}

	/**
	 * 取得给定日期加上一定天数后的日期对象.
	 * 
	 * @param date
	 *            给定的日期对象
	 * @param amount
	 *            需要添加的天数，如果是向前的天数，使用负数就可以.
	 * @param format
	 *            输出格式.
	 * @return Date 加上一定天数以后的Date对象.
	 */
	public static String getFormatDateAdd(Date date, int amount, String format) {
		Calendar cal = new GregorianCalendar();
		cal.setTime(date);
		cal.add(GregorianCalendar.DATE, amount);
		return getFormatDateTime(cal.getTime(), format);
	}
	
	/**
	 * 根据给定的格式与时间(Date类型的)，返回时间字符串。最为通用。<br>
	 * 
	 * @param date
	 *            指定的日期
	 * @param format
	 *            日期格式字符串
	 * @return String 指定格式的日期字符串.
	 */
	public static String getFormatDateTime(Date date, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}
	
	/**
	 * 返回制定日期字符串的date格式
	 * 
	 * @param date
	 * @param format
	 * @return
	 * @throws ParseException
	 */
	public static Date getStrToDate(String date, String format) throws ParseException{
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.parse(date);
	}
	
	
	  /*-------------------------
    该函数用于对数字字符串进行转换
    String number:数字字符串
    Return：转换完后的字符串
    --------------------------*/
    public static String connersion(String number)
    {
       char[] temp=number.toCharArray();
       try {
           for(int i=0;i<temp.length;i++)
           {
               switch(temp[i])
               {
               case '0':temp[i]='零';break;
               case '1':temp[i]='壹';break;
               case '2':temp[i]='贰';break;
               case '3':temp[i]='叁';break;
               case '4':temp[i]='肆';break;
               case '5':temp[i]='伍';break;
               case '6':temp[i]='陆';break;
               case '7':temp[i]='柒';break;
               case '8':temp[i]='捌';break;
               case '9':temp[i]='玖';break;
               }
           }
       } catch (Exception ex) {
            return null;
       }
       return new String(temp);
    }
}
