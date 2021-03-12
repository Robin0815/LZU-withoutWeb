package org.hbrs.ooka.ws2020.uebung2.util.command;

import org.hbrs.ooka.ws2020.uebung2.runtime.RuntimeEnv;

public class State implements Command{
    @Override
    public String execute(RuntimeEnv re, String instruction) {
        String[] s = instruction.split(" ");
        return re.getState(s[1]);
    }

    @Override
    public void undo() {

    }
}