package org.hbrs.ooka.ws2020.uebung2.observer;

public class Event {
    public String compName="ERROR";
    public String state="ERROR";
    public String topic="None";
    public String toString(){
        return "Name: "+compName+" State: "+state;
    }
}
