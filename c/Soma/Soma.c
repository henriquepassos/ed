// Soma.c
#include <stdio.h>

/* Este programa chama o metodo soma para somar dois inteiros. O metodo recebe 3
   parametros: os dois inteiros que serao somados e o endereco onde a resposta
   sera colocada */

void soma (int a, int b, int *c){
  *c = a + b;
}

int main(){
  int x, y, resultado;
  x = 10;
  y = 13;
  printf("resultado: %d (variavel nao calculada e nao inicializada)\n",resultado);
  printf("endereco : %d (endereco da variavel resultado)\n",&resultado);
  soma(x,y,&resultado); // aqui passamos o endereco do resultado
  printf("resultado: %d\n",resultado);
  return 0;
}
