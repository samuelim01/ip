import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Storage {

    private String filePath;

    public Storage(String filePath) {
        this.filePath = filePath;
    }

    public List<Task> load() throws DukeException {
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
                        throw new DukeException("Invalid save data.");
                }

                tasks.add(task);
                if (isMarked) {
                    task.markAsDone();
                }
            }

            return tasks;

        } catch (IOException e) {
            throw new DukeException("There was an IOException while loading the tasks.");
        }
    }

    public void save(TaskList tasks) throws DukeException {
        try {
            File file = new File(filePath);
            file.getParentFile().mkdir();

            FileWriter fileWriter = new FileWriter(file);
            fileWriter.append(tasks.getStorageString());
            fileWriter.close();

        } catch (IOException e) {
            throw new DukeException("There was an IOException while saving the tasks.");
        }
    }
}