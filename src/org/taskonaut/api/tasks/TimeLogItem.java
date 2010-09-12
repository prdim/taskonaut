package org.taskonaut.api.tasks;

/**
 * Интерфейс для элемента лога времени.
 * Конкретные экземпляры будут создаваться фабрикой или маппится из конкретной БД
 * 
 * @author spec
 *
 */
public abstract class TimeLogItem {

    public abstract String getComment();

    public abstract void setComment(String comment);

    public abstract long getEnd();

    public abstract void setEnd(long end);

    public abstract long getID();

    public abstract void setID(long id);

    public abstract long getTaskId();

    public abstract void setTaskId(long taskId);

    public void stop() {
        setEnd(System.currentTimeMillis());
    }

    public long getPeriod() {
        return getEnd() - getID();
    }
}
