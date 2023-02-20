package server;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import manager.Managers;
import manager.Storage;
import manager.TaskManager;
import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.URI;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.regex.Pattern;

import static java.nio.charset.StandardCharsets.UTF_8;

public class HttpTaskServer {
    private Storage storage;

    private final TaskManager taskManager;
    private final Gson gson;
    private HttpServer server;
    private static final int PORT = 8088;
    private static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;

    public HttpTaskServer(URI uri, String key) throws IOException {
        taskManager = Managers.getHttpTaskManager(uri, key);
        gson = new Gson();
        server = HttpServer.create(new InetSocketAddress("localhost", PORT), 0);
        server.createContext("/tasks", this::handleTasks);
        storage = taskManager.getStorage();
    }

    public TaskManager getManager() {
        return taskManager;
    }

    private void handleTasks(HttpExchange httpExchange) {
        try {
            String path = httpExchange.getRequestURI().getPath();
            String requestMethod = httpExchange.getRequestMethod();
            switch (requestMethod) {
                case "GET":
                    if (Pattern.matches("^/tasks/task/$", path)) {
                        String response = gson.toJson(taskManager.getAllTask());
                        sendText(httpExchange, response);
                        break;
                    }
                    if (Pattern.matches("^/tasks/epic$", path)) {
                        String response = gson.toJson(taskManager.getAllEpic());
                        sendText(httpExchange, response);
                        break;
                    }
                    if (Pattern.matches("^/tasks/subtask$", path)) {
                        String response = gson.toJson(taskManager.getAllSubtask());
                        sendText(httpExchange, response);
                        break;
                    }
                    if (Pattern.matches("^/tasks/subtask/epic/?\\d+$", path)) {
                        String pathIdEpic = path.replaceFirst("/tasks/subtask/epic/?", "");
                        int idEpic = parsePathId(pathIdEpic);
                        if (idEpic != -1) {
                            String response = gson.toJson(taskManager.getAllSubtask(idEpic));
                            sendText(httpExchange, response);
                        } else {
                            System.out.println("Получен некорректный id эпика: " + idEpic);
                        }
                        break;
                    }
                    if (Pattern.matches("^/tasks/task/?\\d+$", path)) {
                        String pathId = path.replaceFirst("/tasks/task/?", "");
                        int id = parsePathId(pathId);
                        if (id != -1) {
                            String response = gson.toJson(taskManager.getTask(id));
                            sendText(httpExchange, response);
                        } else {
                            System.out.println("Получен некорректный id задачи: " + id);
                        }
                        break;
                    }
                    if (Pattern.matches("^/tasks/epic/?\\d+$", path)) {
                        String pathId = path.replaceFirst("/tasks/epic/?", "");
                        int id = parsePathId(pathId);
                        if (id != -1) {
                            String response = gson.toJson(taskManager.getEpic(id));
                            sendText(httpExchange, response);
                        } else {
                            System.out.println("Получен некорректный id эпика: " + id);
                        }
                        break;
                    }
                    if (Pattern.matches("^/tasks/subtask/?\\d+$", path)) {
                        String pathId = path.replaceFirst("/tasks/subtask/?", "");
                        int id = parsePathId(pathId);
                        if (id != -1) {
                            String response = gson.toJson(taskManager.getSubtask(id));
                            sendText(httpExchange, response);
                        } else {
                            System.out.println("Получен некорректный id подзадачи: " + id);
                        }
                        break;
                    }
                    if (Pattern.matches("^/tasks$", path)) {
                        String response = gson.toJson(taskManager.getPrioritizedTasks());
                        sendText(httpExchange, response);
                        break;
                    }
                    if (Pattern.matches("^/tasks/history$", path)) {
                        String response = gson.toJson(taskManager.getHistory());
                        sendText(httpExchange, response);
                        break;
                    }

                case "POST":
                    InputStream inputStream = httpExchange.getRequestBody();
                    String body = new String(inputStream.readAllBytes(), DEFAULT_CHARSET);
                    if (Pattern.matches("^/tasks/task$", path)) {
                        Task task = gson.fromJson(body, Task.class);
                        Integer id = task.getId();
                        if (storage.getTasks().containsKey(id)) {
                            taskManager.updateTask(task, id);
                            System.out.println("Задача обновлена.");

                        } else {
                            taskManager.addTask(task);
                            System.out.println("Задача добавлена.");
                        }
                        httpExchange.sendResponseHeaders(200, 0);
                        break;
                    }
                    if (Pattern.matches("^/tasks/epic$", path)) {
                        Epic epic = gson.fromJson(body, Epic.class);
                        Integer id = epic.getId();
                        String name = epic.getNameTask();
                        String description = epic.getDescription();
                        if (storage.getEpics().containsKey(id)) {
                            taskManager.updateEpic(name, description, id);
                            System.out.println("Эпик обновлен.");
                        } else {
                            taskManager.addEpic(epic);
                            System.out.println("Эпик добавлен.");
                        }
                        httpExchange.sendResponseHeaders(200, 0);
                        break;
                    }
                    if (Pattern.matches("^/tasks/subtask$", path)) {
                        Subtask subtask = gson.fromJson(body, Subtask.class);
                        int idEpic = subtask.getEpicId();
                        Integer id = subtask.getId();
                        if (storage.getSubtasks().containsKey(id)) {
                            taskManager.updateSubtask(subtask, id, idEpic);
                            System.out.println("Подзадача обновлена.");

                        } else {
                            taskManager.addSubtask(subtask, idEpic);
                            System.out.println("Подзадача добавлена.");
                        }
                        httpExchange.sendResponseHeaders(200, 0);
                        break;
                    }

                case "DELETE":
                    if (Pattern.matches("^/tasks/task$", path)) {
                        taskManager.clearTask();
                        System.out.println("Все задачи удалены.");
                        httpExchange.sendResponseHeaders(200, 0);
                        break;
                    }
                    if (Pattern.matches("^/tasks/epic$", path)) {
                        taskManager.clearEpics();
                        System.out.println("Все эпики удалены.");
                        httpExchange.sendResponseHeaders(200, 0);
                        break;
                    }
                    if (Pattern.matches("^/tasks/subtask$", path)) {
                        taskManager.clearSubtasks();
                        System.out.println("Все подзадачи удалены.");
                        httpExchange.sendResponseHeaders(200, 0);
                        break;
                    }
                    if (Pattern.matches("^/tasks/task/?\\d+$", path)) {
                        String pathId = path.replaceFirst("/tasks/task/?", "");
                        int id = parsePathId(pathId);
                        if (id != -1) {
                            taskManager.deleteTask(id);
                            System.out.println("Удалена задача id: " + id);
                            httpExchange.sendResponseHeaders(200, 0);
                        } else {
                            System.out.println("Получен некорректный id задачи: " + id);
                        }
                        break;
                    }
                    if (Pattern.matches("^/tasks/epic/?\\d+$", path)) {
                        String pathId = path.replaceFirst("/tasks/epic/?", "");
                        int id = parsePathId(pathId);
                        if (id != -1) {
                            taskManager.deleteEpic(id);
                            System.out.println("Удален эпик id: " + id);
                            httpExchange.sendResponseHeaders(200, 0);

                        } else {
                            System.out.println("Получен некорректный id эпика: " + id);
                        }
                        break;
                    }
                    if (Pattern.matches("^/tasks/subtask/?\\d+/epic/?\\d+$", path)) {
                        String pathId = path.replaceFirst("/tasks/subtask/?", "")
                                .replaceFirst("/epic/?\\d+", "");
                        String pathIdEpic = path.replaceFirst("/tasks/subtask/?\\d+/epic/?", "");
                        int id = parsePathId(pathId);
                        int idEpic = parsePathId(pathIdEpic);
                        if (id != -1) {
                            taskManager.deleteSubtask(id, idEpic);
                            System.out.println("Удалена подзадача id: " + id);
                            httpExchange.sendResponseHeaders(200, 0);
                        } else {
                            System.out.println("Получен некорректный id подзадачи: " + id + " или некорректный id эпика: " + idEpic);
                        }
                        break;
                    }
            }

        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {
            httpExchange.close();
        }
    }

    public void start() {
        System.out.println("Запускаем сервер на порту " + PORT);
        System.out.println("Открой в браузере http://localhost:" + PORT + "/tasks");
        server.start();
    }

    public void stop() {
        server.stop(0);
        System.out.println("Остановили  сервер на порту " + PORT);
    }

    protected String readText(HttpExchange h) throws IOException {
        return new String(h.getRequestBody().readAllBytes(), UTF_8);
    }

    protected void sendText(HttpExchange h, String text) throws IOException {
        byte[] resp = text.getBytes(UTF_8);
        h.getResponseHeaders().add("Content-Type", "application/json");
        h.sendResponseHeaders(200, resp.length);
        h.getResponseBody().write(resp);
    }

    private int parsePathId(String path) {
        try {
            return Integer.parseInt(path);
        } catch (NumberFormatException exception) {
            return -1;
        }
    }
}
