package pixfraudeproject;

public class UserList {
    User head;

    public UserList() {
        head = null;
    }

    public void addOrUpdate(String accountId, double amount, String type) {
        User current = head;
        while (current != null) {
            if (current.accountId.equals(accountId)) {
                current.totalSent += (type.equals("CASH_IN") ? 0 : amount);
                current.totalReceived += (type.equals("CASH_IN") ? amount : 0);
                current.transactionCount++;
                return;
            }
            current = current.next;
        }
        User newUser = new User(accountId, amount, type);
        newUser.next = head;
        head = newUser;
    }
}