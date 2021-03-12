public class Counter {

    int counterStart = 0;
    GUI gui;
    private static boolean run = true;

    public static void setRun(boolean run1){run = run1;}

    public Counter(int counterStart, GUI gui) {
        this.counterStart = counterStart;
        this.gui = gui;
    }

    public void startCounter(){
        while (run){//(counterStart < 30){
            //System.out.println(counterName + ": " + counterStart);
            gui.print("Counter: " + counterStart);
            counterStart++;
            /*try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }*/
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        gui.close();
    }
}
