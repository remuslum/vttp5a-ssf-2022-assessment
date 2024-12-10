package sg.nus.edu.iss.vttp5a_ssf_2022_assessment.model;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class LocalDateConverter {
    public LocalDateConverter(){

    }

    public LocalDate convert(long epoch){
        return Instant.ofEpochSecond(epoch).atZone(ZoneId.of("UTC")).toLocalDate();
    }

    public String convertStringDateToLong(String date){
        LocalDateTime localDateTime = LocalDateTime.parse(date + " 00:00:00",
        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        ZoneId zoneId = ZoneId.of("UTC");
        long epochMilli = localDateTime.atZone(zoneId).toEpochSecond();
        return String.valueOf(epochMilli);
    }
}
