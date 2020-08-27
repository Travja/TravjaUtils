package me.travja.utils.utils;

import me.travja.utils.menu.EndAction;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class IOUtils {

    private static Robot robot;
    private static HashMap<Character, Integer> keyMap = new HashMap<>();

    private static BufferedReader reader;

    private static int failed = 0;

    private static EndAction endAction = () -> {
        System.err.println("Input stream was terminated. Exiting program.");
        System.exit(1);
    };

    public static EndAction getEndAction() {
        return endAction;
    }

    public static void setEndAction(EndAction endAction) {
        IOUtils.endAction = endAction;
    }

    private static BufferedReader getReader() throws IOException {
        if (reader == null)
            reader = new BufferedReader(new InputStreamReader(System.in));

        reader.ready();

        return reader;
    }

    /**
     * Read in user input from the command line
     *
     * @return {@link String} containing the user input. Can be an empty String
     */
    public static String read() {
        String ret = null;
        try {
            ret = getReader().readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if ((ret == null || ret.trim().isEmpty()) && System.console() == null) {
            failed++;
            if (failed >= 5 && endAction != null) //If there is no end-action, we'll assume things are being handled on the developer's side
                endAction.use();
        } else
            failed = 0;

        return ret;
    }

    //public and static methods for easy data retrieval and validation

    /**
     * Prompt the user for a String. Validates that the string is not empty or null.
     *
     * @param prompt What should we tell the user?
     * @return The string fro the user.
     */
    public static String promptForString(String prompt) {
        System.out.print(prompt.trim() + " ");
        String ret;
        String input = read();
        if (input == null || input.trim().isEmpty()) {
            System.out.println("Invalid input. Can't be empty. Try again.");
            ret = promptForString(prompt);
        } else
            ret = input;
        return ret;
    }

    /**
     * Prompt the user for an int.
     *
     * @param prompt What should we tell the user?
     * @param min    What is the minimum int they can enter?
     * @param max    What is the maximum?
     * @return The parsed int from the user
     */
    public static int promptForInt(String prompt, int min, int max) {
        int ret;
        try {
            ret = Integer.parseInt(promptForString(prompt));
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. You should enter a number.");
            ret = promptForInt(prompt, min, max);
        }
        if (ret < min || ret > max) {
            System.out.println("Invalid input. Must be between " + min + " and " + max);
            ret = promptForInt(prompt, min, max);
        }
        return ret;
    }

    /**
     * Prompt the user for an int.
     *
     * @param prompt  What should we tell the user?
     * @param allowed A list of allowed values
     * @return The parsed int from the user
     */
    public static int promptForInt(String prompt, ArrayList<Integer> allowed) {
        int ret;
        try {
            ret = Integer.parseInt(promptForString(prompt));
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. You should enter a number.");
            ret = promptForInt(prompt, allowed);
        }
        if (!allowed.contains(ret)) {
            System.out.println("Invalid input. Must be in {" + listToInlineString(allowed) + "}");
            ret = promptForInt(prompt, allowed);
        }
        return ret;
    }

    /**
     * Converts an ArrayList into a comma separated list
     *
     * @param arr The array to convert
     * @return A comma-delimited string list of values from the array
     */
    private static String listToInlineString(ArrayList<? extends Object> arr) {
        StringBuilder sb = new StringBuilder();
        for (Object i : arr)
            sb.append(i.toString() + ", ");
        int last = sb.lastIndexOf(",");
        if (last > 0)
            return sb.substring(0, last);
        return sb.toString();
    }

    /**
     * Prompt the user for a float
     *
     * @param prompt What should we tell the user?
     * @param min    The lowest acceptable float
     * @param max    The highest acceptable float
     * @return The parsed float value from the user
     */
    public static float promptForFloat(String prompt, float min, float max) {
        float ret;
        try {
            ret = Float.parseFloat(promptForString(prompt));
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. You should enter a number.");
            ret = promptForFloat(prompt, min, max);
        }

        if (ret < min || ret > max) {
            System.out.println("Invalid input. Must be between " + min + " and " + max);
            ret = promptForFloat(prompt, min, max);
        }

        return ret;
    }

    /**
     * Prompt the user for a date with format MM/dd/yyyy
     *
     * @param prompt What should we tell the user?
     * @return The parsed Date object
     */
    public static Date promptForDate(String prompt) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        boolean isValid = false;
        Date date = null;
        do {
            try {
                String bday = promptForString(prompt);
                date = correctDate(dateFormat.parse(bday));
                isValid = true;
            } catch (ParseException e) {
                System.out.println("Invalid format. Try again");
            }
            if (isValid && date.after(new Date(System.currentTimeMillis()))) {
                isValid = false;
                System.out.println("Date must be before right now.");
            }
        } while (!isValid);

        return date;
    }

    /**
     * Attempts to fix the year if it is represented with 2 digits as opposed to all 4
     *
     * @param date
     * @return the new Date object
     */
    private static Date correctDate(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date(System.currentTimeMillis()));
        int currentYear = c.get(Calendar.YEAR) - 2000;
        c.setTime(date);
        int year = c.get(Calendar.YEAR);
        if (year < 100) {
            if (year <= currentYear)
                c.set(Calendar.YEAR, year + 2000);
            else
                c.set(Calendar.YEAR, year + 1900);
        }

        date.setTime(c.getTime().getTime());

        return date;
    }

    /**
     * Prompt the user for a binary situation presented with two options.
     *
     * @param prompt   What should we tell the user?
     * @param trueStr  A list of acceptable inputs for true, separated by a '.'
     * @param falseStr A list of acceptable inputs for false, separated by a '.'
     * @return Whether or not they entered true
     */
    public static boolean promptForBoolean(String prompt, String trueStr, String falseStr) {
        ArrayList<String> yes = new ArrayList<>(Arrays.asList(trueStr.split("\\.")));
        ArrayList<String> no = new ArrayList<>(Arrays.asList(falseStr.split("\\.")));

        String input = promptForString(prompt);
        while (!yes.contains(input.toLowerCase()) && !no.contains(input.toLowerCase())) {
            System.out.println("Did not receive proper input. Try again.");
            input = promptForString(prompt);
        }

        return yes.contains(input.toLowerCase());
    }


    private static void populateMap() {
        keyMap.put('?', KeyEvent.VK_SLASH);
        keyMap.put('!', KeyEvent.VK_1);
        keyMap.put('@', KeyEvent.VK_2);
        keyMap.put('#', KeyEvent.VK_3);
        keyMap.put('$', KeyEvent.VK_4);
        keyMap.put('%', KeyEvent.VK_5);
        keyMap.put('^', KeyEvent.VK_6);
        keyMap.put('&', KeyEvent.VK_7);
        keyMap.put('*', KeyEvent.VK_8);
        keyMap.put('(', KeyEvent.VK_9);
        keyMap.put(')', KeyEvent.VK_0);
        keyMap.put('_', KeyEvent.VK_MINUS);
        keyMap.put('+', KeyEvent.VK_EQUALS);
        keyMap.put('~', KeyEvent.VK_BACK_QUOTE);
        keyMap.put('{', KeyEvent.VK_OPEN_BRACKET);
        keyMap.put('}', KeyEvent.VK_CLOSE_BRACKET);
        keyMap.put('|', KeyEvent.VK_BACK_SLASH);
        keyMap.put(':', KeyEvent.VK_SEMICOLON);
        keyMap.put('<', KeyEvent.VK_COMMA);
        keyMap.put('>', KeyEvent.VK_PERIOD);
    }

    /**
     * Uses {@link Robot} to print text that is editable by the user.
     * Text may not be multi-line.
     *
     * @param text The text to show to the user
     */
    public static void printEditable(String text) {
        initRobot();

        char[] chars = text.toCharArray();
        for (char c : chars) {
            if (Character.isUpperCase(c) || keyMap.containsKey(c))
                robot.keyPress(KeyEvent.VK_SHIFT);
            try {
                int press = keyMap.containsKey(c) ? keyMap.get(c) : Character.toUpperCase(c);
                robot.keyPress(press);
                robot.keyRelease(press);
            } catch (Exception e) {
                System.out.println("Caught error. " + c + " -- " + e.getMessage());
            }
            if (Character.isUpperCase(c) || keyMap.containsKey(c))
                robot.keyRelease(KeyEvent.VK_SHIFT);
        }
    }

    private static void initRobot() {
        if (robot == null) {
            populateMap();
            try {
                robot = new Robot();
                robot.setAutoDelay(10);
                robot.setAutoWaitForIdle(true);
            } catch (AWTException e) {
                e.printStackTrace();
            }
        }
    }

}
