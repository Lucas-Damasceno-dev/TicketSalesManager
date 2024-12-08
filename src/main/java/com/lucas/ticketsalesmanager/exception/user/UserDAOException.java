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

package com.lucas.ticketsalesmanager.exception.user;

/**
 * Exception thrown when there is an error related to user data access operations.
 */
public class UserDAOException extends Exception {
    private final String userMessage;

    /**
     * Constructs a new UserDAOException with a specified user message and log message.
     *
     * @param userMessage A message intended for the user, describing the error.
     * @param logMessage A detailed message for logging purposes.
     */
    public UserDAOException(String userMessage, String logMessage) {
        super(logMessage);
        this.userMessage = userMessage;
    }

    /**
     * Retrieves the user message associated with this exception.
     *
     * @return The message intended for the user.
     */
    public String getUserMessage() {
        return userMessage;
    }
}
