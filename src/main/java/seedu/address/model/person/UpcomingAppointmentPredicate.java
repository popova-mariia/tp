package seedu.address.model.person;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.function.Predicate;

/**
 * Tests if a Person has an appointment after the current system time.
 */
public class UpcomingAppointmentPredicate implements Predicate<Person> {

    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    @Override
    public boolean test(Person person) {
        try {
            String appointmentValue = person.getAppointmentDate().value.trim();

            // Try parsing with full datetime first
            if (appointmentValue.contains(":")) {
                LocalDateTime appointmentDateTime = LocalDateTime.parse(appointmentValue, DATE_TIME_FORMAT);
                return appointmentDateTime.isAfter(LocalDateTime.now());
            } else {
                // If only date is provided, treat the time as 23:59
                LocalDate appointmentDate = LocalDate.parse(appointmentValue, DATE_FORMAT);
                LocalDateTime endOfDay = appointmentDate.atTime(23, 59);
                return endOfDay.isAfter(LocalDateTime.now());
            }
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof UpcomingAppointmentPredicate;
    }
}

