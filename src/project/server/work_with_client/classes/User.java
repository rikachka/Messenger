package project.server.work_with_client.classes;

import project.server.work_with_client.database.IdentifiedObject;

/**
 * Created by rikachka on 07.11.15.
 */
public class User implements IdentifiedObject {
    private Long id;
    private String login;
    private String password;
    private String nickname = "";

    User(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public User(Long id, String login, String password, String nickname) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.nickname = nickname;
    }

    public Long getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
