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
 * Exception thrown when a purchase cannot be found.
 *
 * This exception extends the {@link PurchaseException} class.
 * The error message to the user is "Purchase not found." and the
 * message to the log includes the ID of the purchase that was not found.
 *
 * @param purchaseId The ID of the purchase that was not found.
 */
package com.lucas.ticketsalesmanager.exception.purchase;

public class PurchaseNotFoundException extends PurchaseException {
    public PurchaseNotFoundException(String purchaseId) {
        super("Compra não encontrada.",
                "Compra com o ID " + purchaseId + " não foi encontrada.");
    }
}
