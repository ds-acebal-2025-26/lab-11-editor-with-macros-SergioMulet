package es.uniovi.eii.ds.commands;

import java.util.ArrayList;
import java.util.List;

public class MacroCommand implements UserCommand {

    private List<UserCommand> commands = new ArrayList<>();
    private String name;

    public MacroCommand(String name) {
        this.name = name;
    }

    @Override
    public void execute() {
        for (UserCommand command : commands) {
            command.execute();
        }
    }

    public void addCommand(UserCommand command) {
        this.commands.add(command);
    }

    public String getName() {
        return name;
    }

}
