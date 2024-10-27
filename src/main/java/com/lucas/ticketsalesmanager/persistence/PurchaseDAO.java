package com.lucas.ticketsalesmanager.persistence;

import com.google.gson.reflect.TypeToken;
import com.lucas.ticketsalesmanager.models.Event;
import com.lucas.ticketsalesmanager.models.Purchase;
import com.lucas.ticketsalesmanager.models.Ticket;
import com.lucas.ticketsalesmanager.models.User;
import com.lucas.ticketsalesmanager.models.paymentMethod.Card;
import com.lucas.ticketsalesmanager.models.paymentMethod.Payment;
import jakarta.mail.MessagingException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class PurchaseDAO {
    private static final String FILE_PATH = "purchase.json";
    private static final Type purchaseListType = new TypeToken<List<Purchase>>(){}.getType();
    private final DataAccessObject<Purchase> purchaseDao;

    public PurchaseDAO() {
        this.purchaseDao = new DataAccessObject<>(FILE_PATH, purchaseListType);
    }

    // Add a new purchase
    public void addPurchase(User user, Ticket ticket, Card card) throws MessagingException {
        Purchase purchase = new Purchase(user, ticket, card);
        List<Purchase> purchases = purchaseDao.readData();
        if (purchases == null) {
            purchases = new ArrayList<>();
        }
        purchases.add(purchase);
        purchaseDao.writeData(purchases);
        purchase.processPurchase(user, ticket, card);
    }

    // Cancel purchase
    public void cancelPurchase(Purchase purchase) {
        purchase.getTicket().cancel();
        updatePurchase(purchase);
    }

    // Reactivate canceled purchase
    public void reactivatePurchase(Purchase purchase) {
        purchase.getTicket().reactivate();
        updatePurchase(purchase);
    }

    // List all purchases by event
    public List<Purchase> listPurchasesByEvent(Event event) {
        List<Purchase> purchases = purchaseDao.readData();
        if (purchases != null) {
            return purchases.stream()
                    .filter(purchase -> purchase.getTicket().getEvent().equals(event))
                    .collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    // List all purchases
    public List<Purchase> listPurchases() {
        List<Purchase> purchases = purchaseDao.readData();
        return purchases != null ? purchases : new ArrayList<>();
    }

    // Find purchases by user
    public List<Purchase> findPurchasesByUser(User user) {
        List<Purchase> purchases = purchaseDao.readData();
        if (purchases != null) {
            return purchases.stream()
                    .filter(purchase -> purchase.getUser().equals(user))
                    .collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    // Find purchases by ticket
    public List<Purchase> findPurchasesByTicket(Ticket ticket) {
        List<Purchase> purchases = purchaseDao.readData();
        if (purchases != null) {
            return purchases.stream()
                    .filter(purchase -> purchase.getTicket().equals(ticket))
                    .collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    // Find purchases by payment method
    public List<Purchase> findPurchasesByPaymentMethod(Payment paymentMethod) {
        List<Purchase> purchases = purchaseDao.readData();
        if (purchases != null) {
            return purchases.stream()
                    .filter(purchase -> purchase.getPaymentMethod().equals(paymentMethod))
                    .collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    // Find purchase by purchase date
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

    // Remove a purchase
    public void removePurchase(Purchase purchase) {
        List<Purchase> purchases = purchaseDao.readData();
        if (purchases != null && purchases.remove(purchase)) {
            purchaseDao.writeData(purchases);
        }
    }

    // Update a purchase
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
