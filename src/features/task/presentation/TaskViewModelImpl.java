package features.task.presentation;

import features.task.datasource.*;
import features.task.model.Task;

import java.util.ArrayList;
import java.util.List;

public class TaskViewModelImpl implements TaskPublisher, TaskViewModel {
    private final List<TaskListener> viewListeners = new ArrayList<>();
    private final TaskDatabase taskDatabase;

    public TaskViewModelImpl(TaskDatabase taskDatabase) {
        this.taskDatabase = taskDatabase;
    }

    @Override
    public void subscribe(TaskListener taskObserver) {
        viewListeners.add(taskObserver);
    }

    @Override
    public void addTask(String description) {
        if(description == null || description.isEmpty()) {
            notifyError("Descrição é obrigatório");
        } else {
            taskDatabase.insertTask(description);

            notifyDataChanged();
        }
    }

    @Override
    public void updateTask(int taskId, String description, boolean isDone) {
        if(description == null || description.isEmpty()) {
            notifyError("Descrição é obrigatório");
        } else  {
            taskDatabase.updateTask(taskId, description, isDone);

            notifyDataChanged();
        }
    }

    @Override
    public void setDone(int taskId) {
        taskDatabase.markTaskAsDone(taskId);

        notifyDataChanged();
    }

    @Override
    public List<Task> getTasks() {
        return taskDatabase.getTasks();
    }

    //Notificar view sobre um errro
    private void notifyError(String msg) {
        for(TaskListener listener : viewListeners) {
            listener.showErrorMessage(msg);
        }
    }

    //Notificar view para atualizar dados
    private void notifyDataChanged() {
        for(TaskListener listener : viewListeners) {
            listener.updateData();
        }
    }
}
