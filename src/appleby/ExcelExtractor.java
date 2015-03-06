package appleby;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.WorkbookUtil;

public class ExcelExtractor {

	private static String TAB = "		";
	private static int ISUKHA_IDAKHO = 0;
	private static int SAMIA = 1;
	private static int WANGA = 2;
	private static int BUKUSU = 3;

	private static void readLanguage() {
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(new File("/Users/gwprice/Desktop/project_data/WangaOut.txt")));
			HSSFSheet sheet = getSheet("/Users/gwprice/Desktop/project_data/Wanga.xls");

			//Get iterator to all the rows in current sheet
			Iterator<Row> rowIterator = sheet.iterator();
			while(rowIterator.hasNext()) {
				Row r = rowIterator.next();
				int lastColumn = 5;
				System.out.println(lastColumn);
				String lex = "EMPTY_LEX";
				String pos = "EMPTY_POS";
				String gloss = "EMPTY_GLOSS";
				int i = 0;
				for(int col = 0; col < lastColumn; col++) {
					Cell c = r.getCell(col, Row.RETURN_BLANK_AS_NULL);
					if(c == null) {
						i++;
						continue;
					}
					switch(i) {
					case 0:
						lex = c.toString();
						break;
					case 3:
						pos = c.toString();
						break;
					case 4:
						gloss = c.toString();
						break;
					default:
						break;
					}
					i++;
				}
				bw.write(lex + TAB + gloss + TAB + pos + "\n");
			}
			bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static HSSFSheet getSheet(String filepath) {
		//Get the workbook instance for XLS file 
		HSSFWorkbook workbook = null;
		HSSFSheet sheet = null;
		try {
			workbook = new HSSFWorkbook(new FileInputStream(filepath));
			sheet = workbook.getSheetAt(0);
			workbook.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		//Get first sheet from the workbook

		return sheet;
	}

//	public static void main(String[] args) {
//		System.out.println("here:");
//		authorityAlignmentCounter();
//	}

	public static void authorityAlignmentCounter() {
		ArrayList<HashSet<String>> allSets = getDictionariesAsHashSets();
		HashSet<String> isukhaIdakho = allSets.get(0);
		HashSet<String> samia = allSets.get(1);
		HashSet<String> wanga = allSets.get(2);
		HashSet<String> bukusu = allSets.get(3);
		int[][] alignments = new int[4][4];
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(new File("/Users/gwprice/Desktop/project_data/raw_data/authorityCountOut.txt")));
			HSSFSheet sheet = getSheet("/Users/gwprice/Desktop/project_data/raw_data/authority.xls");
			//Get iterator to all the rows in current sheet
			Iterator<Row> rowIterator = sheet.iterator();
			while(rowIterator.hasNext()) {
				Row r = rowIterator.next();
				int lastColumn = 6;
				boolean I = false;
				boolean S = false;
				boolean W = false;
				boolean B = false;

				String iWord = "";
				String sWord = "";
				String wWord = "";
				String bWord = "";

				for(int col = 0; col < lastColumn; col++) {
					Cell c = r.getCell(col, Row.RETURN_BLANK_AS_NULL);
					String theWord;
					String unchangedWord;
					if(c != null && !(unchangedWord = c.toString()).equals("")) {
						theWord = unchangedWord.toLowerCase();
						if(theWord.equals("")) {
							System.out.println("HI MOM");
						}
					}
					else {
						continue;
					}
					switch(col) {
					case 0:
						if(isukhaIdakho.contains(theWord)) {
							I = true;
							iWord = unchangedWord;
						}
						break;
					case 1:
						if(isukhaIdakho.contains(theWord)) {
							I = true;
							iWord = unchangedWord;
						}
						break;
					case 2:
						if(isukhaIdakho.contains(theWord)) {
							I = true;
							iWord = unchangedWord;
						}
						break;
					case 3:
						if(samia.contains(theWord)) {
							S = true;
							sWord = unchangedWord;
						}
						break;
					case 4:
						if(c != null) {
							if(wanga.contains(theWord)) {
								W = true;
								wWord = unchangedWord;
							}
						}
						break;
					case 5:
						if(bukusu.contains(c.toString().toLowerCase())) {
							B = true;
							bWord = unchangedWord;
						}
						break;
					default:
						break;
					}
				}
				boolean[] match = {I,S,W,B};
				String[] words = {iWord,sWord,wWord,bWord};
				String[] labels = {"Isukha-Idakho","Samia-Lugwe","Wanga","Bukusu"};
				for(int i = 0; i < 4; i++) {
					for(int j = 0; j < 4; j++) {
						if(match[i] && match[j]) {
							alignments[i][j]++;
							if(i < j) {
								System.out.println(labels[i] + ": " + words[i]+"\t\t" + labels[j] + ": " +  words[j]);
							}
						}
					}
				}
			}
			String[] labels = {"Isukha-Idakho","Samia-Lugwe","Wanga\t","Bukusu\t"};
			bw.write("\t\tIsukha-Idakho\t\tSamia-Lugwe\tWanga\t\tBukusu");
			for(int i = 0; i < 4; i++) {
				bw.write("\n" + labels[i]);
				for(int j = 0; j < 4; j++) {
					bw.write("\t\t" + alignments[i][j]);
					//					bw.write(labels[i] + " and " + labels[j] + " have " + alignments[i][j] + " words aligned\n");
					//					bw.write(labels[i] + " and " + labels[j] + " have " + alignments[i][j] + " words aligned\n");
				}
			}
			bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	static ArrayList<HashSet<String>> getDictionariesAsHashSets() {
		HashSet<String> isukhaIdakho = new HashSet<String>();
		HashSet<String> samia = new HashSet<String>();
		HashSet<String> wanga = new HashSet<String>();
		HashSet<String> bukusu = new HashSet<String>();
		addWordsToSet("/Users/gwprice/Desktop/project_data/final_data/Isukha-Idakho.txt",isukhaIdakho);
		addWordsToSet("/Users/gwprice/Desktop/project_data/final_data/Samia-Lugwe.txt",samia);
		addWordsToSet("/Users/gwprice/Desktop/project_data/final_data/Wanga.txt",wanga);
		addWordsToSet("/Users/gwprice/Desktop/project_data/final_data/Bukusu.txt",bukusu);
		ArrayList<HashSet<String>> toReturn = new ArrayList<HashSet<String>>();
		toReturn.add(isukhaIdakho);
		toReturn.add(samia);
		toReturn.add(wanga);
		toReturn.add(bukusu);
		return toReturn;
	}

	static void addWordsToSet(String filepath, HashSet<String> mySet) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(filepath));
			String line = "";
			while((line = br.readLine()) != null){
				String word = line.split("\t\t")[0].toLowerCase();
				mySet.add(word);
			}
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


}
