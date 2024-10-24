package com.lucas.ticketsalesmanager.persistence;

import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.lucas.ticketsalesmanager.models.Event;
import com.lucas.ticketsalesmanager.models.User;
import com.lucas.ticketsalesmanager.models.Ticket;
import com.lucas.ticketsalesmanager.models.paymentMethod.Card;
import com.lucas.ticketsalesmanager.models.Purchase;


public class PurchaseDAO {
    public static void main(String[] args) {
        // Define the file path where purchases will be saved
        String filePath = "purchases.json";

        // Define the type for the list of purchases
        Type purchaseListType = new TypeToken<List<Purchase>>(){}.getType();

        // Create an instance of DataAccessObject for purchases
        DataAccessObject<Purchase> purchaseDao = new DataAccessObject<>(filePath, purchaseListType);

        // Create some test data
        User user = new User("john_doe", "password123", "John Doe", "12345678900", "john@example.com", false);
        Ticket ticket = new Ticket(new Event("Concert", "A great musical concert",
                new Date(System.currentTimeMillis() + 86400000)), 100.0f, "A1");

        Card card1 = new Card("123456789", "123",
                new Date(System.currentTimeMillis() + 86400000), true);

        card1.pay("123");

        // Create a purchase instance
        Purchase purchase = new Purchase(user, ticket, card1);

        // Create a list of purchases for testing
        List<Purchase> purchases = new ArrayList<>();
        purchases.add(purchase);

        // Save the purchases to the file
        purchaseDao.writeData(purchases);
        System.out.println("Purchases saved successfully!");

        // Read the purchases from the file
        List<Purchase> loadedPurchases = purchaseDao.readData();
        if (loadedPurchases != null) {
            System.out.println("Loaded purchases:");
            for (Purchase p : loadedPurchases) {
                System.out.println(p);
            }
        } else {
            System.out.println("No purchases found or error loading.");
        }
    }
}
