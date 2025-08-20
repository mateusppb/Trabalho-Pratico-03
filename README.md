# 📂 Estruturas de Dados - Manipulação de Arquivos CSV (Disney+)

Este repositório contém implementações em **Java** que trabalha com o dataset `disneyplus.csv`.  
Os programas fazem a leitura e manipulaçãode registros de filmes e séries, aplicando diferentes estruturas de dados e algoritmos clássicos.

---

## 📌 Projetos

### 🔹 Pilha Flexível (Java)
**Arquivo:** `PilhaFlex.java`

- Implementa uma **pilha dinâmica** de registros do tipo `Show`.  
- Permite operações de **empilhar (push)**, **desempilhar (pop)** e **exibir** os elementos.  
- A pilha é construída a partir dos dados lidos do `disneyplus.csv`.  

👉 **Conceitos aplicados:** estruturas dinâmicas (pilha encadeada), parsing de arquivos CSV, POO em Java.

---

### 🔹 Árvore Binária de Busca (Java)
**Arquivo:** `arvoreBST.java`

- Implementa uma **árvore binária de busca (ABB)** para armazenar registros de filmes e séries.  
- A busca é feita pelo campo **título**, exibindo o **caminho percorrido** na árvore até encontrar (ou não) o registro.  
- Também realiza a leitura manual do `disneyplus.csv`, tratando strings com aspas e vírgulas.  

👉 **Conceitos aplicados:** árvores de busca binária, inserção ordenada, busca com rastreamento do caminho (sem balanceamento).

---

## 🛠️ Tecnologias Utilizadas

<p align="left">
  <img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/java/java-original.svg" width="50" height="50"/>
</p>

![CSV](https://img.shields.io/badge/CSV-Data-blue?style=for-the-badge)

![Estruturas de Dados](https://img.shields.io/badge/Estruturas%20de%20Dados-Algoritmos-green?style=for-the-badge)

---

## 🚀 Execução
```bash
git clone https://github.com/mateusppb/Trabalho-Pratico-03.git
cd Trabalho-Pratico-03
```

**Pilha Flexível**

<img width="1816" height="511" alt="Captura de Tela (82)" src="https://github.com/user-attachments/assets/3d34c561-c25a-4d18-88cb-cda7199a3a5b" />

```bash
# Compilar
javac PilhaFlex.java

# Executar
java PilhaFlex < disneyplus.csv
```
**Árvore Binária**

<img width="681" height="763" alt="Captura de Tela (81)" src="https://github.com/user-attachments/assets/85e94ba3-105a-407e-904f-9e5b8048df55" />

```bash
# Compilar
javac arvoreBST.java

# Executar
java arvoreBST < disneyplus.csv
```

---

## 👤 Autor

Mateus Pimenta

Curso de Ciência da Computação – PUC Minas  

mateuspimentapb@gmail.com
