/**
 * 
 */
package org.taskonaut.api;

/**
* Интерфейс слушателя, оповещаемого об изменениях объекта
* 
* @author ProlubnikovDA
*/
public interface IChangeDataListener {
   public void onChange(Object o);
}