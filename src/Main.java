
import java.util.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("<------------Welcome to CalendarPro App!!-------------->");
        Scanner scanner = new Scanner(System.in);
        EventsHandler eventsHandler = new EventsHandler();
        while (true) {
            System.out.println("\n<--What Do you like To do?-->");
            System.out.println("1: Add Event");
            System.out.println("2: View All Event");
            System.out.println("3: View(Day/week/month)");
            System.out.println("4: Categorize Events by tag");
            System.out.println("5. Exit");
            System.out.print("Select an option: ");

            int choice = scanner.nextInt();
            // eventsHandler.scheduleReminder(Event);
            switch (choice) {
                case 1:
                    System.out.println("Enter the Event Details");
                    eventsHandler.addEventsToCalendar(scanner);
                    break;
                case 2:
                    eventsHandler.viewAllEvents();
                    break;
                case 3:
                    Overview_of_Calendar.viewCalendarOverview(eventsHandler, scanner);
                    break;
                case 4:
                    System.out.print("Enter the tag to categorize events: ");
                    String tag = scanner.next();
                    eventsHandler.event_Categorize_By_Tag(tag);
                    break;
                case 5:
                    System.out.println("Thank you :)\tExiting...");
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }

    }

}
