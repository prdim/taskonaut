/**
 * 
 */
package org.taskonaut.app;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.taskonaut.api.AbstractProperties;

/**
 * @author spec
 *
 */
public class GuiConfig implements AbstractProperties {
	private static transient GuiConfig me = null;
	private static final String FILE_NAME = "gui_config.xml";
	
	private boolean hideMinimized = true;
	private boolean enableFilterOnFly = false;
	private boolean trackMouseOnTaskList = false;
	
	public static GuiConfig getInstance() {
		if(me == null) {
			try {
				me = (GuiConfig)load(FILE_NAME);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if(me == null) me = new GuiConfig();
		}
		return me;
	}

	/* (non-Javadoc)
	 * @see org.taskonaut.api.AbstractProperties#getTitle()
	 */
	@Override
	public String getTitle() {
		return "Настройки интерфейса пользователя";
	}

	/* (non-Javadoc)
	 * @see org.taskonaut.api.AbstractProperties#getExtendInfo()
	 */
	@Override
	public String getExtendInfo() {
		return "Настройки поведения пользовательского интерфейса";
	}

	/**
	 * @return the hideMinimized
	 */
	public boolean isHideMinimized() {
		return hideMinimized;
	}

	/**
	 * @param hideMinimized the hideMinimized to set
	 */
	public void setHideMinimized(boolean hideMinimized) {
		this.hideMinimized = hideMinimized;
	}

	/**
	 * @return the enableFilterOnFly
	 */
	public boolean isEnableFilterOnFly() {
		return enableFilterOnFly;
	}

	/**
	 * @param enableFilterOnFly the enableFilterOnFly to set
	 */
	public void setEnableFilterOnFly(boolean enableFilterOnFly) {
		this.enableFilterOnFly = enableFilterOnFly;
	}

	/**
	 * @return the trackMouseOnTaskList
	 */
	public boolean isTrackMouseOnTaskList() {
		return trackMouseOnTaskList;
	}

	/**
	 * @param trackMouseOnTaskList the trackMouseOnTaskList to set
	 */
	public void setTrackMouseOnTaskList(boolean trackMouseOnTaskList) {
		this.trackMouseOnTaskList = trackMouseOnTaskList;
	}

	public synchronized void save() {
		try {
			File t = new File("./config");
			if(!t.exists()) t.mkdir();
			
			XMLEncoder encoder = new XMLEncoder(new BufferedOutputStream(new FileOutputStream("./config/" + FILE_NAME)));
			encoder.writeObject(this);
			encoder.close();
		} catch (Exception e) {
			System.err.println("Error saving configuration " + e.getMessage());
		}
	}
	
	private static synchronized Object load(String f) {
		Object o = null;
		try {
			XMLDecoder decoder = new XMLDecoder(new BufferedInputStream(new FileInputStream("./config/" + f)));
			o = decoder.readObject();
			decoder.close();
		} catch (Exception e) {
			System.err.println("Error loading configuration " + e.getMessage());
		}
		return o;
	}
}
