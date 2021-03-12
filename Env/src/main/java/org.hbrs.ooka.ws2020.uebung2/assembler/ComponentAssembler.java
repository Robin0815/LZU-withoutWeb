package org.hbrs.ooka.ws2020.uebung2.assembler;

import org.hbrs.ooka.ws2020.uebung2.annotation.Start;
import org.hbrs.ooka.ws2020.uebung2.annotation.Stop;
import org.hbrs.ooka.ws2020.uebung2.component.Component;
import org.hbrs.ooka.ws2020.uebung2.annotation.Inject;
import org.hbrs.ooka.ws2020.uebung2.logger.LoggerFactory;
import org.hbrs.ooka.ws2020.uebung2.logger.LoggerInterface;
import org.hbrs.ooka.ws2020.uebung2.observer.ObsServer;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class ComponentAssembler {
    public Component loadClasses(String compName, String path) throws ClassNotFoundException, IOException {
        JarFile jarFile = new JarFile(path);
        Enumeration<JarEntry> e = jarFile.entries();

        URL[] urls = {new URL("jar:file:" + path + "!/")};
        URLClassLoader cl = URLClassLoader.newInstance(urls);
        Component com = new Component();
        com.setClassLoader(cl);

        while (e.hasMoreElements()) {
            JarEntry je = e.nextElement();
            if (je.isDirectory() || !je.getName().endsWith(".class")) {
                continue;
            }
            // -6 because of .class
            String className = je.getName().substring(0, je.getName().length() - 6);
            className = className.replace('/', '.');
            System.out.println(className);
            Class c = cl.loadClass(className);
            com.setClass(className, c);
        }


        /*Class startAno = com.getClass("Start");
        Class stopAno = com.getClass("Stop");*/

        System.out.println();
        Method startMethod = null;
        Method stopMethod = null;
        for (Class cla : com.getKlass()) {
            for (Method me : cla.getMethods()) {
                if (me.isAnnotationPresent(Start.class)){
                    startMethod = me;}
                if (me.isAnnotationPresent(Stop.class)){
                    stopMethod = me;}
                //System.out.println(me.getAnnotations().getClass().getName());
            }
        }
        List<Field> inject = new ArrayList<>();
        for (Class cla : com.getKlass()) {
            for (Field field: cla.getFields()){
                if(field.isAnnotationPresent(Inject.class)){
                    inject.add(field);
                }
            }
        }
        com.setInject(inject);

        LoggerInterface myLog = new LoggerFactory().createLogger();
        for (Field field : com.getInject()) {
            if (field.getType().isAssignableFrom(LoggerInterface.class)) {
                field.setAccessible(true);
                try {
                    field.set(null, myLog);
                } catch (IllegalAccessException illegalAccessException) {
                    illegalAccessException.printStackTrace();
                }
                System.out.println("Inject Logger Worked");
            }
            if (field.getType().isAssignableFrom(ObsServer.class)) {
                field.setAccessible(true);
                try {
                    field.set(null, ObsServer.getInstance());
                } catch (IllegalAccessException illegalAccessException) {
                    illegalAccessException.printStackTrace();
                }
                System.out.println("Inject Observer Worked");
            }
        }

        //System.out.print(startMethod.getName());
        com.setStart(startMethod);
        com.setStop(stopMethod);
        com.setName(compName);
        return com;
    }

}
