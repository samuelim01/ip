package socrates.command;

import socrates.data.task.Todo;

/**
 * Represents a command to add a new todo to the list of tasks.
 */
public class AddTodoCommand extends AddTaskCommand {

    public static final String COMMAND_WORD = "todo";
    public static final String COMMAND_DESCRIPTION = COMMAND_WORD + ": Adds a new todo item.";

    public AddTodoCommand(String description) {
        this.toAdd = new Todo(description);
    }

}
