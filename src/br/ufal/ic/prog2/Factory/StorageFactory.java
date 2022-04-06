package br.ufal.ic.prog2.Factory;

import br.ufal.ic.prog2.DAO.UserStorage;

public class StorageFactory {

    private static final UserStorage userStorage = new UserStorage();

    public static UserStorage getUserStorage() {
        return userStorage;
    }
}
