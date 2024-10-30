/***********************************************************************************************
Author: LUCAS DA CONCEIÇÃO DAMASCENO
Curricular Component: EXA 863 - MI Programming - 2024.2 - TP01
Completed on: 10/24/2024
I declare that this code was prepared by me individually and does not contain any
code snippet from another colleague or another author, such as from books and
handouts, and web pages or electronic documents. Any piece of code
by someone other than mine is highlighted with a citation for the author and source
of the code, and I am aware that these excerpts will not be considered for evaluation purposes
************************************************************************************************/

package com.lucas.ticketsalesmanager.exception;

/**
 * The TicketSalesManagerException class is a custom exception for handling errors
 * specific to the Ticket Sales Manager application. It allows for a user-friendly
 * message to be displayed while maintaining a separate log message for developers.
 */
public class TicketSalesManagerException extends Exception {
    private final String userMessage;

    /**
     * Constructs a new TicketSalesManagerException with the specified user message
     * and log message.
     *
     * @param userMessage A user-friendly message to be displayed to the user.
     * @param logMessage A detailed log message for developers.
     */
    public TicketSalesManagerException(String userMessage, String logMessage) {
        super(logMessage);
        this.userMessage = userMessage;
    }

    /**
     * Retrieves the user-friendly message.
     *
     * @return The user message.
     */
    public String getUserMessage() {
        return userMessage;
    }
}
