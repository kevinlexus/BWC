package com.ric.bill;

import java.io.File;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.UUID;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import lombok.extern.slf4j.Slf4j;

/**
 * Утилиты
 * @author lev
 *
 */
@Slf4j
public class Utl {

	
	/**
	 * Аналог LTRIM в Oracle
	 * @param str - исходная строка
	 * @return - усеченная слева строка
	 */
	public static String ltrim (String str) {
		return str.replaceFirst("^0+", "");
	}
	
	/**
	 * сравнить два параметра, с учётом их возможного null 
	 * @param a - 1 значение
	 * @param b - 2 значение
	 * @return
	 */
	public static <T> boolean cmp(T a, T b) {
		if (a == null && b == null) {
			return true;
		} else if (a != null && b != null) {
			if (a.getClass() == BigDecimal.class) {
				BigDecimal bd = (BigDecimal) a;
				if (bd.compareTo((BigDecimal) b) == 0) {
					return true;
				} else {
					return false;
				}
			} else {
				if (a.equals(b)) {
					return true;
				} else {
					return false;
				}
			}
		} else {
			return false;
		}
	}
	
	/**
	 * Вернуть второе значение, если первое пусто (аналог oracle NVL)
	 * @param a - 1 значение
	 * @param b - 2 значение
	 * @return
	 */
	public static <T> T nvl(T a, T b) {
		return (a == null) ? b : a;
	}


	/**
	 * Вернуть, если дата находится в диапазоне периода
	 * @param checkDt - проверяемая дата
	 * @param dt1 - начало периода
	 * @param dt2 - окончание периода
	 * @return
	 */
	public static boolean between(Date checkDt, Date dt1, Date dt2) {
		if (dt1 == null) {
			dt1 = getFirstDt();
		}
		if (dt2 == null) {
			dt2 = getLastDt();
		}
		
		if (checkDt.getTime() >= dt1.getTime() &&
				checkDt.getTime() <= dt2.getTime()) {
			return true;
		} else {
			return false;
		}
	}
	
	// вернуть самую первую дату в биллинге
	public static Date getFirstDt() {
		Calendar calendar;
		calendar = new GregorianCalendar(1940, Calendar.JANUARY, 1);
		calendar.clear(Calendar.ZONE_OFFSET);
		return calendar.getTime();
	}

	// вернуть самую последнюю дату в биллинге
	public static Date getLastDt() {
		Calendar calendar;
		calendar = new GregorianCalendar(2940, Calendar.JANUARY, 1);
		calendar.clear(Calendar.ZONE_OFFSET);
		return calendar.getTime();
	}

	/**
	 * вернуть true если хотя бы одна из дат находится в двух диапазонах периода
	 * @param checkDt1 - проверяемая дата
	 * @param checkDt2 - проверяемая дата
	 * @param dt1 - начало периода
	 * @param dt2 - окончание периода
	 * @return
	 */
	public static boolean between2(Date checkDt1, Date checkDt2, Date dt1, Date dt2) {
		return between(checkDt1, dt1, dt2) || between(checkDt2, dt1, dt2);
	}
	
	//вернуть кол-во лет между датами
	public static int getDiffYears(Date first, Date last) {
	    Calendar a = getCalendar(first);
	    Calendar b = getCalendar(last);
	    int diff = b.get(Calendar.YEAR) - a.get(Calendar.YEAR);
	    if (a.get(Calendar.MONTH) > b.get(Calendar.MONTH) || 
	        (a.get(Calendar.MONTH) == b.get(Calendar.MONTH) && a.get(Calendar.DATE) > b.get(Calendar.DATE))) {
	        diff--;
	    }
	    return diff;
	}

	/**
	 * Вернуть объект Calendar по заданной дате 
	 * @param date
	 * @return
	 */
	public static Calendar getCalendar(Date date) {
	    Calendar cal = Calendar.getInstance(Locale.US);
	    cal.setTime(date);
	    return cal;
	}	
	
	//вернуть случайный UUID
	public static UUID getRndUuid() {
		return UUID.randomUUID();
	}
	
	/**
	 * Вернуть дату в XML типе
	 * @param dt
	 * @return
	 * @throws DatatypeConfigurationException
	 */
	public static XMLGregorianCalendar getXMLDate(Date dt) throws DatatypeConfigurationException {
		GregorianCalendar c = new GregorianCalendar();
		c.setTime(dt);
		XMLGregorianCalendar xmlDt = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
		return xmlDt;
	}
	
	/**
	 * Вернуть хост из строки URL
	 * @param urlStr - URL
	 * @return хост-адрес
	 * @throws UnknownHostException
	 * @throws MalformedURLException
	 */
	public static String getHostFromUrl(String urlStr) throws UnknownHostException, MalformedURLException {
		
		InetAddress address = InetAddress.getByName(new URL(urlStr).getHost());
		
		return address.getHostAddress();
	}
	
	/**
	 * Вернуть путь из строки URL
	 * @param urlStr - URL
	 * @return хост-адрес
	 * @throws UnknownHostException
	 * @throws MalformedURLException
	 */
	public static String getPathFromUrl(String urlStr) throws UnknownHostException, MalformedURLException {
		
		return new URL(urlStr).getPath();
	}
	
	/**
	 * Вернуть последнюю дату месяца 
	 * @param dt
	 * @return
	 */
	public static Date getLastDate(Date dt) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(dt);
		calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));
		return calendar.getTime();
	}

	/**
	 * Вернуть первую дату месяца 
	 * @param dt
	 * @return
	 */
	public static Date getFirstDate(Date dt) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(dt);
		calendar.set(Calendar.DATE, calendar.getActualMinimum(Calendar.DATE));
		return calendar.getTime();
	}

	
	/**
	 * Вернуть дату по формату
	 * @param dt
	 * @return
	 */
	public static Date getDateFromStr(String dt) {
		
		SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
		Date date = null;
        try {
            date = formatter.parse(dt);

        } catch (ParseException e) {
            e.printStackTrace();
        }
		return date;
	}

	/**
	 * Вернуть строку по формату
	 * @param dt
	 * @return
	 */
	public static String getStrFromDate(Date dt) {
		
		SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
		String str = formatter.format(dt);
		return str;
	}

	/**
	 * Конвертировать XMLGregorianCalendar в Date
	 * @param cal
	 * @return
	 */
	public static Date getDateFromXmlGregCal(XMLGregorianCalendar cal) {
		if (cal != null) { 
			return cal.toGregorianCalendar().getTime();
		} else {
			return null;
		}
	}
	
	/**
	 * Получить % одного дня для заданного периода
	 * @return - % дня
	 */
	public static double getPartDays(Date dt1, Date dt2) {
		Calendar cal1 = new GregorianCalendar();
		Calendar cal2 = new GregorianCalendar();
		cal1.setTime(dt1);
		cal2.setTime(dt2);
		return 1 / (double)daysBetween(cal1.getTime(),cal2.getTime());
	}
	
	/**
	 * Вернуть кол-во дней между двумя датами
	 * @param dt1 - нач.дата
	 * @param dt2 - кон.дата
	 * @return - кол-во дней
	 */
	public static int daysBetween(Date dt1, Date dt2){
       return (int)( (dt2.getTime() - dt1.getTime()) / (1000 * 60 * 60 * 24)+1);
	}
	
	/**
	 * Конвертировать дату в ГГГГММ
	 * @param dt - дата вх.
	 * @return
	 */
	public static String getPeriodByDate(Date dt) {
		Calendar calendar = new GregorianCalendar();
		calendar.clear(Calendar.ZONE_OFFSET);
		calendar.setTime(dt);
		String yy = String.valueOf(calendar.get(Calendar.YEAR));
		String mm = String.valueOf(calendar.get(Calendar.MONTH)+1);
		mm = "0"+mm;
		mm = mm.substring(mm.length()-2, mm.length());
		return yy+mm;
	}

	/**
	 * Конвертировать период ГГГГММ в дату
	 * @param period
	 * @return
	 */
	public static Date getDateByPeriod(String period) {
		String str = "01"+"."+period.substring(4, 6)+"."+period.substring(0, 4);
		return getDateFromStr(str);
	}
	
	/**
	 * Конвертировать период ГГГГММ в наименование периода типа Апрель 2017, со склонением
	 * @param period
	 * @param tp - 0 - нач.период, 1 - кон.период  
	 * @return
	 */
	public static String getPeriodName(String period, Integer tp) {
		String str = getMonthName(Integer.valueOf(period.substring(4, 6)), tp) +" "+period.substring(0, 4);
		return str;
	}

	public static String getMonthName(Integer month, Integer tp) {
		String monthString;
		if (tp==0) {
			switch (month) {
			case 1:  monthString = "Января";
	        break;
			case 2:  monthString = "Февраля";
			        break;
			case 3:  monthString = "Марта";
			        break;
			case 4:  monthString = "Апреля";
			        break;
			case 5:  monthString = "Мая";
			        break;
			case 6:  monthString = "Июня";
			        break;
			case 7:  monthString = "Июля";
			        break;
			case 8:  monthString = "Августа";
			        break;
			case 9:  monthString = "Сентября";
			        break;
			case 10: monthString = "Октября";
			        break;
			case 11: monthString = "Ноября";
			        break;
			case 12: monthString = "Декабря";
			        break;
			default: monthString = null;
			        break;
			}
		} else {
			switch (month) {
			case 1:  monthString = "Январь";
	        break;
			case 2:  monthString = "Февраль";
			        break;
			case 3:  monthString = "Март";
			        break;
			case 4:  monthString = "Апрель";
			        break;
			case 5:  monthString = "Май";
			        break;
			case 6:  monthString = "Июнь";
			        break;
			case 7:  monthString = "Июль";
			        break;
			case 8:  monthString = "Август";
			        break;
			case 9:  monthString = "Сентябрь";
			        break;
			case 10: monthString = "Октябрь";
			        break;
			case 11: monthString = "Ноябрь";
			        break;
			case 12: monthString = "Декабрь";
			        break;
			default: monthString = null;
			        break;
			}
		}
		return monthString;
	}
	
	/**
	 * Получить кол-во дней в месяце по дате
	 * @param dt - дата вх.
	 * @return
	 */
	public static double getCntDaysByDate(Date dt) {
		Calendar calendar = new GregorianCalendar();
		calendar.clear(Calendar.ZONE_OFFSET);
		calendar.setTime(dt);
		return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
	}
	
	/**
	 * Получить в виде ГГГГММ месяц + - N мес.
	 * @param period
	 */
	public static String addMonth(String period, int n) {
		Date dt = getDateByPeriod(period);
		Calendar calendar = new GregorianCalendar();
		calendar.clear(Calendar.ZONE_OFFSET);
		calendar.setTime(dt);
		calendar.add(Calendar.MONTH, n);
		return getPeriodByDate(calendar.getTime());
	}
	
	
	/**
	 * Добавить или отнять N дней к дате
	 * @param dt - базовая дата
	 * @param nDays - кол-во дней + -
	 * @return
	 */
	public static Date addDays(Date dt, int nDays) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dt);
        calendar.add(Calendar.DAY_OF_YEAR, nDays);
        return calendar.getTime();
    }
	
	/**
	 * Добавить путь в classpath
	 * @param s
	 * @throws Exception
	 */
	public static void addPath(String s) throws Exception {
	    File f = new File(s);
	    URI u = f.toURI();
	    URLClassLoader urlClassLoader = (URLClassLoader) ClassLoader.getSystemClassLoader();
	    Class<URLClassLoader> urlClass = URLClassLoader.class;
	    Method method = urlClass.getDeclaredMethod("addURL", new Class[]{URL.class});
	    method.setAccessible(true);
	    method.invoke(urlClassLoader, new Object[]{u.toURL()});
	}
	

}

