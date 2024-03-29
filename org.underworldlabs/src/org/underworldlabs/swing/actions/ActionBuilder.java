/*
 * ActionBuilder.java
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

import java.io.CharArrayWriter;
import java.io.IOException;
import java.io.InputStream;

import java.net.URL;
import java.util.Enumeration;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.KeyStroke;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.underworldlabs.util.MiscUtils;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

// Utilitiy class to build all actions to be
// associated with buttons, menu items and key strokes.
/* ----------------------------------------------------------
 * CVS NOTE: Changes to the CVS repository prior to the 
 *           release of version 3.0.0beta1 has meant a 
 *           resetting of CVS revision numbers.
 * ----------------------------------------------------------
 */

/**
 *
 * @author   Takis Diakoumis
 * @version  $Revision: 636 $
 * @date     $Date: 2007-01-03 19:01:11 +1100 (Wed, 03 Jan 2007) $
 */
public class ActionBuilder {
    
    private static final String ACTIONS = "actions";
    private static final String ACTION = "action";
    private static final String NAME = "name";
    private static final String ID = "id";
    private static final String MNEMONIC = "mnemonic";
    private static final String SMALL_ICON = "small-icon";
    private static final String LARGE_ICON = "large-icon";
    private static final String ACCEL_KEY = "accel-key";
    private static final String DESCRIPTION = "description";
    private static final String EXECUTE_CLASS = "execute-class";
    private static final String ACCEL_EDITABLE = "accel-editable";
    
    private static String delimiter = "+";
    
    private static HashMap userDefinedActionMap;
    private static HashMap actionsMap;
    
    /**
     * Builds the action map for the specified action and input maps 
     * using the actions as specified by the XML conf file at path.
     *
     * @param actionMap - the action map to bind to
     * @param inputMap - the input map to bind to
     * @param path - the path to the action XML conf file
     */
    public static void build(ActionMap actionMap, InputMap inputMap, String path) {
        actionsMap = (HashMap)loadActions(path);
        build(actionMap, inputMap);
    }
    
    public static void setActionMaps(JComponent component, Properties shortcuts) {
        ActionMap actionMap = component.getActionMap();
        InputMap inputMap = component.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        build(actionMap, inputMap);

        if (shortcuts == null) {
            return;
        }

        buildUserKeymap(shortcuts, inputMap);
    }
    
    public static void build(ActionMap actionMap, InputMap inputMap) {
        String actionId = null;
        BaseActionCommand command = null;
        
        if (actionMap == null) {
            return;
        }

        Set actionSet = actionsMap.keySet();

        for (Iterator i = actionSet.iterator(); i.hasNext();) {
            command = (BaseActionCommand)actionsMap.get(i.next());
            actionId = command.getActionId();
            actionMap.put(actionId, command);

            if (command.hasAccelerator()) {
                inputMap.put((KeyStroke)command.getValue(
                                            Action.ACCELERATOR_KEY), actionId);
            }

        } 
        
    }

    private static void buildUserKeymap(Properties shortcuts, InputMap inputMap) {

        String actionId = null;
        BaseActionCommand command = null;

        for (Enumeration i = shortcuts.keys(); i.hasMoreElements();) {
            actionId = (String)i.nextElement();
            KeyStroke keyStroke = KeyStroke.getKeyStroke(shortcuts.getProperty(actionId));

            command = (BaseActionCommand)actionsMap.get(actionId);
            if (command != null) {
                command.putValue(Action.ACCELERATOR_KEY, keyStroke);
            }

            inputMap.put(keyStroke, actionId);
        }

    }
    
    /**
     * Reloads the actions from the action conf file at the specified path.
     */
    public static Map reloadActions(String path) {
        return loadActions(path);
    }
    
    /**
     * Updates the action shortcut keys based on the properties specified.
     *
     * @param inputMap - the input map to bind to
     * @param shortcuts - the new shortcut keys
     */
    public static void updateUserDefinedShortcuts(InputMap inputMap, 
                                                  Properties shortcuts) {
        if (shortcuts == null) {
            return;
        }
        buildUserKeymap(shortcuts, inputMap);
    }
    
    /**
     * Returns a map containing key/value pairs of all the currently
     * bound actions.
     */
    public static Map getActions() {
        return actionsMap;
    }
    
    /**
     * Returns the action with the specified key name.
     */
    public static Action get(Object key) {
        return (Action)actionsMap.get(key);
    }
    
    private static Map loadActions(String path) {
        InputStream input = null;
        ClassLoader cl = ActionBuilder.class.getClassLoader();
        
        if (cl != null) {
            input = cl.getResourceAsStream(path);
        }
        else {
            input = ClassLoader.getSystemResourceAsStream(path);
        }
        
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            factory.setNamespaceAware(true);
            
            SAXParser parser = factory.newSAXParser();
            ActionHandler handler = new ActionHandler();
            parser.parse(input, handler);
            return handler.getActions();
        } 
        catch (Exception e) {
            e.printStackTrace();
            throw new InternalError();
        }
        finally {
            try {
                if (input != null) {
                    input.close();
                }
            } catch (IOException ioExc) {}
        }
        
    }
    
    static class ActionHandler extends DefaultHandler {
        
        private Map map;
        private CharArrayWriter contents;
        private BaseActionCommand actionCommand;
        
        public ActionHandler() {
            contents = new CharArrayWriter();
            map = new HashMap();
        }
        
        private ImageIcon loadIcon(String path) {
            URL url = ActionHandler.class.getResource(path);
            
            if (url != null) {
                return new ImageIcon(url);
            }
            
            return null;
        }
        
        public Map getActions() {
            return map;
        }
        
        public void startElement(String nameSpaceURI, String localName,
                                 String qName, Attributes attrs) {
            String value = null;
            contents.reset();
            
            if (localName.equals(ACTION)) {
                
                actionCommand = new BaseActionCommand();
                actionCommand.setActionId(attrs.getValue(ID));
                actionCommand.putValue(Action.NAME, attrs.getValue(NAME));
                
                value = attrs.getValue(MNEMONIC);
                if (!MiscUtils.isNull(value)) {
                    actionCommand.putValue(Action.MNEMONIC_KEY, 
                                           new Integer(value.charAt(0)));
                }

                //value = attrs.getValue(LARGE_ICON);
                value = attrs.getValue(SMALL_ICON);
                if (!MiscUtils.isNull(value)) {
                    actionCommand.putValue(Action.SMALL_ICON, loadIcon(value));
                }
                
                value = attrs.getValue(ACCEL_KEY);
                if (!MiscUtils.isNull(value)) {
                    actionCommand.putValue(Action.ACCELERATOR_KEY, 
                                           KeyStroke.getKeyStroke(value));
                }
                
                value = attrs.getValue(ACCEL_EDITABLE);
                if (!MiscUtils.isNull(value)) {
                    actionCommand.setAcceleratorEditable(
                            Boolean.valueOf(value).booleanValue());
                }

                actionCommand.putValue(Action.SHORT_DESCRIPTION, 
                                       attrs.getValue(DESCRIPTION));
                actionCommand.setCommand(attrs.getValue(EXECUTE_CLASS));
                
            } 
            
        }

        public void endElement(String nameSpaceURI, String localName,
                               String qName) {
            
            if (localName.equals(ACTION)) {
                map.put(actionCommand.getActionId(), actionCommand);
            } 
            
        }
        
        public void characters(char[] data, int start, int length) {
            contents.write(data, start, length);
        }
        
        public void ignorableWhitespace(char[] data, int start, int length) {
            characters(data, start, length);
        }

        public void error(SAXParseException spe) throws SAXException {
            throw new SAXException(spe.getMessage());
        }
        
    } // ActionHandler
    
}




