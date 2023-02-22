import manager.Managers;
import manager.TaskManager;
import org.junit.jupiter.api.Test;
import tasks.Epic;
import tasks.Status;
import tasks.Subtask;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class EpicStatusTest {
    private final TaskManager manager = Managers.getDefault();

    @Test
    public void shouldEpicStatusNew() {
        Epic epic = new Epic("Эпик 1", "Описание", Status.NEW);
        final int epicId = manager.addEpic(epic);
        assertNotNull(manager.getEpic(epicId), "Эпик не найден");
        assertEquals(0, epic.getSubtasksList().size(), "список подзадач не пуст");
        Status status = manager.getEpic(epicId).getStatus();
        assertEquals(Status.NEW, status, "неверный статус эпика");

        Subtask subtask1 = new Subtask("Подзадача 1", "Описание", Status.NEW);
        Subtask subtask2 = new Subtask("Подзадача 2", "Описание", Status.NEW);
        final int subtask1Id = manager.addSubtask(subtask1, epicId);
        final int subtask2Id = manager.addSubtask(subtask2, epicId);
        status = manager.getEpic(epicId).getStatus();
        assertEquals(Status.NEW, status, "неверный статус эпика");

        Subtask subtask22 = new Subtask("Подзадача 2", "Описание", Status.DONE);
        manager.updateSubtask(subtask22, subtask2Id, epicId);
        status = manager.getEpic(epicId).getStatus();
        assertEquals(Status.IN_PROGRESS, status, "неверный статус эпика");

        Subtask subtask11 = new Subtask("Подзадача 1", "Описание", Status.IN_PROGRESS);
        Subtask subtask222 = new Subtask("Подзадача 2", "Описание", Status.IN_PROGRESS);
        manager.updateSubtask(subtask11, subtask1Id, epicId);
        manager.updateSubtask(subtask222, subtask2Id, epicId);
        status = manager.getEpic(epicId).getStatus();
        assertEquals(Status.IN_PROGRESS, status, "неверный статус эпика");

        Subtask subtask111 = new Subtask("Подзадача 1", "Описание", Status.DONE);
        Subtask subtask2222 = new Subtask("Подзадача 2", "Описание", Status.DONE);
        manager.updateSubtask(subtask111, subtask1Id, epicId);
        manager.updateSubtask(subtask2222, subtask2Id, epicId);
        status = manager.getEpic(epicId).getStatus();
        assertEquals(Status.DONE, status, "неверный статус эпика");
    }
}
