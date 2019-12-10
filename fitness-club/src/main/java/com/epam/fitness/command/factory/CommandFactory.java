package com.epam.fitness.command.factory;

import com.epam.fitness.command.Command;

public interface CommandFactory {

    Command create(String commandValue);

}