package appleby;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.WorkbookUtil;

public class ApplebyAligner {

	private static final String DIVIDER = "cequidivise";
	private static final String DIVIDER_WITH_SPACE = " cequidivise ";
	public static HashMap<String, String> applebyToIdakho1 = new HashMap<String, String>();
	public static HashMap<String, String> applebyToIdakho2 = new HashMap<String, String>();
	public static HashMap<String, String> applebyToIsukha = new HashMap<String, String>();
	public static HashMap<String, String> applebyToKabaras = new HashMap<String, String>();
	public static HashMap<String, String> applebyToKisa = new HashMap<String, String>();
	public static HashMap<String, String> applebyToLogoori1 = new HashMap<String, String>();
	public static HashMap<String, String> applebyToLogoori2 = new HashMap<String, String>();
	public static HashMap<String, String> applebyToNyore = new HashMap<String, String>();
	public static HashMap<String, String> applebyToSaamia = new HashMap<String, String>();
	public static HashMap<String, String> applebyToTachoni = new HashMap<String, String>();
	public static HashMap<String, String> applebyToTsotso = new HashMap<String, String>();
	public static HashMap<String, String> applebyToTura = new HashMap<String, String>();


	public static HashMap<String, String> applebyToPosAndGloss = new HashMap<String, String>();

	private static void readIdakho() {
		HSSFSheet sheet = getSheet("src/Appleby_to_Luyia/IdakhoDictionary_combined.xls");

		//Get iterator to all the rows in current sheet
		Iterator<Row> rowIterator = sheet.iterator();
		rowIterator.next();
		while(rowIterator.hasNext()) {
			Row r = rowIterator.next();
			int lastColumn = r.getLastCellNum();
			String appleby = "";
			String luwanga = "";
			String idakho1 = "";
			String idakho2 = "";
			String pos = "";
			String gloss = "";
			int i = 0;
			for(int col = 0; col < lastColumn; col++) {
				Cell c = r.getCell(col, Row.RETURN_BLANK_AS_NULL);
				if(c == null) {
					i++;
					continue;
				}
				switch(i) {
				case 0:
					appleby = c.toString();
					break;
				case 1:
					luwanga = c.toString();
					break;
				case 2:
					idakho1 = c.toString();
					break;
				case 3:
					idakho2 = c.toString();
					break;
				case 4:
					pos = c.toString();
					break;
				case 5:
					gloss = c.toString();
					break;
				default:
					break;
				}
				i++;
			}
			String oldPosAndGloss = applebyToPosAndGloss.get(appleby + DIVIDER_WITH_SPACE + luwanga);
			if(oldPosAndGloss == null || oldPosAndGloss.equals(DIVIDER_WITH_SPACE)) { //nothing there
				applebyToPosAndGloss.put(appleby + DIVIDER_WITH_SPACE + luwanga, pos + DIVIDER_WITH_SPACE + gloss);
			}
			applebyToIdakho1.put(appleby + DIVIDER_WITH_SPACE + luwanga, idakho1);
			applebyToIdakho2.put(appleby + DIVIDER_WITH_SPACE + luwanga, idakho2);
		}
	}

	private static void readKisa() {
		HSSFSheet sheet = getSheet("src/Appleby_to_Luyia/KabarasDictionary_20140728.xls");

		//Get iterator to all the rows in current sheet
		Iterator<Row> rowIterator = sheet.iterator();
		rowIterator.next();
		while(rowIterator.hasNext()) {
			Row r = rowIterator.next();
			int lastColumn = r.getLastCellNum();
			String appleby = "";
			String luwanga = "";
			String kisa = "";
			String pos = "";
			String gloss = "";
			int i = 0;
			for(int col = 0; col < lastColumn; col++) {
				Cell c = r.getCell(col, Row.RETURN_BLANK_AS_NULL);
				if(c == null) {
					i++;
					continue;
				}
				switch(i) {
				case 0:
					appleby = c.toString();
					break;
				case 1:
					luwanga = c.toString();
					break;
				case 2:
					kisa = c.toString();
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
			String oldPosAndGloss = applebyToPosAndGloss.get(appleby + DIVIDER_WITH_SPACE + luwanga);
			if(oldPosAndGloss == null || oldPosAndGloss.equals(DIVIDER_WITH_SPACE)) { //nothing there
				applebyToPosAndGloss.put(appleby + DIVIDER_WITH_SPACE + luwanga, pos + DIVIDER_WITH_SPACE + gloss);
			}
			applebyToKisa.put(appleby + DIVIDER_WITH_SPACE + luwanga, kisa);
		}
	}

	private static void readLogoori() {
		HSSFSheet sheet = getSheet("src/Appleby_to_Luyia/LogooriDictionary_20140728.xls");

		//Get iterator to all the rows in current sheet
		Iterator<Row> rowIterator = sheet.iterator();
		rowIterator.next();
		while(rowIterator.hasNext()) {
			Row r = rowIterator.next();
			int lastColumn = r.getLastCellNum();
			String appleby = "";
			String luwanga = "";
			String logoori1 = "";
			String logoori2 = "";
			String pos = "";
			String gloss = "";
			int i = 0;
			for(int col = 0; col < lastColumn; col++) {
				Cell c = r.getCell(col, Row.RETURN_BLANK_AS_NULL);
				if(c == null) {
					i++;
					continue;
				}
				switch(i) {
				case 0:
					appleby = c.toString();
					break;
				case 1:
					luwanga = c.toString();
					break;
				case 2:
					logoori1 = c.toString();
					break;
				case 3:
					logoori2 = c.toString();
					break;
				case 4:
					pos = c.toString();
					break;
				case 5:
					gloss = c.toString();
					break;
				default:
					break;
				}
				i++;
			}
			String oldPosAndGloss = applebyToPosAndGloss.get(appleby + DIVIDER_WITH_SPACE + luwanga);
			if(oldPosAndGloss == null || oldPosAndGloss.equals(DIVIDER_WITH_SPACE)) { //nothing there
				applebyToPosAndGloss.put(appleby + DIVIDER_WITH_SPACE + luwanga, pos + DIVIDER_WITH_SPACE + gloss);
			}
			applebyToLogoori1.put(appleby + DIVIDER_WITH_SPACE + luwanga, logoori1);
			applebyToLogoori2.put(appleby + DIVIDER_WITH_SPACE + luwanga, logoori2);
		}
	}

	private static void readIsukha() {
		HSSFSheet sheet = getSheet("src/Appleby_to_Luyia/IsukhaDictionary_20140728.xls");

		//Get iterator to all the rows in current sheet
		Iterator<Row> rowIterator = sheet.iterator();
		rowIterator.next();
		while(rowIterator.hasNext()) {
			Row r = rowIterator.next();
			int lastColumn = r.getLastCellNum();
			String appleby = "";
			String luwanga = "";
			String isukha = "";
			String pos = "";
			String gloss = "";
			int i = 0;
			for(int col = 0; col < lastColumn; col++) {
				Cell c = r.getCell(col, Row.RETURN_BLANK_AS_NULL);
				if(c == null) {
					i++;
					continue;
				}
				switch(i) {
				case 0:
					appleby = c.toString();
					break;
				case 1:
					luwanga = c.toString();
					break;
				case 2:
					isukha = c.toString();
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
			String oldPosAndGloss = applebyToPosAndGloss.get(appleby + DIVIDER_WITH_SPACE + luwanga);
			if(oldPosAndGloss == null || oldPosAndGloss.equals(DIVIDER_WITH_SPACE)) { //nothing there
				applebyToPosAndGloss.put(appleby + DIVIDER_WITH_SPACE + luwanga, pos + DIVIDER_WITH_SPACE + gloss);
			}
			applebyToIsukha.put(appleby + DIVIDER_WITH_SPACE + luwanga, isukha);
		}
	}

	private static void readKabaras() {
		HSSFSheet sheet = getSheet("src/Appleby_to_Luyia/KisaDictionary_20090529.xls");

		//Get iterator to all the rows in current sheet
		Iterator<Row> rowIterator = sheet.iterator();
		rowIterator.next();
		while(rowIterator.hasNext()) {
			Row r = rowIterator.next();
			int lastColumn = r.getLastCellNum();
			String appleby = "";
			String luwanga = "";
			String kabaras = "";
			String pos = "";
			String gloss = "";
			int i = 0;
			for(int col = 0; col < lastColumn; col++) {
				Cell c = r.getCell(col, Row.RETURN_BLANK_AS_NULL);
				if(c == null) {
					i++;
					continue;
				}
				switch(i) {
				case 0:
					appleby = c.toString();
					break;
				case 1:
					luwanga = c.toString();
					break;
				case 2:
					kabaras = c.toString();
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
			String oldPosAndGloss = applebyToPosAndGloss.get(appleby + DIVIDER_WITH_SPACE + luwanga);
			if(oldPosAndGloss == null || oldPosAndGloss.equals(DIVIDER_WITH_SPACE)) { //nothing there
				applebyToPosAndGloss.put(appleby + DIVIDER_WITH_SPACE + luwanga, pos + DIVIDER_WITH_SPACE + gloss);
			}
			applebyToKabaras.put(appleby + DIVIDER_WITH_SPACE + luwanga, kabaras);
		}
	}

	private static void readNyore() {
		HSSFSheet sheet = getSheet("src/Appleby_to_Luyia/NyoreDictionary_20140728.xls");

		//Get iterator to all the rows in current sheet
		Iterator<Row> rowIterator = sheet.iterator();
		rowIterator.next();
		while(rowIterator.hasNext()) {
			Row r = rowIterator.next();
			int lastColumn = r.getLastCellNum();
			String appleby = "";
			String luwanga = "";
			String nyore = "";
			String pos = "";
			String gloss = "";
			int i = 0;
			for(int col = 0; col < lastColumn; col++) {
				Cell c = r.getCell(col, Row.RETURN_BLANK_AS_NULL);
				if(c == null) {
					i++;
					continue;
				}
				switch(i) {
				case 0:
					appleby = c.toString();
					break;
				case 1:
					luwanga = c.toString();
					break;
				case 2:
					nyore = c.toString();
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
			String oldPosAndGloss = applebyToPosAndGloss.get(appleby + DIVIDER_WITH_SPACE + luwanga);
			if(oldPosAndGloss == null || oldPosAndGloss.equals(DIVIDER_WITH_SPACE)) { //nothing there
				applebyToPosAndGloss.put(appleby + DIVIDER_WITH_SPACE + luwanga, pos + DIVIDER_WITH_SPACE + gloss);
			}
			applebyToNyore.put(appleby + DIVIDER_WITH_SPACE + luwanga, nyore);
		}
	}

	private static void readSaamia() {
		HSSFSheet sheet = getSheet("src/Appleby_to_Luyia/SaamiaDictionary_20140728.xls");

		//Get iterator to all the rows in current sheet
		Iterator<Row> rowIterator = sheet.iterator();
		rowIterator.next();
		while(rowIterator.hasNext()) {
			Row r = rowIterator.next();
			int lastColumn = r.getLastCellNum();
			String appleby = "";
			String luwanga = "";
			String saamia = "";
			String pos = "";
			String gloss = "";
			int i = 0;
			for(int col = 0; col < lastColumn; col++) {
				Cell c = r.getCell(col, Row.RETURN_BLANK_AS_NULL);
				if(c == null) {
					i++;
					continue;
				}
				switch(i) {
				case 0:
					appleby = c.toString();
					break;
				case 1:
					luwanga = c.toString();
					break;
				case 2:
					saamia = c.toString();
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
			String oldPosAndGloss = applebyToPosAndGloss.get(appleby + DIVIDER_WITH_SPACE + luwanga);
			if(oldPosAndGloss == null || oldPosAndGloss.equals(DIVIDER_WITH_SPACE)) { //nothing there
				applebyToPosAndGloss.put(appleby + DIVIDER_WITH_SPACE + luwanga, pos + DIVIDER_WITH_SPACE + gloss);
			}
			applebyToSaamia.put(appleby + DIVIDER_WITH_SPACE + luwanga, saamia);
		}
	}

	private static void readTachoni() {
		HSSFSheet sheet = getSheet("src/Appleby_to_Luyia/TachoniDictionary_20140728.xls");

		//Get iterator to all the rows in current sheet
		Iterator<Row> rowIterator = sheet.iterator();
		rowIterator.next();
		while(rowIterator.hasNext()) {
			Row r = rowIterator.next();
			int lastColumn = r.getLastCellNum();
			String appleby = "";
			String luwanga = "";
			String tachoni = "";
			String pos = "";
			String gloss = "";
			int i = 0;
			for(int col = 0; col < lastColumn; col++) {
				Cell c = r.getCell(col, Row.RETURN_BLANK_AS_NULL);
				if(c == null) {
					i++;
					continue;
				}
				switch(i) {
				case 0:
					appleby = c.toString();
					break;
				case 1:
					luwanga = c.toString();
					break;
				case 2:
					tachoni = c.toString();
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
			String oldPosAndGloss = applebyToPosAndGloss.get(appleby + DIVIDER_WITH_SPACE + luwanga);
			if(oldPosAndGloss == null || oldPosAndGloss.equals(DIVIDER_WITH_SPACE)) { //nothing there
				applebyToPosAndGloss.put(appleby + DIVIDER_WITH_SPACE + luwanga, pos + DIVIDER_WITH_SPACE + gloss);
			}
			applebyToTachoni.put(appleby + DIVIDER_WITH_SPACE + luwanga, tachoni);
		}
	}

	private static void readTsotso() {
		HSSFSheet sheet = getSheet("src/Appleby_to_Luyia/TsotsoDictionary_20090523.xls");

		//Get iterator to all the rows in current sheet
		Iterator<Row> rowIterator = sheet.iterator();
		rowIterator.next();
		while(rowIterator.hasNext()) {
			Row r = rowIterator.next();
			int lastColumn = r.getLastCellNum();
			String appleby = "";
			String luwanga = "";
			String tsotso = "";
			String pos = "";
			String gloss = "";
			int i = 0;
			for(int col = 0; col < lastColumn; col++) {
				Cell c = r.getCell(col, Row.RETURN_BLANK_AS_NULL);
				if(c == null) {
					i++;
					continue;
				}
				switch(i) {
				case 0:
					appleby = c.toString();
					break;
				case 1:
					luwanga = c.toString();
					break;
				case 2:
					tsotso = c.toString();
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
			String oldPosAndGloss = applebyToPosAndGloss.get(appleby + DIVIDER_WITH_SPACE + luwanga);
			if(oldPosAndGloss == null || oldPosAndGloss.equals(DIVIDER_WITH_SPACE)) { //nothing there
				applebyToPosAndGloss.put(appleby + DIVIDER_WITH_SPACE + luwanga, pos + DIVIDER_WITH_SPACE + gloss);
			}
			applebyToTsotso.put(appleby + DIVIDER_WITH_SPACE + luwanga, tsotso);
		}
	}

	private static void readTura() {
		HSSFSheet sheet = getSheet("src/Appleby_to_Luyia/TuraDictionary_20110311.xls");

		//Get iterator to all the rows in current sheet
		Iterator<Row> rowIterator = sheet.iterator();
		rowIterator.next();
		while(rowIterator.hasNext()) {
			Row r = rowIterator.next();
			int lastColumn = r.getLastCellNum();
			String appleby = "";
			String luwanga = "";
			String tura = "";
			String pos = "";
			String gloss = "";
			int i = 0;
			for(int col = 0; col < lastColumn; col++) {
				Cell c = r.getCell(col, Row.RETURN_BLANK_AS_NULL);
				if(c == null) {
					i++;
					continue;
				}
				switch(i) {
				case 0:
					appleby = c.toString();
					break;
				case 1:
					luwanga = c.toString();
					break;
				case 2:
					tura = c.toString();
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
			String oldPosAndGloss = applebyToPosAndGloss.get(appleby + DIVIDER_WITH_SPACE + luwanga);
			if(oldPosAndGloss == null || oldPosAndGloss.equals(DIVIDER_WITH_SPACE)) { //nothing there
				applebyToPosAndGloss.put(appleby + DIVIDER_WITH_SPACE + luwanga, pos + DIVIDER_WITH_SPACE + gloss);
			}
			applebyToTura.put(appleby + DIVIDER_WITH_SPACE + luwanga, tura);
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
//		readIdakho();
//		readIsukha();
//		readKabaras();
//		readKisa();
//		readLogoori();
//		readNyore();
//		readSaamia();
//		readTachoni();
//		readTsotso();
//		readTura();
//		writeWksht("/Users/gwprice/Desktop/allAligned.xls");
//	}



	private static void writeWksht(String destBook) {
		ArrayList<HashMap<String,String>> translationMaps = new ArrayList<HashMap<String,String>>();
		translationMaps.add(applebyToIdakho1);
		translationMaps.add(applebyToIdakho2);
		translationMaps.add(applebyToIsukha);
		translationMaps.add(applebyToKabaras);
		translationMaps.add(applebyToKisa);
		translationMaps.add(applebyToLogoori1);
		translationMaps.add(applebyToLogoori2);
		translationMaps.add(applebyToNyore);
		translationMaps.add(applebyToSaamia);
		translationMaps.add(applebyToTachoni);
		translationMaps.add(applebyToTsotso);
		translationMaps.add(applebyToTura);


		Workbook wb = new HSSFWorkbook();  // or new XSSFWorkbook();
		Sheet sheet1 = wb.createSheet(WorkbookUtil.createSafeSheetName("Sheet1"));

		Row row = sheet1.createRow(0);
		row.createCell(0).setCellValue("Appleby 1943");
		row.createCell(1).setCellValue("Luwanga");
		row.createCell(2).setCellValue("Idakho 1 (Grace Mmbone Makokha)");
		row.createCell(3).setCellValue("Idakho 2 (Florence Lidiolo)");
		row.createCell(4).setCellValue("Isukha (Leonida Ayesa Shikoli)");
		row.createCell(5).setCellValue("Kabaras (Nedy Watamba)");
		row.createCell(6).setCellValue("Lukisa (Anunda)");
		row.createCell(7).setCellValue("Logoori 1 (Patrick Kigane, Nyang'ori)");
		row.createCell(8).setCellValue("Logoori 2 (Babu Jane Mmboga)");
		row.createCell(9).setCellValue("Lunyore (Joash Opulu)");
		row.createCell(10).setCellValue("Lusaamia");
		row.createCell(11).setCellValue("Lutachoni");
		row.createCell(12).setCellValue("Lutsotso");
		row.createCell(13).setCellValue("Lutura");
		row.createCell(14).setCellValue("POS");
		row.createCell(15).setCellValue("gloss");

		int r = 1;
		for(String appleby_luwanga : applebyToPosAndGloss.keySet()) {
			row = sheet1.createRow(r);
			String[] al = appleby_luwanga.split(DIVIDER);
			String[] pg = applebyToPosAndGloss.get(appleby_luwanga).split(DIVIDER);
			row.createCell(0).setCellValue(al[0]); //appleby
			if(al.length > 1) {
				row.createCell(1).setCellValue(al[1]); //luwanga
			}
			int c = 2;
			for(HashMap<String,String> tMap: translationMaps) {
				String trans = tMap.get(appleby_luwanga);
				row.createCell(c).setCellValue(trans);
				c++;
			}
			row.createCell(c).setCellValue(pg[0]); //POS
			if(pg.length > 1) {
				row.createCell(c+1).setCellValue(pg[1]); //gloss
			}
			r++;
		}
		try {
			FileOutputStream fileOut = new FileOutputStream(destBook);
			wb.write(fileOut);
			fileOut.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
