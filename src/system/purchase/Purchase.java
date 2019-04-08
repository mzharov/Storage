package system.purchase;

import system.customer.Customer;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Класс, для реализации циклов покупок
 */
public class Purchase implements Runnable {

    private final List<Customer> customers = new LinkedList<>();    //Список покупателей
    private final AtomicInteger productsCount;                      //Количество товаров на складе

    /**
     * Создается барьерная переменная и покупатели добавляются в список после инициализации,
     * в ней задается количество потоков (равное количеству покупателей) и объект Runnable, метод
     * которого будет выполняться после завершения цикла (в данном случае сам объект Purchase)
     * @param customersCount количество покупателей
     */
    public Purchase(int customersCount, AtomicInteger productsCount) {
        this.productsCount = productsCount;
        CyclicBarrier barrier = new CyclicBarrier(customersCount, this);
        for(int iterator = 0; iterator < customersCount; iterator++) {
            customers.add(new Customer(iterator+1, barrier, productsCount));
        }
    }

    /**
     * Метод для запуска первой итерации покупок и метод,
     * выполняющийся в конце цикла итерации (барьерный метод)
     */
    @Override
    public void run() {
        if(productsCount.get() > 0) {
            ExecutorService executor = Executors.newFixedThreadPool(customers.size());
            for (Customer customer : customers) {
                executor.execute(customer);
            }
            executor.shutdown();
        } else {
            System.out.println("Результат:");
            customers.forEach(customer -> System.out.println(customer.toString()));
            System.out.println("Всего товаров куплено: "
                    + customers.stream()
                    .map(Customer::getProducts)
                    .reduce(0, Integer::sum));
        }
    }

}
