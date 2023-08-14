import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Overview_of_Calendar {
    static void viewCalendarOverview(EventsHandler handler, Scanner scanner) {
        System.out.println("\n\t\tEasy Viewing:");
        System.out.println("1. Day View"+"\t\t2. Week View"+"\t\t3. Month View");
        System.out.print("Enter your choice of View: ");
        int viewType = scanner.nextInt();
        switch (viewType) {
            case 1:
                System.out.println("\n<-- Day Overview -->");
                System.out.print("Enter date (dd-MM-yyyy): ");
                String dateString = scanner.next();
                LocalDate date = LocalDate.parse(dateString, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                viewDayOverview(date);
                break;
            case 2:
                System.out.println("\n<-- Week Overview -->");
                System.out.print("Enter start date (dd-MM-yyyy): ");
                String startDateString = scanner.next();
                LocalDate startDate = LocalDate.parse(startDateString, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                viewWeekOverview(startDate);
                break;
            case 3:
                System.out.println("\n<-- Month Overview -->");
                System.out.print("Enter month (MM-yyyy): ");
                String monthString = scanner.next();
                YearMonth yearMonth = YearMonth.parse(monthString, DateTimeFormatter.ofPattern("MM-yyyy"));
                LocalDate monthDate = yearMonth.atDay(1);
                viewMonthOverview(monthDate);
                break;
            default:
                System.out.println("Invalid choice.");
        }
    }
    public static void viewDayOverview(LocalDate date) {
        System.out.println("\nDay Overview for " + date + ":");
        EventsHandler.viewEvents(date, date);
    }

    public static void viewWeekOverview(LocalDate startDate) {
        LocalDate endDate = startDate.plusDays(6);
        System.out.println("\nWeek Overview from " + startDate + " to " + endDate + ":");
        EventsHandler.viewEvents(startDate, endDate);
    }

    public static void viewMonthOverview(LocalDate date) {
        LocalDate firstDayOfMonth = date.withDayOfMonth(1);
        LocalDate lastDayOfMonth = date.withDayOfMonth(date.lengthOfMonth());
        System.out.println("\nMonth Overview for " + date.getMonth() + ":");
        EventsHandler.viewEvents(firstDayOfMonth, lastDayOfMonth);
    }
    public void viewCalendar(int viewType) {
        LocalDate currentDate = LocalDate.now();

        switch (viewType) {
            case 1:
                System.out.println("\nDay View:");
                EventsHandler.viewEvents(currentDate, currentDate);
                break;
            case 2: // Week View
                System.out.println("\nWeek View:");
                LocalDate endDate = currentDate.plusDays(6);
                EventsHandler.viewEvents(currentDate, endDate);
                break;
            case 3: // Month View
                System.out.println("\nMonth View:");
                LocalDate firstDayOfMonth = currentDate.withDayOfMonth(1);
                LocalDate lastDayOfMonth = currentDate.withDayOfMonth(currentDate.lengthOfMonth());
                EventsHandler.viewEvents(firstDayOfMonth, lastDayOfMonth);
                break;
            default:
                System.out.println("Invalid view type.");
        }
    }
}
