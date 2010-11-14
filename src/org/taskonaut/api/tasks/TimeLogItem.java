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
 * Интерфейс для элемента лога времени.
 * Конкретные экземпляры будут создаваться фабрикой или маппится из конкретной БД
 * 
 * @author Prolubnikov Dmitry
 *
 */
public abstract class TimeLogItem {

    public abstract String getComment();

    public abstract void setComment(String comment);

    public abstract long getEnd();

    public abstract void setEnd(long end);

    public abstract long getID();

    public abstract void setID(long id);

    public abstract long getTaskId();

    public abstract void setTaskId(long taskId);

    public void stop() {
        setEnd(System.currentTimeMillis());
    }

    public long getPeriod() {
        return getEnd() - getID();
    }
}
