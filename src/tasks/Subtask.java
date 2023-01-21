package tasks;

import manager.Status;
import manager.Type;

import java.util.Objects;

public class Subtask extends Task {
    private Integer epicId;

    public Subtask(String nameTask, String description, Status status) {
        super(nameTask, description, status);
    }

    public Subtask(Integer id, Type type, String nameTask, Status status, String description, Integer epicId) {
        super( id, type, nameTask, status, description);
        this.epicId = epicId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Subtask subtask = (Subtask) o;
        return Objects.equals(epicId, subtask.epicId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), epicId);
    }

    public Integer getEpicId() {
        return epicId;
    }

    public void setEpicId(int epicId) {
        this.epicId = epicId;
    }

    @Override
    public String toString() {
        return "tasks.Subtask{" +
                "type='" + getType() + '\'' +
                "nameTask='" + getNameTask() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", status='" + getStatus() + '\'' +
                ", id=" + getId() +
                ", epicId=" + epicId +
                '}';
    }
}
