/**
 * 
 */
package org.taskonaut.db;

import com.amazon.carbonado.PrimaryKey;
import com.amazon.carbonado.Storable;

/**
 *
 * @author ProlubnikovDA
 */
@PrimaryKey("ID")
public interface TimeLogStore extends Storable {
    long getID();
    void setID(long id);

    String getComment();
    void setComment(String comment);

    long getEnd();
    void setEnd(long end);

    long getTaskId();
    void setTaskId(long taskId);
}
