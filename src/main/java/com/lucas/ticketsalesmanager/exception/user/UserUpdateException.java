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
 * Exception thrown when there is an error updating user information.
 */
public class UserUpdateException extends Exception {

    /**
     * The information that is being updated.
     */
    private final String infoToUpdate;

    /**
     * Constructs a new UserUpdateException with the specified details.
     *
     * @param login The login of the user whose information is being updated.
     * @param infoToUpdate The specific information that is being updated.
     * @param logMessage A message for logging purposes that provides details about the exception.
     */
    public UserUpdateException(String login, String infoToUpdate, String logMessage) {
        super(logMessage);
        this.infoToUpdate = infoToUpdate;
    }

    /**
     * Gets the information that is being updated.
     *
     * @return The specific information that caused the exception.
     */
    public String getInfoToUpdate() {
        return infoToUpdate;
    }
}
