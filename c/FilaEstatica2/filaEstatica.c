/******************************************************************************
//     filaEstatica.c
// Este programa gerencia filas implementadas em arranjos
   (implementacao estatica).
// As filas gerenciadas podem ter no maximo MAX elementos.
// Nao usaremos sentinela nesta estrutura.
******************************************************************************/
#include <stdio.h>
#define ERRO -1
#define true 1
#define false 0
#define MAX 50

typedef int bool;
typedef int TIPOCHAVE;

typedef struct {
  TIPOCHAVE chave;
} REGISTRO;

typedef struct {
  int inicio;
  int numElementos;
  REGISTRO A[MAX];
} FILA;

/* Inicializacao da fila (a fila jah esta criada e eh apontada
pelo endereco em l) */
void inicializarFila(FILA *f){
  f->inicio=0;
  f->numElementos=0;
} /* inicializarFila */


/* Retornar o tamanho da fila (numero de elementos) */
int tamanhoFila(FILA *f) {
  return f->numElementos;
} /* tamanho */

/* Exibicao da fila sequencial */
void exibirFila(FILA *f){
   printf("Fila: \" ");
   int i = f->inicio;
   int temp;
   for (temp = 0; temp < f->numElementos; temp++){
      printf("%d ", f->A[i].chave); // soh lembrando TIPOCHAVE = int
      i = (i + 1) % MAX;
    }
   printf("\"\n");
} /* exibirLista */


/* Retornar o tamanho em bytes da fila. Neste caso, isto nao depende do numero
   de elementos que estao sendo usados.   */
int tamanhoEmBytesFila(FILA *f) {
  return sizeof(FILA);
} /* tamanhoEmBytes */

/* Busca Fila - retorna posicao do primeiro elemento da fila */
int buscaFila(FILA *f){
  return f->inicio;
} /* buscaFila */

/* Destruicao da fila */
void destruirFila(FILA *f) {
     inicializarFila(f);
} /* destruirFila */

/* inserirElementoFila - insere elemento no fim da fila   */
bool inserirElementoFila(REGISTRO reg, FILA *f){
     if (f->numElementos >= MAX) return false;
     f->A[(f->inicio + f->numElementos)%MAX] = reg;
     f->numElementos++;
     return true;
} /* inserirElementoFila */

/* excluirElementoFila - retorna e exclui 1o elemento da fila
retorna false se nao houver elemento a ser retirado */
bool excluirElementoFila(FILA *f, REGISTRO * reg){
   if (f->numElementos==0) return false;
   *reg = f->A[f->inicio];
   f->inicio = (f->inicio+1)%MAX;
   f->numElementos--;
   return true;
} /* excluirElementoFila */

/* retornarPrimeiroFila
retorna a posicao do primeiro elemento da fial e o valor de sua chave no
conteudo do endereco ch. Retorna -1 caso a lista esteja vazia */
int retornarPrimeiroFila(FILA *f, TIPOCHAVE * ch){
    if (f->numElementos==0)return -1;
    *ch = f->A[f->inicio].chave;
    return f->inicio;
}
