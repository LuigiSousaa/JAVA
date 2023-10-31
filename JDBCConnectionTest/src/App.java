import Connection.ConnectionDAO;

public class App {
    public static void main(String[] args) throws Exception {
        new ConnectionDAO().inserir("Luigi", "luigi@gmail.com");
    }
}
