package system.storage;

import system.purchase.Purchase;

import java.util.concurrent.atomic.AtomicInteger;


public class Storage {

    private final static
    AtomicInteger productsCount = new AtomicInteger(1000); //Количество товаров на складе

    public static int getProductsCount() {return productsCount.get();}

    /**
     * Изменение количества товаров на складе (вычитание из текущего количества)
     * @param count количество товаров, которые куплены
     */
    public static void subProductsCount(Integer count) {
        productsCount.addAndGet(-count);
    }

    /**
     * Проверка начального аргумента, является ли он числом
     * @param parameter строка - входной параметр программы
     * @return true - если в строке хранится число, false - иначе
     */
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
            }
        } else {
            System.out.println("Не указано количество покупателей в параметрах запуска программы");
        }
    }
}
