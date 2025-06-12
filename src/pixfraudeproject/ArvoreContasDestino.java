package pixfraudeproject;

public class ArvoreContasDestino {
    private NoArvore root;

    private class NoArvore {
        User user;
        NoArvore left, right;

        NoArvore(User user) {
            this.user = user;
            this.left = null;
            this.right = null;
        }
    }

    public void inserirOuAtualizar(User user) {
        root = inserirOuAtualizarRec(root, user);
    }

    private NoArvore inserirOuAtualizarRec(NoArvore node, User user) {
        if (node == null) {
            return new NoArvore(user);
        }
        if (user.accountId.equals(node.user.accountId)) {
            node.user.totalSent += user.totalSent;
            node.user.transactionCount += user.transactionCount;
        } else if (user.totalSent > node.user.totalSent) {
            node.left = inserirOuAtualizarRec(node.left, user);
        } else {
            node.right = inserirOuAtualizarRec(node.right, user);
        }
        return node;
    }

    public void exportarTopN(int limite, CSVBuilder csv) {
        percorrerInOrder(root, limite, csv);
    }

    private void percorrerInOrder(NoArvore node, int limite, CSVBuilder csv) {
        if (node != null && limite > 0) {
            percorrerInOrder(node.right, limite - 1, csv);
            csv.addLine(new String[]{
                node.user.accountId,
                String.format("%.2f", node.user.totalSent),
                String.valueOf(node.user.transactionCount)
            });
            percorrerInOrder(node.left, limite - 1, csv);
        }
    }
}