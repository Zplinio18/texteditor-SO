package com.mycompany.texteditorapp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class TextEditorApp extends JFrame {
    private JTextArea textArea;
    private JTextField fileNameField;
    private FileManager fileManager;
    private TextSearcher textSearcher;

    public TextEditorApp() {
        setTitle("Editor de Texto");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        

        // Componentes da interface
        textArea = new JTextArea();
        fileNameField = new JTextField(20);
        JButton loadButton = new JButton("Carregar/Editar");
        JButton saveButton = new JButton("Salvar");
        JButton searchButton = new JButton("Procurar Texto");
        JButton deleteButton = new JButton("Excluir");
        setLocationRelativeTo(null);

        // Instanciar gerenciadores
        fileManager = new FileManager();
        textSearcher = new TextSearcher();

        // Painel superior (para carregar arquivo)
        JPanel topPanel = new JPanel();
        topPanel.add(new JLabel("Arquivo:"));
        topPanel.add(fileNameField);
        topPanel.add(loadButton);

        // Painel inferior (para salvar, procurar texto e excluir arquivo)
        JPanel bottomPanel = new JPanel();
        bottomPanel.add(saveButton);
        bottomPanel.add(searchButton);
        bottomPanel.add(deleteButton);

        // Adicionar componentes à janela
        add(topPanel, BorderLayout.NORTH);
        add(new JScrollPane(textArea), BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
        
        JOptionPane.showMessageDialog(null, "Bem-vindo ao Editor de Texto:\n \n"
                + "1. Adicione o nome de um arquivo existente ou crie um;\n"
                + "2. Edite o texto como desejar;\n"
                + "3. Excluia o arquivo que está editando;\n\n");


        // Ações dos botões
        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String fileName = fileNameField.getText();
                try {
                    String content = fileManager.loadFile(fileName);
                    textArea.setText(content);
                    JOptionPane.showMessageDialog(null, "Arquivo carregado com sucesso!");
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "Erro ao carregar arquivo: " + ex.getMessage());
                }
            }
        });

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String fileName = fileNameField.getText();
                String content = textArea.getText();
                try {
                    fileManager.saveFile(fileName, content);
                    JOptionPane.showMessageDialog(null, "Arquivo salvo com sucesso!");
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "Erro ao salvar arquivo: " + ex.getMessage());
                }
            }
        });

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String searchText = JOptionPane.showInputDialog("Digite o texto a ser procurado:");
                if (searchText != null) {
                    int position = textSearcher.searchText(textArea.getText(), searchText);
                    if (position != -1) {
                        textArea.setCaretPosition(position);
                        textArea.requestFocus();
                        JOptionPane.showMessageDialog(null, "Texto encontrado na posição: " + position);
                    } else {
                        JOptionPane.showMessageDialog(null, "Texto não encontrado.");
                    }
                }
            }
        });

        deleteButton.addActionListener(new ActionListener() { 
            @Override
            public void actionPerformed(ActionEvent e) {
                String fileName = fileNameField.getText();
                int confirm = JOptionPane.showConfirmDialog(null, "Deseja realmente excluir o arquivo?", "Confirmação", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    try {
                        boolean success = fileManager.deleteFile(fileName);
                        if (success) {
                            textArea.setText("");  // Limpa a área de texto
                            fileNameField.setText("");  // Limpa o campo de nome do arquivo
                            JOptionPane.showMessageDialog(null, "Arquivo excluído com sucesso!");
                        } else {
                            JOptionPane.showMessageDialog(null, "Erro ao excluir o arquivo.");
                        }
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(null, "Erro ao excluir arquivo: " + ex.getMessage());
                    }
                }
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new TextEditorApp().setVisible(true);
        });
    }
}

