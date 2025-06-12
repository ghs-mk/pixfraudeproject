package pixfraudeproject;

public class TransactionList {
    Transaction head;

    public TransactionList() {
        head = null;
    }

    public void add(Transaction t) {
        t.next = head;
        head = t;
    }
}