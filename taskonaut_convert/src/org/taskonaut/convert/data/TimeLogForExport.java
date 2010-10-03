/**
 * 
 */
package org.taskonaut.convert.data;

import org.taskonaut.api.tasks.TimeLogItem;


/**
 * @author spec
 *
 */
public class TimeLogForExport extends TimeLogItem {
	private String comment = "";
	private long end = 0;
	private long ID = 0;
	private long taskId = 0;
	
	public TimeLogForExport() {
		// конструктор по умолчанию
	}
	
	public TimeLogForExport(TimeLogItem t) {
		comment = t.getComment();
		end = t.getEnd();
		ID = t.getID();
		taskId = t.getTaskId();
	}

	@Override
	public String getComment() {
		return comment;
	}

	@Override
	public void setComment(String comment) {
		this.comment = comment;
	}

	@Override
	public long getEnd() {
		return end;
	}

	@Override
	public void setEnd(long end) {
		this.end = end;
	}

	@Override
	public long getID() {
		return ID;
	}

	@Override
	public void setID(long id) {
		this.ID = id;
	}

	@Override
	public long getTaskId() {
		return taskId;
	}

	@Override
	public void setTaskId(long taskId) {
		this.taskId = taskId;
	}

}
