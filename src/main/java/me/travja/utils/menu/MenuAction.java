package me.travja.utils.menu;

import java.io.EOFException;

public interface MenuAction {
    /**
     * Simply put, this is a block of code that should be performed when this action is run.
     */
    void use() throws EOFException;
}
