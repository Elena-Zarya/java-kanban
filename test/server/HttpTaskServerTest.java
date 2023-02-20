package server;

import server.HttpTaskServer;
import server.KVServer;
import com.google.gson.Gson;
import manager.HttpTaskManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;

import static java.time.Month.FEBRUARY;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static tasks.Status.DONE;
import static tasks.Status.NEW;

public class HttpTaskServerTest {
    private HttpTaskServer taskServer;
    private final Gson gson = new Gson();
    private HttpTaskManager taskManager;
    private Task task;
    KVServer kvServer;
    int taskId;
    int epicId;
    int subtaskId;

    @BeforeEach
    public void init() throws IOException {
        kvServer = new KVServer();
        kvServer.start();
        taskServer = new HttpTaskServer(URI.create("http://localhost:8078/"), "1");
        taskManager = (HttpTaskManager) taskServer.getManager();

        task = new Task("Задача 1", "Описание 2", DONE, 10, 2023, FEBRUARY, 5, 12, 40);
        taskId = taskManager.addTask(task);
        Epic epic = new Epic("Эпик 1", "Описание", NEW);
        epicId = taskManager.addEpic(epic);
        Subtask subtask = new Subtask("Подзадача 1", "Описание", NEW, 20, 2023, FEBRUARY, 5, 10, 30, epicId);
        subtaskId = taskManager.addSubtask(subtask, epicId);

        taskServer.start();
    }

    @AfterEach
    public void tearDown() {
        taskManager.clearTask();
        taskManager.clearEpics();
        taskManager.clearSubtasks();
        taskServer.stop();
        kvServer.stop();
    }

    @Test
    public void getAllTaskTest() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        URI uri = URI.create("http://localhost:8088/tasks/task/");
        HttpRequest request = HttpRequest.newBuilder().uri(uri).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode());
    }

    @Test
    public void getAllEpicTest() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        URI uri = URI.create("http://localhost:8088/tasks/epic");
        HttpRequest request = HttpRequest.newBuilder().uri(uri).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode());
    }

    @Test
    public void getAllSubtaskTest() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        URI uri = URI.create("http://localhost:8088/tasks/subtask");
        HttpRequest request = HttpRequest.newBuilder().uri(uri).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode());
    }

    @Test
    public void getAllSubtaskEpicTest() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8088/tasks/subtask/epic/" + epicId);
        HttpRequest request = HttpRequest.newBuilder().uri(url).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode());
    }

    @Test
    public void getTaskIdTest() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        URI uri = URI.create("http://localhost:8088/tasks/task/" + taskId);
        HttpRequest request = HttpRequest.newBuilder().uri(uri).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode());
    }

    @Test
    public void getEpicIdTest() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        URI uri = URI.create("http://localhost:8088/tasks/epic/" + epicId);
        HttpRequest request = HttpRequest.newBuilder().uri(uri).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode());
    }

    @Test
    public void getSubtaskIdTest() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        URI uri = URI.create("http://localhost:8088/tasks/subtask/" + epicId);
        HttpRequest request = HttpRequest.newBuilder().uri(uri).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode());
    }

    @Test
    public void getPrioritizedTasksTest() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        URI uri = URI.create("http://localhost:8088/tasks");
        HttpRequest request = HttpRequest.newBuilder().uri(uri).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode());
    }

    @Test
    public void getHistoryTest() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        URI uri = URI.create("http://localhost:8088/tasks/history");
        HttpRequest request = HttpRequest.newBuilder().uri(uri).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode());
    }

    @Test
    public void addTaskTest() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        Task task = new Task("Задача 1", "Описание", NEW, 30, 2023, FEBRUARY, 5, 12, 30);
        String json = gson.toJson(task);
        URI uri = URI.create("http://localhost:8088/tasks/task");
        HttpRequest request = HttpRequest.newBuilder().uri(uri).POST(HttpRequest.BodyPublishers.ofString(json)).build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode());
    }

    @Test
    public void updateTaskTest() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        Task task = new Task(5, "Задача 1", NEW, "Описание", LocalDateTime.of(2024, 02, 20, 12, 30), 30);
        int id = taskManager.addTask(task);
        URI uri = URI.create("http://localhost:8088/tasks/task");
        Task task2 = new Task(id, "Задача 1", NEW, "Описание", LocalDateTime.of(2023, 02, 20, 12, 30), 30);
        String json = gson.toJson(task2);
        HttpRequest request = HttpRequest.newBuilder().uri(uri).POST(HttpRequest.BodyPublishers.ofString(json)).build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode());
    }

    @Test
    public void addEpicTest() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        Epic epic = new Epic("Эпик 1", "Описание", NEW);
        String json = gson.toJson(epic);
        URI uri = URI.create("http://localhost:8088/tasks/epic");
        HttpRequest request = HttpRequest.newBuilder().uri(uri).POST(HttpRequest.BodyPublishers.ofString(json)).build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode());
    }

    @Test
    public void updateEpicTest() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        Epic epic = new Epic("Эпик 1", "Описание", NEW);
        int id = taskManager.addEpic(epic);
        URI uri = URI.create("http://localhost:8088/tasks/epic");
        Epic epic2 = new Epic(id, "Эпик 1", NEW, "Описание", LocalDateTime.of(2023, 02, 20, 12, 30), 30);
        String json = gson.toJson(epic2);
        HttpRequest request = HttpRequest.newBuilder().uri(uri).POST(HttpRequest.BodyPublishers.ofString(json)).build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode());
    }

    @Test
    public void addSubtaskTest() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        Subtask subtask = new Subtask("Подзадача 1", "Описание", NEW, 20, 2023, FEBRUARY, 5, 10, 30, epicId);
        String json = gson.toJson(subtask);
        URI uri = URI.create("http://localhost:8088/tasks/subtask");
        HttpRequest request = HttpRequest.newBuilder().uri(uri).POST(HttpRequest.BodyPublishers.ofString(json)).build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode());
    }

    @Test
    public void updateSubtaskTest() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        Subtask subtask = new Subtask("Подзадача 1", "Описание", NEW, 20, 2023, FEBRUARY, 5, 10, 30, epicId);
        int id = taskManager.addSubtask(subtask, epicId);
        URI uri = URI.create("http://localhost:8088/tasks/epic");
        Subtask subtask2 = new Subtask(id, "Подзадача 1", NEW, "Описание", epicId, LocalDateTime.of(2023, 02, 20, 12, 30), 30);
        String json = gson.toJson(subtask2);
        HttpRequest request = HttpRequest.newBuilder().uri(uri).POST(HttpRequest.BodyPublishers.ofString(json)).build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode());
    }

    @Test
    public void clearTaskTest() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        URI uri = URI.create("http://localhost:8088/tasks/task");
        HttpRequest request = HttpRequest.newBuilder().uri(uri).DELETE().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode());
    }

    @Test
    public void clearEpicsTest() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        URI uri = URI.create("http://localhost:8088/tasks/epic");
        HttpRequest request = HttpRequest.newBuilder().uri(uri).DELETE().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode());
    }


    @Test
    public void clearSubtasksTest() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        URI uri = URI.create("http://localhost:8088/tasks/subtask");
        HttpRequest request = HttpRequest.newBuilder().uri(uri).DELETE().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode());
    }

    @Test
    public void deleteTaskIdTest() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        URI uri = URI.create("http://localhost:8088/tasks/task/" + taskId);
        HttpRequest request = HttpRequest.newBuilder().uri(uri).DELETE().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode());
    }

    @Test
    public void deleteEpicIdTest() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        URI uri = URI.create("http://localhost:8088/tasks/epic/" + epicId);
        HttpRequest request = HttpRequest.newBuilder().uri(uri).DELETE().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode());
    }

    @Test
    public void deleteSubtaskIdTest() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        URI uri = URI.create("http://localhost:8088/tasks/subtask/" + subtaskId + "/epic/" + epicId);
        HttpRequest request = HttpRequest.newBuilder().uri(uri).DELETE().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode());
    }
}
