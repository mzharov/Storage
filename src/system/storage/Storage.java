package system.storage;

import system.purchase.Purchase;


public class Storage implements StorageInterface{

    private static int storageProducts = 1000;

    @Override
    public void start(int customersCount) {
        Thread purchaseThread = new Thread(new Purchase(customersCount, this));
        purchaseThread.start();
        try {
            purchaseThread.join();
        } catch (InterruptedException e) {
            System.out.println("Ошибка в ходе работы потока");
        }
    }

    @Override
    public synchronized int makePurchase(int productsCount) {
        if(storageProducts > 0) {
            if(productsCount > storageProducts) {
                productsCount = storageProducts;
            }
            storageProducts -= productsCount;
            return productsCount;
        } else {
            return 0;
        }
    }

    @Override
    public int getProductsBalance() {
        return storageProducts;
    }
}
