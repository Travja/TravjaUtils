package me.travja.utils.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TempFile {

    private String name;
    private StringBuilder data = new StringBuilder();

    public TempFile(InputStream stream) {
        if (stream == null)
            return;

        try (BufferedReader in = new BufferedReader(new InputStreamReader(stream))) {
            String input;
            while ((input = in.readLine()) != null) {
                data.append(input).append("\n");
            }
        } catch (IOException e) {
            System.err.println("Couldn't create file from stream.");
            e.printStackTrace();
        }
    }

    public TempFile(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void write(Object data) {
        this.data.append(data.toString());
    }

    public void writeln(Object data) {
        this.data.append(data.toString()).append("\n");
    }

    public String[] getLines() {
        return this.data.toString().split("\n");
    }

    public void setData(String data) {
        this.data = new StringBuilder(data);
    }

    public String getData() {
        return data.toString();
    }
}
