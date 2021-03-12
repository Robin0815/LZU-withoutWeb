package org.hbrs.ooka.ws2020.uebung2.view;

import org.hbrs.ooka.ws2020.uebung2.runtime.BackupManager;
import org.hbrs.ooka.ws2020.uebung2.runtime.RuntimeEnv;
import org.hbrs.ooka.ws2020.uebung2.util.command.AllState;
import org.hbrs.ooka.ws2020.uebung2.util.command.Command;
import org.hbrs.ooka.ws2020.uebung2.util.command.Delete;
import org.hbrs.ooka.ws2020.uebung2.util.command.Init;
import org.hbrs.ooka.ws2020.uebung2.util.command.Start;
import org.hbrs.ooka.ws2020.uebung2.util.command.State;
import org.hbrs.ooka.ws2020.uebung2.util.command.Stop;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class GUI extends JPanel implements ActionListener {
    protected JTextField textField;
    protected JTextArea textArea;
    private final static String newline = "\n";
    private RuntimeEnv re = new RuntimeEnv();
    private BackupManager bm = BackupManager.getInstance();
    public GUI() {
        super(new GridBagLayout());

        textField = new JTextField(20);
        textField.addActionListener(this);

        textArea = new JTextArea(25, 80);
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        textArea.append("New RuntimeEnviroment started: \nPlease type command (init, delete, start, stop, state, allstate)\n" +
                "init: needs to be followed by component name and path to jar file\n" +
                "delete, start, stop just needs the name of the component\n" +
                "allstate needs no additional infos\n" +
                "an existing config in backup.txt can be loaded by loadconfig\n");

        //Add Components to this panel.
        GridBagConstraints c = new GridBagConstraints();
        c.gridwidth = GridBagConstraints.REMAINDER;

        c.fill = GridBagConstraints.HORIZONTAL;
        add(textField, c);

        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1.0;
        c.weighty = 1.0;
        add(scrollPane, c);
    }

    public void actionPerformed(ActionEvent evt) {
        String text = textField.getText();
        Command init = new Init();
        Command delete = new Delete();
        Command start = new Start();
        Command stop = new Stop();
        Command state = new State();
        Command allState = new AllState();
        ArrayList<String> instructions = new ArrayList<>();
        if (!text.equals("loadconfig")) {
            this.bm.writeConfig(text);
            instructions.add(text);
        } else {
            instructions = bm.readConfig();
            if (instructions.isEmpty()) {
                textArea.append("Config is empty.");
            }
        }

        for (String instruction : instructions) {
            String[] s = instruction.split(" ");
            textArea.append(instruction + newline);
            try {
                if (s[0].equals("init")) {
                    //textArea.append(re.initComponent(s[1], s[2]) + newline);
                    textArea.append(init.execute(re, instruction) +newline);
                } else if (s[0].equals("delete")) {
                    //textArea.append(re.deleteComponent(s[1]) + newline);
                    textArea.append(delete.execute(re, instruction) +newline);
                } else if (s[0].equals("start")) {
                    //textArea.append(re.startComp(s[1]) + newline);
                    textArea.append(start.execute(re, instruction) +newline);
                } else if (s[0].equals("stop")) {
                    //textArea.append(re.stopComp(s[1]) + newline);
                    textArea.append(stop.execute(re, instruction) +newline);
                } else if (s[0].equals("state")) {
                    //textArea.append(re.getState(s[1]) + newline);
                    textArea.append(state.execute(re, instruction) +newline);
                } else if (s[0].equals("allstate")) {
                    //textArea.append(re.getThreadListString());
                    textArea.append(allState.execute(re, instruction) +newline);
                }
            } catch (Exception e) {
                e.printStackTrace();
                textArea.append("Error at: " + text + newline);
            }
            textField.selectAll();

            //Make sure the new text is visible, even if there
            //was a selection in the text area.
            textArea.setCaretPosition(textArea.getDocument().getLength());
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event dispatch thread.
     */
    public static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("Input");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Add contents to the window.
        frame.add(new GUI());

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    public void print(String s){
        textArea.append(s + "\n"); // all outputs get their own line
    }

    public String getInput(){

        return "";
    }
}
