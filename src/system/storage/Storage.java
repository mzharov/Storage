package system.storage;

import system.customer.Customer;
import system.purchase.Purchase;
import java.util.concurrent.atomic.AtomicInteger;

public class Storage {

    private static AtomicInteger productsCount = new AtomicInteger(5000);

    public static int getProductsCount() {return productsCount.get();}
    public static void changeProductsCount(Integer count) {
        productsCount.addAndGet(count);
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

                int count = 0;
                for(Customer customer : purchase.getCustomers()) {
                    count +=customer.getProducts();
                }
                System.out.println(count);
            }
        } else {
            System.out.println("Не указано количество покупателей в параметрах запуска программы");
        }
    }
}
