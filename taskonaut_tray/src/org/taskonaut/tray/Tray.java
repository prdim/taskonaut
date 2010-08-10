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
import java.util.Date;
import java.util.TimeZone;
import java.util.Vector;

import javax.swing.ImageIcon;

import org.taskonaut.app.MainApplication;
import org.taskonaut.tasks.OneTask;
import org.taskonaut.tasks.TaskList;
import org.taskonaut.tasks.TimeLogItem;
import org.taskonaut.tasks.TimeLogger;

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
   Vector<OneTask> taskMenu = new Vector<OneTask>();

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
                   if (TimeLogger.getInstance().getActiveTask() == null) {
                       return;
                   }
                   TimeLogger.getInstance().stopTask();
                   TimeLogger.getInstance().save();
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
      taskMenu = TaskList.getInstance().getActiveTasks();
      ActionListener listener = new ActionListener() {
           public void actionPerformed(ActionEvent e) {
               String s = e.getActionCommand();
               for(OneTask i : taskMenu) {
                   if(s.equals(i.getName())){
                       if(TimeLogger.getInstance().getActiveTask()!=null) {
                           TimeLogger.getInstance().stopTask();
                           TimeLogger.getInstance().save();
                       }
                       TimeLogger.getInstance().startTask(i.getId());
                       message("Выполняю " + i.getName());
                   }
               }
           }
       };
       for(OneTask i : taskMenu) {
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
           TimeLogItem ti = TimeLogger.getInstance().getActiveTask();
           if(ti==null) return;

           TimeLogger.getInstance().updateTask();
           TimeLogger.getInstance().save();

           OneTask t = TaskList.getInstance().getTask(ti.getTaskId());
           SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
           df.setTimeZone(TimeZone.getTimeZone("GMT"));
           trayIcon.setToolTip(t.getName() + " " + df.format(new Date(ti.getPeriod())));
       }

       public void run() {
           Thread myThread = Thread.currentThread();
           while (my == myThread) {
               if(TimeLogger.getInstance().getActiveTask()==null) {
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
               if(TimeLogger.getInstance().getActiveTask()==null) {
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
