package pixfraudeproject;

public class Transaction {
    int step;
    String type;
    double amount;
    String nameOrig;
    double oldbalanceOrg;
    double newbalanceOrig;
    String nameDest;
    double oldbalanceDest;
    double newbalanceDest;
    int isFraud;
    int isFlaggedFraud;
    Transaction next;

    public Transaction(int step, String type, double amount, String nameOrig,
                       double oldbalanceOrg, double newbalanceOrig, String nameDest,
                       double oldbalanceDest, double newbalanceDest, int isFraud, int isFlaggedFraud) {
        this.step = step;
        this.type = type;
        this.amount = amount;
        this.nameOrig = nameOrig;
        this.oldbalanceOrg = oldbalanceOrg;
        this.newbalanceOrig = newbalanceOrig;
        this.nameDest = nameDest;
        this.oldbalanceDest = oldbalanceDest;
        this.newbalanceDest = newbalanceDest;
        this.isFraud = isFraud;
        this.isFlaggedFraud = isFlaggedFraud;
        this.next = null;
    }
}