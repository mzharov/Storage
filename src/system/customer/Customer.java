package system.customer;

import system.storage.StorageInterface;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * Класс "покупателя"
 */
public class Customer implements Runnable {

    private int purchases;                      //Количество совершенных покупок
    private int products;                       //Количество купленных товаров
    private final int id;                       //Номер покупателя
    private final CyclicBarrier barrier;        //Барьер для равномерного распределения покупок
    private final StorageInterface storage;     // Интерфейс для обращения к магазину

    private final static
    Random random = new Random();               //Генератор случайных чисел

    public Customer(int id, CyclicBarrier barrier, StorageInterface storage) {
        this.id = id;
        this.barrier = barrier;
        this.storage = storage;
    }

    /**
     * Метод для совершения покупки.
     * Если на складе еще остался товар, то проверяется его количество
     * 1) если его меньше, чем кол-во товаров, которые покупатель хочет купить, то ему достается остаток;
     * 2) если его достаточно, то из кол-ва товаров на складе вычитается кол-во, которое покупатель хочет купить;
     * 3) если ничего не осталось, ничего не изменяется.
     */
    private void makePurchase() {
        int productsCount = random.nextInt(10) + 1;
        productsCount = storage.makePurchase(productsCount);

        if(productsCount > 0) {
            products += productsCount;
            ++purchases;
        }
    }

    /**
     * Вызов метода совершения покупки и ожидание, пока остальные покупатели не закончат цикл
     */
    @Override
    public void run() {
        makePurchase();
        try {
            barrier.await();
        }
        catch (InterruptedException | BrokenBarrierException e) {
            System.out.println("Ошибка в ходе работы потока");
        }
    }

    @Override
    public String toString() {
        return "Покупателем [" + id + "] сделано покупок: " + purchases + "; приобретено товаров: "
                + products;
    }

    public int getProducts() {
        return products;
    }
    public int getPurchases() {return purchases;}
}
