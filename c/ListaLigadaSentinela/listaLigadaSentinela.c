/******************************************************************************
     listaLigadaSentinela.c
  Este programa gerencia listas lineares ligadas (implementacao dinamica) com
no sentinela no final da lista..
  As listas gerenciadas podem ter um numero arbitrario de elementos.
  O no sentinela acelera o algoritmo de busta sequencial por reduzir o numero
de comparacoes.
******************************************************************************/
#include <stdio.h>
#define true 1
#define false 0

typedef int bool;
typedef int TIPOCHAVE;

typedef struct tempRegistro{
  TIPOCHAVE chave;
  struct tempRegistro *prox;
// outros campos...
} REGISTRO;

typedef REGISTRO* PONT;

typedef struct {
  PONT inicio;
  PONT sentinela;
} LISTA;

/* Inicializacao da lista ligada (a lista jah esta criada e eh apontada
pelo endereco em l) */
void inicializarLista(LISTA *l){
  l->inicio = (PONT) malloc(sizeof(REGISTRO));
  l->sentinela = l->inicio;
} /* inicializarLista */

/* Exibicao da lista sequencial */
void exibirLista(LISTA *l){
  PONT end = l->inicio;
  printf("Lista: \" ");
  while (end != l->sentinela){
    printf("%d ", end->chave); // soh lembrando TIPOCHAVE = int
    end = end->prox;
  }
  printf("\"\n");
} /* exibirLista */

/* Retornar o tamanho da lista (numero de elementos) */
int tamanho(LISTA *l) {
  PONT end = l->inicio;
  int tam = 0;
  while (end != l->sentinela){
    tam++;
    end = end->prox;
  }
  return tam;
} /* tamanho */

/* Retornar o tamanho em bytes da lista. Neste caso, isto depende do numero
   de elementos que estao sendo usados.   */
int tamanhoEmBytes(LISTA *l) {
  return((tamanho(l)+1)*sizeof(REGISTRO))+sizeof(LISTA); // sizeof(LISTA) = sizeof(PONT) pois a "LISTA" eh um ponteiro para o 1o elemento
} /* tamanhoEmBytes */

/* Busca sequencial (lista ordenada ou nao) */
PONT buscaSeq(TIPOCHAVE ch, LISTA *l){
  PONT pos = l->inicio;
  l->sentinela->chave = ch;
  while (pos->chave != ch) pos = pos->prox;
  if (pos != l->sentinela) return pos;
  return NULL;
} /* buscaSeq */

/* Busca sequencial - funcao de exclusao (retorna no endereco *ant o indice do
   elemento anterior ao elemento que esta sendo buscado [ant recebe o elemento
   anterior independente do elemento buscado ser ou nao encontrado]) */
PONT buscaSeqExc(TIPOCHAVE ch, LISTA *l, PONT *ant){
  *ant = NULL;
  PONT pos = l->inicio;
  while ((pos != l->sentinela) && (pos->chave<ch)){
    *ant = pos;
    pos = pos->prox;
  }
  if ((pos != l->sentinela) && (pos->chave == ch)) return pos;
  return NULL;
} /* buscaSeqExc */

/* Exclusao do elemento de chave indicada */
bool excluirElemLista(TIPOCHAVE ch, LISTA *l){
  PONT ant, i;
  i = buscaSeqExc(ch, l, &ant);
  if (i == NULL) return false;
  if (ant == NULL) l->inicio = i->prox;
  else ant->prox = i->prox;
  free(i);
  return true;
} /* excluirElemListaEnc */

/* Destruicao da lista sequencial
   libera a memoria de todos os elementos da lista*/
void destruirLista(LISTA *l) {
  PONT end = l->inicio;
  while (end != l->sentinela){
    PONT apagar = end;
    end = end->prox;
    free(apagar);
  }
  l->inicio = l->sentinela;
} /* destruirLista */


/* Insercao em lista ordenada sem duplicacao */
bool inserirElemListaOrd(REGISTRO reg, LISTA *l) {
  TIPOCHAVE ch = reg.chave;
  PONT ant, i;
  i = buscaSeqExc(ch, l, &ant);
  if (i != NULL)  return false;
  i = (PONT) malloc(sizeof(REGISTRO));
  *i = reg;
  if (ant == NULL) { // o novo elemento serah o 1o da lista
    i->prox = l->inicio;
    l->inicio = i;
  } else {  // insercao apos um elemento ja existente
    i->prox = ant->prox;
    ant->prox = i;
  }
  return true;
} /* inserirElemListaOrd */

/* retornarPrimeiro - retorna o endereco do primeiro elemento da lista e (caso
   a lista nao esteja vazia) retorna a chave desse elemento na memoria
   apontada pelo ponteiro ch */
PONT retornarPrimeiro(LISTA *l, TIPOCHAVE *ch){
  if (l->inicio != l->sentinela) *ch = l->inicio->chave;
  return l->inicio;
} /* retornarPrimeiro */

/* retornarUltimo - retorna o endereco do ultimo elemento da lista e (caso
   a lista nao esteja vazia) retorna a chave desse elemento na memoria
   apontada pelo ponteiro ch */
PONT retornarUltimo(LISTA *l, TIPOCHAVE *ch){
  PONT ultimo = l->inicio;
  if (l->inicio == l->sentinela) return NULL;
  while (ultimo->prox != l->sentinela) ultimo = ultimo->prox;
  *ch = ultimo->chave;
  return ultimo;
} /* retornarUltimo */
