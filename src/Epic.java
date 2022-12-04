import java.util.ArrayList;
import java.util.Objects;

public class Epic extends Task {
    private ArrayList<Integer> subtasksList;

    public Epic(String nameTask, String description, String status) {
        super(nameTask, description, status);
        subtasksList = new ArrayList<>();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Epic epic = (Epic) o;
        return Objects.equals(subtasksList, epic.subtasksList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), subtasksList);
    }

    @Override
    public String toString() {
        return "Epic{" +
                "nameTask='" + getNameTask() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", status='" + getStatus() + '\'' +
                ", subtasksList.size=" + subtasksList.size() +
                '}';
    }

    public ArrayList<Integer> getSubtasksList() {
        return subtasksList;
    }

    public void setSubtasksList(Integer idSubtask) {

        subtasksList.add(idSubtask);
    }
}