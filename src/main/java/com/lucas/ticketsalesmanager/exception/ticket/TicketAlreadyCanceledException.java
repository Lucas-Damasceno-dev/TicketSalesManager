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

package com.lucas.ticketsalesmanager.exception.ticket;

import com.lucas.ticketsalesmanager.exception.TicketSalesManagerException;

/**
 * Exception thrown when an attempt is made to cancel a ticket that has already been canceled.
 */
public class TicketAlreadyCanceledException extends TicketSalesManagerException {

    /**
     * Constructs a new TicketAlreadyCanceledException with specified user and log messages.
     *
     * @param userMessage The message to be shown to the user.
     * @param logMessage The message to be logged for internal purposes.
     */
    public TicketAlreadyCanceledException(String userMessage, String logMessage) {
        super(userMessage, logMessage);
    }
}
