package main.application;

public class Application {
	public static void main(String[] args) {

        try {
            ChatMediator chatMediator = new ChatMediator();
            chatMediator.openConversation();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}
