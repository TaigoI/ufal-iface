package br.ufal.ic.prog2.Factory;

import br.ufal.ic.prog2.Model.DAO.UserStorage;
import br.ufal.ic.prog2.View.*;

public class ViewFactory {

    private static final BaseCLI BASE_CLI = new BaseCLI();
    private static final ChatCLI CHAT_CLI = new ChatCLI();
    private static final CommunitiesCLI COMMUNITIES_CLI = new CommunitiesCLI();
    private static final FeedCLI FEED_CLI = new FeedCLI();
    private static final UserPropertiesCLI USER_PROPERTIES_CLI = new UserPropertiesCLI();

    public static BaseCLI getBaseCLI() {
        return BASE_CLI;
    }
    public static ChatCLI getChatCLI() {
        return CHAT_CLI;
    }
    public static CommunitiesCLI getCommunitiesCLI() {
        return COMMUNITIES_CLI;
    }
    public static FeedCLI getFeedCLI() {
        return FEED_CLI;
    }
    public static UserPropertiesCLI getUserPropertiesCLI() {
        return USER_PROPERTIES_CLI;
    }
}
