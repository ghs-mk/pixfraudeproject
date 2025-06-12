package pixfraudeproject;

public class TransactionTypeCounter {
    String type;
    int count;
    TransactionTypeCounter next;

    public TransactionTypeCounter(String type) {
        this.type = type;
        this.count = 1;
        this.next = null;
    }
}

