package it.unibo.nestedenum;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Implementation of {@link MonthSorter}.
 */
public final class MonthSorterNested implements MonthSorter {
    private enum Month {
        JANUARY(31),
        FEBRUARY(28),
        MARCH(31),
        APRIL(30),
        MAY(31),
        JUNE(30),
        JULY(31),
        AUGUST(31),
        SEPTEMBER(30),
        OCTOBER(31),
        NOVEMBER(30),
        DECEMBER(31);

        private final int days;

        private Month(final int days) {
            this.days = days;
        }

        public static Month fromString(final String mothString) {
            final List<Month> possibleMonth = new ArrayList<>();
            final String monthStringUpper = mothString.toUpperCase();
            for (Month m : values()) {
                if (m.name().contains(monthStringUpper)) {
                    possibleMonth.add(m);
                }
            }
            if (possibleMonth.size() == 0 || possibleMonth.size() > 1) {
                throw new IllegalArgumentException("the month is not present in enum or is ambiguous");
            }
            return possibleMonth.getFirst();
        }
    }

    @Override
    public Comparator<String> sortByDays() {
        return new SortByDate();
    }

    @Override
    public Comparator<String> sortByOrder() {
        return new SortByMonthOrder();
    }

    public static class SortByMonthOrder implements Comparator<String> {

        @Override
        public int compare(final String o1, final String o2) {
            return Integer.compare(Month.fromString(o1).ordinal(), Month.fromString(o2).ordinal());
        }

    }

    public static class SortByDate implements Comparator<String> {

        @Override
        public int compare(final String o1, final String o2) {
            return Integer.compare(Month.fromString(o1).days, Month.fromString(o2).days);
        }
    }

}
