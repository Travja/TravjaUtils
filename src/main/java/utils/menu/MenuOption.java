package utils.menu;

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

    public void setAction(MenuAction action) {
        if (action == null)
            action = () -> {};
        this.action = action;
    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }

    @Override
    public String toString() {
        return option;
    }
}
