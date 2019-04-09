package system.storage;

public interface StorageInterface {
    int makePurchase(int productsCount);
    int getProductsBalance();
    void start(int customersCount);
}
