/* Author: Justin T.
 * Course: CS 406
 * Date: 1/23/2020
 * Assignment: Homework One
 * */
#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include <omp.h>

void fillMatrix(int *matrix, int m, int n);
void printMatrix(int *matrix, int m, int n);
void writeMatrixToFile(int *matrix, int m, int n, FILE *fp);
void productOfMatrices(int *matrix1, int *matrix2, int *result, int m, int n, int p);
void productOfMatricesOMP(int *matrix1, int *matrix2, int *result, int m, int n, int p);
long getElapsedTime(struct timeval startTime);

int main(int argc, char* argv[]) {
	// Too few or too many arguments so exit program
	if (argc <= 1 || argc > 4) {
		return 1;
	}

	// Open output file and prepare pointer for writing results later
	FILE *fp;
	fp = fopen("output.txt", "w+");

	// Get time since epoch and seed pseudo-random number generator with it for dynamic values
	time_t t;
	time(&t);
	srand(t);

	int *matrix1;
	int *matrix2;
	int *product;
	int m = 0;
	int n = 0;
	int p = 0;
	struct timeval startTime;
	long elapsedTime = 0;

	// MxM matrix
	if (argc == 2) {
		m = atoi(argv[1]);
		matrix1 = (int *)malloc(m*m*sizeof(int));
		matrix2 = (int *)malloc(m*m*sizeof(int));
		product = (int *)malloc(m*m*sizeof(int));

		// Filling matrices with random values
		fillMatrix(matrix1, m, m);
		fillMatrix(matrix2, m, m);

		// Writing both matrices to the output file
		writeMatrixToFile(matrix1, m, m, fp);
		writeMatrixToFile(matrix2, m, m, fp);

		// Calculating product of matrices; serial
		gettimeofday(&startTime, 0);
		productOfMatrices(matrix1, matrix2, product, m, m, m);
		elapsedTime = getElapsedTime(startTime);
		printf("Elapsed time for serial implementation in milliseconds: %ld \n", elapsedTime);
		printf("Elapsed time for serial implementation in seconds: %f \n", elapsedTime*0.001);

		// Calculating product of matrices; parallel
		gettimeofday(&startTime, 0);
		productOfMatricesOMP(matrix1, matrix2, product, m, m, m);
		elapsedTime = getElapsedTime(startTime);
		printf("Elapsed time for parallel implementation in milliseconds: %ld \n", elapsedTime);
		printf("Elapsed time for parallel implementation in seconds: %f \n", elapsedTime*0.001);

		writeMatrixToFile(product, m, m, fp);
	}

	// MxN matrix and NxM matrix
	if (argc == 3) {
		m = atoi(argv[1]);
		n = atoi(argv[2]);
		matrix1 = (int *)malloc(m*n*sizeof(int));
		matrix2 = (int *)malloc(n*m*sizeof(int));
		product = (int *)malloc(m*m*sizeof(int));

		// Filling matrices with random values
		fillMatrix(matrix1, m, n);
		fillMatrix(matrix2, n, m);

		// Writing both matrices to the output file
		writeMatrixToFile(matrix1, m, n, fp);
		writeMatrixToFile(matrix2, n, m, fp);

		// Calculating product of matrices; serial
		gettimeofday(&startTime, 0);

		// Zero-fill result matrix
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < m; j++) {
				*(product + (i*m) + j) = 0;
			}
		}

		// Multiply matrices and store product
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < m; j++) {
				for (int k = 0; k < n; k++) {
					*(product + (i*m) + j) += (*(matrix1 + (i*n) + k)) * (*(matrix2 + (k*m) + j));
				}
			}
		}

		elapsedTime = getElapsedTime(startTime);
		printf("Elapsed time for serial implementation in milliseconds: %ld \n", elapsedTime);
		printf("Elapsed time for serial implementation in seconds: %f \n", elapsedTime*0.001);

		// Calculating product of matrices; parallel
		gettimeofday(&startTime, 0);

		// Zero-fill result matrix
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < m; j++) {
				*(product + (i*m) + j) = 0;
			}
		}

		// Multiply matrices and store product; parallel
		#pragma omp parallel
		#pragma omp for collapse(3)
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < m; j++) {
				for (int k = 0; k < n; k++) {
					*(product + (i*m) + j) += (*(matrix1 + (i*n) + k)) * (*(matrix2 + (k*m) + j));
				}
			}
		}

		elapsedTime = getElapsedTime(startTime);
		printf("Elapsed time for parallel implementation in milliseconds: %ld \n", elapsedTime);
		printf("Elapsed time for parallel implementation in seconds: %f \n", elapsedTime*0.001);

		writeMatrixToFile(product, m, m, fp);
	}

	// MxP & PxN matrices
	if (argc == 4) {
		m = atoi(argv[1]);
		n = atoi(argv[2]);
		p = atoi(argv[3]);
		matrix1 = (int *)malloc(m*p*sizeof(int));
		matrix2 = (int *)malloc(p*n*sizeof(int));
		product = (int *)malloc(m*n*sizeof(int));

		// Filling matrices with random values
		fillMatrix(matrix1, m, p);
		fillMatrix(matrix2, p, n);

		// Writing both matrices to the output file
		writeMatrixToFile(matrix1, m, p, fp);
		writeMatrixToFile(matrix2, p, n, fp);

		// Calculating product of matrices; serial
		gettimeofday(&startTime, 0);
		productOfMatrices(matrix1, matrix2, product, m, n, p);
		elapsedTime = getElapsedTime(startTime);
		printf("Elapsed time for serial implementation in milliseconds: %ld \n", elapsedTime);
		printf("Elapsed time for serial implementation in seconds: %f \n", elapsedTime*0.001);

		// Calculating product of matrices; parallel
		gettimeofday(&startTime, 0);
		productOfMatricesOMP(matrix1, matrix2, product, m, n, p);
		elapsedTime = getElapsedTime(startTime);
		printf("Elapsed time for parallel implementation in milliseconds: %ld \n", elapsedTime);
		printf("Elapsed time for parallel implementation in seconds: %f \n", elapsedTime*0.001);

		writeMatrixToFile(product, m, n, fp);
	}

	// Free the memory allocated on the heap & close output file
	free(matrix1);
	free(matrix2);
	free(product);
	fclose(fp);

	return 0;
}

/* fillMatrix takes a pointer to a matrix and fills it with random digits between 0-9
 * */
void fillMatrix(int *matrix, int m, int n) {
	for (int i = 0; i < m; i++) {
		for (int j = 0; j < n; j++) {
			*(matrix + (i*n) + j) = (rand() % 10);
		}
	}
}

/* printMatrix takes a pointer to a matrix and prints it to stdout
 * */
void printMatrix(int *matrix, int m, int n) {
	for (int i = 0; i < m; i++) {
		for (int j = 0; j < n; j++) {
			printf("%d \t", *(matrix + (i*n) + j));
		}

		printf("\n");
	}
}

/* writeMatrixToFile takes a pointer to a matrix and a file and writes the matrix to the file
 * */
void writeMatrixToFile(int *matrix, int m, int n, FILE *fp) {
	for (int i = 0; i < m; i++) {
		for (int j = 0; j < n; j++) {
			fprintf(fp, "%d \t", *(matrix + (i*n) + j));
		}

		fprintf(fp, "\n");
	}

	fprintf(fp, "--------------------------------------------------------- \n");
}

/* productOfMatrices takes pointers to three matrices and stores the product of the first two 
 * matrices in the third matrix
 * */
void productOfMatrices(int *matrix1, int *matrix2, int *result, int m, int n, int p) {
	// Zero-fill result matrix
	for (int i = 0; i < m; i++) {
		for (int j = 0; j < n; j++) {
			*(result + (i*n) + j) = 0;
		}
	}

	// Multiply matrices
	for (int i = 0; i < m; i++) {
		for (int j = 0; j < n; j++) {
			for (int k = 0; k < p; k++) {
				*(result + (i*n) + j) += (*(matrix1 + (i*p) + k)) * (*(matrix2 + (k*n) + j));
			}
		}
	}
}

/* productOfMatricesOMP takes pointers to three matrices and stores the product of the first two 
 * matrices in the third matrix. The matrix multiplication is done in parallel utilizing the
 * max number of CPU cores available on the system.
 * */
void productOfMatricesOMP(int *matrix1, int *matrix2, int *result, int m, int n, int p) {
	//Zero-fill result matrix
	for (int i = 0; i < m; i++) {
		for (int j = 0; j < n; j++) {
			*(result + (i*n) + j) = 0;
		}
	}

	// Multiply matrices
	#pragma omp parallel
	#pragma omp for collapse(3)
	for (int i = 0; i < m; i++) {
		for (int j = 0; j < n; j++) {
			for (int k = 0; k < p; k++) {
				*(result + (i*n) + j) += (*(matrix1 + (i*p) + k)) * (*(matrix2 + (k*n) + j));
			}
		}
	}
}

/* getElapsedTime calculates the wall-clock time elapsed between the functions call and the initialization of the
 * timeval struct passed to it.
 * */
long getElapsedTime(struct timeval startTime) {
	struct timeval tempTime;
	gettimeofday(&tempTime, 0);

	return ((tempTime.tv_sec - startTime.tv_sec)*1000000 + (tempTime.tv_usec-startTime.tv_usec));
}