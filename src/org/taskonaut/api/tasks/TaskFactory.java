/**
 * 
 */
package org.taskonaut.api.tasks;

/**
 * @author spec
 *
 */
public class TaskFactory {

	public static TaskItem createNewTask(String name) {
		TaskItem t = new TaskItem() {
			private long id = 0;
		    private long relation_id = 0;
		    private String name = "";
		    private String comment = "";
		    private Type type = Type.задача;
		    private Status state = Status.запланирована;
		    private Priority priority = Priority.средний;
		    private long execute = 0;
		    private String owner = "";

			@Override
			public long getID() {
				return id;
			}

			@Override
			public void setID(long id) {
				this.id = id;
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
			public long getExecute() {
				return execute;
			}

			@Override
			public void setExecute(long execute) {
				this.execute = execute;
			}

			@Override
			public String getName() {
				return name;
			}

			@Override
			public void setName(String name) {
				this.name = name;
			}

			@Override
			public String getOwner() {
				return owner;
			}

			@Override
			public void setOwner(String owner) {
				this.owner = owner;
			}

			@Override
			public String getPriority() {
				return priority.name();
			}

			@Override
			public void setPriority(String priority) {
				this.priority = Priority.valueOf(priority);
			}

			@Override
			public long getRelation_id() {
				return relation_id;
			}

			@Override
			public void setRelation_id(long relation_id) {
				this.relation_id = relation_id;
			}

			@Override
			public String getState() {
				return state.name();
			}

			@Override
			public void setState(String state) {
				this.state = Status.valueOf(state);
			}

			@Override
			public String getType() {
				return type.name();
			}

			@Override
			public void setType(String type) {
				this.type = Type.valueOf(type);
			}
			
		};
		t.setID(System.currentTimeMillis());
		t.setName(name);
		return t;
	}
}
