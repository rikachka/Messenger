package project.server.database.classes;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by rikachka on 07.11.15.
 */
public class Users {
    final private String BASIC_PASSWORD = "0";

    private Map<Long, User> users = new HashMap<>();
    private Map<String, Long> logins = new HashMap<>();

    public User createUser(String login) {
        long createdId = users.size() + 1;
        User createdUser = new User(createdId, login, BASIC_PASSWORD);
        users.put(createdId, createdUser);
        logins.put(login, createdId);
        return createdUser;
    }

    public User getUser(Long id) {
        User user = null;
        if (users.containsKey(id)) {
            user = users.get(id);
        }
        return user;
    }

    public User getUser(String login) {
        User user = null;
        if (logins.containsKey(login)) {
            Long userId = logins.get(login);
            user = users.get(userId);
        }
        return user;
    }

    public User getUser(String login, String password) {
        User user = null;
        if (logins.containsKey(login)) {
            long userId = logins.get(login);
            String rightPassword = users.get(userId).getPassword();
            if (password.equals(rightPassword)) {
                user = users.get(userId);
            }
        }
        return user;
    }

    public void addUser(User user) {
        users.put(user.getId(), user);
        logins.put(user.getLogin(), user.getId());
    }

    public Collection<User> getUsers() {
        return users.values();
    }
}
