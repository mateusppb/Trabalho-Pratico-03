# 📂 Estruturas de Dados - Manipulação de Arquivos CSV (Disney+)

Este repositório contém implementações em **Java** e **C** que trabalham com o dataset `disneyplus.csv`.  
Os programas fazem a leitura, manipulação e ordenação de registros de filmes e séries, aplicando diferentes estruturas de dados e algoritmos clássicos.

---

## 📌 Projetos

### 🔹 Quicksort com Lista Duplamente Encadeada (C)
**Arquivo:** `quicksort.c`

- Lê o arquivo `disneyplus.csv` e armazena os registros em memória.  
- Implementa um **QuickSort** que ordena os títulos por **data de adição** (critério principal) e **nome do título** (critério de desempate).  
- Os resultados são inseridos em uma **lista duplamente encadeada** e exibidos em ordem.  
- O parser do CSV trata campos entre aspas, valores ausentes e normaliza dados como `NaN`.  

👉 **Conceitos aplicados:** manipulação manual de CSV, listas dinâmicas, ordenação com múltiplos critérios.

---

### 🔹 Pilha Flexível (Java)
**Arquivo:** `PilhaFlex.java`

- Implementa uma **pilha dinâmica** de registros do tipo `Show`.  
- Permite operações de **empilhar (push)**, **desempilhar (pop)** e **exibir** os elementos.  
- A pilha é construída a partir dos dados lidos do `disneyplus.csv`.  

👉 **Conceitos aplicados:** estruturas dinâmicas (pilha encadeada), parsing de arquivos CSV, POO em Java.

---

### 🔹 Árvore Binária de Busca (Java)
**Arquivo:** `Principal.java`

- Implementa uma **árvore binária de busca (ABB)** para armazenar registros de filmes e séries.  
- A busca é feita pelo campo **título**, exibindo o **caminho percorrido** na árvore até encontrar (ou não) o registro.  
- Também realiza a leitura manual do `disneyplus.csv`, tratando strings com aspas e vírgulas.  

👉 **Conceitos aplicados:** árvores de busca binária, inserção ordenada, busca com rastreamento do caminho.

---

## 🚀 Execução

### Java
```bash
# Compilar
javac Principal.java
javac PilhaFlex.java

# Executar
java Principal < disneyplus.csv
java PilhaFlex < disneyplus.csv

# Compilar
gcc quicksort.c -o quicksort

# Executar
./quicksort < disneyplus.csv
```

---

## 👤 Autor

Mateus Pimenta

Curso de Ciência da Computação – PUC Minas  

mateuspimentapb@gmail.com
