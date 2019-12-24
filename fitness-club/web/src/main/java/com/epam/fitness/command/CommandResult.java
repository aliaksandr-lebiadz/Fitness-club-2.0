package com.epam.fitness.command;

public class CommandResult {

    private String page;
    private boolean redirect;

    private CommandResult(String page, boolean redirect){
        this.page = page;
        this.redirect = redirect;
    }

    public String getPage() {
        return page;
    }

    public boolean isRedirect() {
        return redirect;
    }

    public static CommandResult redirect(String page){
        return new CommandResult(page, true);
    }

    public static CommandResult forward(String page){
        return new CommandResult(page, false);
    }
}