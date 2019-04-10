package system.purchase;

import system.customer.Customer;
import system.storage.StorageInterface;

import java.util.IntSummaryStatistics;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

/**
 * Класс, для реализации циклов покупок
 */
public class Purchase implements Runnable {

    private final List<Customer> customers = new LinkedList<>();    //Список покупателей
    private final StorageInterface storage;                         //Интерфейс для обращения к магазину
    private final ExecutorService executor;

    /**
     * Создается  объект CyclicBarrier с параметром равным количеству покупателей,
     * для равномерного расппределения покупок
     * Инициализируются объекты представляющие покупателей
     * @param customersCount количество покупателей
     */
    public Purchase(int customersCount, StorageInterface storage) {
        this.storage = storage;
        CyclicBarrier barrier = new CyclicBarrier(customersCount);
        for(int iterator = 0; iterator < customersCount; iterator++) {
            customers.add(new Customer(iterator+1, barrier, storage));
        }
        executor = Executors.newFixedThreadPool(customers.size());
    }

    /**
     * Метод для запуска первой итерации покупок и метод,
     * выполняющийся в конце цикла итерации (барьерный метод)
     */
    @Override
    public void run() {

        //Пока на складе не закончатся товары, совершать покупки
        while (storage.getProductsBalance() > 0) {
            for (Customer customer : customers) {
                executor.execute(customer);
            }
        }
        executor.shutdown();

        //Вывод списка результатов закупки
        System.out.println("Результат:");
        customers.forEach(customer -> System.out.println(customer.toString()));
        System.out.println("Всего товаров куплено: " +
                customers.stream()
                        .map(Customer::getProducts)
                        .reduce(0, Integer::sum));

        //Вывод дополнительной информации
        IntSummaryStatistics stats = customers.stream()
                .map(Customer::getPurchases)
                .collect(Collectors.summarizingInt(Integer::intValue));

        int min = stats.getMin();
        int max = stats.getMax();

        if(min == max) {
            System.out.println("Количество закупок покупателей одинаково: " + max);
        } else {
            System.out.println("Минимальное и максимальное количество закупок всех покупателей: " +
                    "мин. = " + stats.getMin() + "; макс. = " + stats.getMax());
        }
    }
}
