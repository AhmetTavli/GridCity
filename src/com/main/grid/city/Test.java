package com.main.grid.city;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * 
 * @author Ahmet
 *
 */

public class Test {

	private static String errorTitle = "An Error Has Occured";
	private static BufferedReader reader;
	private static int row = 0, column = 0, rowVal = -1;
	private static String line;
	private static char chVal = 0;
	private static ArrayList<Integer> arrList;
	private static ArrayList<Integer> sumList;
	private static int counter = 0;
	private static boolean isInside = false;
	private static int[][] matrix1;
	private static int[][] matrix2;

	public static void main(String[] args) {
		try {
			// TODO Auto-generated method stub
			JFileChooser chooserObj = new JFileChooser();
			FileNameExtensionFilter filter = new FileNameExtensionFilter(
					"Text Files", "txt", "text");
			chooserObj.setFileFilter(filter);
			chooserObj.setCurrentDirectory(new File(System
					.getProperty("user.home")
					+ System.getProperty("line.separator")));

			int result = chooserObj.showOpenDialog(null);

			File selectedFile = null;
			if (result == JFileChooser.APPROVE_OPTION) {
				selectedFile = chooserObj.getSelectedFile();
				System.out.println("Selected file Path: "
						+ selectedFile.getAbsolutePath());
			} // end if

			if (result == JFileChooser.CANCEL_OPTION)
				System.exit(0);
			// Read Selected Text File
			InputStream inpStr = new FileInputStream(
					selectedFile.getAbsolutePath());
			reader = new BufferedReader(new InputStreamReader(inpStr));
			line = reader.readLine();

			getColumnAndRowValues();
			int row1 = 0, col1 = 0, row2 = 0, col2 = 0;
			sumList = new ArrayList<Integer>();
			arrList = new ArrayList<Integer>();
			matrix1 = new int[row + 1][column];
			matrix2 = new int[column + 1][row];

			for (int i = 0; i < line.length(); i++) {
				if (!Character.isWhitespace(line.charAt(i)))
					chVal = (char) (chVal + line.charAt(i));
				else
					add2ArrayList();
			}

			add2ArrayList();
			printMatrixes();
			System.out.println("\n\n");
			String results = "(1,1)";
			move(row1, col1, row2, col2, results);
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, ex.getMessage(), errorTitle,
					JOptionPane.ERROR_MESSAGE);
		} // end catch
	}

	/**
	 * This method sets Row and Column Values of the matrix respectively
	 */
	private static void getColumnAndRowValues() {
		try {
			int counter = 0;
			for (int i = 0; i <= line.length(); i++) {
				counter++;
				if (!Character.isWhitespace(line.charAt(i))) {
					if (counter == 1)
						row = Character.getNumericValue(line.charAt(i));
					else {
						column = Character.getNumericValue(line.charAt(i));
						line = reader.readLine();
						break;
					}
				} else
					counter--;
			} // end for
			System.out.println("\nRow Value is : " + row
					+ "\nColumn Value is :  " + column);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), errorTitle,
					JOptionPane.ERROR_MESSAGE);
		}
	} // end getColumnAndRowValues

	/**
	 * This method get values from text, then add to arraylist.
	 */
	private static void add2ArrayList() {
		arrList.add(Character.getNumericValue(chVal));
		chVal = '\0';
		if (counter != (row + 1)) {
			if (arrList.size() == column) {
				fillMatrix(matrix1);
				counter++;
			}
		} else if (arrList.size() == row) {
			if (!isInside)
				setInitialRowValue();
			fillMatrix(matrix2);
		}
	}

	/**
	 * This method fill the selected integer matrix
	 */
	private static void fillMatrix(int[][] matrixObj) {
		rowVal++;
		for (int j = 0; j < arrList.size(); j++)
			matrixObj[rowVal][j] = arrList.get(j);
		arrList.clear();
	}

	/**
	 * This Method initializes rowValue just before filling matrix
	 */
	private static void setInitialRowValue() {
		isInside = true;
		rowVal = -1;
	}

	/**
	 * This method prints the integer matrixes
	 */
	private static void printMatrixes() {
		System.out.println("\n");
		System.out.print("Row Values : ");
		for (int i = 0; i < row + 1; i++) {
			for (int j = 0; j < column; j++) {
				System.out.print(matrix1[i][j] + " ");
			}
		}

		System.out.print("\nColumn Values : ");
		for (int i = 0; i < column + 1; i++) {
			for (int j = 0; j < row; j++) {
				System.out.print(matrix2[i][j] + " ");
			}
		}
	}

	/**
	 * @param row1
	 *            Matrix 1 row value
	 * @param col1
	 *            Matrix 1 column value
	 * @param row2
	 *            Matrix 2 column value
	 * @param col2
	 *            Matrix 2 column value
	 */
	private static void move(int row1, int col1, int row2, int col2,
			String resText) {
		if (col1 == column) {
			if (col2 == row) {
				System.out.println("\nTotal Weight of the Path : "
						+ sumList.stream().mapToInt(Integer::intValue).sum());
				System.out.println("\n---Finish---");
				resText = resText.concat("  Total Weight of the Path : "
						+ sumList.stream().mapToInt(Integer::intValue).sum());
				writeResult(resText);
				System.exit(row);
			}
			System.out.println("\nReach end of the column");
			System.out.print("Now continue with South " + matrix2[row2][col2]);
			System.out.print("  Select " + matrix2[row2][col2] + " ");
			sumList.add(matrix2[row2][col2]);
			col2++;
			resText = resText
					.concat(" (" + (row2 + 1) + "," + (col2 + 1) + ")");
		} else if (col2 == row) {
			if (col1 == column) {
				System.out.println("\nTotal Weight of the Path : "
						+ sumList.stream().mapToInt(Integer::intValue).sum());
				System.out.println("---Finish---");
				resText = resText.concat("  Total Weight of the Path : "
						+ sumList.stream().mapToInt(Integer::intValue).sum());
				writeResult(resText);
				System.exit(column);
			}
			System.out.println("\nReach end of the row");
			System.out.print("Now continue with East " + matrix1[row1][col1]);
			System.out.print("  Select " + matrix1[row1][col1] + " ");
			sumList.add(matrix1[row1][col1]);
			col1++;
			resText = resText
					.concat(" (" + (col1 + 1) + "," + (row1 + 1) + ")");
		} else {
			System.out.print("\nComparing East " + matrix1[row1][col1]
					+ " w/ South " + matrix2[row2][col2] + "; ");
			if (matrix1[row1][col1] > matrix2[row2][col2]) {
				System.out.print("  Select " + matrix1[row1][col1] + " ");
				sumList.add(matrix1[row1][col1]);
				row2++;
				col1++;
				resText = resText.concat(" (" + (col1 + 1) + "," + (row1 + 1)
						+ ")");
			} else {
				System.out.print("  Select " + matrix2[row2][col2] + " ");
				sumList.add(matrix2[row2][col2]);
				row1++;
				col2++;
				resText = resText.concat(" (" + (row2 + 1) + "," + (col2 + 1)
						+ ")");
			}
		}
		move(row1, col1, row2, col2, resText);
	}

	/**
	 * This method will write the result in sample output file.txt Text File
	 * will be created inside Project Directory For current project :
	 * C:\Users\Ahmet\workspace\GridCity
	 */
	private static void writeResult(String resultObj) {
		try {
			final JFileChooser writeObj = new JFileChooser();
			int returnVal = writeObj.showSaveDialog(null);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = writeObj.getSelectedFile();
				BufferedWriter writer = new BufferedWriter(new FileWriter(file));
				writer.write(resultObj);
				writer.close();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, e.getMessage(), errorTitle,
					JOptionPane.ERROR_MESSAGE);
		}
	}
} // end class