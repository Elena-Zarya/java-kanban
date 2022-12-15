package tasks;

import manager.Status;

import java.util.Objects;

public class Task {
    private String nameTask;
    private String description;
    private Status status;
    private Integer id;

    public Task(String nameTask, String description, Status status) {
        this.nameTask = nameTask;
        this.description = description;
        this.status = status;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return Objects.equals(nameTask, task.nameTask) && Objects.equals(description, task.description) && Objects.equals(status, task.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nameTask, description, status);
    }

    @Override
    public String toString() {
        return "tasks.Task{" +
                "nameTask='" + nameTask + '\'' +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                ", id=" + id +
                '}';
    }
}

