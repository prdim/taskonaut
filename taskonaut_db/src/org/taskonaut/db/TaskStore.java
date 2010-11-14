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

package org.taskonaut.db;

import com.amazon.carbonado.PrimaryKey;
import com.amazon.carbonado.Storable;

import org.taskonaut.api.tasks.TaskItem;

/**
 *
 * @author Prolubnikov Dmitry
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
