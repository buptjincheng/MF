package MFcode;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class MF {
	public static double eij = 0, e = 0;
    public static int i, j, k, x, z;
	// hàm MF
	//public static void MatrixFactorization(double[][] A, double[][] B, double[][] C, double[][] D, int step, int K, int n,
	//		int m, double beta, double lamda) {
		// chuyen vi ma tran
		

	//}

	public static void main(String[] args) {
		int n, m, K, i, j, k;
		int i1, j1, k1;
		int step = 5000;
		double beta = 0.0002;
		double lamda = 0.02;
		Scanner s = new Scanner(System.in);
		Random rd = new Random();
		
		System.out.println("Nhap so user: ");
		n = s.nextInt();
		
		System.out.println("Nhap so Item: ");
		m = s.nextInt();
		
		System.out.println("Nhap so nhan to: ");
		K = s.nextInt();
		
		//ma tran user_item ban dau
		System.out.println("Nhap ma tran User_Item: ");
		double A[][] = new double[n][m];
		for (i = 0; i < n; i++) {
			for(j = 0; j < m; j++) {
				A[i][j] = s.nextDouble();
			}
		}
	    
		//random ma tran W user
		double B[][] = new double[n][K];
	    for(i = 0; i < n; i++) {
	    	for(j = 0; j < K; j++) {
	    		B[i][j] = rd.nextDouble();
	    	}
	    }
	    for(i = 0; i < n; i++) {
	    	for(j = 0; j < K; j++) {
	    		B[i][j] = Math.round(B[i][j]*1000.00)/1000.00;
	    	}
	    }
	    System.out.println();
	    System.out.print("Ma tran W User: ");
	    for(i = 0; i < n; i++) {
	    	System.out.println();
	    	for(j = 0; j < K; j++) {
	    		System.out.print(B[i][j]+"\t");
	    	}
	    }
	    
	    //random ma tran H item
	    double C[][] = new double[m][K];
	    for(i = 0; i < m; i++) {
	    	for(j = 0; j < K; j++) {
	    		C[i][j] = rd.nextDouble();
	    	}
	    }
	    for(i = 0; i < m; i++) {
	    	for(j = 0; j < K; j++) {
	    		C[i][j] = Math.round(C[i][j]*1000.00)/1000.00;
	    	}
	    }
	    System.out.println("\n");
	    System.out.print("Ma tran H Items: ");
	    for(i = 0; i < m; i++) {
	    	System.out.println();
	    	for(j = 0; j < K; j++) {
	    		System.out.print(C[i][j]+"\t");
	    	}
	    }
	    

	    double D[][] = new double[n][m];
	  /*  for (i = 0; i < n; i++) {
			for (j = 0; j < m; j++) {
				D[i][j] = 0;
			}
		} */
	    
        
	    //ma tran chuyen vi cua H Item
	    double F[][] = new double[K][m];
	    for (i = 0; i < m; i++) {
			for (j = 0; j < K; j++) {
				F[j][i] = C[i][j];
			}
		}
	   /* for (i = 0; i < K; i++) {
			for (j = 0; j < m; j++) {
				System.out.print(F[i][j]+"\t");
			}
		} */
	 /* for (i = 0; i < n; i++) {
			for (j = 0; j < m; j++) {
				for(k = 0; k < K; k++) {
				D[i][j] = D[i][j] + B[i][k] * F[k][j];
				}
			}} */
	
		
        //Ham MF
		for (x = 0; x < step; x++) {
			for (i = 0; i < n; i++) {
				for (j = 0; j < m; j++) {
					if (A[i][j] > 0) {	
						D[i][j] = 0;
						for(k = 0; k < K; k++) {
							D[i][j] += B[i][k] * F[k][j];
							}
						eij = A[i][j] - D[i][j];
						for (k = 0; k < K; k++) {
							B[i][k] = B[i][k] + beta * (2 * eij * F[k][j] - lamda * B[i][k]);
							F[k][j] = F[k][j] + beta * (2 * eij * B[i][k] - lamda * F[k][j]);
						}
					}
				}
			}
			
			for(i = 0; i < n; i++) {
				for(j = 0; j < m; j++) {
					if(A[i][j] > 0) {
						D[i][j] = 0;
						for(k = 0; k < K; k++) {				
							D[i][j] =D[i][j] + B[i][k] * F[k][j];
							}
						e = e + Math.pow(A[i][j] - D[i][j], 2);
						for(k = 0; k < K; k++) {
							e = e + (lamda/2) * ( Math.pow(B[i][k],2) + Math.pow(F[k][j],2) );
						}
					}
				}
			}
			if(e < 0.001) break;
		}
		
		//ket qua ma tran User
		for(i = 0; i < n; i++) {
	    	for(j = 0; j < K; j++) {
	    		B[i][j] = Math.round(B[i][j]*1000.00)/1000.00;
	    	}
	    }
		System.out.println("\n");
		System.out.print("Ma tran W nguoi dung");
		for (i = 0; i < n; i++) {
			System.out.println();
			for (j = 0; j < K; j++) {
				System.out.print(B[i][j]+"\t");
			}
		}
		
		//ket qua ma tran H item
		for (i = 0; i < K; i++) {
			for (j = 0; j < m; j++) {
				C[j][i] = F[i][j];
			}
		}
		for(i = 0; i < m; i++) {
	    	for(j = 0; j < K; j++) {
	    		C[i][j] = Math.round(C[i][j]*1000.00)/1000.00;
	    	}
	    }
		System.out.println("\n");
		System.out.print("Ma tran H item");
		for (i = 0; i < m; i++) {
			System.out.println();
			for (j = 0; j < K; j++) {
				System.out.print(C[i][j]+"\t");
			}
		}
		
		//ma tran ket qua
		for (i = 0; i < n; i++) {
			for (j = 0; j < m; j++) {
				D[i][j] = 0;
			}
		}
		for (i = 0; i < n; i++) {
			for (j = 0; j < m; j++) {
				for(k = 0; k < K; k++) {
				D[i][j] = D[i][j] + B[i][k] * F[k][j];
				}
			}
		}
		for (i = 0; i < n; i++) {
			for (j = 0; j < m; j++) {
				D[i][j] = Math.round(D[i][j]*1000.00)/1000.00;
			}
		}
		System.out.println("\n");
		System.out.print("Ma tran ket qua: ");
		for (i = 0; i < n; i++) {
			System.out.println();
			for (j = 0; j < m; j++) {
				System.out.print(D[i][j]+"\t");
				}
			}
		
	   // MatrixFactorization(A,B,C,D,step,K,n,m,lamda,beta);
	}
}

/*
 5 3 0 1
 4 0 0 1
 1 1 0 5
 1 0 0 4
 0 1 5 4
*/