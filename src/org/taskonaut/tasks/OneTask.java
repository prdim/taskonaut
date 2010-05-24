/**
 * 
 */
package org.taskonaut.tasks;

import java.util.Date;

/**
 * Бин задачи
 * @author ProlubnikovDA
 */
public class OneTask {
    public enum Type {задача, проект, мысль};
    public enum Status {запланирована, выполняется, выполнена, отложена, отменена};
    public enum Priority {низкий, средний, высокий, критично};
    private long id = 0;
    private long relation_id = 0;
    private String name = "";
    private String comment = "";
    private Type type = Type.задача;
    private Status state = Status.запланирована;
    private Priority priority = Priority.средний;
    private Date execute = new Date();
    private String owner = "";

    public OneTask() {
        id = System.currentTimeMillis();
    }

    public OneTask(String name) {
        id = System.currentTimeMillis();
        this.name = name;
    }



    public OneTask(long id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getExecute() {
        return execute;
    }

    public void setExecute(Date execute) {
        this.execute = execute;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public long getRelation_id() {
        return relation_id;
    }

    public void setRelation_id(long relation_id) {
        this.relation_id = relation_id;
    }

    public Status getState() {
        return state;
    }

    public void setState(Status state) {
        this.state = state;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    @Override
    public String toString() {
        try {
            if(TimeLogger.getInstance().getActiveTask()!=null && id==TimeLogger.getInstance().getActiveTask().getTaskId())
                return ">>> " + name;
            if(state==Status.выполнена || state==Status.отменена)
                return "[x] " + name;
        } catch (Exception e) {}
        return name;
    }


}
