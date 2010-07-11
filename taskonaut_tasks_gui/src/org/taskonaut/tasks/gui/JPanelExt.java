/**
 * 
 */
package org.taskonaut.tasks.gui;

import javax.swing.JPanel;

/**
 * Расширение панели - добавление методов проверки и подготовки перед закрытием
 * 
 * @author spec
 *
 */
public abstract class JPanelExt extends JPanel {
	
	/**
	 * Возвращае истину, если диалог с панелью можно закрыть по "Ок"
	 * @return
	 */
	public abstract boolean checkOk();
	
	/**
	 * Вызывается перед закрытием диалога с панелью
	 */
	public abstract void beforeCloseOk();
	
	/**
	 * Вызывается перед закрытием диалога с панелью
	 */
	public abstract void beforeCloseCancel();
}
