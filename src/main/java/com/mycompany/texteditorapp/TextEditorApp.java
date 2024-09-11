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
        
        setLocationRelativeTo(null);
        setTitle("Editor de Texto");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Componentes da interface
        textArea = new JTextArea(); // O JTextArea já permite você a fazer tudo o que um editor de texto permite que eh editar texto ou remover texto
        fileNameField = new JTextField(20);
        JButton loadButton = new JButton("Carregar/Editar");
        JButton saveButton = new JButton("Salvar");
        JButton searchButton = new JButton("Procurar Texto");
        setLocationRelativeTo(null);

        // Instanciar gerenciadores que fazem o processo de leitura e escrita de arquivo
        fileManager = new FileManager();
        textSearcher = new TextSearcher();

        // Painel superior (para carregar arquivo)
        JPanel topPanel = new JPanel();
        topPanel.add(new JLabel("Arquivo:"));
        topPanel.add(fileNameField);
        topPanel.add(loadButton);

        // Painel inferior (para salvar e procurar texto)
        JPanel bottomPanel = new JPanel();
        bottomPanel.add(saveButton);
        bottomPanel.add(searchButton);

        // Adicionar componentes à janela
        add(topPanel, BorderLayout.NORTH);
        add(new JScrollPane(textArea), BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

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
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new TextEditorApp().setVisible(true);
        });
    }
}
