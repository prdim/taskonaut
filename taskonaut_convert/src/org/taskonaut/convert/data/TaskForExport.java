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

package org.taskonaut.convert.data;

import java.util.ArrayList;
import java.util.List;

import org.taskonaut.api.tasks.TaskItem;
import org.taskonaut.api.tasks.TimeLogItem;

/**
 * Реализация TaskItem для возможности экспорта
 * 
 * @author Prolubnikov Dmitry
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
	private List<TimeLogForExport> times = new ArrayList<TimeLogForExport>(); 
	
	public TaskForExport() {
		// конструктор по умолчанию
	}

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
	
	public void addTimes(List<TimeLogItem> t) {
		for(TimeLogItem i : t) {
			times.add(new TimeLogForExport(i));
		}
	}
	
	public List<TimeLogForExport> getTimes() {
		return times;
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
		return priority;
	}

	@Override
	public void setPriority(String priority) {
		this.priority = priority;
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
		return state;
	}

	@Override
	public void setState(String state) {
		this.state = state;
	}

	@Override
	public String getType() {
		return type;
	}

	@Override
	public void setType(String type) {
		this.type = type;
	}

}
