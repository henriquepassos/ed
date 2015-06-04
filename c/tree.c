#include <stdio.h>
#include <stdlib.h>
#include <time.h>

#define RAND rand() / 10000000

struct tree {
	int key;
	struct tree *esq;
	struct tree *dir;
};

int max(int x, int y) {
	return x > y ? x : y;
}

struct tree* Tree_create(int key) {
	struct tree *node = (struct tree*) malloc(sizeof(struct tree));
	node->key = key;
	node->esq = NULL;
	node->dir = NULL;
	return node;
}

void Tree_destroy(struct tree *node) {
	if (!node) {
		return;
	}
	Tree_destroy(node->esq);
	Tree_destroy(node->dir);
	free(node);
}

int Tree_add(struct tree *node, int key) {
	if (!node) {
		return 0;
	}
	if (node->key == key) {
		return 0;
	}
	struct tree *previous;
	struct tree *current = node;
	while (current) {
		previous = current;
		if (current->key > key) {
			current = current->esq;
		} else if (current->key < key) {
			current = current->dir;
		} else {
			return 0;
		}
	}
	current = Tree_create(key);
	if (previous->key > key) {
		previous->esq = current;
	} else {
		previous->dir = current;
	}
	return 1;
}

struct tree* Tree_find(struct tree *node, int key) {
	if (!node) {
		return NULL;
	}
	struct tree *current = node;
	while (current) {
		if (current->key > key) {
			current = current->esq;
		} else if (current->key < key) {
			current = current->dir;
		} else {
			return current;
		}
	}
	return NULL;
}

int Tree_height(struct tree *node) {
	if (!node) {
		return 0;
	}
	return 1 + max(Tree_height(node->esq), Tree_height(node->dir));
}

int Tree_node_count(struct tree *node) {
	if (!node) {
		return 0;
	}
	return 1 + Tree_node_count(node->esq) + Tree_node_count(node->dir);
}

void Tree_pre_order(struct tree *node) {
	if (!node) {
		return;
	}
	printf("%i ", node->key);
	Tree_pre_order(node->esq);
	Tree_pre_order(node->dir);
}

void Tree_in_order(struct tree *node) {
	if (!node) {
		return;
	}
	Tree_in_order(node->esq);
	printf("%i ", node->key);
	Tree_in_order(node->dir);
}

void Tree_post_order(struct tree *node) {
	if (!node) {
		return;
	}
	Tree_post_order(node->esq);
	Tree_post_order(node->dir);
	printf("%i ", node->key);
}

int main(int argc, char** argv) {
	srand(time(NULL));
	struct tree *node = Tree_create(RAND);
	int i;
	for (i = 0; i < 100; i++) {
		printf("%i ", Tree_add(node, RAND));
	}printf("\n");
	printf("%p\n", Tree_find(node, RAND));
	printf("%i\n", Tree_height(node));
	printf("%i\n", Tree_node_count(node));
	Tree_pre_order(node);printf("\n");
	Tree_in_order(node);printf("\n");
	Tree_post_order(node);printf("\n");
	Tree_destroy(node);
	return EXIT_SUCCESS;
}
