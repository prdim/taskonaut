/**
 * 
 */
package org.taskonaut.convert.data;

import org.taskonaut.api.tasks.TaskItem;

/**
 * Реализация TaskItem для возможности экспорта
 * 
 * @author spec
 *
 */
public class TaskForExport extends TaskItem {
	private long ID = 0;
	private String comment = "";
	private long execute = 0;
	private String name = "";
	private String owner = "";
	private String priority = "";
	private long relation_id = 0;
	private String state = "";
	private String type = "";
	
	public TaskForExport(TaskItem t) {
		ID = t.getID();
		comment = t.getComment();
		execute = t.getExecute();
		name = t.getName();
		owner = t.getOwner();
		priority = t.getPriority();
		relation_id = t.getRelation_id();
		state = t.getState();
		type = t.getType();
	}

	@Override
	public long getID() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setID(long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getComment() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setComment(String comment) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public long getExecute() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setExecute(long execute) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setName(String name) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getOwner() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setOwner(String owner) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getPriority() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setPriority(String priority) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public long getRelation_id() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setRelation_id(long relation_id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getState() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setState(String state) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setType(String type) {
		// TODO Auto-generated method stub
		
	}

}
