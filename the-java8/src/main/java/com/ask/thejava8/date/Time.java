package com.ask.thejava8.date;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.Period;
import java.time.Year;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;

/**
 * java.util.Date mutable 하기 때문에 thead safe 하지 않다. type safe 하지 않음
 * JSR-310 스펙을 구현한 Date Time Api 추가 됨
 * Clear, Fluent, Immutable, Extensible
 *
 * 기계용 시간 (machine time)과 인류용 시간(human time)으로 나눌 수 있다.
 * 기계용 시간은 EPOCK (1970년 1월 1일 0시 0분 0초)부터 현재까지의 타임스탬프를 표현한다.
 * 인류용 시간은 우리가 흔히 사용하는 연,월,일,시,분,초 등을 표현한다.
 * 타임스탬프는 Instant 를 사용한다.
 * 특정 날짜(LocalDate), 시간(LocalTime), 일시(LocalDateTime)를 사용할 수 있다.
 * 기간을 표현할 때는 Duration (시간 기반)과 Period (날짜 기반)를 사용할 수 있다.
 * DateTimeFormatter 를 사용해서 일시를 특정한 문자열로 포매팅할 수 있다.
 */
public class Time {

  public static void main(String[] args) {
    Instant now = Instant.now();

    ZonedDateTime fluentZonedDateTime = Year.of(2021)
        .atMonth(10)
        .atDay(15)
        .atTime(2, 4)
        .atZone(ZoneId.systemDefault());

    System.out.println("fluentZonedDateTime = " + fluentZonedDateTime);

    System.out.println("now = " + now); // UTC, GMT
    System.out.println("now.atZone(ZoneId.systemDefault()) = " + now.atZone(ZoneId.systemDefault()));
    // System.out.println("ZoneId.getAvailableZoneIds() = " + ZoneId.getAvailableZoneIds());

    ZonedDateTime zonedDateTime = now.atZone(ZoneId.systemDefault());
    System.out.println("zonedDateTime = " + zonedDateTime);

    // Zone 을 직접 입력
    ZonedDateTime nairobi = ZonedDateTime.now(ZoneId.of("Africa/Nairobi"));
    System.out.println("nairobi = " + nairobi);

    // Local 이기 때문에 현재 실행되는 서버의 Zone 사용
    LocalDateTime localDateTime = LocalDateTime.now();
    System.out.println("localDateTime = " + localDateTime);

    LocalDateTime birthDay = LocalDateTime.of(1994, Month.MAY, 30, 0, 0, 0);
    System.out.println("birthDay = " + birthDay);

    // Period 인류용 시간 비교
    LocalDate today = LocalDate.now();
    LocalDate end = LocalDate.of(2021, Month.DECEMBER, 31);
    Period between = Period.between(today, end);
    System.out.println("between.getYears() = " + between.getYears());
    System.out.println("between.getMonths() = " + between.getMonths());
    System.out.println("between.getDays() = " + between.getDays());

    Period until = today.until(end);
    System.out.println("until.getDays() = " + until.getDays());
    System.out.println("until.get(ChronoUnit.DAYS) = " + until.get(ChronoUnit.DAYS));

    // Duration 기계용 시간 비교
    Instant instant = Instant.now();
    Instant plus = instant.plus(371, ChronoUnit.DAYS);

    Duration duration = Duration.between(instant, plus);
    System.out.println("duration.getSeconds() = " + duration.getSeconds());
    System.out.println("duration.toDays() = " + duration.toDays());

    LocalDateTime ldt = LocalDateTime.of(2021, Month.JANUARY, 1, 0, 0, 0);

    // ChronoUnit 시간 비교
    long daysBetween = ChronoUnit.DAYS.between(ldt, ldt.plusMonths(11));
    System.out.println("daysBetween = " + daysBetween);

    // LocalDateTime 시간 비교
    System.out.println("ldt.plusDays(1) = " + ldt.plusDays(1));
    System.out.println("ldt.plusYears(10) = " + ldt.plusYears(10));
    System.out.println("ldt.until(ldt.plusMonths(11), ChronoUnit.DAYS) = " + ldt.until(ldt.plusMonths(11), ChronoUnit.DAYS));

    // 파싱 또는 포매팅
    LocalDateTime localDateTimeNow = LocalDateTime.now();
    String stringTime = localDateTimeNow.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    System.out.println("stringTime = " + stringTime);

    // YearMonth
    YearMonth yearMonth = YearMonth.now();
    System.out.println("yearMonth = " + yearMonth);
    System.out.println("yearMonth.lengthOfMonth() = " + yearMonth.lengthOfMonth());
    System.out.println("yearMonth.atEndOfMonth() = " + yearMonth.atEndOfMonth());

    // DayOfWeek
    DayOfWeek dayOfWeek1 = DayOfWeek.MONDAY;
    DayOfWeek dayOfWeek2 = LocalDateTime.now().getDayOfWeek();
    System.out.println("dayOfWeek1 = " + dayOfWeek1);
    System.out.println("dayOfWeek2 = " + dayOfWeek2);

    // 요일로 날짜 가져오기
    LocalDate ld = LocalDate.now();
    System.out.println("TemporalAdjusters.lastDayOfMonth()) = " + ld.with(TemporalAdjusters.lastDayOfMonth()));
    System.out.println("TemporalAdjusters.next(DayOfWeek.MONDAY)) = " + ld.with(TemporalAdjusters.next(DayOfWeek.MONDAY)));
    System.out.println("TemporalAdjusters.dayOfWeekInMonth(2, DayOfWeek.FRIDAY)) = " + ld.with(TemporalAdjusters.dayOfWeekInMonth(2, DayOfWeek.FRIDAY)));
    System.out.println("TemporalAdjusters.firstInMonth(DayOfWeek.THURSDAY)) = " + ld.with(TemporalAdjusters.firstInMonth(DayOfWeek.THURSDAY)));
  }
}
