package me.travja.utils.utils;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class FileUtils {

    public static List<File> getFiles(String directory) {
        File dir = new File(directory);
        if (!dir.isDirectory())
            return dir.exists() ? Collections.singletonList(dir) : new ArrayList<>();
        else {
            return new ArrayList<>(Arrays.asList(dir.listFiles()));
        }
    }

    public static FileReader readFile(String name) {
        return readFile(new File(name));
    }

    /**
     * Creates a FileReader from the File. Returns null if the file cannot be found
     *
     * @param file Which file to read
     * @return {@link FileReader} or null
     */
    public static FileReader readFile(File file) {
        try {
            FileReader reader = new FileReader(file);
            return reader;
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
            return null;
        }
    }

    /**
     * Similar to {@link #readFile(String)}, this method produces a {@link FileInputStream}
     *
     * @param name The file path to get the stream from.
     * @return FileInputStream
     */
    public static FileInputStream getStream(String name) {
        return getStream(new File(name));
    }

    /**
     * Similar to {@link #readFile(File)}, this method produces a {@link FileInputStream}
     *
     * @param file The file to get the stream from.
     * @return FileInputStream
     */
    public static FileInputStream getStream(File file) {
        try {
            FileInputStream in = new FileInputStream(file);
            return in;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Writes the given text to the file 'name'
     *
     * @param name The path of the file to write to
     * @param text The text to write to the file
     */
    public static void write(String name, String text) {
        File file = new File(name);
        try {
            FileWriter writer = new FileWriter(file);
            writer.write(text);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String readFileFully(String name) {
        File file = new File(name);
        return readFileFully(file);
    }

    /**
     * Reads the file fully, creates a FileReader and closes it. Returns a String containing all the text in the file.
     *
     * @param file The file to read
     * @return String representing the file contents.
     */
    public static String readFileFully(File file) {
        try {
            StringBuilder builder = new StringBuilder();
            FileReader reader = new FileReader(file);
            int c;
            while ((c = reader.read()) != -1) {
                builder.append((char) c);
            }
            reader.close();
            return builder.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Takes in a {@link FileReader} and reads the contents. Closes the reader before exiting.
     *
     * @param reader The FileReader to read through
     * @return String representing the contents of the reader
     */
    public static String readFully(FileReader reader) {
        try {
            StringBuilder builder = new StringBuilder();
            int c;
            while ((c = reader.read()) != -1) {
                builder.append((char) c);
            }
            reader.close();
            return builder.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    //Sample methods with commented out sections for reading/writing objects to the file.
    public static boolean save(int slot) {
        try {
            File saves = new File("saves");
            saves.mkdir();
            FileOutputStream writer = new FileOutputStream("saves/slot" + slot + ".spacejam");
            ObjectOutputStream o = new ObjectOutputStream(writer);
//            o.writeObject(getQuadrants());
//            o.writeObject(getPlayer());
//            o.writeObject(yogurtLoc);
//            o.writeObject(ringLoc);
//            o.writeObject(spaceballLoc);
//            o.writeObject(previousTile);
//            o.writeObject(StoryEvents.START);
//            o.writeObject(StoryEvents.ENTER_LONE_STAR);
//            o.writeObject(StoryEvents.FIND_LONE_STAR);
//            o.writeObject(StoryEvents.FIND_YOGURT);
//            o.writeObject(StoryEvents.COMB_DESERT);
////            o.writeObject(StoryEvents.SHREK_FIGHT);
//            o.writeObject(StoryEvents.DISTRACT_GUARD);
//            o.writeObject(StoryEvents.GIVE_YOGURT_LOC);
//            o.writeObject(StoryEvents.GIVE_YOGURT_QUADRANT);
//            o.writeObject(StoryEvents.FINAL_FIGHT);
//            o.write(moves);
//            o.write(StoryEvents.storyStep);
            o.close();
            writer.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean load(int slot) {
        try {
            File saves = new File("saves");
            saves.mkdir();
            FileInputStream in = new FileInputStream("saves/slot" + slot + ".spacejam");
            ObjectInputStream o = new ObjectInputStream(in);
//            quadrants = (Map[]) o.readObject();
//            player = (Player) o.readObject();
//            yogurtLoc = (Location) o.readObject();
//            ringLoc = (Location) o.readObject();
//            spaceballLoc = (Location) o.readObject();
//            previousTile = (Tile) o.readObject();
//            StoryEvents.START = (Event) o.readObject();
//            StoryEvents.ENTER_LONE_STAR = (Event) o.readObject();
//            StoryEvents.FIND_LONE_STAR = (Event) o.readObject();
//            StoryEvents.FIND_YOGURT = (Event) o.readObject();
//            StoryEvents.COMB_DESERT = (Event) o.readObject();
////            StoryEvents.SHREK_FIGHT = (Event) o.readObject();
//            StoryEvents.DISTRACT_GUARD = (Event) o.readObject();
//            StoryEvents.GIVE_YOGURT_LOC = (Event) o.readObject();
//            StoryEvents.GIVE_YOGURT_QUADRANT = (Event) o.readObject();
//            StoryEvents.FINAL_FIGHT = (Event) o.readObject();
//            story = new StoryController();
//            moves = o.read();
//            StoryEvents.storyStep = o.read();
            o.close();
            in.close();
//            win = false;
            return true;
        } catch (IOException /*| ClassNotFoundException*/ e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void writeObjectToFile(Object obj, String filePath) {
        try {
            ObjectOutputStream objectOut = new ObjectOutputStream(new FileOutputStream(filePath));
            objectOut.writeObject(obj);
            objectOut.close();
            System.out.println("Object successfully written to file");
        } catch (IOException e) {
            System.out.println("Could not write object to file.");
            e.printStackTrace();
        }
    }

    /**
     * Reads an object from the FileInputStream, leaving it open for further reads.
     *
     * @param stream The stream to read from
     * @return An Object, read from the Stream
     */
    public static Object readObject(FileInputStream stream) {
        try {
            ObjectInputStream oin = new ObjectInputStream(stream);
            return oin.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error reading object from file. The class might have changed or is not found.");
            e.printStackTrace();
        }
        return null;
    }

}
