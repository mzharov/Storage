package system.purchase;

import system.customer.Customer;
import system.storage.Storage;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class Purchase implements Runnable {

    private List<Customer> customers = new LinkedList<>();

    public Purchase(int customersCount) {
        CyclicBarrier barrier = new CyclicBarrier(customersCount, this);
        for(int iterator = 0; iterator < customersCount; iterator++) {
            customers.add(new Customer(iterator+1, barrier));
        }
    }

    @Override
    public void run() {
        if(Storage.getProductsCount() > 0) {
            ExecutorService executor = Executors.newFixedThreadPool(customers.size());
            for (Customer customer : customers) {
                executor.execute(customer);
            }
            executor.shutdown();
        }
    }

    public List<Customer> getCustomers() {
        return customers;
    }

}
