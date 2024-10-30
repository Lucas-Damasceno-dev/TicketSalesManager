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
 * Exception thrown when it is not possible to update an event.
 *
 * This exception extends the {@link TicketSalesManagerException} class.
 * The error message to the user indicates that the event update
 * failed and provides the reason, while the message to the log details the
 *update failed.
 *
 * @param eventName The name of the event being updated.
 * @param reason The reason the update failed.
 */
package com.lucas.ticketsalesmanager.exception.event;

import com.lucas.ticketsalesmanager.exception.TicketSalesManagerException;

public class EventUpdateException extends TicketSalesManagerException {
    public EventUpdateException(String eventName, String reason) {
        super("Unable to update event " + eventName + ": " + reason,
                "Failed to update event: " + eventName + ". Reason: " + reason);
    }
}
