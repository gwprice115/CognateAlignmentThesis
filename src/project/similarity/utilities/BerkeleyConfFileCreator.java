package project.similarity.utilities;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class BerkeleyConfFileCreator implements UtilityInterface {

	public static void generateNinetyPercentConfFile(int section) {
		try {
			PrintWriter o = new PrintWriter(new FileWriter("/Users/gwprice/Documents/workspace/CognateAlignmentThesis/berkeley_files/ninety.conf"));
			o.println("forwardModels   MODEL1;HMM");
			o.println("reverseModels   MODEL1;HMM");
			o.println("mode    JOINT;JOINT");
			o.println("iters   5;5");
			o.println("execDir /Users/gwprice/Desktop/project_data/hand_aligned/IsukhaIdakho_SamiaLugwe"+ section + "_90/output");
			o.println("create  true");
			o.println("overwriteExecDir        true");
			o.println("saveParams      true");
			o.println("numThreads      48");
			o.println("msPerLine       500");
			o.println("alignTraining");
			o.println("handleUnknownWords   true");
			o.println("foreignSuffix   langTwo");
			o.println("englishSuffix   langOne");
			o.println("trainSources /Users/gwprice/Desktop/project_data/hand_aligned/IsukhaIdakho_SamiaLugwe"+ section + "_90");
			o.println("sentences MAX");
			o.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
