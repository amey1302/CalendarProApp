
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.time.LocalDate;
import java.util.stream.Collectors;

class Event {
    LocalDateTime dateTime;
    String eventName;
    LocalDate startDate;
    LocalDate endDate;
    LocalTime startTime;
    LocalTime endTime;
    String location;
    String notes;
    List<String> tags;

    public Event(String eventName, LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime,
                 String location, String notes, List<String> tags) {
        this.eventName = eventName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.location = location;
        this.notes = notes;
        this.tags = tags;
        this.dateTime = LocalDateTime.of(startDate, startTime);

    }

    public String getEventName() {
        return eventName;
    }

    public String getNotes() {
        return notes;
    }
}

class Reminder {
    Event event;
    int minutesBefore;

    public Reminder(Event event, int minutesBefore) {
        this.event = event;
        this.minutesBefore = minutesBefore;
    }
}

public class EventsHandler {

    static List<Event> eventslist;
    List<Reminder> reminders;
    private Thread reminderThread;

    public EventsHandler() {
        eventslist = new ArrayList<>();
        reminders = new ArrayList<>();
        reminderThread = new Thread(new ReminderChecker());
        reminderThread.start();
    }

    public void addEvent(String eventName, LocalDate startDate, LocalDate endDate,
                         LocalTime startTime, LocalTime endTime, String location,
                         String notes, List<String> tags)
    {
        Event event = new Event(eventName, startDate, endDate, startTime, endTime, location, notes, tags);
        eventslist.add(event);
    }
    // This method used to add list of reminders
    public void addReminder(Event event, int minutesBefore) {
        Reminder reminder = new Reminder(event, minutesBefore);
        reminders.add(reminder);
    }
    // To view all Events from the Calendar
    public static void viewEvents(LocalDate startDate, LocalDate endDate) {
        boolean found = false;
        for (Event event : eventslist) {
            if (!event.startDate.isBefore(startDate) && !event.endDate.isAfter(endDate)) {
                System.out.println("Event: " + event.eventName);
                System.out.println("Date: " + event.startDate + " - " + event.endDate);
                System.out.println("Time: " + event.startTime + " - " + event.endTime);
                System.out.println("Location: " + event.location);
                System.out.println("Notes: " + event.notes);
                System.out.println("Tags: " + event.tags);

                found = true;
            }
        }
        if (!found) {
            System.out.println("No events found within the specified date range.");
            System.out.println("------------------------------------");
        }
    }

    // To Add Events to Calendar
    public void addEventsToCalendar(Scanner scanner) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        //Taking input :Event Name
        System.out.print("Event Name: ");
        String eventName = scanner.next();

        //Taking input : Start Date
        LocalDate startDate = null;
        while (startDate == null) {
            System.out.print("Event Start Date (dd-MM-yyyy): ");
            String startDateString = scanner.next();
            // Handling the exception to StartDate
            try {
                startDate = LocalDate.parse(startDateString, dateFormatter);
            } catch (DateTimeParseException e) {
                System.out.println("Invalid Format!!, Please use dd-MM-yyyy.");
            }
        }


        // //Taking input :End date

        LocalDate endDate = null;
        while (endDate == null) {
            System.out.print("Event End Date (dd-MM-yyyy): ");
            String endDateString = scanner.next();
            // Handling the exception to endDare
            try {
                endDate = LocalDate.parse(endDateString, dateFormatter);
            } catch (DateTimeParseException e) {
                System.out.println("Invalid format!!, Please use dd-MM-yyyy.");

            }
        }
        // //Taking input : Start time

        LocalTime startTime = null;
        while (startTime == null) {
            System.out.print("Event Start Time (HH:mm): ");
            // Handling the Exception to startTime
            try {
                DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
                startTime = LocalTime.parse(scanner.next(), timeFormatter);
            } catch (DateTimeParseException e) {
                System.out.println("Invalid format !!Please use HH:mm.");

            }
        }
        // Taking input : End time

        LocalTime endTime = null;
        while(endTime == null) {
            System.out.print("Event End Time (HH:mm): ");
            // Handling the exception to End time
            try {
                DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
                endTime = LocalTime.parse(scanner.next(), timeFormatter);
            } catch (DateTimeParseException e) {
                System.out.println("Invalid Format!!, Please use HH:mm.");

            }
        }
        // This is for Scheduling
        LocalDateTime event_with_StartDateTime = LocalDateTime.of(startDate, startTime);
        LocalDateTime event_with_EndDateTime = LocalDateTime.of(endDate, endTime);
        for (Event existingEvent : eventslist) {
            LocalDateTime existingStartDateTime = LocalDateTime.of(existingEvent.startDate, existingEvent.startTime);
            LocalDateTime existingEndDateTime = LocalDateTime.of(existingEvent.endDate, existingEvent.endTime);

            if (event_with_StartDateTime.isBefore(existingEndDateTime) && event_with_EndDateTime.isAfter(existingStartDateTime)) {
                System.out.println("Event with Same Time is Present"+"\nExisting Event: " + existingEvent.eventName);
                System.out.println("Start Time: " + existingEvent.startTime + " - End Time: " + existingEvent.endTime);
                System.out.println("Please choose a different time.");
                return;
            }
        }

        //Taking input : location
        System.out.print("Location: ");
        String location = scanner.next();
        //Taking input : notes
        System.out.print("Notes: ");
        scanner.nextLine();
        String notes = scanner.nextLine();
        // Taking input : Tags
        System.out.print("Enter event tags (Personal/Work/Health/Other): ");
        String tagsStr = scanner.nextLine();
        List<String> tags = Arrays.asList(tagsStr.split(","));
        tags = tags.stream().map(String::trim).collect(Collectors.toList());

        //Passing the input to the addEvent method
        addEvent(eventName, startDate, endDate,startTime, endTime, location, notes, tags);


        System.out.println("Want to add Reminder for Event(Y/N) :");
        String reminderChoice = scanner.next();
        if (reminderChoice.equalsIgnoreCase("Y")) {
            System.out.print("Enter reminder time before the event start time (in minutes): ");
            Event eve = new Event(eventName, startDate, endDate, startTime, endTime, location, notes, tags);
            int timeinmins = scanner.nextInt();
            addReminder(eve,timeinmins);
            System.out.println("Event added with reminder.");

        }
    }
    public void viewAllEvents() {
        System.out.println("All Events:");
        for (Event event : eventslist) {
            System.out.println("Event: " + event.eventName);
            System.out.println("Date: " + event.startDate + " - " + event.endDate);
            System.out.println("Location: " + event.location);
            System.out.println("Notes: " + event.notes);
            System.out.println("Tags: " + event.tags);
            System.out.println("------------------------------------");
        }
    }
    public static void scheduleReminder(Event event, int minutesBefore) {
        LocalDateTime reminderTime = event.dateTime.minusMinutes(minutesBefore);
        LocalDateTime currentTime = LocalDateTime.now();

        if (reminderTime.isAfter(currentTime)) {
            long delay = Duration.between(currentTime, reminderTime).toMillis();

            Thread reminderThread = new Thread(() -> {
                try {
                    Thread.sleep(delay);
                    System.out.println("Reminder: It's time for the event - " + event.eventName);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });

            reminderThread.start();
        } else {
            System.out.println("Reminder time is passed");

        }
    }

    public static void event_Categorize_By_Tag(String tag) {
        boolean tagPresent = false;
        for (Event event : eventslist) {
            if (event.tags.contains(tag)) {
                tagPresent = true;
                break;
            }
        }

        if (tagPresent) {
            System.out.println("Events categorized under tag: " + tag);
            for (Event event : eventslist) {
                if (event.tags.contains(tag)) {
                    System.out.println("Event: " + event.eventName);
                    System.out.println("Date: " + event.startDate + " - " + event.endDate);
                    System.out.println("Time: " + event.startTime + " - " + event.endTime);
                    System.out.println("Location: " + event.location);
                    System.out.println("Notes: " + event.notes);
                    System.out.println("Tags: " + event.tags);
                    System.out.println("___________________________________________________");
                }
            }
        } else {
            System.out.println("No events found for the entered tag.");
        }
    }
    private class ReminderChecker implements Runnable {
        private Set<Reminder> allreminders = new HashSet<>();
        @Override
        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                LocalDateTime currentTime = LocalDateTime.now();

                for (Reminder reminder : reminders) {
                    LocalDateTime reminderTime = reminder.event.dateTime.minusMinutes(reminder.minutesBefore);

                    if (currentTime.isAfter(reminderTime) && !allreminders.contains(reminder)) {
                        scheduleReminder(reminder.event, reminder.minutesBefore);
                        allreminders.add(reminder);
                    }
                }

                try {
                    Thread.sleep(60 * 1000); // Sleep for a minute before checking reminders again
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }



}
