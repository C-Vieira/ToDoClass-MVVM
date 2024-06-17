package features.task.presentation;

import features.task.datasource.*;
import features.task.model.Task;

import java.util.ArrayList;
import java.util.List;

//Classe que define o ViewModel da aplicação
//Antigo Controller
public class TaskViewModelImpl implements TaskPublisher, TaskViewModel {
    private final List<TaskListener> viewListeners = new ArrayList<>(); //<-- Lista de observadores (Views)
    private final TaskDatabase taskDatabase; //<-- Referência para a classe TaskDAO (Model)

    //Construtor + Injeção de dependência para comunicação com o Model
    public TaskViewModelImpl(TaskDatabase taskDatabase) {
        this.taskDatabase = taskDatabase;
    }

    @Override
    public void addTask(String description) {
        if(description == null || description.isEmpty()) {
            notifyError("Descrição é obrigatório");
        } else {
            taskDatabase.insertTask(description); //<-- Requisição de operação ao Model

            notifyDataChanged();//<-- O ViewModel fica então responsável por notificar alterações para as
        }                          // Views que o observam, atualizando-as
    }

    @Override
    public void updateTask(int taskId, String description, boolean isDone) {
        if(description == null || description.isEmpty()) {
            notifyError("Descrição é obrigatório");//<-- Notifica erros também
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

    @Override
    public void subscribe(TaskListener taskObserver) {
        viewListeners.add(taskObserver);
    }

    //Notificar View sobre um erro
    private void notifyError(String msg) {
        for(TaskListener listener : viewListeners) {
            listener.showErrorMessage(msg);
        }
    }

    //Notificar View para atualizar dados
    private void notifyDataChanged() {
        for(TaskListener listener : viewListeners) {
            listener.updateData();
        }
    }
}
