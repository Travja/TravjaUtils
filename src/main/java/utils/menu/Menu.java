package utils.menu;

import me.travja.persistence.utils.IOUtils;

import java.util.ArrayList;
import java.util.Arrays;

public class Menu {

    protected static Menu lastMenu;

    private Menu parentMenu;
    private ArrayList<MenuOption> options = new ArrayList<>();
    private String header;
    private String prompt;
    private String footer;
    private MenuAction perpetual;
    private MenuDirection direction = MenuDirection.VERTICAL;

    //Give our constructors with various overloads so scaling is simple
    public Menu() {

    }

    public Menu(String prompt, MenuOption... options) {
        this(null, null, prompt, "Enter your selection:", options);
    }

    public Menu(String header, String prompt, MenuOption... options) {
        this(null, header, prompt, "Enter your selection:", options);
    }

    public Menu(String header, String prompt, String footer, MenuOption... options) {
        this(null, header, prompt, footer, options);
    }

    public Menu(Menu parentMenu, String prompt, MenuOption... options) {
        this(parentMenu, null, prompt, "Enter your selection:", options);
    }

    public Menu(Menu parentMenu, String header, String prompt, MenuOption... options) {
        this(parentMenu, header, prompt, "Enter your selection:", options);
    }

    public Menu(Menu parentMenu, String header, String prompt, String footer, MenuOption... options) {
        setParentMenu(parentMenu);
        setHeader(header);
        setPrompt(prompt);
        setFooter(footer);
        this.options = new ArrayList<>(Arrays.asList(options));
    }

    //Gets the menu going and prompts for input, then tells the input to run!
    public void open(boolean loop) {
        int choice;
        do {
            lastMenu = this; //This way, when we fall-through, the choice is already set to 0, but the last menu will be different, keeping it alive.
            System.out.println();
            //Display the menu to the user and ask for input, then run their choice.
            choice = IOUtils.promptForInt(buildMessage(), 0, getOptions().size());
            if (choice > 0)
                runChoice(choice); //Fall-through (dropping menus) happens here in the case of a choice opening another menu.
        } while (loop && (choice != 0 || (!this.equals(lastMenu) && choice == 0)));
        //The loop should run if....
        // *The user hasn't selected 0
        // *We're dropping menus, and it's not from this menu
    }

    private void runChoice(int choice) {
        MenuAction action = getOptions().get(choice - 1).getAction();
        if (action != null) {
            action.use();
            if (getPerpetual() != null) //If we have a perpetual action after each selection, run it.
                getPerpetual().use();
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
        }
    }

    private String buildMessage() {
        StringBuilder sb = new StringBuilder();
        appendIfNotNull(getHeader(), sb, "\n\n");//Add our header and prompt
        appendIfNotNull(getPrompt(), sb);
        String split = getDirection() == MenuDirection.VERTICAL ? "\n" : "\t";
        for (int i = 0; i < getOptions().size(); i++) //Build our options and number them
            sb.append(String.format(split + "%d) %s", i + 1, getOption(i)));
        sb.append((getParentMenu() == null || isMainMenu()) ? split + split + "0) Exit\n" : split + split + "0) Back\n");//Add our default Exit/Back options. Back if the menu is a submenu.
        appendIfNotNull(getFooter(), sb, " ");//Add our footer
        return sb.toString();
    }

    private StringBuilder appendIfNotNull(String str, StringBuilder sb, String ifSuc) {
        if (str != null && !str.trim().isEmpty())
            sb.append(str).append(ifSuc);
        return sb;
    }

    private StringBuilder appendIfNotNull(String str, StringBuilder sb) {
        return appendIfNotNull(str, sb, "");
    }

    public Menu getParentMenu() {
        return parentMenu;
    }

    public Menu setParentMenu(Menu parentMenu) {
        this.parentMenu = parentMenu;
        return this;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getPrompt() {
        return prompt;
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }

    public String getFooter() {
        return footer;
    }

    public void setFooter(String footer) {
        this.footer = footer;
    }

    public ArrayList<MenuOption> getOptions() {
        return options;
    }

    public MenuOption getOption(int index) {
        if (options.size() > index)
            return options.get(index);

        return null;
    }

    public Menu addOption(MenuOption option) {
        if (option != null)
            options.add(option);
        return this;
    }

    /**
     * Works much like the ArrayList#add function.
     * This will insert the MenuOption at the selected index
     * and shift the current and subsequent elements to
     * the right.
     *
     * @param index  Where to insert the MenuOption
     * @param option The MenuOption to insert
     * @return
     */
    public Menu addOption(int index, MenuOption option) throws IndexOutOfBoundsException {
        if (option != null)
            options.add(index, option);
        return this;
    }

    public Menu removeOption(MenuOption option) {
        if (option != null)
            options.remove(option);
        return this;
    }

    public Menu removeOption(int index) {
        if (options.size() < index)
            options.remove(index);
        return this;
    }

    public MenuAction getPerpetual() {
        return perpetual;
    }

    public Menu setPerpetual(MenuAction perpetual) {
        this.perpetual = perpetual;
        return this;
    }

    public boolean isMainMenu() {
        return getParentMenu().equals(this);
    }

    public Menu setMainMenu(boolean main) {
        if (main)
            setParentMenu(this);
        else
            setParentMenu(null);
        return this;
    }

    public MenuDirection getDirection() {
        return direction;
    }

    public Menu setDirection(MenuDirection direction) {
        this.direction = direction;
        return this;
    }
}
