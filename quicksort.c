#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h>

#define MAX_LINE 1000
#define MAX_SHOWS 2000
#define MAX_IDS 2000

typedef struct {
    char show_id[20];
    char type[20];
    char title[200];
    char director[100];
    char cast[10][100];
    int cast_size;
    char country[50];
    char date_added[30];
    int release_year;
    char rating[10];
    char duration[20];
    char listed_in[100];
} Show;

void ordenar_cast(char elenco[10][100], int tamanho);

// Lista de todos os shows do CSV
Show catalogo[MAX_SHOWS];
int total = 0;

// Lista de shows lidos por ID
Show input[MAX_IDS];
int total_ids = 0;

void leitura(char *linha, Show *s) {
    char *campos[11];
    int idx = 0, i = 0, dentro_aspas = 0;
    char campo[500];
    int campo_index = 0;

    while (linha[i] != '\0') {
        if (linha[i] == '"') {
            dentro_aspas = !dentro_aspas;
        } else if (linha[i] == ',' && !dentro_aspas) {
            campo[campo_index] = '\0';
            campos[idx++] = strdup(campo);
            campo_index = 0;
        } else {
            campo[campo_index++] = linha[i];
        }
        i++;
    }
    campo[campo_index] = '\0';
    campos[idx++] = strdup(campo);

    strcpy(s->show_id, campos[0]);
    strcpy(s->type, campos[1]);
    strcpy(s->title, campos[2]);
    strcpy(s->director, campos[3]);

    s->cast_size = 0;
    if (strlen(campos[4]) > 0) {
        char *ator = strtok(campos[4], ",");
        while (ator != NULL && s->cast_size < 10) {
            while (isspace((unsigned char)*ator)) ator++;
            char *end = ator + strlen(ator) - 1;
            while (end > ator && isspace((unsigned char)*end)) end--;
            *(end + 1) = '\0';
            strcpy(s->cast[s->cast_size++], ator);
            ator = strtok(NULL, ",");
        }
    }

    strcpy(s->country, campos[5]);
    strcpy(s->date_added, strlen(campos[6]) > 0 ? campos[6] : "March 1, 1900");
    s->release_year = strlen(campos[7]) > 0 ? atoi(campos[7]) : 0;
    strcpy(s->rating, campos[8]);
    strcpy(s->duration, campos[9]);
    strcpy(s->listed_in, campos[10]);

    if (strlen(s->director)==0) strcpy(s->director,"NaN");
    if (strlen(s->country)==0) strcpy(s->country,"NaN");
    if (strlen(s->rating)==0) strcpy(s->rating,"NaN");
    if (strlen(s->duration)==0) strcpy(s->duration,"NaN");
    if (strlen(s->listed_in)==0) strcpy(s->listed_in,"NaN");
    if (s->cast_size == 0) {
        strcpy(s->cast[0], "NaN");
        s->cast_size = 1;
    }

    ordenar_cast(s->cast, s->cast_size);

    for (int k = 0; k < idx; k++) free(campos[k]);
}

void imprimir_show(Show s) {
    printf("=> %s ## %s ## %s ## %s ## [", s.show_id, s.title, s.type, s.director);
    for (int i = 0; i < s.cast_size; i++) {
        printf("%s", s.cast[i]);
        if (i < s.cast_size - 1) printf(", ");
    }
    printf("] ## %s ## %s ## %d ## %s ## %s ## [%s] ##\n",
           s.country, s.date_added, s.release_year,
           s.rating, s.duration, s.listed_in);
}

int buscar_show_por_id(char *id) {
    for (int i = 0; i < total; i++) {
        if (strcmp(catalogo[i].show_id, id) == 0) return i;
    }
    return -1;
}

void ordenar_cast(char elenco[10][100], int tamanho) {
    for (int i = 0; i < tamanho - 1; i++)
        for (int j = i + 1; j < tamanho; j++)
            if (strcmp(elenco[i], elenco[j]) > 0) {
                char temp[100];
                strcpy(temp, elenco[i]);
                strcpy(elenco[i], elenco[j]);
                strcpy(elenco[j], temp);
            }
}

//Lista duplamente encadeada

typedef struct CelulaDupla {
    Show elemento;
    struct CelulaDupla* ant;
    struct CelulaDupla* prox;
} CelulaDupla;

typedef struct {
    CelulaDupla* inicio;
    CelulaDupla* fim;
    int tamanho;
} ListaDupla;

void inicializarListaDupla(ListaDupla* l) {
    l->inicio = l->fim = NULL;
    l->tamanho = 0;
}

void inserirFimDupla(ListaDupla *lista, Show *s) {
    CelulaDupla *nova = malloc(sizeof(CelulaDupla));
    nova->elemento = *s;
    nova->prox = NULL;
    nova->ant = lista->fim;
    if (!lista->inicio) lista->inicio = nova;
    else lista->fim->prox = nova;
    lista->fim = nova;
    lista->tamanho++;
}

void imprimirListaDupla(ListaDupla *lista) {
    for (CelulaDupla *at = lista->inicio; at; at = at->prox) {
        imprimir_show(at->elemento);
    }
}

//Quicksort com date

int converterMes(char* mes) {
    char* meses[] = {"January","February","March","April","May","June","July","August","September","October","November","December"};
    for(int i=0;i<12;i++)
        if(strncmp(mes, meses[i], strlen(meses[i]))==0) return i+1;
    return 0;
}

int converterData(const char* data) {
    char mes[20];
    int dia, ano;
    if (sscanf(data, "%s %d, %d", mes, &dia, &ano)==3) {
        int m = converterMes(mes);
        return ano*10000 + m*100 + dia;
    }
    return 0;
}

void swap(Show *a, Show *b) {
    Show temp = *a;
    *a = *b;
    *b = temp;
}

void quicksort(int esq, int dir) {
    if (esq >= dir) return;
    Show pivo = input[(esq + dir) / 2];
    int pivoData = converterData(pivo.date_added);
    int i = esq, j = dir;

    while (i <= j) {
        while (converterData(input[i].date_added) < pivoData ||
              (converterData(input[i].date_added) == pivoData &&
               strcmp(input[i].title, pivo.title) < 0)) i++;
        while (converterData(input[j].date_added) > pivoData ||
              (converterData(input[j].date_added) == pivoData &&
               strcmp(input[j].title, pivo.title) > 0)) j--;
        if (i <= j) { swap(&input[i], &input[j]); i++; j--; }
    }

    if (esq < j) quicksort(esq, j);
    if (i < dir) quicksort(i, dir);
}

int main() {
    FILE *fp = fopen("/tmp/disneyplus.csv", "r");
    if (!fp) { perror("Erro ao abrir o arquivo"); return 1; }

    char linha[MAX_LINE];
    fgets(linha, MAX_LINE, fp); // pula cabeçalho
    while (fgets(linha, MAX_LINE, fp) && total < MAX_SHOWS) {
        linha[strcspn(linha, "\n")] = 0;
        if (strcmp(linha, "FIM")==0) break;
        leitura(linha, &catalogo[total++]);
    }
    fclose(fp);

    ListaDupla lista;
    inicializarListaDupla(&lista);

    while (total_ids < MAX_IDS && fgets(linha, MAX_LINE, stdin)) {
        linha[strcspn(linha, "\n")] = 0;
        if (strcmp(linha, "FIM")==0) break;
        int idx = buscar_show_por_id(linha);
        if (idx != -1) {
            input[total_ids++] = catalogo[idx];
        }
    }

    if (total_ids > 0) {
        quicksort(0, total_ids - 1);
        for (int i = 0; i < total_ids; i++) {
            inserirFimDupla(&lista, &input[i]);
        }
    }

    imprimirListaDupla(&lista);
    return 0;
}