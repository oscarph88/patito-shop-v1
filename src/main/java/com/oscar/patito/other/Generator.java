package com.oscar.patito.other;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class Generator {

    public static void main(String[] args) {
        StringBuilder sb= new StringBuilder();
        int dominio=0;
        int street=0;
        double old=10000.00;
        double curr=10000.00;
        //String genders[]={"Male", "Female"};
        String s1="{ \"corporateEmail\": \"oscar.padron@patito.com.mx";
        String s2="\",\"firstName\": \"OscarUS\",\"lastName\": \"PadronUS\",\"gender\": \"";
        String s3="\",\"contact\": {\"personalEmail\": \"oscar.padron@personal.com\",\"phoneNumber\": \"34563748576\",\"address\": \"Street number ";
        String s4="\",\"city\": \"matamoros\",\"state\": \"mataulipas\",\"country\": \"mexico\",\"birthday\": \"2012-04-23T18:25:43.511Z\"},";
        String s5="\"positionInfo\": { \"currentPosition\": { \"id\": ";//+1+"  }, \"currentSalary\":";
        String s6="  }, \"currentSalary\":";
        //String s6=",\"currentSalary\":";
        String s7="}},";
        /*String s4="\",\"city\": \"matamoros\",\"state\": \"mataulipas\",\"country\": \"mexico\",\"birthday\": \"2012-04-23T18:25:43.511Z\"},";
        String s5="\"position\": {\"oldPosition\": "+1+", \"currentPosition\": "+2+", \"oldSalary\":";
        String s6=",\"currentSalary\":";
        String s7="}},";*/

        for(int i=0; i<50; i++){
        sb.append(s1+dominio);
        sb.append(s2+getGender());
        sb.append(s3+street);
        sb.append(s4);
        sb.append(s5+getRandomPosition());
        sb.append(s6+curr);
        //sb.append(s6+curr);
        sb.append(s7);
        dominio++;
        street+=5;
        //old+=1000.00;
        curr+=10000.00;
        }

        System.out.println(sb.toString());
        calculeDates2();
    }

    static String getGender(){
        String names[] = { "Male", "Female" , "Male2", "Male3", "Male4"};
        Random r = new Random();
        int n = r.nextInt(names.length);
        System.out.println(names[n]);
        return names[n];
    }

    static int getRandomPosition(){
        int positions[]= {1,2,3,4,5};
        Random r2 = new Random();
        int n = r2.nextInt(positions.length);
        System.out.println(positions[n]);
        return positions[n];
    }

    static String generatePhoneNumber(){
        Random rand = new Random();
        System.out.println( rand.nextInt(4) );
        return "";
    }

    static void calculatedates(){
        Calendar cal = Calendar.getInstance();
        Date now = cal.getTime();
        cal.add(Calendar.DATE, 7);
        Date then = cal.getTime();

        System.out.println(now);
        System.out.println(then);

        Calendar cal2 = Calendar.getInstance();

// "move" cal to monday this week (i understand it this way)
        cal2.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);

// calculate monday week ago (moves cal 7 days back)
        cal2.add(Calendar.DATE, +7);
        Date firstDateOfPreviousWeek = cal2.getTime();

// calculate sunday last week (moves cal 6 days fwd)
        cal2.add(Calendar.DATE, 6);
        Date lastDateOfPreviousWeek = cal2.getTime();

        System.out.println(firstDateOfPreviousWeek);
        System.out.println(lastDateOfPreviousWeek);

    }

    static void calculeDates2(){
        LocalDateTime fiveHoursBefore4 = LocalDateTime.now().with(TemporalAdjusters.next(DayOfWeek.MONDAY)).with(LocalTime.of(0, 0));
        LocalDateTime fiveHoursBefore7 = fiveHoursBefore4.with(TemporalAdjusters.next(DayOfWeek.MONDAY));
        System.out.println(fiveHoursBefore4);
        System.out.println(fiveHoursBefore7);
    }
}
