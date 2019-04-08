package system.purchase;

import system.customer.Customer;
import system.storage.Storage;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Purchase implements Runnable {

    private List<Customer> customers = new LinkedList<>();
    private ExecutorService executor = Executors.newCachedThreadPool();
    private Integer productsCount;

    public Purchase(int customersCount, Integer productsCount) {
        this.productsCount = productsCount;
        CyclicBarrier barrier = new CyclicBarrier(customersCount, this);
        for(int iterator = 0; iterator < customersCount; iterator++) {
            customers.add(new Customer(iterator+1, barrier));
        }
    }

    @Override
    public void run() {
        if(Storage.getProductsCount() > 0) {
            for (Customer customer : customers) {
                executor.execute(customer);
            }
        }
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public Integer getProductsCount() {
        return productsCount;
    }
}
