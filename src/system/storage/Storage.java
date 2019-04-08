package system.storage;

import system.customer.Customer;
import system.purchase.Purchase;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class Storage {

    private static volatile AtomicInteger productsCount = new AtomicInteger(1000);

    public static int getProductsCount() {return productsCount.get();}
    public static void setProductsCount(Integer count) {
        productsCount.addAndGet(-count);
    }

    private static boolean isInteger(String parameter) {
        try {
            Integer.parseInt(parameter);
            return true;
        } catch (NumberFormatException e) {
            System.out.println("Указанный входной параметр не является числом");
            return false;
        }
    }
    public static void main(String[] args) {

        if(args.length !=0) {
            if(isInteger(args[0])) {
                Purchase purchase = new Purchase(Integer.parseInt(args[0]));
                purchase.run();

                System.out.println("Результат:");
                purchase.getCustomers().forEach(customer -> System.out.println(customer.toString()));
                System.out.println("Всего товаров куплено: "
                        + purchase.getCustomers().stream()
                        .map(Customer::getProducts)
                        .reduce(0, Integer::sum));
            }
        } else {
            System.out.println("Не указано количество покупателей в параметрах запуска программы");
        }
        System.out.println("Программа закончена");
    }
}
