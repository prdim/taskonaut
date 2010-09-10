/**
 * 
 */
package org.taskonaut.api.tasks;

/**
 * @author spec
 *
 */
public abstract class TaskItem {
	public enum Type {задача, проект, мысль};
    public enum Status {запланирована, выполняется, выполнена, отложена, отменена};
    public enum Priority {низкий, средний, высокий, критично};
	
	public abstract long getID();
	public abstract void setID(long id);

	public abstract String getComment();
	public abstract void setComment(String comment);

	public abstract long getExecute();
	public abstract void setExecute(long execute);

	public abstract String getName();
	public abstract void setName(String name);

	public abstract String getOwner();
	public abstract void setOwner(String owner);

	public abstract String getPriority();
	public abstract void setPriority(String priority);

	public abstract long getRelation_id();
	public abstract void setRelation_id(long relation_id);

	public abstract String getState();
	public abstract void setState(String state);

	public abstract String getType();
	public abstract void setType(String type);
	
	
}
