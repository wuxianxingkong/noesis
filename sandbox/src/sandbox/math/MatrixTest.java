package sandbox.math;

import ikor.math.Matrix;
import ikor.math.SparseMatrix;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import noesis.Network;
import noesis.io.PajekNetworkReader;
import noesis.network.AdjacencyMatrix;

import org.ujmp.core.doublematrix.stub.AbstractSparseDoubleMatrix2D;
import org.ujmp.core.exceptions.MatrixException;

public class MatrixTest {

	// Benchmark
	// ---------
	
	// Baseline:
	//							NOESIS		UJMP
	// - Inverse				 2999 ms	 436 ms
	// - SS multiplication		10825 ms	 407 ms
	// - SD multiplication		 5551 ms	 254 ms
	// - DS multiplication		 5460 ms	1484 ms	
	// - DD multiplication		  454 ms	 705 ms		
	
	// GraphNetwork -> ArrayNetwork 
	//							NOESIS		UJMP
	// - Inverse				 3036 ms	 353 ms
	// - SS multiplication		 1713 ms	 149 ms
	// - SD multiplication		 1186 ms	 240 ms
	// - DS multiplication		 1128 ms	 331 ms	
	// - DD multiplication		  500 ms	 762 ms		

	// ArrayNetwork -> AttributeNetwork (r561)
	//							NOESIS		UJMP
	// - Inverse				 3000 ms	 344 ms
	// - SS multiplication		 1480 ms	 142 ms
	// - SD multiplication		 1069 ms	 200 ms
	// - DS multiplication		 1127 ms	 372 ms	
	// - DD multiplication		  497 ms	 690 ms		
	
	public static void main(String[] args) throws IOException 
	{
		long startTime;
		InputStream file = new FileInputStream ("data/network/infectious.net");
		Reader myReader = new InputStreamReader(file, "UTF-8");
		PajekNetworkReader reader = new PajekNetworkReader(myReader);
		Network network = reader.read();
		AdjacencyMatrix adjacencyMatrix = new AdjacencyMatrix(network);
		Matrix result;
		
		startTime = System.currentTimeMillis();
		inverse(adjacencyMatrix);
		System.out.println("UJMP Matrix Inverse: " + (System.currentTimeMillis() - startTime) + " milliseconds");
		
		startTime = System.currentTimeMillis();
		adjacencyMatrix.inverse();
		System.out.println("NOESIS Matrix Inverse: " + (System.currentTimeMillis() - startTime) + " milliseconds");
		
		startTime = System.currentTimeMillis();
		result = multiply(adjacencyMatrix,adjacencyMatrix);
		System.out.println("UJMP Matrix Multiplication S-S: " + (System.currentTimeMillis() - startTime) + " milliseconds");
		
		startTime = System.currentTimeMillis();
		result = adjacencyMatrix.multiply(adjacencyMatrix);
		System.out.println("NOESIS Matrix Multiplication S-S: " + (System.currentTimeMillis() - startTime) + " milliseconds");

		startTime = System.currentTimeMillis();
		multiply(adjacencyMatrix,result);
		System.out.println("UJMP Matrix Multiplication S-D: " + (System.currentTimeMillis() - startTime) + " milliseconds");
		
		startTime = System.currentTimeMillis();
		result.multiply(adjacencyMatrix);
		System.out.println("NOESIS Matrix Multiplication S-D: " + (System.currentTimeMillis() - startTime) + " milliseconds");

		startTime = System.currentTimeMillis();
		multiply(result,adjacencyMatrix);
		System.out.println("UJMP Matrix Multiplication D-S: " + (System.currentTimeMillis() - startTime) + " milliseconds");
		
		startTime = System.currentTimeMillis();
		adjacencyMatrix.multiply(result);
		System.out.println("NOESIS Matrix Multiplication D-S: " + (System.currentTimeMillis() - startTime) + " milliseconds");

		startTime = System.currentTimeMillis();
		multiply(result,result);
		System.out.println("UJMP Matrix Multiplication D-D: " + (System.currentTimeMillis() - startTime) + " milliseconds");
		
		startTime = System.currentTimeMillis();
		result.multiply(result);
		System.out.println("NOESIS Matrix Multiplication D-D: " + (System.currentTimeMillis() - startTime) + " milliseconds");
		
	}
	
	// UJMP interface (Universal java Matrix Package)
	
	public static class InterfaceMatrix extends AbstractSparseDoubleMatrix2D {

		private Matrix matrix;
		
		public InterfaceMatrix(Matrix matrix) {
			this.matrix = matrix;
		}
		
		@Override
		public double getDouble(long row, long col) {
			return matrix.get((int)row, (int)col);
		}

		@Override
		public double getDouble(int row, int col) {
			return matrix.get(row, col);
		}

		@Override
		public void setDouble(double val, long row, long col) {
			matrix.set((int)row, (int)col, val);
		}

		@Override
		public void setDouble(double val, int row, int col) {
			matrix.set(row, col, val);
		}

		@Override
		public long[] getSize() {
			long [] size = {matrix.rows(), matrix.columns()};
			return size;
		}

		@Override
		public boolean contains(long... pos) throws MatrixException {
			return matrix.get((int)pos[0], (int)pos[1])!=0.0;
		}

	}
	
	public static Matrix multiply(Matrix A, Matrix B) {
		int rows = A.rows();
		int cols = B.columns();
		InterfaceMatrix rA = new InterfaceMatrix(A);
		InterfaceMatrix rB = new InterfaceMatrix(B);
		org.ujmp.core.Matrix r = rA.mtimes(rB);
		Matrix result = new SparseMatrix(rows, cols);
		
		for(int i=0;i<rows;i++)
			for(int j=0;j<cols;j++) {
				result.set(i, j, r.getAsDouble(i, j));
			}
		
		return result;
	}
	
	public static Matrix inverse(Matrix A) {
		int size = A.rows();
		InterfaceMatrix rA = new InterfaceMatrix(A);
		org.ujmp.core.Matrix r = rA.inv();
		Matrix result = new SparseMatrix(size, size);
		
		for(int i=0;i<size;i++)
			for(int j=0;j<size;j++) {
				result.set(i, j, r.getAsDouble(i, j));
			}
		
		return result;
	}

}
