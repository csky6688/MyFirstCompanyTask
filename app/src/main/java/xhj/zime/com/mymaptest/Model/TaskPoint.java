package xhj.zime.com.mymaptest.Model;

public class TaskPoint {
    private String taskPointName;
    private int recordStatus;

    public TaskPoint(String taskPointName, int recordStatus) {
        this.taskPointName = taskPointName;
        this.recordStatus = recordStatus;
    }

    public String getTaskPointName() {
        return taskPointName;
    }

    public void setTaskPointName(String taskPointName) {
        this.taskPointName = taskPointName;
    }

    public int getRecordStatus() {
        return recordStatus;
    }

    public void setRecordStatus(int recordStatus) {
        this.recordStatus = recordStatus;
    }
}
