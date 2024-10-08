package com.mycompany.texteditorapp;

import java.io.*; //Essa eh a biblioteca Java que permite fazer essa interação com arquivos externos

public class FileManager {

    // Carrega o arquivo, se não existir cria um novo
    public String loadFile(String fileName) throws IOException {
        File file = new File(fileName);
        if (!file.exists()) {
            file.createNewFile();
            return "";
        }

        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
        }

        return content.toString();
    }

    // Salva o conteúdo no arquivo
    public void saveFile(String fileName, String content) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write(content);
        }
    }

    // Exclui o arquivo
    public boolean deleteFile(String fileName) throws IOException {
        File file = new File(fileName);
        if (file.exists()) {
            return file.delete();  // Retorna true se o arquivo foi excluído
        } else {
            throw new IOException("Arquivo não encontrado.");
        }
    }
}


//Em quase todas as funções dessa classe usei o Buffered que permite otimizar a passagem de dados de arquivos externos, em C o IOStream ja possui isso como default
