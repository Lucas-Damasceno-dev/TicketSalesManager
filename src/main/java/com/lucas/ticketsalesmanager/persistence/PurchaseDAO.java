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

package com.lucas.ticketsalesmanager.persistence;

import com.google.gson.reflect.TypeToken;
import com.lucas.ticketsalesmanager.models.Event;
import com.lucas.ticketsalesmanager.models.Purchase;
import com.lucas.ticketsalesmanager.models.Ticket;
import com.lucas.ticketsalesmanager.models.User;
import com.lucas.ticketsalesmanager.models.paymentMethod.Payment;
import jakarta.mail.MessagingException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The PurchaseDAO class provides data access methods for managing purchase data.
 * It handles operations such as adding, canceling, reactivating, and listing purchases
 * stored in a JSON file.
 */
public class PurchaseDAO {
    private static final String FILE_PATH = "purchase.json";
    private static final Type purchaseListType = new TypeToken<List<Purchase>>(){}.getType();
    private final DataAccessObject<Purchase> purchaseDao;

    /**
     * Constructs a PurchaseDAO instance, initializing the data access object
     * with the specified file path and type for purchase lists.
     */
    public PurchaseDAO() {
        this.purchaseDao = new DataAccessObject<>(FILE_PATH, purchaseListType);
    }

    /**
     * Adds a new purchase to the purchase list.
     *
     * @param user The user making the purchase.
     * @param ticket The ticket being purchased.
     * @param paymentMethod The payment method used for the purchase.
     * @throws MessagingException if there is an error sending the purchase confirmation email.
     */
    public void addPurchase(User user, Ticket ticket, Payment paymentMethod) throws MessagingException {
        Purchase purchase = new Purchase(user, ticket, paymentMethod.getName());
        List<Purchase> purchases = purchaseDao.readData();
        if (purchases == null) {
            purchases = new ArrayList<>();
        }
        purchases.add(purchase);
        purchaseDao.writeData(purchases);
        purchase.processPurchase(user, ticket, paymentMethod);
    }

    /**
     * Cancels a specified purchase.
     *
     * @param purchase The purchase to be canceled.
     */
    public void cancelPurchase(Purchase purchase) {
        purchase.getTicket().cancel();
        updatePurchase(purchase);
    }

    /**
     * Reactivates a previously canceled purchase.
     *
     * @param purchase The purchase to be reactivated.
     */
    public void reactivatePurchase(Purchase purchase) {
        purchase.getTicket().reactivate();
        updatePurchase(purchase);
    }

    /**
     * Lists all purchases associated with a specific event.
     *
     * @param event The event for which purchases are to be listed.
     * @return A list of purchases related to the specified event, or an empty list if none found.
     */
    public List<Purchase> listPurchasesByEvent(Event event) {
        List<Purchase> purchases = purchaseDao.readData();
        if (purchases != null) {
            return purchases.stream()
                    .filter(purchase -> purchase.getTicket().getEvent().equals(event))
                    .collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    /**
     * Lists all purchases.
     *
     * @return A list of all purchases, or an empty list if no purchases are found.
     */
    public List<Purchase> listPurchases() {
        List<Purchase> purchases = purchaseDao.readData();
        return purchases != null ? purchases : new ArrayList<>();
    }

    /**
     * Finds all purchases made by a specific user.
     *
     * @param user The user whose purchases are to be found.
     * @return A list of purchases made by the specified user, or an empty list if none found.
     */
    public List<Purchase> findPurchasesByUser(User user) {
        List<Purchase> purchases = purchaseDao.readData();
        if (purchases != null) {
            return purchases.stream()
                    .filter(purchase -> purchase.getUser().equals(user))
                    .collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    /**
     * Finds all purchases associated with a specific ticket.
     *
     * @param ticket The ticket for which purchases are to be found.
     * @return A list of purchases associated with the specified ticket, or an empty list if none found.
     */
    public List<Purchase> findPurchasesByTicket(Ticket ticket) {
        List<Purchase> purchases = purchaseDao.readData();
        if (purchases != null) {
            return purchases.stream()
                    .filter(purchase -> purchase.getTicket().equals(ticket))
                    .collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    /**
     * Finds all purchases made using a specific payment method.
     *
     * @param paymentMethod The payment method used for the purchases.
     * @return A list of purchases made with the specified payment method, or an empty list if none found.
     */
    public List<Purchase> findPurchasesByPaymentMethod(Payment paymentMethod) {
        List<Purchase> purchases = purchaseDao.readData();
        if (purchases != null) {
            return purchases.stream()
                    .filter(purchase -> purchase.getPaymentMethod().equals(paymentMethod))
                    .collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    /**
     * Finds a purchase by its purchase date.
     *
     * @param purchaseDate The date of the purchase to find.
     * @return The Purchase object if found, or null if not found.
     */
    public Purchase findPurchaseByPurchaseDate(Date purchaseDate) {
        List<Purchase> purchases = purchaseDao.readData();
        if (purchases != null) {
            for (Purchase purchase : purchases) {
                if (purchase.getPurchaseDate().equals(purchaseDate)) {
                    return purchase;
                }
            }
        }
        return null;
    }

    /**
     * Removes a specified purchase from the purchase list.
     *
     * @param purchase The purchase to be removed.
     */
    public void removePurchase(Purchase purchase) {
        List<Purchase> purchases = purchaseDao.readData();
        if (purchases != null && purchases.remove(purchase)) {
            purchaseDao.writeData(purchases);
        }
    }

    /**
     * Updates an existing purchase in the purchase list.
     *
     * @param purchase The purchase with updated information.
     */
    public void updatePurchase(Purchase purchase) {
        List<Purchase> purchases = purchaseDao.readData();
        if (purchases != null) {
            int index = purchases.indexOf(purchase);
            if (index != -1) {
                purchases.set(index, purchase);
                purchaseDao.writeData(purchases);
            }
        }
    }
}
