// A logica pensada foi de adicionar e editar linhas, ou seja, ao selecionar uma linha deve-se escrever tudo oq possui nela!!

#include <iostream>
#include <fstream>
#include <vector>
#include <string>

using namespace std;

void showMenu() {
    cout << "==============================";
    cout << "\n=      Editor de Texto       =\n";
    cout << "==============================\n";
    cout << "1. Escrever nova linha\n";
    cout << "2. Mostrar conteúdo\n";
    cout << "3. Editar linha\n";
    cout << "4. Remover linha\n";
    cout << "5. Salvar e sair\n";
    cout << "==============================\n";
}

// Mostra o conteudo que será adicionado mas nao mostra o que ja ta no documento (corrigir)
void displayContent(const vector<string> & content) {

    // Essa função exibe o conteúdo do arquivo rodando todo o vetor content de Strings
    cout << "-----------------------------------";
    cout << "\nConteúdo atual:\n \n";
    for (size_t i = 0; i < content.size(); ++i) {
        cout << i + 1 << ": " << content[i] << endl;
    }
    cout << "-----------------------------------\n";
}

// Está reescrevendo o conteudo, estudar a possibilidade de adicionar conteudo ao final do arquivo para salvar as linhas que ja foram escritas
void saveToFile(const vector<string>& content, const string& filename) {
    ofstream file(filename); // abre o arquivo para escrita utilizando o ofstream (PERGUNTAR PROFESSOR)
    if (!file.is_open()) {
        cout << "Erro ao abrir o arquivo para escrita.\n";
        return;
    }

    for (const auto& line : content) {
        file << line << "\n";
    }

    file.close();
    cout << "Conteúdo salvo em " << filename << endl;
}

void editLine(vector<string> & content) {
    //implementar logica de edição de linha
}

void removeLine(vector<string>& content) {
    //implementar logica de remoção de linha
}

int main() {
    vector<string> content; // conteúdo do arquivo, optei por usar Vector pois eh mais facil de manipular linhas
    string filename; // nome do arquivo que vai ser editado

    cout << "Bem-vindo ao Editor de Texto!\n";
    cout << "Informe o nome do arquivo para salvar (somente o nome): ";
    getline(cin, filename);

    int option;
    do {
        showMenu();
        cin >> option;
        cin.ignore(); 

        // loop de execução do editor para quando 5 for digitado
        switch (option) {
            case 1: {
                cout << "Digite a nova linha de texto (digite \"exit\" para parar): \n";
                string line;
                while (getline(cin, line)) {
                    if (line == "exit") break;
                    content.push_back(line);
                }
                break;
            }
            case 2:
                displayContent(content);
                break;
            case 3:
                editLine(content);
                break;
            case 4:
                removeLine(content);
                break;
            case 5:
                saveToFile(content, filename);
                cout << "Saindo do editor...\n";
                break;
            default:
                cout << "Opção inválida, tente novamente.\n";
        }
    } while (option != 5);

    return 0;
}