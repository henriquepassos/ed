/******************************************************************************
//     filaEmLLD.c
// Este programa gerencia filas implementadas em listas lineares ligadas
   (implementacao dinamica).
// As filas gerenciadas podem ter um numero arbitrario de elementos.
// Nao usaremos sentinela nesta estrutura.
******************************************************************************/
//#include "listaLigadaD.c"

typedef LISTA FILA;

/* Inicializacao da fila (a fila jah esta criada e eh apontada
pelo endereco em l) */
void inicializarFila(FILA *l){
     inicializarLista(l);
} /* inicializarFila */

/* Exibicao da fila sequencial */
void exibirFila(FILA *l){
  exibirLista(l);
} /* exibirLista */

/* Retornar o tamanho da fila (numero de elementos) */
int tamanhoFila(FILA *l) {
  return tamanho(l);
} /* tamanho */

/* Retornar o tamanho em bytes da fila. Neste caso, isto depende do numero
   de elementos que estao sendo usados.   */
int tamanhoEmBytesFila(FILA *l) {
  return tamanhoEmBytes(l);
} /* tamanhoEmBytes */

/* Busca Fila - retorna primeira chave da fila */
PONT buscaFila(FILA *l){
  return l->inicio;
} /* buscaFila */

/* Destruicao da fila
   libera a memoria de todos os elementos da fila*/
void destruirFila(FILA *l) {
     destruirLista(l);
} /* destruirFila */

/* inserirElementoFila - insere elemento no "topo" da fila   */
bool inserirElementoFila(REGISTRO reg, FILA *l){
     TIPOCHAVE temp;
     PONT ultimo = retornarUltimo(l, &temp);
     PONT novo = (PONT) malloc(sizeof(REGISTRO));
     *novo = reg;
     novo->prox = NULL;
     if (ultimo != NULL) ultimo->prox = novo;
     else l->inicio = novo;
     return true;
} /* inserirElementoFila */

void entrarFila(void* pont, FILA * f){
     REGISTRO temp;
     temp.chave = pont;
     inserirElementoFila(temp, f);
}

/* excluirElementoFila - retorna e exclui 1o elemento da fila
retorna false se nao houver elemento a ser retirado */
bool excluirElementoFila(FILA *l, REGISTRO * reg){
   if (l->inicio == NULL) return false;
   *reg = *(l->inicio);
   PONT tempInicio = l->inicio;
   l->inicio = l->inicio->prox;
   free(tempInicio);
   return true;
} /* excluirElementoFila */

void* sairFila(FILA *f){
  REGISTRO temp;
  temp.chave = NULL;
  excluirElementoFila(f,&temp);
  return temp.chave;
}

PONT retornarPrimeiroFila(FILA *l, TIPOCHAVE * ch){
     return retornarPrimeiro(l,ch);
}
