package me.travja.utils.menu;

public class MenuOption {

    private MenuAction action;
    private String option;


    public MenuOption(String option, MenuAction action) {
        setOption(option);
        setAction(action);
    }

    public MenuAction getAction() {
        return action;
    }

    /**
     * When this option is selected in a menu, this action is executed.
     *
     * @param action The {@link MenuAction} to run
     */
    public void setAction(MenuAction action) {
        if (action == null)
            action = () -> {
            };
        this.action = action;
    }

    public String getOption() {
        return option;
    }

    /**
     * Set the text to be associated with this MenuOption
     *
     * @param option
     */
    public void setOption(String option) {
        this.option = option;
    }

    @Override
    public String toString() {
        return option;
    }
}
