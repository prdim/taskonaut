/**
 * 
 */
package org.taskonaut.tasks.gui;

/**
 * Родительская панель (фрейм, диалог и т.п.), содержащая JPanelExt
 * @author spec
 *
 */
public interface ParentPanel {
	/**
	 * Закрыть панель. Вызывается из дочерней панели.
	 */
	public void onClose();
}
