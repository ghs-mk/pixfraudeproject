package pixfraudeproject;

public class TransactionTypeList {
    TransactionTypeCounter head;

    public TransactionTypeList() {
        head = null;
    }

    public void addOrIncrement(String type) {
        TransactionTypeCounter current = head;
        while (current != null) {
            if (current.type.equals(type)) {
                current.count++;
                return;
            }
            current = current.next;
        }
        TransactionTypeCounter newType = new TransactionTypeCounter(type);
        newType.next = head;
        head = newType;
    }
}