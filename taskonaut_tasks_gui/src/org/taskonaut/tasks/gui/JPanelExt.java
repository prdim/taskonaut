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

package org.taskonaut.tasks.gui;

import javax.swing.JPanel;

/**
 * Расширение панели - добавление методов проверки и подготовки перед закрытием
 * 
 * @author Prolubnikov Dmitry
 *
 */
public abstract class JPanelExt extends JPanel {
	protected ParentPanel parentPanel = null;
	
	/**
	 * Возвращае истину, если диалог с панелью можно закрыть по "Ок"
	 * @return
	 */
	public abstract boolean checkOk();
	
	/**
	 * Вызывается перед закрытием диалога с панелью
	 */
	public abstract void beforeClose();
	
	/**
	 * Установить родительскую панель 
	 * (чтобы дочерняя смогла инициировать ее закрытие при необходимости)
	 * @param p
	 */
	public void setParentPanel(ParentPanel p) {
		parentPanel = p;
	}
}
