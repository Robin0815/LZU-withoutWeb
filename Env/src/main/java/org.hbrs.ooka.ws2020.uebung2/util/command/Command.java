package org.hbrs.ooka.ws2020.uebung2.util.command;

import org.hbrs.ooka.ws2020.uebung2.runtime.RuntimeEnv;

public interface Command {
    String execute(RuntimeEnv re, String s)throws Exception;
    void undo(RuntimeEnv re, String s)throws Exception;
}
