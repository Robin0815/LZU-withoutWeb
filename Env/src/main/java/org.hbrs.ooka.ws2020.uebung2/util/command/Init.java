package org.hbrs.ooka.ws2020.uebung2.util.command;

import org.hbrs.ooka.ws2020.uebung2.runtime.RuntimeEnv;

public class Init implements Command{
    @Override
    public String execute(RuntimeEnv re, String instruction) throws Exception {
        String[] s = instruction.split(" ");
        return re.initComponent(s[1], s[2]);
    }

    @Override
    public void undo(RuntimeEnv re, String s)throws Exception{

    }
}
