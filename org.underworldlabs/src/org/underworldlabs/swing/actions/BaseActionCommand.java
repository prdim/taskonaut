/*
 * BaseActionCommand.java
 *
 * Copyright (C) 2002-2007 Takis Diakoumis
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 *
 */

package org.underworldlabs.swing.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Icon;
import javax.swing.SwingUtilities;

/* ----------------------------------------------------------
 * CVS NOTE: Changes to the CVS repository prior to the 
 *           release of version 3.0.0beta1 has meant a 
 *           resetting of CVS revision numbers.
 * ----------------------------------------------------------
 */

/** <p>This is the base class for the action library. All
 *  actions will inherit from this class. The CommandAction
 *  defines a generic implementation of actionPerformed.
 *  Here actionPerformed simply calls the execute method
 *  on its command object.<br>
 *
 *  A developer can use this type directly by passing in
 *  the command, string, and action. However, convenience
 *  implementations are available that already provide the
 *  string and icon. The developer simply needs to provide
 *  the proper command.<br>
 *
 *  @author   Takis Diakoumis
 * @version  $Revision: 636 $
 * @date     $Date: 2007-01-03 19:01:11 +1100 (Wed, 03 Jan 2007) $
 */
public class BaseActionCommand extends AbstractAction {
    
    /** The action ID */
    private String actionId;

    /** The associated command for execution */
    protected BaseCommand command;

    /** Whether the accelerator is editable */
    private boolean acceleratorEditable;

    /** <p>Constructs a new command */
    public BaseActionCommand() {
        super();
    }
    
    /** <p>This constructor creates an action without an icon.
     *
     *  @param command the command for this action to act upon
     *  @param name the action's name
     */
    public BaseActionCommand(BaseCommand command, String name) {
        super(name);
        setCommand(command);
    }
    
    /** <p>This constructor creates an action with an icon but no name.
     *  (for buttons that require only an icon)
     *
     *  @param command the command for this action to act upon
     *  @param icon the action's icon
     */
    public BaseActionCommand(BaseCommand command, Icon icon) {
        super(null, icon);
        setCommand(command);
    }
    
    /** <p>This constructor creates an action with an icon.
     *
     *  @param command the command for this action to act upon
     *  @param name the action's name
     *  @param icon the action's icon
     */
    public BaseActionCommand(BaseCommand command, String name, Icon icon) {
        super(name, icon);
        setCommand(command);
    }
    
    /** <p>ActionPerformed is what executed the command.<br>
     *  ActionPerformed is called whenever the action is acted upon.
     *
     *  @param e the action event
     */
    public void actionPerformed(final ActionEvent e) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                getCommand().execute(e);
            }
        });
    }
    
    /** <p>This method retrieves the encapsulated command.
     *
     * @return the command
     */
    public final BaseCommand getCommand() {
        return command;
    }
    
    /** <p>Sets the action's command object to the specified value.
     *
     * @param newValue the command for this action to act upon
     */
    protected final void setCommand(BaseCommand newValue) {
        this.command = newValue;
    }
    
    /** <p>Sets the action's command object using the
     *  class name specified which is loaded using
     *  <code>Class.forName(...)</code>.
     *
     *  @param the command's fully qualified class name
     */
    public void setCommand(String className) {
        
        try {
            Class _class = Class.forName(className, true,
                                         ClassLoader.getSystemClassLoader());
            Object object = _class.newInstance();
            command = (BaseCommand)object;
        } 
        catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new InternalError();
        }         
        catch (Exception e) {
            e.printStackTrace();
        } 
        
    }
    
    /** <p>Returns whether this action has an associated
     *  accelerator key combination.
     *
     *  @return true | false
     */
    public boolean hasAccelerator() {
        return getValue(ACCELERATOR_KEY) != null;
    }
    
    /** <p>Returns this command's action ID.
     *
     *  @return the action id
     */
    public String getActionId() {
        return actionId;
    }
    
    /** <p>Sets this command's action ID to the
     *  specified value.
     *
     *  @param the action ID
     */
    public void setActionId(String actionId) {
        this.actionId = actionId;
    }

    public boolean isAcceleratorEditable() {
        return acceleratorEditable;
    }

    public void setAcceleratorEditable(boolean acceleratorEditable) {
        this.acceleratorEditable = acceleratorEditable;
    }
    
}







