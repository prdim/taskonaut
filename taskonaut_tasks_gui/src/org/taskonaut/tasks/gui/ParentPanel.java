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
	 * Закрыть панель с сохранением данных. Вызывается из дочерней панели.
	 */
	public void onCloseOk();
	
	/**
	 * Закрыть панель без сохранения данных. Вызывается из дочерней панели.
	 */
	public void onCloseCancel();
}
