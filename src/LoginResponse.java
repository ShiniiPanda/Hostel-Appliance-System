public class LoginResponse {

    public int result;
    public User user;

    public LoginResponse(int r, User u) {
        this.result = r;
        this.user = u;
    }
}
