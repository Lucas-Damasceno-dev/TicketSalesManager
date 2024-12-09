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

/**
 * Exception thrown when an event with the same name already exists.
 *
 * This exception extends the {@link TicketSalesManagerException} class.
 * The error message to the user indicates that an event already exists
 * with the same name, while the message to the log details the attempt
 * to add a duplicate event.
 *
 * @param eventName The name of the event that already exists.
 */
package com.lucas.ticketsalesmanager.exception.event;

import com.lucas.ticketsalesmanager.exception.TicketSalesManagerException;

public class EventAlreadyExistsException extends TicketSalesManagerException {
    public EventAlreadyExistsException(String eventName) {
        super("Event already exists with the name: " + eventName,
                "Attempted to add duplicate event: " + eventName);
    }
}
