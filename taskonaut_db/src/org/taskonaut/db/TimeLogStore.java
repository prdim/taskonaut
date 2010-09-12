/**
 * 
 */
package org.taskonaut.db;

import org.taskonaut.api.tasks.TimeLogItem;

import com.amazon.carbonado.PrimaryKey;
import com.amazon.carbonado.Storable;

/**
 *
 * @author ProlubnikovDA
 */
@PrimaryKey("ID")
public abstract class TimeLogStore extends TimeLogItem implements Storable {

}
