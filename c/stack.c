#include <stdio.h>
#include <stdlib.h>

#define STACK_EMPTY -1

struct Stack {
	int top;
	int max;
	int *array;
};

struct Stack * Stack_create(int max) {
	struct Stack *s = malloc(sizeof(struct Stack));
	s->top = STACK_EMPTY;
	s->max = max;
	s->array = malloc(s->max * sizeof(int));
}

void Stack_destroy(struct Stack * s) {
	free(s->array);
	free(s);
}

int Stack_empty(struct Stack * s) {
	return s->top == STACK_EMPTY;
}

void Stack_push(struct Stack * s, int x) {
	s->top++;
	s->array[s->top] = x;
}

int Stack_pop(struct Stack * s) {
	if (Stack_empty(s)) {
		printf("underflow\n");
		return;
	}
	return s->array[s->top--];
}

void Stack_plot(struct Stack * s) {
	printf("s->top = %d\ns->max = %d\n", s->top, s->max);
	int i;
	for (i = 0; i < s->max; i++) {
		printf("s->array[%d] = %d, top = %d\n", i, s->array[i], i == s->top);
	}
	printf("stack-empty = %d\n", Stack_empty(s));
}

int main(int argc, char** argv) {

	struct Stack *s = Stack_create(5);

	Stack_plot(s);

	Stack_push(s, 23);
	Stack_push(s, 11);
	Stack_push(s, 1990);

	printf("stack-pop = %d\n", Stack_pop(s));

	Stack_plot(s);

	Stack_destroy(s);

	return (EXIT_SUCCESS);
}
