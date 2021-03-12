package org.hbrs.ooka.ws2020.uebung2.util.command;

import org.hbrs.ooka.ws2020.uebung2.runtime.RuntimeEnv;

public class AllState implements Command{

    @Override
    public String execute(RuntimeEnv re, String instruction) {
        String[] s = instruction.split(" ");
        return re.getThreadListString();
    }

    @Override
    public void undo() {

    }
}
