package org.hbrs.ooka.ws2020.uebung2.observer;

public interface Subscriber {
    default String topic(){
        return "None";
    }

    default void update(Event event) {

    }
}
