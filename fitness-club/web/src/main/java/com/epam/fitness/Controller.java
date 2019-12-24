package com.epam.fitness;

import com.epam.fitness.command.Command;
import com.epam.fitness.command.CommandResult;

import com.epam.fitness.utils.CurrentPageGetter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationContext;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/controller")
public class Controller extends HttpServlet{

    private static final Logger LOGGER = LogManager.getLogger(Controller.class);
    private static final String COMMAND_PARAMETER = "command";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException{
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException{
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException {
        String commandValue = request.getParameter(COMMAND_PARAMETER);
        ApplicationContext context = SpringContextManager.getContext();
        try{
            Command command = context.getBean(commandValue, Command.class);
            CommandResult commandResult = command.execute(request, response);
            processCommandResult(commandResult, request, response);
        } catch(Exception ex) {
            LOGGER.error(ex.getMessage(), ex);
            throw new ServletException(ex.getMessage(), ex);
        }
    }

    private void processCommandResult(CommandResult commandResult,
                                      HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException{
        boolean redirect = commandResult.isRedirect();
        String page = commandResult.getPage();
        if(redirect){
            String contextPath = request.getContextPath();
            response.sendRedirect(contextPath + page);
        } else{
            RequestDispatcher dispatcher = request.getRequestDispatcher(page);
            dispatcher.forward(request, response);
        }
    }
}