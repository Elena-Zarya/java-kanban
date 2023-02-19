package manager;

import history.HistoryManager;
import history.InMemoryHistoryManager;

import java.io.File;
import java.net.URI;

public class Managers {
    public static TaskManager getDefault() {
        return new InMemoryTaskManager();
    }

    public static TaskManager getFileBackedTasksManager() {
        return new FileBackedTasksManager(new File("fileToSave.csv"));
    }

    public static TaskManager getHttpTaskManager(URI uri, String key) {
        return new HttpTaskManager(uri, key);
    }

    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }
}
