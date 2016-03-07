package org.lnmiit;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;


public class Similarity {

	public float wordsSimilarity (String file1, String file2) throws IOException {
		float similarity = 0;
		BufferedReader br1 = new BufferedReader(new InputStreamReader(new FileInputStream(file1)));
		BufferedReader br2 = new BufferedReader(new InputStreamReader(new FileInputStream(file2)));
		String str1;
		String str2;
		int same = 0;
		int total = 0;
		while((str1 = br1.readLine())!=null) {
			str2 = br2.readLine();
			if (str1.equals(str2)) {
				same++;
			}
			total++;	
		}
		similarity = (float) same/total;
		return similarity;
	}
	
	
	public float characterSimilarity (String file1, String file2) throws IOException {
		float similarity = 0;
		BufferedReader br1 = new BufferedReader(new InputStreamReader(new FileInputStream(file1)));
		BufferedReader br2 = new BufferedReader(new InputStreamReader(new FileInputStream(file2)));
		int eofmarker;
		char ch1;
		char ch2;
		int same = 0;
		int total = 0;
		while ((eofmarker = br1.read())!=-1) {
			ch1 = (char) eofmarker;
			ch2 = (char) br2.read();
			if (ch1 != '\n') {
				if (ch1==ch2) {
					same++;
				}
				total++;
			}
		}
		similarity = (float) same/total;
		return similarity;
	}
	
}
