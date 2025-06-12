package pixfraudeproject;

public class CSVBuilder {
    StringBuilder content;

    public CSVBuilder(String[] header) {
        content = new StringBuilder();
        content.append(String.join(",", header)).append("\n");
    }

    public void addLine(String[] line) {
        content.append(String.join(",", line)).append("\n");
    }

    public void save(String filename) {
        try {
            java.io.FileWriter writer = new java.io.FileWriter(filename);
            writer.write(content.toString());
            writer.close();
            System.out.println("Arquivo salvo: " + filename);
        } catch (java.io.IOException e) {
            System.out.println("Erro ao salvar CSV: " + e.getMessage());
        }
    }
}