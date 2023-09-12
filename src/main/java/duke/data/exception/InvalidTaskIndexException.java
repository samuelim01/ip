package duke.data.exception;

/**
 * Represents an exception due to an invalid task index.
 */
public class InvalidTaskIndexException extends DukeException {

    /**
     * Returns an instance of {@code DukeException} with the given error message.
     *
     * @param message The error message of the exception.
     */
    public InvalidTaskIndexException(String message) {
        super(message);
    }
}
