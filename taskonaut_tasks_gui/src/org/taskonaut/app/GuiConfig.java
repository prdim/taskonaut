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
public class GuiConfig extends AbstractProperties {
	private static transient GuiConfig me = null;
	private static final String FILE_NAME = "gui_config.xml";
	
	private boolean hideMinimized = false;
	
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

	@Override
	public void save() {
		save(FILE_NAME);
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
	
	// TODO Подумать...

	public synchronized void save(String f) {
		try {
			File t = new File("./config");
			if(!t.exists()) t.mkdir();
			
			XMLEncoder encoder = new XMLEncoder(new BufferedOutputStream(new FileOutputStream("./config/" + f)));
			encoder.writeObject(this);
			encoder.close();
		} catch (Exception e) {
			System.err.println("Error saving configuration " + e.getMessage());
		}
	}
	
	public static synchronized Object load(String f) {
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
