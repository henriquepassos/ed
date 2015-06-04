#include <stdio.h>
#include <malloc.h>
#define true 1
#define false 0

typedef int bool;

typedef struct NO{
	int info;
	int h;
	struct NO *dir, *esq;
} no;

int max(int a, int b)
{
	if(a > b)
		return a;
	return b;
}

int alteraH(no * d, no * e)
{
	int id = 0;
	int ie = 0;
	if (e != NULL)
		ie = e->h;
	if (d != NULL)
		id = d->h;
	return max(id, ie);
}

void rotEsq(no ** p){
	no * q;
	no * temp;
	q = (*p)->dir;
	temp = q->esq;
	q->esq = (*p);
	(*p)->dir = temp;
	(*p) = q;

	(*p)->esq->h = alteraH((*p)->esq->dir, (*p)->esq->esq) + 1;
	(*p)->h = alteraH((*p)->dir, (*p)->esq) + 1;
}

void rotDir(no ** p){
	no * q;
	no * temp;
	q = (*p)->esq;
	temp = q->dir;
	q->dir = (*p);
	(*p)->esq = temp;
	(*p) = q;

	(*p)->dir->h = alteraH((*p)->dir->dir, (*p)->dir->esq) + 1;
	(*p)->h = alteraH((*p)->dir, (*p)->esq) + 1;
}

int h(no * p)
{
	if (p == NULL)
		return 0;
	return p->h;
}

void tri_node_restructure(no ** raiz)
{
	int dif = h((*raiz)->esq) - h((*raiz)->dir);
	if(dif > 1)
	{
		no * x = *raiz;
		no * y = x->esq;
		no * z;
		if(h(y->esq) >= h(y->dir))
			z = y->esq;
		else
			z = y->dir;

		if(z->info < y->info)
			rotDir(raiz);
		else
		{
			rotEsq(&(*raiz)->esq);
			rotDir(raiz);
		}
	}
	else if(dif < -1)
	{
		no * x = *raiz;
		no * y = x->esq;
		no * z;
		if(h(y->esq) >= h(y->dir))
			z = y->esq;
		else
			z = y->dir;

		if(z->info > y->info)
			rotEsq(raiz);
		else
		{
			rotDir(&(*raiz)->dir);
			rotEsq(raiz);
		}
	}
}


bool excluir(no ** raiz, int value)
{
	if((*raiz) == NULL)
		return false;

	if((*raiz)->info == value)
	{
		if(((*raiz)->esq == NULL) && ((*raiz)->dir == NULL))
		{
			free(*raiz);
			*raiz = NULL;
			return true;
		}
		else if(((*raiz)->esq != NULL) && ((*raiz)->dir != NULL))
		{
			no * p = (*raiz)->dir;
			while(p != NULL)
			{
				if(p->esq != NULL)
					p = p->esq;
				else
					break;
			}
			(*raiz)->info = p->info;
			excluir(&(*raiz)->dir, p->info);
			(*raiz)->h = alteraH((*raiz)->esq, (*raiz)->dir);
		}
		else if((*raiz)->dir != NULL)
		{
			*raiz = (*raiz)->dir;
			return true;
		}
		else
		{
			*raiz = (*raiz)->esq;
			return true;
		}
	}
	else if((*raiz)->info > value)
	{
		if (excluir(&(*raiz)->esq, value))
		{
			(*raiz)->h = alteraH((*raiz)->esq, (*raiz)->dir) + 1;
		}
	}
	else if((*raiz)->info < value)
		if(excluir(&(*raiz)->dir, value))
		{
			(*raiz)->h = alteraH((*raiz)->esq, (*raiz)->dir) + 1;
		}

	tri_node_restructure(raiz);
}

bool inserir(no ** raiz, int valor)
{
	if(*raiz == NULL)
	{
		no * novo = (no*) malloc(sizeof(no));
		novo->info = valor;
		novo->h = 1;
		novo->dir = NULL;
		novo->esq = NULL;
		*raiz = novo;
		return true;
	}

	if((*raiz)->info == valor)
		return false;

	bool alterado = false;
	if((*raiz)->info > valor)
		if(inserir(&(*raiz)->esq, valor))
		{
			(*raiz)->h = alteraH((*raiz)->esq, (*raiz)->dir) + 1;
			int dif = h((*raiz)->esq) - h((*raiz)->dir);
			if(dif > 1)
			{
				if((*raiz)->esq->info > valor)
					rotDir(raiz);
				else
				{
					rotEsq(&(*raiz)->esq);
					rotDir(raiz);
				}
			}
			return true;
		}

	if((*raiz)->info < valor)
		if(inserir(&(*raiz)->dir, valor))
		{
			(*raiz)->h = alteraH((*raiz)->esq, (*raiz)->dir) + 1;
			int dif = h((*raiz)->esq) - h((*raiz)->dir);
			if(dif < -1)
			{
				if((*raiz)->dir->info < valor)
					rotEsq(raiz);
				else
				{
					rotDir(&(*raiz)->dir);
					rotEsq(raiz);
				}
			}
			return true;
		}

	return false;
}

no * nivel1()
{
	no * n1 = (no*) malloc(sizeof(no));
	no * n2 = (no*) malloc(sizeof(no));
	no * n3 = (no*) malloc(sizeof(no));

	n1->info = 0;
	n2->info = -1;
	n3->info = 1;

	n1->h = 2;
	n2->h = 1;
	n3->h = 1;

	n1->esq = n2;
	n1->dir = n3;

	n2->dir = n3->dir = NULL;
	n2->dir = n3->esq = NULL;
	return n1;
}

no * nivel2()
{
	no * n1 = (no*) malloc(sizeof(no));
	no * n2 = (no*) malloc(sizeof(no));
	no * n3 = (no*) malloc(sizeof(no));
	no * n4 = (no*) malloc(sizeof(no));
	no * n5 = (no*) malloc(sizeof(no));
	no * n6 = (no*) malloc(sizeof(no));
	no * n7 = (no*) malloc(sizeof(no));

	n1->info = 0;
	n2->info = -2;
	n3->info = 2;
	n4->info = -3;
	n5->info = -1;
	n6->info = 1;
	n7->info = 3;

	n1->h = 3;
	n2->h = 2;
	n3->h = 2;
	n4->h = 1;
	n5->h = 1;
	n6->h = 1;
	n7->h = 1;

	n1->esq = n2;
	n1->dir = n3;
	n2->esq = n4;
	n2->dir = n5;
	n3->esq = n6;
	n3->dir = n7;


	n4->dir = n5->dir = n6->dir = n7->dir = NULL;
	n4->dir = n5->esq = n6->dir = n7->dir = NULL;
	return n1;
}

void printArvore(no * p)
{
	if(p != NULL)
	{
		printf("\t%d - %d\n", p->info, p->h);
		printArvore(p->esq);
		printArvore(p->dir);
	}
}


int main()
{
	no * n1 = (no*) malloc(sizeof(no));
	n1->dir = NULL;
	n1->esq = NULL;
	n1->info = 0;

	excluir(&n1, 1);
	if(n1 != NULL)
		printf("caso 1 ok\n");
	printArvore(n1);

	excluir(&n1, 0);
	if(n1 == NULL)
		printf("caso 2 ok\n");
	printArvore(n1);

	n1 = nivel1();
	excluir(&n1, -1);
	if(n1->esq == NULL)
		printf("caso 3 ok\n");
	printArvore(n1);

	n1 = nivel1();
	excluir(&n1, 1);
	if(n1->dir == NULL)
		printf("caso 4 ok\n");
	printArvore(n1);

	n1 = nivel1();
	excluir(&n1, 0);
	if(n1 != NULL)
		printf("caso 5 ok\n");
	printArvore(n1);

	n1 = nivel2();
	excluir(&n1, -3);
	if(n1->esq->esq == NULL)
		printf("caso 6 ok\n");
	printArvore(n1);

	n1 = nivel2();
	excluir(&n1, -1);
	if(n1->esq->dir == NULL)
		printf("caso 7 ok\n");
	printArvore(n1);

	n1 = nivel2();
	excluir(&n1, -2);
	if(n1->esq->info != -2)
		printf("caso 8 ok\n");
	printArvore(n1);

	n1 = nivel2();
	excluir(&n1, 2);
	if(n1->dir->info != 2)
		printf("caso 9 ok\n");
	printArvore(n1);

	n1 = nivel2();
	excluir(&n1, 1);
	excluir(&n1, 2);
	excluir(&n1, 3);
	printf("caso 10 ok\n");
	printArvore(n1);

	/*n1 = nivel2();
	excluir(&n1, 1);
	excluir(&n1, 3);
	excluir(&n1,-3);
	excluir(&n1, 2);
	printf("caso 11 ok\n");
	printArvore(n1);

	n1 = NULL;
	inserir(&n1,0);
	inserir(&n1,1);
	inserir(&n1,2);
	printf("caso 12 ok\n");
	printArvore(n1);*/

	return 0;
}
