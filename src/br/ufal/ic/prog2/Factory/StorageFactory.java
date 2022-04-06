package br.ufal.ic.prog2.Factory;

import br.ufal.ic.prog2.DAO.FeedStorage;
import br.ufal.ic.prog2.DAO.UserStorage;

public class StorageFactory {

    private static final UserStorage userStorage = new UserStorage();
    private static final FeedStorage feedStorage = new FeedStorage();

    public static UserStorage getUserStorage() {
        return userStorage;
    }
    public static FeedStorage getFeedStorage(){
        return feedStorage;
    }
}
