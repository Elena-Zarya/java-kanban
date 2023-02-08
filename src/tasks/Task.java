package tasks;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Objects;

public class Task {
    private Type type;
    private String nameTask;
    private String description;
    private Status status;
    private Integer id;
    private Integer epicId;
    private Integer duration;
    private LocalDateTime startTime;

    public Task(String nameTask, String description, Status status, int duration, int year, Month month, int dayOfMonth,
                int hour, int minute) {
        this.nameTask = nameTask;
        this.description = description;
        this.status = status;
        this.duration = duration;
        startTime = LocalDateTime.of(year, month, dayOfMonth, hour, minute);
    }

    public Task(String nameTask, String description, Status status) {
        this.nameTask = nameTask;
        this.description = description;
        this.status = status;
    }

    public Task(Integer id, Type type, String nameTask, Status status, String description, LocalDateTime startTime, Integer
            duration) {
        this.type = type;
        this.nameTask = nameTask;
        this.description = description;
        this.status = status;
        this.id = id;
        this.duration = duration;
        this.startTime = startTime;
    }

    public String getNameTask() {
        return nameTask;
    }

    public void setNameTask(String nameTask) {
        this.nameTask = nameTask;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Integer getEpicId() {
        return epicId;
    }

    public void setEpicId(Integer epicId) {
        this.epicId = epicId;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return duration == task.duration && type == task.type && Objects.equals(nameTask, task.nameTask) &&
                Objects.equals(description, task.description) && status == task.status && Objects.equals(id, task.id)
                && Objects.equals(epicId, task.epicId) && Objects.equals(startTime, task.startTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, nameTask, description, status, id, epicId, duration, startTime);
    }

    @Override
    public String toString() {
        return "tasks.Task{" +
                "type='" + type + '\'' +
                ", id=" + id + '\'' +
                ", nameTask='" + nameTask + '\'' +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                ", starTime=" + startTime + '\'' +
                ", duration=" + duration +
                '}';
    }

    public LocalDateTime getEndTime() {
        return startTime.plusMinutes(duration);
    }
}

