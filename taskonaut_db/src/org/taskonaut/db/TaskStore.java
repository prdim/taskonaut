/**
 * 
 */
package org.taskonaut.db;

import com.amazon.carbonado.PrimaryKey;
import com.amazon.carbonado.Storable;

import org.taskonaut.api.tasks.TaskItem;
import org.taskonaut.tasks.OneTask.Priority;
import org.taskonaut.tasks.OneTask.Status;
import org.taskonaut.tasks.OneTask.Type;
import java.util.Date;

/**
 *
 * @author ProlubnikovDA
 */
@PrimaryKey("ID")
public abstract class TaskStore extends TaskItem implements Storable {
//    long getID();
//    void setID(long id);
//
//    String getComment();
//    void setComment(String comment);
//
//    Date getExecute();
//    void setExecute(Date execute);
//
//    String getName();
//    void setName(String name);
//
//    String getOwner();
//    void setOwner(String owner);
//
//    String getPriority();
//    void setPriority(String priority);
//
//    long getRelation_id();
//    void setRelation_id(long relation_id);
//
//    String getState();
//    void setState(String state);
//
//    String getType();
//    void setType(String type);
}
