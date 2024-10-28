package com.lucas.ticketsalesmanager.controllers;

import com.lucas.ticketsalesmanager.exception.purchase.*;
import com.lucas.ticketsalesmanager.exception.payment.*;
import com.lucas.ticketsalesmanager.models.Event;
import com.lucas.ticketsalesmanager.models.Purchase;
import com.lucas.ticketsalesmanager.models.Ticket;
import com.lucas.ticketsalesmanager.models.User;
import com.lucas.ticketsalesmanager.models.paymentMethod.Payment;
import com.lucas.ticketsalesmanager.persistence.PurchaseDAO;

import java.util.Date;
import java.util.List;

/**
 * Controller to manage purchase processes, including creation, cancellation, and listing of purchases.
 */
public class PurchaseController {

    private final PurchaseDAO purchaseDAO;

    public PurchaseController() {
        this.purchaseDAO = new PurchaseDAO();
    }

    /**
     * Processes a new purchase, associating a user, a ticket, and a payment method.
     *
     * @param user The user making the purchase.
     * @param ticket The ticket being purchased.
     * @param payment The payment method used for the purchase.
     * @throws PurchaseException If there's an error during the purchase process.
     * @throws TicketUnavailableException If the ticket is unavailable.
     * @throws PaymentException If there is an issue with the payment.
     * @throws InsufficientFundsException If the user does not have enough funds.
     */
    public void processPurchase(User user, Ticket ticket, Payment payment) throws PurchaseException, TicketUnavailableException, PaymentException, InsufficientFundsException {
        try {
            if (!ticket.isActive()) {
                throw new TicketUnavailableException("Ticket is not available for purchase.");
            }
            // Assuming the correct method names for the Payment class
            if (!payment.validate(payment.getPaymentDetails())) {
                throw new InvalidPaymentMethodException("Invalid payment method selected.");
            }
            if (!payment.executePayment()) {
                throw new PaymentFailedException("Payment processing failed.", "Additional details if needed");
            }
            purchaseDAO.addPurchase(user, ticket, payment);
        } catch (Exception e) {
            throw new PurchaseException("Failed to process purchase.", e.getMessage());
        }
    }

    /**
     * Cancels an existing purchase and reactivates the ticket for future use.
     *
     * @param purchase The purchase to cancel.
     * @throws PurchaseNotFoundException If the purchase cannot be found.
     * @throws PurchaseException If there is an error during the cancellation.
     */
    public void cancelPurchase(Purchase purchase) throws PurchaseNotFoundException, PurchaseException {
        try {
            purchaseDAO.cancelPurchase(purchase);
        } catch (Exception e) {
            throw new PurchaseException("Failed to cancel purchase.", e.getMessage());
        }
    }

    /**
     * Reactivates a previously cancelled purchase.
     *
     * @param purchase The purchase to reactivate.
     * @throws PurchaseException If the purchase cannot be reactivated.
     */
    public void reactivatePurchase(Purchase purchase) throws PurchaseException {
        try {
            purchaseDAO.reactivatePurchase(purchase);
        } catch (Exception e) {
            throw new PurchaseException("Failed to reactivate purchase.", e.getMessage());
        }
    }

    /**
     * Lists all purchases made by a specific user.
     *
     * @param user The user whose purchases to list.
     * @return List of purchases made by the user.
     */
    public List<Purchase> listPurchasesByUser(User user) {
        return purchaseDAO.findPurchasesByUser(user);
    }

    /**
     * Lists all purchases associated with a particular event.
     *
     * @param event The event whose associated purchases to list.
     * @return List of purchases for the event.
     */
    public List<Purchase> listPurchasesByEvent(Event event) {
        return purchaseDAO.listPurchasesByEvent(event);
    }

    /**
     * Finds purchases by the payment method used.
     *
     * @param paymentMethod The payment method to search by.
     * @return List of purchases using the specified payment method.
     */
    public List<Purchase> findPurchasesByPaymentMethod(Payment paymentMethod) {
        return purchaseDAO.findPurchasesByPaymentMethod(paymentMethod);
    }

    /**
     * Retrieves purchases based on the ticket.
     *
     * @param ticket The ticket associated with the purchases.
     * @return List of purchases related to the ticket.
     */
    public List<Purchase> findPurchasesByTicket(Ticket ticket) {
        return purchaseDAO.findPurchasesByTicket(ticket);
    }

    /**
     * Finds a purchase based on the purchase date.
     *
     * @param purchaseDate The date of the purchase.
     * @return The purchase made on the specified date, or null if not found.
     */
    public Purchase findPurchaseByDate(Date purchaseDate) {
        return purchaseDAO.findPurchaseByPurchaseDate(purchaseDate);
    }

    /**
     * Lists all purchases made in the system.
     *
     * @return List of all purchases.
     */
    public List<Purchase> listAllPurchases() {
        return purchaseDAO.listPurchases();
    }
}
