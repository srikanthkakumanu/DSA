package basics;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.StringJoiner;
import java.util.TimeZone;

/**
 * Date represents a specific instant in time with millisecond precision. All modern OS assume that 1 day = 24 * 60 * 60 = 86400 seconds.
 * 
 * There are two important time systems named Coordinated Universal Time (UTC) and Greenwhich Mean Time (GMT). Universal Time (UT) is 
 * scientific name for GMT (civil name). UT1 is enhanced time system version of UT.
 * 
 * In UTC, once in every year or two (Jun 30 and Dec 31), an extra second is added and it is called leap second. The leap second is always 
 * added as the last second of the day (always on Dec 31 or Jun 30).
 * 
 * Distinction between GMT (UT) and UTC is that UTC is based on atomic clock and GMT (UT) is based on astronomical observations (Because the 
 * earth’s rotation is not uniform. It slows down and speeds up in complicated ways, Therefore UT does not always flow uniformly).
 * 
 * Hence Leap seconds are introduced in UTC to keep UTC within 0.9 seconds of UT1. But the time scale used by the satellite-based global 
 * positioning system (GPS) is synchronised to UTC but is not adjusted for leap seconds.
 * 
 * Java 8’s new date-time API is thread-safe and immutable
 * 
 * Java 8’s new date-time API is introduced to overcome the following drawbacks of old date-time API : 
 *                                                                    --------- 
 * Not thread safe : Unlike old java.util.Date which is not thread safe the new date-time API is immutable and doesn’t have setter methods.
 * Less operations : In old API there are only few date operations but the new API provides us with many date operations.
 * Java 8 under the package java.time introduced a new date-time API, most important classes among them are :  
 * 
 * Local : Simplified date-time API with no complexity of timezone handling.
 * Zoned : Specialised date-time API to deal with various time zones.
 * The major improvement/change in new date time API (from Java 8) is that the new date time is no longer represented by no. of milliseconds 
 * since Jan 1st, 1970. But it is now represented by no. of seconds and nano seconds since Jan 1st, 1970 where the no. of seconds can be 
 * both positive and negative represented by long and no. of nano seconds is always positive represented by an int. However Java 7 date time 
 * API is represented by no. of milliseconds since Jan 1st, 1970.

 * Usage and Approaches
 * ====================
 * Wherever possible, applications should use LocalDate, LocalTime and LocalDateTime to better model the domain. Any use of a time-zone, 
 * such as ‘Europe/Paris’ adds considerable complexity to a calculation. Many applications can be written only using LocalDate, LocalTime, 
 * Instant and the time-zone is added at user interface (UI) layer.
 * 
 * Multiple calendar systems is an awkward addition to the design challenges. The first principle is that most users want the standard ISO 
 * calendar system. As such, the main classes are ISO-only. The second principle is that most of those that want a non-ISO calendar system 
 * want it for user interaction, thus it is a UI localization issue. As such, date and time objects should be held as ISO objects in the 
 * data model and persistent storage, only being converted to and from a local calendar for display. The calendar system would be stored 
 * separately in the user preferences.
 * 
 * Packages and Classes Information
 * ---------------------------------
 * Java 8
 * ------
 * Below components are packages.
 * 
 * java.time =	API for dates, times, instants and duration’s.
 * java.time.chrono = API for chronological calendar systems (non ISO i.e. not the default ISO-8601 calendar systems) such as Japanese, Thai, Taiwanese and Islamic calendars.
 * java.time.format = It is used to parse and format dates, times from and to strings.
 * java.time.temporal = Access to date and time using fields and units, and date time adjusters.
 * java.time.zone = Supports time zones.
 * 
 * Below listed components are classes.
 * 
 * Instant	= An instant in time on the time line i.e. a numeric timestamp. In Java 7 date time API an instant was represented by a no. of milliseconds since Jan. 1st. 1970. In Java 8, it represents no. of seconds and a no. of nanoseconds since Jan. 1st 1970. It is the closest equivalent to java.util.Date.
 * Duration = It deals with time based amount of time. A duration of time i.e. time between two instants. It is represented by a number of seconds and nanoseconds.
 * Period = It deals with date based amount of time. An amount of time in units meaningful to humans, such as years or days.
 * LocalDate = A date without timezone information.
 * LocalDateTime = A date and time without timezone information.
 * LocalTime = A local time of day without time zone information.
 * ZonedDateTime = A date and time including time zone information. It is the closest equivalent to java.util.GregorianCalendar.
 * ZoneId = Represents time zones and it is equivalent to java.util.TimeZone class.
 * DateTimeFormatter = Formats date time objects as strings.
 * OffsetDateTime = They are intended primarily for use with network protocols and database access. For example, most databases cannot 
 *                  automatically store a time-zone like ‘Europe/Paris’, but they can store an offset like ‘+02:00’.
 * OffsetTime = They are intended primarily for use with network protocols and database access. For example, most databases cannot 
 *              automatically store a time-zone like ‘Europe/Paris’, but they can store an offset like ‘+02:00’.
 * TemporalAdjuster = 	
 * 
 * Java 7
 * ------
 * System.currentTimeMillis() = Returns the current date and time as long value (timestamp) in milliseconds since January 1st 1970. 
 *                              It is not the worlds most precise or fine grained timer.
 * java.util.Date = Represents a date and time but most methods are deprecated.
 * java.sql.Date = Represents just a date without time information.
 * java.sql.Timestamp = Represents a date and time. It is a thin wrapper around java.util.Date that allows the JDBC API to identify 
 *                      this as an SQL TIMESTAMP value. It is a composite of a java.util.Date and a separate nanoseconds value. 
 *                      Only integral seconds are stored in the java.util.Date component. The fractional seconds - the nanos - are separate.
 * java.sql.Time = A thin wrapper around the java.util.Date that allows the JDBC API to identify this as an SQL TIME value. 
 * java.util.GregorianCalendar = Represents Gregorian calendar which is used in most of the western world today. It is a sub class of an 
 *                              abstract class java.util.Calendar. 
 *                              Important to Remember:
 *                              Months starts from 0 i.e. January is 0 and December is 11.
                                First day of week is SUNDAY and not MONDAY i.e. 1 = SUNDAY, 2 = MONDAY, …, 7 = SATURDAY.
 * java.util.TimeZone = Represents time zones, and is helpful when doing calendar arithmetic’s across time zones.
 * java.text.SimpleDateFormat = Used to parse strings to dates and vise versa.
 * 
 * Different date time formats
 * ---------------------------
 * yyyy-MM-dd           (2009-12-31)
 * dd-MM-YYYY           (31-12-2009)
 * yyyy-MM-dd HH:mm:ss  (2009-12-31 23:59:59)
 * HH:mm:ss.SSS         (23:59.59.999)
 * yyyy-MM-dd HH:mm:ss.SSS   (2009-12-31 23:59:59.999)
 * yyyy-MM-dd HH:mm:ss.SSS Z   (2009-12-31 23:59:59.999 +0100)
 *  
 * This below class demonstrates usage of Java 7 date time and new Date Time API of Java 8.
*/

public class DatenTime {
    public static void main(String[] args) { 
        // java7();
        java8();
    }

    /**
     * Usage of Java 7's date time classes.
     */
    private static void java7() {
        System.out.println("\nJAVA 7 APPROACH");
        System.out.println("-----------------");
        // long value (timestamp) returns in milliseconds since Jan 1st, 1970.
        // Granualarity is larger than 1 and it is not the world's most precise or fine grained timer.
        long start = System.currentTimeMillis();
        System.out.print("System.currentTimeMillis: ");
        long end = System.currentTimeMillis();
        long total = end - start;
        StringJoiner joiner = new StringJoiner(",", "[", "]");
        joiner.add("start=" + start).add("end=" + end).add("total=" + total);
        System.out.println(joiner.toString());
        Date udate = new Date(System.currentTimeMillis());
        GregorianCalendar gcal = new GregorianCalendar();
        java.sql.Date sdate = new java.sql.Date(System.currentTimeMillis());
        gcal.setTimeInMillis(System.currentTimeMillis());
        System.out.println("Util Date: " + udate + " , Sql Date: " + sdate + " , Gregorian Calendar Date: " + gcal.getTime());

        // timestamp
        Timestamp stamp = new Timestamp(System.currentTimeMillis());
        System.out.println("Timestamp without nanos: " + stamp);
        stamp.setNanos(123456);
        System.out.println("Timestamp with nanos: " + stamp);
        stamp.setTime(040606);
        System.out.println("Timestamp with custom time + nanos: " + stamp);

        // Calendar + GregorianCalendar
        GregorianCalendar calendar = new GregorianCalendar();
        int year       = calendar.get(Calendar.YEAR);
        int month      = calendar.get(Calendar.MONTH); 
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH); // Jan = 0, not 1
        int dayOfWeek  = calendar.get(Calendar.DAY_OF_WEEK);
        int weekOfYear = calendar.get(Calendar.WEEK_OF_YEAR);
        int weekOfMonth= calendar.get(Calendar.WEEK_OF_MONTH);
        int dayOfWeekInMonth = calendar.get(Calendar.DAY_OF_WEEK_IN_MONTH);

        int hour       = calendar.get(Calendar.HOUR);        // 12 hour clock
        int hourOfDay  = calendar.get(Calendar.HOUR_OF_DAY); // 24 hour clock
        int minute     = calendar.get(Calendar.MINUTE);
        int second     = calendar.get(Calendar.SECOND);
        int millisecond= calendar.get(Calendar.MILLISECOND);
        StringJoiner cjoiner = new StringJoiner(",", "[", "]");
        cjoiner.add("YEAR=" + year).add("MONTH=" + month).add("DAY_OF_MONTH=" + dayOfMonth)
            .add("DAY_OF_WEEK=" + dayOfWeek).add("WEEK_OF_YEAR=" + weekOfYear).add("WEEK_OF_MONTH=" + weekOfMonth)
            .add("DAY_OF_WEEK_IN_MONTH=" + dayOfWeekInMonth).add("HOUR=" + hour).add("HOUR_OF_DAY=" + hourOfDay)
            .add("MINUTE=" + minute).add("SECOND=" + second).add("MILLISECOND=" + millisecond);
        System.out.println("Gregorian Calendar Properties: " + cjoiner.toString());

        Calendar old_calendar = new GregorianCalendar();
        // set date to last day of 2009
        old_calendar.set(Calendar.YEAR, 2001);
        old_calendar.set(Calendar.MONTH, 11); // 11 = december
        old_calendar.set(Calendar.DAY_OF_MONTH, 31); // new years eve
        // add one day
        old_calendar.add(Calendar.DAY_OF_MONTH, 1);
        // date is now jan. 1st 2002
        year = old_calendar.get(Calendar.YEAR); // now 2002
        month = old_calendar.get(Calendar.MONTH); // now 0 (Jan = 0)
        dayOfMonth = old_calendar.get(Calendar.DAY_OF_MONTH); // now 1
        StringJoiner custom_cal_joiner = new StringJoiner(",", "[", "]");
        custom_cal_joiner.add("YEAR=" + year).add("MONTH=" + month).add("DAY_OF_MONTH=" + dayOfMonth)
            .add("DAY_OF_WEEK=" + dayOfWeek).add("WEEK_OF_YEAR=" + weekOfYear).add("WEEK_OF_MONTH=" + weekOfMonth)
            .add("DAY_OF_WEEK_IN_MONTH=" + dayOfWeekInMonth).add("HOUR=" + hour).add("HOUR_OF_DAY=" + hourOfDay)
            .add("MINUTE=" + minute).add("SECOND=" + second).add("MILLISECOND=" + millisecond);
        System.out.println("Custom Gregorian Calendar Properties: " + custom_cal_joiner.toString());

        // Time zones
        //TimeZone tz = gcal.getTimeZone(); // time zone can extracted from calendar
        //TimeZone tzone = TimeZone.getDefault(); // get current time zone where this prog. runs 
        TimeZone tzone = TimeZone.getTimeZone("America/Los_Angeles"); // get specific time zone

        StringJoiner idJoiner = new StringJoiner(",", "Available IDs=[", "]");
        Arrays.asList(TimeZone.getAvailableIDs()).forEach(id -> idJoiner.add(id));

        String tid = tzone.getID();
        String tname = tzone.getDisplayName();
        var offset = tzone.getOffset(System.currentTimeMillis());
        StringJoiner tzjoiner = new StringJoiner(",", "[", "]");
        tzjoiner.add("ID=" + tid).add("NAME=" + tname).add("OFFSET=" + offset).add(idJoiner.toString());
        System.out.println("Timezone Info: " + tzjoiner.toString());

        // Convert between time zones
        TimeZone la_tz = TimeZone.getTimeZone("America/Los_Angeles");
        TimeZone pr_tz = TimeZone.getTimeZone("Europe/Paris");
        calendar.setTimeZone(la_tz); // calender set to LA timezone
        System.out.println("Current LA Time: " + calendar.getTimeInMillis() + " and HOUR_OF_DAY: " + calendar.get(Calendar.HOUR_OF_DAY));
        calendar.setTimeZone(pr_tz); // calendar set to Paris timezone
        System.out.println("Current Paris Time: " + calendar.getTimeInMillis() + " and HOUR_OF_DAY: " + calendar.get(Calendar.HOUR_OF_DAY));

        // date time formatting
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS Z");
        String dateToText = sdf.format(udate);
        try {
            Date textToDate = sdf.parse(dateToText);
            System.out.println("DATE_TO_TEXT=" + dateToText + " and TEXT_TO_DATE=" + textToDate);
        } catch (ParseException e) { System.err.println("Exception caught while parsing text to date" + e.getMessage()); }
    }

    /**
     * Usage of Java 8's date time classes.
     */
    private static void java8() {
        System.out.println("\nJAVA 8 APPROACH");
        System.out.println("-----------------");
       
        // Different ways to convert from old date time to new date time API
        // from date
        LocalDateTime new_ldt = LocalDateTime.ofInstant(new Date().toInstant(), ZoneId.systemDefault());
        // from calendar
        new_ldt = LocalDateTime.ofInstant(new GregorianCalendar().toInstant(), ZoneId.systemDefault());
        // from epoch seconds
        new_ldt = LocalDateTime.ofEpochSecond(1465817690, 0, ZoneOffset.UTC); // 2016-06-13T11:34:50
        System.out.println("Convert Old API to New Date & Time API : " + new_ldt);

        // Instant: represents an instant in time on the timeline
        // Internally it contains two fields: seconds since the epoch and nanoseconds
        Instant inst = Instant.now();
        System.out.println(inst.toString());
        System.out.println("Epoch Second: " + inst.getEpochSecond());
        System.out.println("Nano Second: " + inst.getNano());
        System.out.println("Adding Seconds: " + inst.plusSeconds(4).toString());
        System.out.println("Subtract Seconds: " + inst.minusSeconds(2).toString());

        // Duration: represents period of time between two Instant objects
        // or It represents quanity of time in terms of seconds and nanoseconds.
        Instant now = Instant.now();
        Instant later = now.plusSeconds(3*60);
        Duration duration = Duration.between(now, later);
        System.out.println("Duration(now, later)= " + duration.toString());
        System.out.println("Convert whole duration to Nanos: " + duration.toNanos());
        System.out.println("Convert whole duration to Milliseconds: " + duration.toMillis());
        System.out.println("Convert whole duration to Minutes: " + duration.toMinutes());
        System.out.println("Convert whole duration to Days: " + duration.toDays());
        System.out.println("Duration + Days : " + duration.plusDays(2));
        LocalTime stime = LocalTime.of(4, 39, 02);
        LocalTime etime = stime.plus(Duration.ofSeconds(39));
        System.out.println("Duration (stime-etime): " + Duration.between(stime, etime).getSeconds());
        System.out.println("Duration (stime-etime) using ChronoUnit: " + ChronoUnit.SECONDS.between(stime, etime));

        // Period: represents quantity of time in terms of years, months and days.
        LocalDate start = LocalDate.parse("2005-12-31");
        LocalDate end = start.plus(Period.ofDays(5));
        System.out.println("Period in INT result: " + Period.between(start, end).getDays());
        System.out.println("Period in LONG result: " + ChronoUnit.DAYS.between(start, end));

        // local date: represents local date without time and zone info
        LocalDate ldate = LocalDate.now(); // current date. simple way
        ldate = LocalDate.of(2010, 12, 31); // custom date
        ldate = LocalDate.parse("2005-12-31"); // custom date in ISO format (yyyy-MM-dd)
        StringJoiner ldateJoiner = new StringJoiner(",", "[", "]");
        ldateJoiner.add("YEAR=" + ldate.getYear()).add("MONTH=" + ldate.getMonth()).add("DAY_OF_MONTH=" + ldate.getDayOfMonth())
                    .add("DAY_OF_YEAR=" + ldate.getDayOfYear()).add("DAY_OF_WEEK=" + ldate.getDayOfWeek());
        System.out.println("Local Date Info: " + ldateJoiner.toString());
        ldate = ldate.plusYears(3);
        ldate = ldate.minusYears(2); 
        System.out.println("LocalDate = " + ldate);
        ldate = LocalDate.now().minus(1, ChronoUnit.MONTHS);
        System.out.println("Previous Month and Same Day: " + ldate);

        // local time: represents specific time in a day without time zone info
        LocalTime ltime = LocalTime.now(); // current time. simple way
        ltime = LocalTime.of(21, 31, 58, 11001); // custom time
        ltime = LocalTime.parse("06:30").plus(1, ChronoUnit.HOURS); // custom time alternative approach

        StringJoiner ltimeJoiner = new StringJoiner(",", "[", "]");
        ltimeJoiner.add("HOUR=" + ltime.getHour()).add("MINUTE=" + ltime.getMinute()).add("SECONDS=" + ltime.getSecond())
                    .add("NANOSECONDS=" + ltime.getNano());
        System.out.println("Local Time Info: " + ltimeJoiner.toString());
        ltime = ltime.plusHours(3);
        ltime = ltime.plusMinutes(2);
        ltime = ltime.plusNanos(1100);
        ltime = ltime.minusHours(1); 
        System.out.println("LocalTime = " + ltime);

        // Local date and time: represents local date and time without time zone info
        LocalDateTime ldt = LocalDateTime.now(); // current date & time. simple way
        ldt = LocalDateTime.of(2015, 11, 24, 13, 55, 36, 123); // custom date & time
        ldt = LocalDateTime.parse("2015-02-20T06:30:00"); // custom date & time alternative approach
        ldt = ldt.minusYears(2);
        System.out.println("LocalDateTime= " + ldt.toString());

        // zoned date time: represents date and time with time zone info
        ZonedDateTime zdt = ZonedDateTime.now(); // current zoned date & time. simple way

        ZoneId zid = ZoneId.of("UTC+1"); // time zone with ZoneId.
        zid = ZoneId.of("Europe/Paris"); // alternative way. time zone with ZoneId.
        zdt = ZonedDateTime.of(2005, 11, 20, 21, 45, 22, 1234, zid); // custom way using ZoneId
        zdt = ZonedDateTime.parse("2015-05-03T10:15:30+01:00[Europe/Paris]"); // custom way alternative approach
        // Be aware: Calculations that span across the daylight savings changes (start or end) may not give the result 
        // we expect! Therefore an alternative is to use a Period instance.
        zdt = zdt.plus(Period.ofDays(3));
        System.out.println("ZonedDateTime = " + zdt);


        // date time formatting
        DateTimeFormatter dtf = DateTimeFormatter.BASIC_ISO_DATE;
        String dateToText = dtf.format(LocalDate.now());
        System.out.println("BASIC_ISO_DATE = " + dateToText);        
    }
}