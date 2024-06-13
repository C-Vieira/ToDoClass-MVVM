package di;

import features.task.datasource.TaskDAO;
import features.task.datasource.TaskDatabase;
import features.task.datasource.TaskPublisher;
import features.task.presentation.TaskViewModel;
import features.task.presentation.TaskViewModelImpl;
import features.task.presentation.TaskView;
import features.task.presentation.TaskViewImpl;

public class ServiceLocator {

    // Instancia para o singleton
    private static ServiceLocator instance;

    public static ServiceLocator getInstance() {
        if(instance == null) {
            instance = new ServiceLocator();
        }

        return instance;
    }

    private TaskDAO taskDAO;

    private TaskDAO getTaskDao() {
        if(taskDAO == null) {
            taskDAO = new TaskDAO();
        }

        return taskDAO;
    }

    private TaskViewModel taskViewModel;

    private TaskViewModel getTaskViewModel(){
        if(taskViewModel == null) {
            taskViewModel = new TaskViewModelImpl(getTaskDatabase());
        }

        return taskViewModel;
    }

    public TaskDatabase getTaskDatabase() {
        return getTaskDao();
    }

    public TaskPublisher getTaskPublisher(){
        return (TaskPublisher) getTaskViewModel();
    }

    public TaskView getTaskView() {
        return new TaskViewImpl(getTaskPublisher(), getTaskViewModel());
    }
}
