package businessObjects;

/**
 * Created by Sergey Mokhov (sergey) on 2017-01-10.
 */
public class User {
    private String username;
    private String password;

    public User(String username, String password){
        this.username = username;
        this.password = password;
    }

    public User(){
        this.username = "root";
        this.password = "root1234";
    }

    public String getUsername() {
        return username;
    }
    public String getPassword(){
        return password;
    }
}