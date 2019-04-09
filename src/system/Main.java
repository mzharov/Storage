package system;

import system.storage.Storage;
import system.storage.StorageInterface;

class Main {

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
                StorageInterface storage = new Storage();
                storage.start(Integer.parseInt(args[0]));
            }
        } else {
            System.out.println("Не указано количество покупателей в параметрах запуска программы");
        }
    }
}
