package br.ufal.ic.prog2.DAO;

public class StorageFactory {

    private static final UserStorage userStorageObject = new UserStorage();

    public static UserStorage getUserStorageObject() {
        return userStorageObject;
    }
}
