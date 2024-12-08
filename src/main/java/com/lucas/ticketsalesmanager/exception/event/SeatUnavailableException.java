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
 * Exception thrown when a requested seat is unavailable for an event.
 * <p>
 * This exception extends the {@link TicketSalesManagerException} class.
 * The error message to the user indicates that the specific seat
 * is not available for the event, while the message for the log
 * details the attempt to reserve an unavailable seat.
 *
 * @param eventName The name of the event.
 * @param seat The seat that is unavailable.
 */
package com.lucas.ticketsalesmanager.exception.event;

import com.lucas.ticketsalesmanager.exception.TicketSalesManagerException;

public class SeatUnavailableException extends TicketSalesManagerException {
    public SeatUnavailableException(String eventName, String seat) {
        super("Seat " + seat + " is unavailable for event " + eventName,
                "Attempted to reserve unavailable seat: " + seat + " for event: " + eventName);
    }
}
