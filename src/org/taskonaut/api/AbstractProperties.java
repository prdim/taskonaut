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
 * @author spec
 *
 */
public interface AbstractProperties {
	
	public abstract String getTitle();
	public abstract String getExtendInfo();
	
	public abstract void save();
}
