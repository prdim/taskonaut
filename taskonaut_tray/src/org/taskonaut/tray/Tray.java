/**
 * 
 */
package org.taskonaut.tray;

import java.awt.AWTException;
import java.awt.Image;
import java.awt.Menu;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.Vector;

import javax.swing.ImageIcon;

import org.taskonaut.api.tasks.ActiveTask;
import org.taskonaut.api.tasks.TaskItem;
import org.taskonaut.api.tasks.TaskStoreServiceConnector;
import org.taskonaut.app.MainApplication;

/**
*
* @author ProlubnikovDA
*/
public class Tray /*implements IChangeDataListener*/{
   private static Tray me = null;
   static TrayIcon trayIcon = null;
   static SystemTray tray = SystemTray.getSystemTray();
//   TaskonautView win;
   static Image image1;
   static Image image2;
   List<TaskItem> taskMenu = new ArrayList<TaskItem>();

   public static Tray getInstance() {
       if(me==null) {
           me = new Tray();
       }
       return me;
   }

//   public void onChange(Object o) {
//       System.out.println("!");
//       trayIcon.getPopupMenu().remove(1);
//       trayIcon.getPopupMenu().insert(getMenu(), 1);
//   }
   
//   public void setWin(TaskonautView win) {
//       this.win = win;
//   }

  public  void sTrayShow(){
       if (SystemTray.isSupported()) {
    	   System.out.println("Tray icon size: " + SystemTray.getSystemTray().getTrayIconSize().getHeight());
           image1 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("resources/icons/clock-16.png"));
           image2 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("resources/icons/clock_play-16.png"));
//           image1 = ((ImageIcon)MainApplication.getInstance().getContext().
//        		   getResourceMap(Tray.class).getIcon("ico_main")).getImage();
//           image2 = ((ImageIcon)MainApplication.getInstance().getContext().
//        		   getResourceMap(Tray.class).getIcon("ico_play")).getImage();
           ActionListener listener = new ActionListener() {
               public void actionPerformed(ActionEvent e) {
                   MainApplication.getInstance().quit(null);
               }
           };
           ActionListener hideListener = new ActionListener() {
               public void actionPerformed(ActionEvent e) {
                   if(((MainApplication)MainApplication.getInstance()).getMainFrame().isVisible())
                	   ((MainApplication)MainApplication.getInstance()).getMainFrame().setVisible(false);
                   else
                	   ((MainApplication)MainApplication.getInstance()).getMainFrame().setVisible(true);
               }
           };
           ActionListener stopListener = new ActionListener() {
               public void actionPerformed(ActionEvent e) {
                   if (!ActiveTask.getInstance().isActive()) {
                       return;
                   }
                   ActiveTask.getInstance().stop();
                   ActiveTask.getInstance().save();
                   Tray.getInstance().message("Заканчиваю...");
               }
           };

           PopupMenu popup = new PopupMenu();

           MenuItem itemProps = new MenuItem("Остановить");
           itemProps.addActionListener(stopListener);
           popup.add(itemProps);
           popup.add(getMenu());
           MenuItem defaultItem = new MenuItem("Выход");
           defaultItem.addActionListener(listener);
           popup.add(defaultItem);

           trayIcon = new TrayIcon(image1, "Скрыть", popup);
           trayIcon.addActionListener(hideListener);
           try {
               tray.add(trayIcon);
           } catch (AWTException ex) {
//               Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
           }
       } else {
    	   // TODO Как-то сообщить, что трей не доступен
       }
       TrackerThread runner = new TrackerThread();
       runner.start();
//       TaskList.getInstance().addListener(this);
   }
  
  public Menu getMenu() {
      Menu m = new Menu("Запустить");
      try {
    	  taskMenu = TaskStoreServiceConnector.getStore().getTasksForStatus(TaskItem.Status.выполняется);
      } catch (Exception e) {
		e.printStackTrace();
	}
      ActionListener listener = new ActionListener() {
           public void actionPerformed(ActionEvent e) {
               String s = e.getActionCommand();
               for(TaskItem i : taskMenu) {
                   if(s.equals(i.getName())){
                       if(ActiveTask.getInstance().isActive()) {
                           ActiveTask.getInstance().stop();
                           ActiveTask.getInstance().save();
                       }
                       ActiveTask.getInstance().start(i.getID());
                       message("Выполняю " + i.getName());
                   }
               }
           }
       };
       for(TaskItem i : taskMenu) {
           MenuItem item = new MenuItem(i.getName());
           item.addActionListener(listener);
           m.add(item);
       }
       return m;
  }

  public void setOfflineIcon() {
      trayIcon.setImage(image1);
  }

  public void setWorkIcon() {
      trayIcon.setImage(image2);
  }

  public void message(String msg) {
      trayIcon.displayMessage("Сообщение", msg, TrayIcon.MessageType.INFO);
  }

  class TrackerThread implements Runnable {
       private Thread my = null;

       public void start() {
           if (my == null) {
               my = new Thread(this, "TaskTracker");
               my.start();
           }
       }

       public void stop() {
           my = null;
       }

       public void checkTask() {
           if(!ActiveTask.getInstance().isActive()) return;

           ActiveTask.getInstance().check();
           ActiveTask.getInstance().save();

           TaskItem t = TaskStoreServiceConnector.getStore().readTask(ActiveTask.getInstance().getActiveTaskId());
           SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
           df.setTimeZone(TimeZone.getTimeZone("GMT"));
           trayIcon.setToolTip(t.getName() + " " + df.format(new Date(ActiveTask.getInstance().getTime())));
       }

       public void run() {
           Thread myThread = Thread.currentThread();
           while (my == myThread) {
               if(!ActiveTask.getInstance().isActive()) {
                   trayIcon.setImage(image2);
               } else {
                   trayIcon.setImage(image1);
                   checkTask();
               }
               try {
                   Thread.sleep(200);
               } catch (Exception ex) {
                   ex.printStackTrace();
               }
               if(!ActiveTask.getInstance().isActive()) {
                   trayIcon.setImage(image1);
               } else {
                   trayIcon.setImage(image2);
               }
               try {
                   Thread.sleep(1000 + (int)(Math.random() * 15000));
               } catch (Exception ex) {
                   ex.printStackTrace();
               }
           }
       }

  }
}
