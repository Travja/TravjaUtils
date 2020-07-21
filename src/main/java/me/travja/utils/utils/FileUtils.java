package me.travja.utils.utils;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class FileUtils {

    /**
     * Attempts to get the the resource included in the compiled jar. Returns null if none is found.
     *
     * @param filename The file to search for
     * @return A {@link File} or null
     */
    public static File getResource(Class clazz, String filename) {
        URL url = clazz.getClassLoader().getResource(filename);
        try {
            return new File(url.toURI());
        } catch (URISyntaxException e) {
            System.out.println("Could not find resource '" + filename + "'");
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Takes a directory and returns a List of files in that directory
     *
     * @param directory The directory to search
     * @return {@link List}
     */
    public static List<File> getFiles(String directory) {
        File dir = new File(directory);
        if (!dir.isDirectory())
            return dir.exists() ? Collections.singletonList(dir) : new ArrayList<>();
        else {
            return new ArrayList<>(Arrays.asList(dir.listFiles()));
        }
    }

    /**
     * Creates a {@link FileReader} for the given file at the specified path
     *
     * @param name The path to read
     * @return {@link FileReader}
     */
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

    /**
     * Reads the file fully, creates a FileReader and closes it. Returns a String containing all the text in the file.
     *
     * @param name The path to the file to read
     * @return String representing the file contents.
     */
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

    /**
     * Writes an object to the given file
     *
     * @param obj      The object to write
     * @param filePath The file path to write to
     */
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
     * Writes an object to the given file
     *
     * @param obj  The object to write
     * @param file The file to write to
     */
    public static void writeObjectToFile(Object obj, File file) {
        try {
            ObjectOutputStream objectOut = new ObjectOutputStream(new FileOutputStream(file));
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
