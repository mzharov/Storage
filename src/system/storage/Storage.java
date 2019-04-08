package system.storage;

import system.purchase.Purchase;

import java.util.concurrent.atomic.AtomicInteger;


class Storage {

    private static final int STORAGE_SIZE = 1000; //Размер склада

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
                Purchase purchase = new Purchase(Integer.parseInt(args[0]),
                        new AtomicInteger(STORAGE_SIZE));
                purchase.run();
            }
        } else {
            System.out.println("Не указано количество покупателей в параметрах запуска программы");
        }
    }
}
