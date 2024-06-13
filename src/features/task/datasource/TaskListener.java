package features.task.datasource;

public interface TaskListener {
    void updateData();
    void showErrorMessage(String msg);
}
