package project.server.work_with_client.classes;

/**
 * Created by rikachka on 07.11.15.
 */
public class User {
    private Long id;
    private String login;
    private String nickname = "";
    private String password;

    User(Long id, String login, String password) {
        this.id = id;
        this.login = login;
        this.password = password;
    }

    public User(String[] userInfo) throws Exception {
        if (userInfo.length != 4) {
            throw new Exception("creating user: wrong number of fields - " + userInfo.length);
        }
        try {
            id = new Long(userInfo[0]);
        } catch (Exception e) {
            throw new Exception("creating user: wrong format of arguments");
        }
        login = userInfo[1];
        nickname = userInfo[2];
        password = userInfo[3];
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

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
