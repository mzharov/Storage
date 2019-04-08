package system.customer;

import system.storage.Storage;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class Customer implements Runnable {

    private int purchases;
    private int products;
    private int id;
    private CyclicBarrier barrier;
    private static Random random = new Random();

    public Customer(int id, CyclicBarrier barrier) {
        this.id = id;
        this.barrier = barrier;
    }


    private synchronized int makePurchase() {
        int nProducts = random.nextInt(10+1);
        int count  = Storage.getProductsCount();
        if(count <= 0) return 0;
        if(nProducts > count) {
            nProducts = count;
        }

        Storage.setProductsCount(nProducts);
        return nProducts;
    }

    @Override
    public void run() {

        int nPurchase = makePurchase();
        if(nPurchase > 0) {
            products +=nPurchase;
            ++purchases;
        }

        try {
            barrier.await();
        } catch (InterruptedException | BrokenBarrierException e) {
            System.out.println("Ошибка в ходе работы потока");
        }
    }

    @Override
    public String toString() {
        return "Покупателем [" + id + "] сделано покупок: " + purchases + "; приобретено товаров: " + products;
    }

    public int getProducts() {
        return products;
    }
}