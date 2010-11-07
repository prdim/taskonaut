/**
 * 
 */
package org.taskonaut.app;

import java.awt.Dimension;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

/**
 * Сохранение последних заданных пользователем размеров форм для последующего использования как размеров по умолчанию
 *  
 * @author spec
 *
 */
public class GuiDefaultSize {
	private static transient GuiDefaultSize me = null;
	private static final String FILE_NAME = "gui_default_size.xml";
	
	private Properties props;
	
	public GuiDefaultSize() {
		props = new Properties();
		load();
	}
	
	public static GuiDefaultSize getInstance() {
		if(me == null) {
			me = new GuiDefaultSize();
		}
		return me;
	}
	
	private void load() {
		try {
			File f = new File("./config/" + FILE_NAME);
			FileInputStream fi = new FileInputStream(f);
			props.loadFromXML(fi);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void save() {
		try {
			File t = new File("./config");
			if(!t.exists()) t.mkdir();
			File f = new File("./config/" + FILE_NAME);
			FileOutputStream fo = new FileOutputStream(f);
			props.storeToXML(fo, "GUI Default Size");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Dimension getDimensionForm(String formName) {
		// TODO Почему возвращаемые размеры с каждым разом меньше на 7 пикселей?
		return new Dimension(getIntValue(formName + ".w"), getIntValue(formName + ".h")+7);
	}
	
	public void storeDimensionForm(String formName, Dimension d) {
		setIntValue(formName + ".w", d.width);
		setIntValue(formName + ".h", d.height);
	}
	
	public boolean isFormStored(String formName) {
		return props.containsKey(formName + ".w");
	}
	
	private int getIntValue(String property) {
		System.out.println(property + "=" + props.getProperty(property, "0"));
		return Integer.parseInt(props.getProperty(property, "0"));
	}
	
	private void setIntValue(String property, int value) {
		System.out.println(property + "=" + value);
		props.setProperty(property, Integer.toString(value));
	}
}
