public class Controller {

    public void initCounter(int counterStart, GUI gui){
        Counter counter = new Counter( counterStart, gui);
        try {
            counter.startCounter();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

}
