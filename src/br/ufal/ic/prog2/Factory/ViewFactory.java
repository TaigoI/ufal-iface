package br.ufal.ic.prog2.Factory;

import br.ufal.ic.prog2.View.*;

public class ViewFactory {

    private static final PagesCLI PAGES_CLI = new PagesCLI();
    private static final PostCLI POST_CLI = new PostCLI();
    private static final ChatCLI CHAT_CLI = new ChatCLI();
    private static final CommunityCLI COMMUNITIES_CLI = new CommunityCLI();
    private static final FeedCLI FEED_CLI = new FeedCLI();
    private static final UserCLI USER_CLI = new UserCLI();
    private static final FriendsCLI FRIENDS_CLI = new FriendsCLI();

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
    public static UserCLI getUserCLI() {
        return USER_CLI;
    }
    public static FriendsCLI getFriendsCLI(){
        return FRIENDS_CLI;
    }
}
