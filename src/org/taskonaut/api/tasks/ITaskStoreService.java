package org.taskonaut.api.tasks;

import java.util.Collection;
import java.util.List;

/**
 * Интерфейс сервиса, который предоставляет услугу хранения списка задач
 * @author spec
 *
 */
public interface ITaskStoreService {
	
	/**
	 * Извлекает из БД все задачи
	 * @return
	 */
	public List<TaskItem>readAllTasks();
	
	/**
	 * Сохраняет в БД список задач
	 * @param c
	 */
	public void saveAllTasks(List<TaskItem> c);
	
	/**
	 * Извлекает из БД все записи о времени исполнения
	 * @return
	 */
	public List<TimeLogItem>readAllTimeLogItems();
	
	/**
	 * Извлекает из БД логи конкретной задачи
	 * @param task_id
	 * @return
	 */
	public List<TimeLogItem>readTimeLogItems(long task_id);
	
	/**
	 * Сохраняет список логов времени в БД
	 * @param c
	 */
	public void saveAllTimeLogItems(List<TimeLogItem> c);
	
	/**
	 * Сохранение задачи в БД
	 * @param t
	 */
	public void saveTask(TaskItem t);
	
	/**
	 * Сохранение отметки времени
	 * @param t
	 */
	public void saveTimeLog(TimeLogItem t);
	
	/**
	 * Создает новую задачу
	 * @param name
	 * @return
	 */
	public TaskItem createNewTask(String name);
	
	/**
	 * Создает и добавляет в БД новую отметку времени
	 * @param start
	 * @param duration
	 * @param task_id
	 * @return
	 */
	public TimeLogItem addTime(long start, long duration, long task_id);
	
	/**
	 * Читает из БД задачу с заданным ИД
	 * @param task_id
	 * @return
	 */
	public TaskItem readTask(long task_id);
	
	/**
	 * Удаление задачи из БД
	 * @param task_id
	 */
	public void deleteTask(long task_id);
	
	/**
	 * Удаление из БД отметки времени
	 * @param time_log_id
	 */
	public void deleteTimeLog(long time_log_id);
	
	/**
	 * Удаление всего лога времени по заданной задаче
	 * @param task_id
	 */
	public void deleteAllTimeLog(long task_id);
}
