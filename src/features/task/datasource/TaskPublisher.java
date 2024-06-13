package features.task.datasource;

public interface TaskPublisher {
    void subscribe(TaskListener taskObserver);
}
