

import org.hbrs.ooka.ws2020.uebung2.annotation.Inject;
import org.hbrs.ooka.ws2020.uebung2.logger.LoggerInterface;
import org.hbrs.ooka.ws2020.uebung2.observer.Event;
import org.hbrs.ooka.ws2020.uebung2.observer.ObsServer;
import org.hbrs.ooka.ws2020.uebung2.annotation.Start;
import org.hbrs.ooka.ws2020.uebung2.annotation.Stop;
import org.hbrs.ooka.ws2020.uebung2.observer.Subscriber;

import java.beans.PropertyChangeEvent;

public class Client implements Subscriber {
    private static int counter = 0;
    private static GUI gui;

    @Inject
    public static LoggerInterface myLog;

    @Inject
    public static ObsServer obs;


    @Start
    public void main(String[] args) {
        //
        myLog.sendLog("Counter"+counter+"Started");
        obs.subscribe(this);
        Event event = new Event();
        event.state="started";
        event.compName="Counter"+counter;
        obs.ownNotify(event);


        //ab hier benötigt vorher nur logger und observer
        Controller con = new Controller();
        gui = new GUI("Counter"+counter);
        counter++;
        Counter.setRun(true);
        con.initCounter(0, gui);
    }

    @Stop
    public void stop(){
        //
        myLog.sendLog("Counter"+counter+" Stopped");
        Event event = new Event();
        event.state="stopped";
        event.compName="Counter"+counter;
        obs.unsubscribe(this);
        obs.ownNotify(event);
        System.out.println("Code wird ausgeführt");
        //ab hier benötigt vorher nur logger und observer
        Counter.setRun(false);
        gui.close();
    }

    @Override
    public void update(Event event){
        System.out.println("Name: "+event.compName+" Status: "+event.state);
    }
}
