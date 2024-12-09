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

import com.lucas.ticketsalesmanager.exception.TicketSalesManagerException;

/**
 * Exception thrown when a user attempts to perform an action that requires administrative privileges.
 */
public class UserNotAuthorizedException extends TicketSalesManagerException {

    /**
     * Constructs a new UserNotAuthorizedException with a specified user login.
     *
     * @param userLogin The login of the user attempting the unauthorized action.
     */
    public UserNotAuthorizedException(String userLogin) {
        super("Only administrators can register events.",
                "The user " + userLogin + " is not an admin!");
    }
}
