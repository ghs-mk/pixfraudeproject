package pixfraudeproject;

public class User {
    String accountId; // nameOrig
    double totalSent; // enviado
    double totalReceived; // recebido
    int transactionCount; // todas transações
    User next; // P lista encadeada

    public User(String accountId, double amount, String type) {
        this.accountId = accountId;
        this.totalSent = (type.equals("CASH_IN") ? 0 : amount);
        this.totalReceived = (type.equals("CASH_IN") ? amount : 0);
        this.transactionCount = 1;
        this.next = null;
    }
}