package org.lnmiit;

import java.io.*;


public class Utilities {


	public static void checkDir(final String directoryName, final String message) throws IOException {
		if (!new File(directoryName).exists()) {
			System.out.println("creating directory: " + directoryName);
			new File(directoryName).mkdirs();
		}
	}

	


	public static void writeFile(float[] arr, String fname) throws IOException {
		FileWriter fw = new FileWriter(fname);
		for (int i = 0; i < arr.length; i++) {
			fw.write(arr[i] + "\n");
		}
		fw.close();
	}

	


	public static float[][] readTransitionPotentials(String infile) throws IOException{
		BufferedReader fp_trans = new BufferedReader(new InputStreamReader(new FileInputStream(infile)));
		String strin;
		String[] strinparts = new String[10];
		float[][] transpotentials = new float[10][10];
	
		while((strin = fp_trans.readLine()) != null) {
			strinparts = strin.split("\t");
			transpotentials[Utilities.convertStringToInt(strinparts[0])][Utilities.convertStringToInt(strinparts[1])] = Float.parseFloat(strinparts[2]);
		}
		fp_trans.close();
		return transpotentials;
	}

	


	public static int[][] readImages(String inputfile) throws FileNotFoundException, IOException {
		BufferedReader fds_images = new BufferedReader(new InputStreamReader(new FileInputStream(inputfile)));
		int[][] small_images = new int[GraphicalModel.numlines*2][10];
		String[] temp;
		int[] temp_int = new int[10];
		int i = 0;
		String strin;
		while((strin = fds_images.readLine()) != null) {
			temp  = strin.split("\t");
			GraphicalModel.strlengths[i] = temp.length;
			for (int j=0; j<temp.length; j++) {
				temp_int[j] = Integer.parseInt(temp[j]);
				small_images[i][j] = temp_int[j];
			}
			strin = fds_images.readLine();
			i++;
			temp  = strin.split("\t");
			GraphicalModel.strlengths[i] = temp.length;
			for (int j=0; j<temp.length; j++) {
				temp_int[j] = Integer.parseInt(temp[j]);
				small_images[i][j] = temp_int[j];
			}
			i++;
			strin = fds_images.readLine();
			
		}
		return small_images;
	}

	


	public static float[][] readOCRPotentials(String infile) throws IOException {	
		BufferedReader fp_ocr = new BufferedReader(new InputStreamReader(new FileInputStream(infile)));
		String strin;
		String[] strinparts = new String[10];
		float[][] ocrpotentials = new float[1000][10];
	
		while((strin = fp_ocr.readLine()) != null) {
			strinparts = strin.split("\t");
			//System.out.println(strinparts[0]);
			ocrpotentials[Integer.parseInt(strinparts[0])][Utilities.convertStringToInt(strinparts[1])] = Float.parseFloat(strinparts[2]);
		}
		fp_ocr.close();
		return ocrpotentials;
	}

	


	public static int convertStringToInt(String string) {
		if (string.equals("d")) {
			return 0;
		}
		if (string.equals("o")) {
			return 1;
		}
		if (string.equals("i")) {
			return 2;
		}
		if (string.equals("r")) {
			return 3;
		}
		if (string.equals("a")) {
			return 4;
		}
		if (string.equals("h")) {
			return 5;
		}
		if (string.equals("t")) {
			return 6;
		}
		if (string.equals("n")) {
			return 7;
		}
		if (string.equals("s")) {
			return 8;
		}
		if (string.equals("e")) {
			return 9;
		}
		return -1; //default
	}

	




	public static String convertIntToString(int imageval) {
		switch (imageval) {
		case 0:
			return "d";
		case 1:
			return "o";
		case 2:
			return "i";
		case 3:
			return "r";
		case 4:
			return "a";
		case 5:
			return "h";
		case 6:
			return "t";
		case 7:
			return "n";
		case 8:
			return "s";
		case 9:
			return "e";
		default:		
			return "";
		}
	}

	


	public static int[][] readWords(String inputfile) throws IOException {
		BufferedReader fds_images = new BufferedReader(new InputStreamReader(new FileInputStream(inputfile)));
		int[][] characters = new int[GraphicalModel.numlines*2][10];
		int[] temp_int = new int[10];
		int i = 0;
		String strin;
		while((strin = fds_images.readLine()) != null) {
			for (int j=0; j<GraphicalModel.strlengths[i]; j++) { 
				temp_int[j] = convertStringToInt(strin.substring(j,j+1));
				characters[i][j] = temp_int[j];
			}
			i++;
			strin = fds_images.readLine();
			for (int j=0; j<GraphicalModel.strlengths[i]; j++) { 
				temp_int[j] = convertStringToInt(strin.substring(j,j+1));
				characters[i][j] = temp_int[j];
			}
			i++;
			strin = fds_images.readLine();
		}
		return characters;
	}
}
