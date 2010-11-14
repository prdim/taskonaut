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

package org.taskonaut.api;

import javax.swing.Action;


/**
 * @author Prolubnikov Dmitry
 *
 */
public interface IMenuAction {
	
	/**
	 * Имя действия
	 * @return
	 */
	public String getActionName();
	
	/**
	 * Путь в меню (например: "Файл|Экспорт") без заключительного пункта
	 * @return
	 */
	public String getMenuPath();
	
	/**
	 * Приоритет данного пункта - для сортировки пунктов в меню
	 * @return
	 */
	public int getPriority();
}
