package com.vanlam.foodle.listeners;

public interface HandleOrder {
    void cancelOrder(String orderId);

    void receivedOrder(String orderId);
}
