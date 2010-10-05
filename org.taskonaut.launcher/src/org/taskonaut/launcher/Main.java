/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.taskonaut.launcher;

/**
 * @author spec
 *
 */
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.SplashScreen;
import java.awt.geom.Rectangle2D;
import java.io.*;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.*;
import org.osgi.framework.*;
import org.osgi.framework.launch.*;

public class Main {
    private static SplashScreen splash;
    private static Rectangle2D.Double splashTextArea;
    private static Rectangle2D.Double splashProgressArea;
    private static Graphics2D splashGraphics;

    private static Framework fwk;

    public static void main(String[] args) throws Exception {
        // Must specify a bundle directory.
//        SplashFrame splash = new SplashFrame();
//        splash.setVisible(true);
//        splash.updateStatus("Установка плагинов", -1);
        splashInit();
        File[] files;
        if (args.length < 1 || !new File(args[0]).isDirectory()) {
//      System.out.println("Usage: <bundle-directory>");
            files = new File("./plugins").listFiles();
        } else {
            // Look in the specified bundle directory to create a list
            // of all JAR files to install.
            files = new File(args[0]).listFiles();
        }
        Arrays.sort(files);
        List jars = new ArrayList();
        for (int i = 0; i < files.length; i++) {
            if (files[i].getName().endsWith(".jar")) {
                jars.add(files[i]);
            }
        }

        // If no bundle JAR files are in the directory, then exit.
        if (jars.isEmpty()) {
            System.out.println("No bundles to install.");
        } else {
            // If there are bundle JAR files to install, then register a
            // shutdown hook to make sure the OSGi framework is cleanly
            // shutdown when the VM exits.
            Runtime.getRuntime().addShutdownHook(new Thread() {

                public void run() {
                    try {
                        System.out.println("Shutdown now!");
                        if (fwk != null) {
                            fwk.stop();
                            fwk.waitForStop(0);
                        }
                    } catch (Exception ex) {
                        System.err.println("Error stopping framework: " + ex);
                    }
                }
            });

            // Record any bundle with a Main-Class.
            Bundle mainBundle = null;

            try {
                // Create, configure, and start an OSGi framework instance
                // using the ServiceLoader to get a factory.
                List bundleList = new ArrayList();
                Map m = new HashMap();
                m.putAll(System.getProperties());
                m.put(Constants.FRAMEWORK_STORAGE_CLEAN, "onFirstInit");
//                splash.updateStatus("Запуск OSGi", -1);
                splashText("Запуск OSGi");
                fwk = getFrameworkFactory().newFramework(m);
                fwk.start();

                // Install bundle JAR files and remember the bundle objects.
                BundleContext ctxt = fwk.getBundleContext();
//                splash.updateStatus("Загрузка плагинов", -1);
                splashText("Загрузка плагинов");
                for (int i = 0; i < jars.size(); i++) {
                    Bundle b = ctxt.installBundle(((File) jars.get(i)).toURI().toString());
                    bundleList.add(b);
                    // Remember "main" bundle.
                    if (b.getHeaders().get("Main-Class") != null) {
                        mainBundle = b;
                    }
                }

                // Start all installed non-fragment bundles.
                for (int i = 0; i < bundleList.size(); i++) {
                    if (!isFragment((Bundle) bundleList.get(i))) {
//                        splash.updateStatus("Запуск " + bundleList.get(i).toString(), i*100/bundleList.size());
                        splashText("Запуск " + bundleList.get(i).toString());
                        splashProgress(i*100/bundleList.size());
                        System.out.println("Запуск " + bundleList.get(i).toString());
                        ((Bundle) bundleList.get(i)).start();
                    }
                }

                // If a bundle exists with a "Main-Class", then load the class and
                // invoke its static main method.
                if (mainBundle != null) {
                    final String mainClassName = (String) mainBundle.getHeaders().get("Main-Class");
                    if (mainClassName != null) {
                        final Class mainClass = mainBundle.loadClass(mainClassName);
                        try {
                            Method method = mainClass.getMethod("main", new Class[]{String[].class});
                            String[] mainArgs = new String[args.length - 1];
                            System.arraycopy(args, 1, mainArgs, 0, mainArgs.length);
                            method.invoke(null, new Object[]{mainArgs});
                        } catch (Exception ex) {
                            System.err.println("Error invoking main method: " + ex + " cause = " + ex.getCause());
                        }
                    } else {
                        System.err.println("Main class not found: " + mainClassName);
                    }
                }
//                splash.dispose();
                splash.close();
                // Wait for framework to stop.
                fwk.waitForStop(0);
                System.out.println("OSGi stop");
                System.exit(0);

            } catch (Exception ex) {
                System.err.println("Error starting framework: " + ex);
                ex.printStackTrace();
//                splash.dispose();
                System.exit(0);
            }
//      }
        }
    }

    private static boolean isFragment(Bundle bundle) {
        return bundle.getHeaders().get(Constants.FRAGMENT_HOST) != null;
    }

    /**
     * Simple method to parse META-INF/services file for framework factory.
     * Currently, it assumes the first non-commented line is the class name
     * of the framework factory implementation.
     * @return The created <tt>FrameworkFactory</tt> instance.
     * @throws Exception if any errors occur.
     **/
    private static FrameworkFactory getFrameworkFactory() throws Exception {
        URL url = Main.class.getClassLoader().getResource(
                "META-INF/services/org.osgi.framework.launch.FrameworkFactory");
        if (url != null) {
            BufferedReader br =
                    new BufferedReader(new InputStreamReader(url.openStream()));
            try {
                for (String s = br.readLine(); s != null; s = br.readLine()) {
                    s = s.trim();
                    // Try to load first non-empty, non-commented line.
                    if ((s.length() > 0) && (s.charAt(0) != '#')) {
                        return (FrameworkFactory) Class.forName(s).newInstance();
                    }
                }
            } finally {
                if (br != null) {
                    br.close();
                }
            }
        }

        throw new Exception("Could not find framework factory.");
    }

    private static void splashInit() {
        splash = SplashScreen.getSplashScreen();
        if(splash != null) {
            Dimension ssDim = splash.getSize();
            int height = ssDim.height;
            int width = ssDim.width;
            splashTextArea = new Rectangle2D.Double(15., 15., width - 25., 20.);
            splashProgressArea = new Rectangle2D.Double(width * .55, height*.92, width*.4, 16 );
            splashGraphics = splash.createGraphics();
            splashGraphics.setFont(new Font("Dialog", Font.PLAIN, 12));
            splashText("Starting");
            splashProgress(0);
        }
    }

    private static void splashText(String str) {
        if (splash != null && splash.isVisible()) {
            // erase the last status text
            splashGraphics.setPaint(Color.LIGHT_GRAY);
            splashGraphics.fill(splashTextArea);

            // draw the text
            splashGraphics.setPaint(Color.BLACK);
            splashGraphics.drawString(str, (int)(splashTextArea.getX() + 10),(int)(splashTextArea.getY() + 15));

            // make sure it's displayed
            splash.update();
        }
    }

    private static void splashProgress(int pct) {
        if (splash != null && splash.isVisible()) {
            // Note: 3 colors are used here to demonstrate steps
            // erase the old one
            splashGraphics.setPaint(Color.LIGHT_GRAY);
            splashGraphics.fill(splashProgressArea);

            // draw an outline
            splashGraphics.setPaint(Color.BLUE);
            splashGraphics.draw(splashProgressArea);

            // Calculate the width corresponding to the correct percentage
            int x = (int) splashProgressArea.getMinX();
            int y = (int) splashProgressArea.getMinY();
            int wid = (int) splashProgressArea.getWidth();
            int hgt = (int) splashProgressArea.getHeight();

            int doneWidth = Math.round(pct*wid/100.f);
            doneWidth = Math.max(0, Math.min(doneWidth, wid-1));  // limit 0-width

            // fill the done part one pixel smaller than the outline
            splashGraphics.setPaint(Color.GREEN);
            splashGraphics.fillRect(x, y+1, doneWidth, hgt-1);

            // make sure it's displayed
            splash.update();
        }
    }
}
