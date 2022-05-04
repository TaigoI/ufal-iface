package br.ufal.ic.prog2.Factory;

import br.ufal.ic.prog2.View.*;

public class ViewFactory {

    private static final BaseCLI BASE_CLI = new BaseCLI();
    private static final PagesCLI PAGES_CLI = new PagesCLI();
    private static final PostCLI POST_CLI = new PostCLI();
    private static final ChatCLI CHAT_CLI = new ChatCLI();
    private static final CommunityCLI COMMUNITIES_CLI = new CommunityCLI();
    private static final FeedCLI FEED_CLI = new FeedCLI();
    private static final UserPropertiesCLI USER_PROPERTIES_CLI = new UserPropertiesCLI();

    public static BaseCLI getBaseCLI() {
        return BASE_CLI;
    }
    public static PagesCLI getPagesCLI() {
        return PAGES_CLI;
    }
    public static PostCLI getPostCLI(){
        return POST_CLI;
    }
    public static ChatCLI getChatCLI() {
        return CHAT_CLI;
    }
    public static CommunityCLI getCommunitiesCLI() {
        return COMMUNITIES_CLI;
    }
    public static FeedCLI getFeedCLI() {
        return FEED_CLI;
    }
    public static UserPropertiesCLI getUserPropertiesCLI() {
        return USER_PROPERTIES_CLI;
    }
}
