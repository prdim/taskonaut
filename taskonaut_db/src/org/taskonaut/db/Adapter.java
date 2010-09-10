/**
 * 
 */
package org.taskonaut.db;

import org.taskonaut.tasks.OneTask;
import org.taskonaut.tasks.TimeLogItem;

/**
 *
 * @author ProlubnikovDA
 */
public class Adapter {
    public static TimeLogStore adaptee(TimeLogItem t1, TimeLogStore t2) {
        t2.setID(t1.getId());
        t2.setEnd(t1.getEnd());
        t2.setTaskId(t1.getTaskId());
        t2.setComment(t1.getComment());
        return t2;
    }

    public static TimeLogItem adaptee(TimeLogStore s) {
        TimeLogItem t = new TimeLogItem();
        t.setId(s.getID());
        t.setEnd(s.getEnd());
        t.setTaskId(s.getTaskId());
        t.setComment(s.getComment());
        return t;
    }

    public static TaskStore adaptee(OneTask t1, TaskStore t2) {
        t2.setID(t1.getId());
        t2.setComment(t1.getComment());
//        t2.setExecute(t1.getExecute());
        t2.setName(t1.getName());
        t2.setOwner(t1.getOwner());
        t2.setPriority(t1.getPriority().name());
        t2.setRelation_id(t1.getRelation_id());
        t2.setState(t1.getState().name());
        t2.setType(t1.getType().name());
        return t2;
    }

    public static OneTask adaptee(TaskStore s) {
        OneTask t = new OneTask();
        t.setId(s.getID());
        t.setComment(s.getComment());
//        t.setExecute(s.getExecute());
        t.setName(s.getName());
        t.setOwner(s.getOwner());
        t.setPriority(OneTask.Priority.valueOf(s.getPriority()));
        t.setRelation_id(s.getRelation_id());
        t.setState(OneTask.Status.valueOf(s.getState()));
        t.setType(OneTask.Type.valueOf(s.getType()));
        return t;
    }

}

