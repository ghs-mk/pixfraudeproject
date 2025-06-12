package pixfraudeproject;

import java.sql.*;
import java.io.*;

public class PixFraudeProject {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/testenovodb";
        String user = "root";
        String password = "teste";

        TransactionList transferList = new TransactionList();
        TransactionList cashOutList = new TransactionList();
        UserList userList = new UserList();
        ArvoreContasDestino arvoreContas = new ArvoreContasDestino();
        TransactionTypeList tipoFraudes = new TransactionTypeList();

        try {
            Connection conn = DriverManager.getConnection(url, user, password);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM transactions");

            while (rs.next()) {
                Transaction t = new Transaction(
                    rs.getInt("step"),
                    rs.getString("type"),
                    rs.getDouble("amount"),
                    rs.getString("nameOrig"),
                    rs.getDouble("oldbalanceOrg"),
                    rs.getDouble("newbalanceOrig"),
                    rs.getString("nameDest"),
                    rs.getDouble("oldbalanceDest"),
                    rs.getDouble("newbalanceDest"),
                    rs.getInt("isFraud"),
                    rs.getInt("isFlaggedFraud")
                );
                if (t.type.equals("TRANSFER")) {
                    transferList.add(t);
                } else if (t.type.equals("CASH_OUT")) {
                    cashOutList.add(t);
                }
                userList.addOrUpdate(t.nameOrig, t.amount, t.type);
            }

            // ? Exportar fraudes marcadas no banco original
            exportarTransacoesFraudeOriginal(conn);

            conn.close();
        } catch (SQLException e) {
            System.out.println("Erro na conexão: " + e.getMessage());
        }

        // Popular árvore com usuários
        User currentUser = userList.head;
        while (currentUser != null) {
            arvoreContas.inserirOuAtualizar(currentUser);
            currentUser = currentUser.next;
        }


        // Identificar contas zumbis (uma transação)
        CSVBuilder zombieCSV = new CSVBuilder(new String[]{"account", "total_amount", "transaction_count"});
        currentUser = userList.head;
        while (currentUser != null) {
            if (currentUser.transactionCount == 1 && currentUser.totalSent > 10000) {
                zombieCSV.addLine(new String[]{
                    currentUser.accountId,
                    String.format("%.2f", currentUser.totalSent),
                    String.valueOf(currentUser.transactionCount)
                });
            }
            currentUser = currentUser.next;
        }
        zombieCSV.save("zombie_accounts.csv");

        // Contas de alto volume
        CSVBuilder topAccountsCSV = new CSVBuilder(new String[]{"account", "total_amount", "transaction_count"});
        arvoreContas.exportarTopN(10, topAccountsCSV);
        topAccountsCSV.save("top_accounts.csv");

        // Análise de isFraud (curiosidade)
        CSVBuilder fraudCSV = new CSVBuilder(new String[]{"nameOrig", "nameDest", "amount", "type", "step"});
        Transaction current = transferList.head;
        while (current != null) {
            if (current.isFraud == 1) {
                fraudCSV.addLine(new String[]{
                    current.nameOrig,
                    current.nameDest,
                    String.format("%.2f", current.amount),
                    current.type,
                    String.valueOf(current.step)
                });
                tipoFraudes.addOrIncrement(current.type);
            }
            current = current.next;
        }
        current = cashOutList.head;
        while (current != null) {
            if (current.isFraud == 1) {
                fraudCSV.addLine(new String[]{
                    current.nameOrig,
                    current.nameDest,
                    String.format("%.2f", current.amount),
                    current.type,
                    String.valueOf(current.step)
                });
                tipoFraudes.addOrIncrement(current.type);
            }
            current = current.next;
        }
        fraudCSV.save("fraud_transactions.csv");

    }

    //  NOVO MÉTODO ADICIONADO
    public static void exportarTransacoesFraudeOriginal(Connection conn) {
        String sql = "SELECT * FROM transactions WHERE isFraud = 1";
        String arquivoSaida = "original_isfraud.csv";

        try (
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            PrintWriter writer = new PrintWriter(new FileWriter(arquivoSaida))
        ) {
            // Cabeçalho do CSV
            writer.println("step,type,amount,nameOrig,oldbalanceOrg,newbalanceOrig,nameDest,oldbalanceDest,newbalanceDest,isFraud,isFlaggedFraud");

            while (rs.next()) {
                String linha = rs.getInt("step") + "," +
                               rs.getString("type") + "," +
                               rs.getDouble("amount") + "," +
                               rs.getString("nameOrig") + "," +
                               rs.getDouble("oldbalanceOrg") + "," +
                               rs.getDouble("newbalanceOrig") + "," +
                               rs.getString("nameDest") + "," +
                               rs.getDouble("oldbalanceDest") + "," +
                               rs.getDouble("newbalanceDest") + "," +
                               rs.getInt("isFraud") + "," +
                               rs.getInt("isFlaggedFraud");
                writer.println(linha);
            }

            System.out.println("Arquivo original_isfraud.csv gerado com sucesso!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
