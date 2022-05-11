package br.ufal.ic.prog2.Factory;

import br.ufal.ic.prog2.Model.DAO.CommunityStorage;
import br.ufal.ic.prog2.Model.DAO.FeedStorage;
import br.ufal.ic.prog2.Model.DAO.UserStorage;

public class StorageFactory {

    private static final UserStorage USER_STORAGE = new UserStorage();
    private static final CommunityStorage COMMUNITY_STORAGE = new CommunityStorage();
    private static final FeedStorage FEED_STORAGE = new FeedStorage();

    public static UserStorage getUserStorage() {
        return USER_STORAGE;
    }
    public static CommunityStorage getCommunityStorage() {
        return COMMUNITY_STORAGE;
    }
    public static FeedStorage getFeedStorage() {
        return FEED_STORAGE;
    }
}
