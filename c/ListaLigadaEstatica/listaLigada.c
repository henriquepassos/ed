/******************************************************************************
//     listaLigadaEstatica.c
// Este programa gerencia listas lineares ligadas (implementacao estatica).
// As listas gerenciadas podem ter MAX elementos (posicoes de 0 a MAX-1 no
//   arranjo A). Nao usaremos sentinela nesta estrutura.
******************************************************************************/
#include <stdio.h>
#define MAX 50
#define ERRO -1
#define NUL -1
#define true 1
#define false 0


typedef int bool;

typedef int TIPOCHAVE;

typedef struct{
  TIPOCHAVE chave;
  int prox;
// outros campos...
} REGISTRO;

typedef struct {
  REGISTRO A[MAX];
  int inicio;
  int dispo;
} LISTA;

/* Inicializacao da lista sequencial (a lista jah esta criada e eh apontada
pelo endereco em l) */
void inicializarLista(LISTA *l){
  int i;
  l->inicio=NUL;
  l->dispo=0;
  for (i=0; i<MAX-1; i++)
    l->A[i].prox = i + 1;
  l->A[MAX-1].prox = NUL;
} /* inicializarLista */

/* Exibicao da lista sequencial */
void exibirLista(LISTA *l){
  int i = l->inicio;
  printf("Lista: \" ");
  while (i != NUL){
    printf("%d ", l->A[i].chave); // soh lembrando TIPOCHAVE = int
    i = l->A[i].prox;
  }
  printf("\"\n");
} /* exibirLista */

/* Retornar o tamanho da lista (numero de elementos "validos") */
int tamanho(LISTA *l) {
  int i = l->inicio;
  int tam = 0;
  while (i != NUL){
    tam++;
    i = l->A[i].prox;
  }
  return tam;
} /* tamanho */

/* Retornar o tamanho em bytes da lista. Neste caso, isto nao depende do numero
   de elementos que estao sendo usados, pois a alocacao de memoria eh estatica.
   A priori, nao precisariamos do ponteiro para a lista, vamos utiliza-lo apenas
   porque teremos as mesmas funcoes para listas ligadas implementacao nao estatica.
*/
int tamanhoEmBytes(LISTA *l) {
  return(sizeof(LISTA));
} /* tamanhoEmBytes */

/* Busca sequencial (lista ordenada ou nao) */
int buscaSeq(TIPOCHAVE ch, LISTA *l){
  int i = l->inicio;
  while (i != NUL){
    if (l->A[i].chave == ch) return i;
    i = l->A[i].prox;
  }
  return NUL;
} /* buscaSeq */

/* Busca sequencial - funcao de exclusao (retorna no endereco *ant o indice do
   elemento anterior ao elemento que esta sendo buscado [ant recebe o elemento
   anterior independente do elemento buscado ser ou nao encontrado]) */
int buscaSeqExc(TIPOCHAVE ch, LISTA *l, int *ant){
  *ant = NUL;
  int i = l->inicio;
  while ((i != NUL) && (l->A[i].chave<ch)){
    *ant = i;
    i = l->A[i].prox;
  }
  if ((i != NUL) && (l->A[i].chave == ch)) return i;
  return NUL;
} /* buscaSeqExc */

/* Obter no disponivel e marca-lo como nao disponivel - esta operacao sera usada
  junto com insercoes, por exemplo */
int obterNo(LISTA *l){
  int resultado = l->dispo;
  if (l->dispo != NUL) l->dispo = l->A[l->dispo].prox;
  return resultado;
} /* obterNo */

/* Devolver no da posicao j para a listo de nos disponiveis - coloca-se o no j
   como primeiro na lista de disponiveis */
void devolverNo(LISTA *l, int j){
     l->A[j].prox = l->dispo;
     l->dispo = j;
} /* devolverNo */

/* Exclusao do elemento de chave indicada */
bool excluirElemLista(TIPOCHAVE ch, LISTA *l){
  int ant, i;
  i = buscaSeqExc(ch, l, &ant);
  if (i == NUL) return false;
  if (ant == NUL) l->inicio = l->A[i].prox;
  else l->A[ant].prox = l->A[i].prox;
  devolverNo(l,i);
  return true;
} /* excluirElemListaEnc */



/* Destruicao da lista sequencial */
void destruirLista(LISTA *l) {
  inicializarLista(l);
} /* destruirLista */


/* Insercao em lista ordenada sem duplicacao */
bool inserirElemListaOrd(REGISTRO reg, LISTA *l) {
  if (l->dispo == NUL) return false; // se lista cheia, nao e possivel inserir
  TIPOCHAVE ch = reg.chave;
  int ant, i;
  i = buscaSeqExc(ch, l, &ant);
  if (i != NUL)  return false;
  i = obterNo(l);
  l->A[i] = reg;
  if (ant == NUL) { // o novo elemento sera o 1o da lista (a lista poderia estar vazia ou nao)
    l->A[i].prox = l->inicio;
    l->inicio = i;
  } else {  // insercao apos um elemento ja existente
    l->A[i].prox = l->A[ant].prox;
    l->A[ant].prox = i;
  }
  return true;
} /* inserirElemListaOrd */
