package socrates.storage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import socrates.data.exception.StorageLoadException;
import socrates.data.exception.StorageSaveException;
import socrates.data.task.Deadline;
import socrates.data.task.Event;
import socrates.data.task.Task;
import socrates.data.task.TaskList;
import socrates.data.task.Todo;

/**
 * Represents the file used to store task data.
 */
public class Storage {

    /** The file path where the task data is stored */
    private final String filePath;

    /** Initializes the data file with the given path */
    public Storage(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Loads the data from the file and returns it as a list.
     *
     * @return The list of tasks.
     * @throws StorageLoadException If there was an issue running the file.
     */
    public List<Task> load() throws StorageLoadException {
        try {
            List<Task> tasks = new ArrayList<>();
            File file = new File(filePath);
            if (!file.exists()) {
                return tasks;
            }

            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            String saveString;

            while ((saveString = bufferedReader.readLine()) != null) {
                String[] saveStringArgs = saveString.split(" \\| ");
                String type = saveStringArgs[0];
                boolean isMarked = saveStringArgs[1].equals("1");
                String description = saveStringArgs[2];

                Task task;

                switch (type) {
                case "T":
                    task = new Todo(description);
                    break;
                case "D":
                    String by = saveStringArgs[3];
                    LocalDate localBy = LocalDate.parse(by);
                    task = new Deadline(description, localBy);
                    break;
                case "E":
                    String from = saveStringArgs[3];
                    String to = saveStringArgs[4];
                    LocalDate localFrom = LocalDate.parse(from);
                    LocalDate localTo = LocalDate.parse(to);
                    task = new Event(description, localFrom, localTo);
                    break;
                default:
                    throw new StorageLoadException("Invalid save data.");
                }

                tasks.add(task);
                if (isMarked) {
                    task.markAsDone();
                }
            }

            return tasks;

        } catch (IOException e) {
            throw new StorageLoadException("There was an IOException while loading the tasks.");
        }
    }

    /**
     * Saves the list of tasks to the file.
     *
     * @param tasks The list of tasks to be saved to the file.
     * @throws StorageSaveException If there was an issue saving the file.
     */
    public void save(TaskList tasks) throws StorageSaveException {
        try {
            File file = new File(filePath);
            file.getParentFile().mkdir();

            FileWriter fileWriter = new FileWriter(file);
            fileWriter.append(tasks.getStorageString());
            fileWriter.close();

        } catch (IOException e) {
            throw new StorageSaveException("There was an IOException while saving the tasks.");
        }
    }
}
