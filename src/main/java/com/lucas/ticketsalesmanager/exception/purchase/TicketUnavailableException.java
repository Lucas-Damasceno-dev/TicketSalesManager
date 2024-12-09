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
 * Exception thrown when a requested seat is unavailable.
 *
 * This exception extends the {@link PurchaseException} class.
 * The error message to the user indicates that the specific seat
 * is already unavailable, while the message to the log details the failure
 * when attempting to purchase.
 *
 * @param seat The seat that is unavailable.
 */
package com.lucas.ticketsalesmanager.exception.purchase;

public class TicketUnavailableException extends PurchaseException {
    public TicketUnavailableException(String seat) {
        super("O assento " + seat + " já está indisponível.",
                "Falha na compra: o assento " + seat + " já foi reservado.");
    }
}
