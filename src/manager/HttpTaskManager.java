package manager;

import server.KVServer;
import server.KVTaskClient;

import java.io.IOException;
import java.net.URI;

public class HttpTaskManager extends FileBackedTasksManager {
    private static KVTaskClient client;
    private static String key;

    public HttpTaskManager(URI uri, String key) {
        super(uri);
        this.key = key;
        try {
            client = new KVTaskClient(uri);

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void save() {
        try {
            CSVTaskFormatter formatter = new CSVTaskFormatter();
            StringBuilder savedTasks = new StringBuilder();
            savedTasks.append(formatter.title() + "\n");

            for (Integer i : storage.getTasks().keySet()) {
                savedTasks.append(formatter.toString(storage.getTasks().get(i)) + "\n");
            }
            for (Integer i : storage.getEpics().keySet()) {
                savedTasks.append(formatter.toString(storage.getEpics().get(i)) + "\n");
            }
            for (Integer i : storage.getSubtasks().keySet()) {
                savedTasks.append(formatter.toString(storage.getSubtasks().get(i)) + "\n");
            }
            savedTasks.append("\n");
            savedTasks.append(formatter.historyToString(getHistoryManager()));

            client.put(key, savedTasks.toString());
        } catch (IOException | InterruptedException e) {
            throw new ManagerSaveException("Произошла ошибка при сохранении файла");
        }
    }

    public static HttpTaskManager loadFromHttp(URI uri, String key) {
        HttpTaskManager httpTaskManager = new HttpTaskManager(uri, key);
        Storage storage = httpTaskManager.storage;

        try {
            final String loadTasks = client.load(key);
            if (loadTasks != null) {
                final String[] lines = loadTasks.split("\r?\n|\r");
                load(lines, storage, httpTaskManager);
            }
        } catch (IOException | InterruptedException e) {
            throw new ManagerReaderException("Произошла ошибка во время чтения файла");
        }
        return httpTaskManager;
    }
}
