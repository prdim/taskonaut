/**
 * 
 */
package org.taskonaut.tasks;

import org.taskonaut.util.FileUtils;
import org.taskonaut.api.IChangeDataListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

/**
 * Хранит и отслеживает время выполнения задач
 * @author ProlubnikovDA
 */
public class TimeLogger {
    private static TimeLogger me = null;
    private ArrayList<TimeLogItem> list = /*Collections.synchronizedList(*/new ArrayList<TimeLogItem>()/*)*/;
    private TimeLogItem activeTask = null;
    private static final  String FILE_NAME = "task_log.xml";

    private Vector<IChangeDataListener> ls = new Vector<IChangeDataListener>();

    public TimeLogger() {
    }

    public void addListener(IChangeDataListener c) {
        ls.add(c);
    }

    public boolean removeListener(IChangeDataListener c) {
        return ls.remove(c);
    }

    public void clearAllListeners() {
        ls.clear();
    }

    private void onChange(TimeLogItem t) {
        for(IChangeDataListener i : ls) {
            try {
                i.onChange(t);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static TimeLogger getInstance() {
        if(me==null) {
            try {
                me = (TimeLogger) FileUtils.XmlToBean(FILE_NAME);
                // Во время последнего сохранения осталась висеть активная задача
                // Сохраним ее в логе с последним сохраненным статусом
                if(me.getActiveTask() != null) {
                    me.repairTask();
                }
            } catch (Exception e) {
                me = new TimeLogger();
            }
        }
        return me;
    }

    public void save() {
        try {
            FileUtils.beanToXML(me, FILE_NAME);
        } catch (Exception e) {
            System.err.println("Error saving task log " + e.getMessage());
        }
        System.out.println("Saved task log successfully");
    }

    public ArrayList<TimeLogItem> getTaskLog(long taskId) {
        ArrayList<TimeLogItem> lst = new ArrayList<TimeLogItem>();
        for(TimeLogItem t : list) {
            if(t.getTaskId()==taskId) {
                lst.add(t);
            }
        }
        return lst;
    }

    public long getTaskPeriod(long taskId) {
        long lt = 0;
        for(TimeLogItem t : list) {
            if(t.getTaskId()==taskId) lt += t.getPeriod();
        }
        return lt;
    }

    public ArrayList<TimeLogItem> getList() {
        return list;
    }

    public void setList(ArrayList<TimeLogItem> list) {
        this.list = list;
    }

    public TimeLogItem getActiveTask() {
        return activeTask;
    }

    public void setActiveTask(TimeLogItem activeTask) {
        this.activeTask = activeTask;
    }

    public void startTask(long taskId) {
        activeTask = new TimeLogItem(taskId);
        onChange(activeTask);
    }

    public void stopTask() {
        activeTask.stop();
        addLogItem(activeTask);
        activeTask = null;
        onChange(activeTask);
    }

    protected void repairTask() {
        if(activeTask==null) return;
        addLogItem(activeTask);
        activeTask = null;
    }

    public void updateTask() {
        activeTask.stop();
        onChange(activeTask);
    }

    public void addLogItem(TimeLogItem t) {
        if(t.getPeriod()<60000) return; // Периоды активности меньше минуты не сохраняем
        list.add(t);
        onChange(null);
    }

    public TimeLogItem getLogItem(long id) {
        for(TimeLogItem t : list) {
            if(t.getId()==id) return t;
        }
        return null;
    }

    public void removeLogItem(long id) {
        for(TimeLogItem t : list) {
            if(t.getId()==id) {
                list.remove(t);
                return;
            }
        }
        onChange(null);
    }

    public void removeTaskLogItem(long task_id) {
        List<TimeLogItem> tList = getTaskLog(task_id);
        for(TimeLogItem t : tList) {
            list.remove(t);                
        }
        onChange(null);
    }
}
