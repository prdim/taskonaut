/**
 * 
 */
package org.taskonaut.util;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 *
 * @author ProlubnikovDA
 */
public class FileUtils {
    private FileUtils() {
        throw new AssertionError();
    }

    public static void beanToXML(Object bean, String fn) throws FileNotFoundException {
        XMLEncoder encoder = new XMLEncoder(new BufferedOutputStream(new FileOutputStream(fn)));
        encoder.writeObject(bean);
        encoder.close();
    }

    public static Object XmlToBean(String fn) throws FileNotFoundException {
        XMLDecoder decoder = new XMLDecoder(new BufferedInputStream(new FileInputStream(fn)));
        Object o = decoder.readObject();
        decoder.close();
        return o;
    }
}
