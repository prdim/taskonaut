
/*
 * Copyright (C) 2006 Sun Microsystems, Inc. All rights reserved. Use is
 * subject to license terms.
 */ 

package org.taskonaut.test.internal;

import org.jdesktop.application.Action;
import org.jdesktop.application.Application;
import org.jdesktop.application.ApplicationContext;
import org.jdesktop.application.LocalStorage;
import org.jdesktop.application.Task;
import org.jdesktop.application.Task.BlockingScope;
import org.jdesktop.application.Task.InputBlocker;
import org.jdesktop.application.TaskMonitor;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.RoundRectangle2D;
import java.util.logging.Logger;
import javax.swing.ActionMap;
import javax.swing.InputVerifier;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JToolBar;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;
import javax.swing.event.MouseInputAdapter;
import javax.swing.event.MouseInputListener;
import org.jdesktop.application.SingleFrameApplication;


/**
 * A demo of the {@code @Action} <i>block</i> options for background
 * task.  It's an example of three of the {@code Action.Block} types:
 * <pre>
 * &#064;Action(block = Task.BlockingScope.ACTION)  
 * public Task blockAction() { ... }
 * 
 * &#064;Action(block = Task.BlockingScope.COMPONENT) 
 * public Task blockComponent() { ... }
 * 
 * &#064;Action(block = Task.BlockingScope.WINDOW) 
 * public Task blockWindow() { ... }
 * 
 * &#064;Action(block = Task.BlockingScope.APPLICATION)
 * public Task blockApplication() { ... }
 * </pre>
 * The first {@code BlockingScope.ACTION} {@code @Action} disables the
 * corresponding {@code Action} while {@code blockAction} method runs.
 * When you press the blockAction button or toolbar-button or menu
 * item you'll observe that all of the components are disabled.  The
 * {@code BlockingScope.COMPONENT} version only disables the component
 * that triggered the action.  The {@code Block.WINDOW} method
 * uses a custom {@link Task.InputBlocker inputBlocker} to 
 * temporarily block input to the by making the window's
 * glass pane visible.  And the {@code Task.BlockingScope.APPLICATION} 
 * version pops up a modal dialog for the action's duration.
 * The blocking dialog's title/message/icon are defined by resources
 * from the ResourceBundle named {@code BlockingExample1}:
 * <pre>
 * BlockingDialog.title = Blocking Application 
 * BlockingDialog.message = Please wait patiently ...
 * Action.BlockingDialog.icon = wait.png
 * </pre>
 * 
 * <p>
 * All of the actions in this example just sleep for about 2 seconds,
 * while periodically updating their Task's message/progress properties.
 * 
 * <p>
 * This class loads resources from the ResourceBundle called
 * {@code BlockingExample1}.  It depends on the example {@code StatusBar} class.
 * 
 * 
 * @author Hans Muller (Hans.Muller@Sun.COM)
 * @see ApplicationContext
 * @see Application
 * @see Action
 * @see Task
 * @see TaskMonitor
 * @see StatusBar
 */

public class BlockingExample1 extends SingleFrameApplication {
    private static Logger logger = Logger.getLogger(LocalStorage.class.getName());
    private JFrame mainFrame = null;
    private StatusBar statusBar = null;
    private BusyIndicator busyIndicator = null;

    @Override protected void startup() {
	statusBar = new StatusBar(this, getContext().getTaskMonitor());
        busyIndicator = new BusyIndicator();
	mainFrame = getMainFrame();
	mainFrame.setJMenuBar(createMenuBar());
	mainFrame.add(createToolBar(), BorderLayout.NORTH);
	mainFrame.add(createMainPanel(), BorderLayout.CENTER);
	mainFrame.add(statusBar, BorderLayout.SOUTH);
        mainFrame.setGlassPane(busyIndicator);
        show(mainFrame);
    }

    private ActionMap actionMap() {
	return getContext().getActionMap();
    }

    private JMenu createMenu(String menuName, String[] actionNames) {
	JMenu menu = new JMenu();
	menu.setName(menuName);
	for (String actionName : actionNames) {
	    JMenuItem menuItem = new JMenuItem();
	    menuItem.setAction(actionMap().get(actionName));
	    menu.add(menuItem);
	}
	return menu;
    }

    private JMenuBar createMenuBar() {
	String[] demoMenuActionNames = {
	    "blockAction",
	    "blockComponent",
	    "blockApplication",
	    "blockWindow",
	    "quit"
	};
	JMenuBar menuBar = new JMenuBar();
	menuBar.add(createMenu("demoMenu", demoMenuActionNames));
	return menuBar;
    }

    private JComponent createToolBar() {
	String[] toolbarActionNames = {
	    "blockAction",
	    "blockComponent",
	    "blockApplication",
	    "blockWindow",
	};
	JToolBar toolBar = new JToolBar();
        toolBar.setFloatable(false);
	for (String actionName : toolbarActionNames) {
	    JButton button = new JButton();
	    button.setRequestFocusEnabled(false);
	    button.setAction(actionMap().get(actionName));
	    button.setVerticalTextPosition(JButton.BOTTOM);
	    button.setHorizontalTextPosition(JButton.CENTER);
	    button.setName(actionName + "ToolBarButton");
	    toolBar.add(button);
	}
	return toolBar;
    }

    private JComponent createMainPanel() {
	JButton actionButton = new JButton(actionMap().get("blockAction"));
	JButton componentButton = new JButton(actionMap().get("blockComponent"));
	JButton applicationButton = new JButton(actionMap().get("blockApplication"));
	JButton windowButton = new JButton(actionMap().get("blockWindow"));
	JPanel panel1 = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 50));
	panel1.add(actionButton);
	panel1.add(componentButton);
	panel1.add(applicationButton);
	panel1.add(windowButton);
	JPanel panel2 = new JPanel(new BorderLayout());
	panel2.add(new JSeparator(), BorderLayout.NORTH);
	panel2.add(panel1, BorderLayout.CENTER);
	panel2.setBorder(new EmptyBorder(0, 2, 0, 2)); // top, left, bottom, right
	return panel2;
    }

    /* Progress is interdeterminate for the first 150ms, then
     * run for another 7500ms, marking progress every 150ms.
     */
    private class DoNothingTask extends Task<Void, Void> {
        DoNothingTask() {
            super(BlockingExample1.this);
            setUserCanCancel(true);
        }
	@Override protected Void doInBackground() throws InterruptedException {
	    for(int i = 0; i < 50; i++) {
		setMessage("Working... [" + i + "]");
		Thread.sleep(150L);
		setProgress(i, 0, 49);
	    }
	    Thread.sleep(150L);
            return null;
	}
	@Override protected void succeeded(Void ignored) {
	    setMessage("Done");
	}
	@Override protected void cancelled() {
	    setMessage("Canceled");
	}

    }

    @Action(block = BlockingScope.ACTION)
    public Task blockAction() {
	return new DoNothingTask();
    }

    @Action(block = BlockingScope.COMPONENT)
    public Task blockComponent() {
	return new DoNothingTask();
    }

    @Action(block = BlockingScope.WINDOW)
    public Task blockWindow() {
        return new DoNothingTask();
    }

    @Action(block = BlockingScope.APPLICATION)
    public Task blockApplication() {
	Task task = new DoNothingTask();
        task.setInputBlocker(new BusyIndicatorInputBlocker(task));
        return task;
    }

    public static void main(String[] args) {
        Application.launch(BlockingExample1.class, args);
    }

    /* This component is intended to be used as a GlassPane.  It's
     * start method makes this component visible, consumes mouse
     * and keyboard input, and displays a spinning activity indicator 
     * animation.  The stop method makes the component not visible.
     * The code for rendering the animation was lifted from 
     * org.jdesktop.swingx.painter.BusyPainter.  I've made some
     * simplifications to keep the example small.
     */
    private static class BusyIndicator extends JComponent implements ActionListener {
        private int frame = -1;  // animation frame index
        private final int nBars = 8;  
        private final float barWidth = 6;
        private final float outerRadius = 28;  
        private final float innerRadius = 12; 
        private final int trailLength = 4;
        private final float barGray = 200f;  // shade of gray, 0-255
        private final Timer timer = new Timer(65, this); // 65ms = animation rate

        BusyIndicator() {
            setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            MouseInputListener blockMouseEvents = new MouseInputAdapter() {};
            addMouseMotionListener(blockMouseEvents);
            addMouseListener(blockMouseEvents);
            InputVerifier retainFocusWhileVisible = new InputVerifier() { 
                public boolean verify(JComponent c) { 
                    return !c.isVisible(); 
                } 
            };
            setInputVerifier(retainFocusWhileVisible);
        }

        public void actionPerformed(ActionEvent ignored) {
            frame += 1;
            repaint();
        }

        void start() {
            setVisible(true);
            requestFocusInWindow();
            timer.start(); 
        }

        void stop() {
            setVisible(false);
            timer.stop(); 
        }

        @Override protected void paintComponent(Graphics g) {
            RoundRectangle2D bar = new RoundRectangle2D.Float(
                innerRadius, -barWidth/2, outerRadius, barWidth, barWidth, barWidth);
                // x,         y,          width,       height,   arc width,arc height
            double angle = Math.PI * 2.0 / (double)nBars; // between bars
            Graphics2D g2d = (Graphics2D)g;
            g2d.translate(getWidth() / 2, getHeight() / 2);
            g2d.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            for (int i = 0; i < nBars; i++) {
                // compute bar i's color based on the frame index
                Color barColor = new Color((int)barGray, (int)barGray, (int)barGray);
                if (frame != -1) {
                    for(int t = 0; t < trailLength; t++) {
                        if (i == ((frame - t + nBars) % nBars)) {
                            float tlf = (float)trailLength;
                            float pct = 1.0f - ((tlf - t) / tlf);
                            int gray = (int)((barGray - (pct * barGray)) + 0.5f);
                            barColor = new Color(gray, gray, gray);
                        }
                    }
                }
                // draw the bar
                g2d.setColor(barColor);
                g2d.fill(bar);
                g2d.rotate(angle);
            }
        }
    }

    private class BusyIndicatorInputBlocker extends InputBlocker {
        BusyIndicatorInputBlocker(Task task) {
            super(task, Task.BlockingScope.WINDOW, busyIndicator);
        }
        @Override protected void block() {
            busyIndicator.start();
        }
        @Override protected void unblock() {
            busyIndicator.stop();
        }
    }
}
