import java.util.ArrayList;
import java.util.List;

public class User {
    private String username;
    private String email;
    private String password;
    private List<Item> userItems;

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.userItems = new ArrayList<>();
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public boolean checkPassword(String inputPassword) {
        return this.password.equals(inputPassword);
    }

    public List<Item> getUserItems() {
        return userItems;
    }

    public void addItem(Item item) {
        userItems.add(item);
    }
}
