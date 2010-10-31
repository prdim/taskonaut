/**
 * 
 */
package org.taskonaut.api;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * Глобальные параметры приложения
 * @author spec
 *
 */
public class GlobalConfig implements AbstractProperties {
	private static transient GlobalConfig me = null;
	private static final String FILE_NAME = "global_config.xml";
	
	private int minTaskActive = 10; 

	/* (non-Javadoc)
	 * @see org.taskonaut.api.AbstractProperties#getTitle()
	 */
	@Override
	public String getTitle() {
		return "Основные настройки";
	}

	/* (non-Javadoc)
	 * @see org.taskonaut.api.AbstractProperties#getExtendInfo()
	 */
	@Override
	public String getExtendInfo() {
		return "Общие настройки приложения, используемые всеми модулями";
	}

	/* (non-Javadoc)
	 * @see org.taskonaut.api.AbstractProperties#save()
	 */
	@Override
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
	
	public static GlobalConfig getInstance() {
		if(me == null) {
			try {
				me = (GlobalConfig)load(FILE_NAME);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if(me == null) me = new GlobalConfig();
		}
		return me;
	}

	/**
	 * @return the minTaskActive
	 */
	public int getMinTaskActive() {
		return minTaskActive;
	}

	/**
	 * @param minTaskActive the minTaskActive to set
	 */
	public void setMinTaskActive(int minTaskActive) {
		this.minTaskActive = minTaskActive;
	}
	
	
}
