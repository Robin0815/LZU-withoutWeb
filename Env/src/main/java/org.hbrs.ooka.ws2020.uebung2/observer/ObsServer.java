package org.hbrs.ooka.ws2020.uebung2.observer;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import org.hbrs.ooka.ws2020.uebung2.assembler.ComponentThread;
import org.hbrs.ooka.ws2020.uebung2.component.Component;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ObsServer {

    public static Event event = new Event();
    private PropertyChangeSupport support = new PropertyChangeSupport(this);
    private ObsServer(){

    }
    private static ObsServer instance = new ObsServer();

    public static ObsServer getInstance(){
        return instance;
    }


    //private Map<String, Subscriber> subscribers = new HashMap<>();
    List<Subscriber> subscribers = new ArrayList<>();

    public void subscribe(Subscriber subscriber){
        if(!subscribers.contains(subscriber)){
            subscribers.add(subscriber);
        }
    }

    public void unsubscribe(Subscriber subscriber){
        subscribers.remove(subscriber);
    }
    public void ownNotify(Event event){
        if(subscribers.size() > 0){
            for(Subscriber subscriber : subscribers){
                if(subscriber.topic().equals(event.topic)){
                    subscriber.update(event);
                }
            }
        }
    }
}
