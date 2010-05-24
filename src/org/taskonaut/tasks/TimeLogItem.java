/**
 * 
 */
package org.taskonaut.tasks;

/**
 * Элемент отслеживания времени выполнения задачи
 * @author ProlubnikovDA
 */
public class TimeLogItem {
    private long id = 0;
    private long end = 0;
    private long taskId = 0;
    private String comment = "";

    public TimeLogItem() {
    }

    public TimeLogItem(long task_id) {
        this.taskId = task_id;
        id = System.currentTimeMillis();
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public long getEnd() {
        return end;
    }

    public void setEnd(long end) {
        this.end = end;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getTaskId() {
        return taskId;
    }

    public void setTaskId(long taskId) {
        this.taskId = taskId;
    }

    public void stop() {
        end = System.currentTimeMillis();
    }

    public long getPeriod() {
        return end - id;
    }
}
