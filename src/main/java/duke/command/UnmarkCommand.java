package duke.command;

import duke.data.exception.DukeException;
import duke.data.task.Task;
import duke.data.task.TaskList;
import duke.storage.Storage;

/**
 * Represents a command to mark the specified task as not done yet.
 */
public class UnmarkCommand extends ModifyTaskCommand {

    public static final String COMMAND_WORD = "unmark";
    private static final String COMMAND_RESPONSE = "Nice! I've marked this task as not done yet:\n\t";

    public UnmarkCommand(String taskIndex) {
        super(taskIndex);
    }

    @Override
    public String execute(TaskList tasks, Storage storage) throws DukeException {
        Task task = tasks.unmark(taskIndex);
        storage.save(tasks);
        return COMMAND_RESPONSE + task;
    }
}
