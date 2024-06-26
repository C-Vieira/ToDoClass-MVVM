package features.task.presentation;

import features.task.model.Task;

import java.util.List;

public interface TaskViewModel {
    void addTask(String description);
    void updateTask(int taskId, String description, boolean isDone);
    void setDone(int taskId);
    List<Task> getTasks();
}
