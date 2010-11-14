/*
 * Copyright 2010 Prolubnikov Dmitry
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.taskonaut.api.tasks;

/**
 * @author Prolubnikov Dmitry
 *
 */
public abstract class TaskItem {
	public enum Type {задача, проект, мысль};
    public enum Status {запланирована, выполняется, выполнена, отложена, отменена};
    public enum Priority {низкий, средний, высокий, критично};
    
    public Type getTypeId() {
    	return Type.valueOf(getType());
    }
    
    public Status getStateId() {
    	return Status.valueOf(getState());
    }
    
    public Priority getPriorityId() {
    	return Priority.valueOf(getPriority());
    }
	
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
