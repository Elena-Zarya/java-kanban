package tasks;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Objects;

public class Subtask extends Task {
    private Integer epicIds;

    public Subtask(String nameTask, String description, Status status, int duration, int year, Month month, int
            dayOfMonth, int hour, int minute, Integer epicId) {
        super(nameTask, description, status, duration, year, month, dayOfMonth, hour, minute);
        this.epicIds = epicId;
    }

    public Subtask(String nameTask, String description, Status status) {
        super(nameTask, description, status);
    }

    public Subtask(Integer id, String nameTask, Status status, String description, Integer epicId,
                   LocalDateTime startTime, Integer duration) {
        super(id, nameTask, status, description, startTime, duration);
        this.epicIds = epicId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Subtask subtask = (Subtask) o;
        return Objects.equals(epicIds, subtask.epicIds);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), epicIds);
    }

    @Override
    public Type getType() {
        return Type.SUBTASK;
    }

    public Integer getEpicId() {
        return epicIds;
    }

    public void setEpicId(int epicId) {
        this.epicIds = epicId;
    }

    @Override
    public String toString() {
        return "tasks.Subtask{" +
                "type='" + getType() + '\'' +
                ", id=" + getId() + '\'' +
                ", nameTask='" + getNameTask() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", status='" + getStatus() + '\'' +
                ", startTime=" + getStartTime() + '\'' +
                ", duration=" + getDuration() + '\'' +
                ", epicIds=" + epicIds +
                '}';
    }
}
