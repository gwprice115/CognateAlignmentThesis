package project.similarity.utilities;

public interface UtilityInterface {
	static final String SL_PATH = "/Users/gwprice/Desktop/project_data/final_data/Samia-Lugwe";
	static final String II_PATH = "/Users/gwprice/Desktop/project_data/final_data/Isukha-Idakho";
	static final String T_PATH = "/Users/gwprice/Desktop/project_data/final_data/Tiriki";
	static final String B_PATH = "/Users/gwprice/Desktop/project_data/final_data/Bukusu";
	static final String W_PATH = "/Users/gwprice/Desktop/project_data/final_data/Wanga";
	static final String[] DICTIONARY_FILES = {SL_PATH,
		II_PATH,
		T_PATH,
		B_PATH,
		W_PATH
	};//no suffix so dest file names can be generated at runtime
	
	static final String TXT = ".txt";
	
	static final String DOUBLE_TAB = "		";
	static final String T_T = "\t\t";
	static final String LITERAL_SPACE = " ";
}
