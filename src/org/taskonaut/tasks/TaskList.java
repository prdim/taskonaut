/**
 * 
 */
package org.taskonaut.tasks;

import org.taskonaut.util.FileUtils;
import org.taskonaut.api.IChangeDataListener;
import java.util.HashMap;
import java.util.Vector;

/**
 * Список задач
 * @author ProlubnikovDA
 */
public class TaskList {
    private static TaskList me = null;
    private static final String FILE_NAME = "task_list.xml";
    private HashMap<Long,OneTask> taskList = new HashMap<Long,OneTask>();

    private Vector<IChangeDataListener> ls = new Vector<IChangeDataListener>();

    public TaskList() {
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

    private void onChange(OneTask t) {
        for(IChangeDataListener i : ls) {
            try {
                i.onChange(t);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static TaskList getInstance() {
        if(me == null) {
            try {
                me = (TaskList) FileUtils.XmlToBean(FILE_NAME);
                
            } catch (Exception e) {
                me = new TaskList();
            }
        }
        return me;
    }


    public void save() {
        try {
            FileUtils.beanToXML(me, FILE_NAME);
        } catch (Exception e) {
            System.err.println("Error saving task data " + e.getMessage());
        }
        System.out.println("Saved task data successfully");
    }
    
    public void putTask(OneTask t) {
        taskList.put(t.getId(), t);
        onChange(t);
    }

    public OneTask getTask(long id) {
        return taskList.get(id);
    }

    public void removeTask(long id) {
        taskList.remove(id);
        onChange(null);
    }

    public HashMap<Long, OneTask> getTaskList() {
        return taskList;
    }

    public void setTaskList(HashMap<Long, OneTask> taskList) {
        this.taskList = taskList;
    }

    public Vector<OneTask> getActiveTasks() {
        Vector<OneTask> v = new Vector<OneTask>();
        for(OneTask i : taskList.values()) {
            if(i.getState()==OneTask.Status.выполняется)
                v.add(i);
        }
        return v;
    }
}
