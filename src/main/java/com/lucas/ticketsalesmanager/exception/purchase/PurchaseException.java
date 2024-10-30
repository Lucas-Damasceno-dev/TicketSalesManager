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
 * Base class for purchase-related exceptions.
 *
 * This exception is thrown in general cases of failures during the process
 * purchase. It allows specific messages to be provided
 * for the user and for the logs.
 *
 * @param userMessage Message visible to the user.
 * @param logMessage Message to be recorded in the logs.
 */
package com.lucas.ticketsalesmanager.exception.purchase;

import com.lucas.ticketsalesmanager.exception.TicketSalesManagerException;

public class PurchaseException extends TicketSalesManagerException {
    public PurchaseException(String userMessage, String logMessage) {
        super(userMessage, logMessage);
    }
}
