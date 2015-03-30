package project.similarity;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import project.similarity.measurers.JaccardCharNgramsInCommonMeasurer;

public class DictionaryShrinker {

	public DictionaryShrinker() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		String[] correctWords1 = {"lukaka","lira","linu","indangu","hano","shicherani","shikombe","shilaro","shimiyu","lisa","shitabu","shitambaya","shitali","shimeme","yenza","hakari","halala","kuka","libeka","libuyu","lichesa","lusambwa","lichinga","lihulu","lirimu","likata","likongolo","likunda","liloba","linyonyi","linyinya","liremwa","lishene","lishere","chema","likhubuyu","linyonyi","lipata","khulula","lundi","mukhwasi","ne","buyanzi","bumanani","lubango","lukaka","lundi","lusa","lwana","mubasu","mubeyi","mukhutsu","mukafiri","mukongo","mukoye","mulimi","muloli","mulubi","mundu","murende","lihu","mwami","mwami","mwayi","mwene","mwimani","mwoyo","ra","renya","senje","shichira","ti","samba","yala","yala"};
		String[] correctWords2 = {"engaka","erita","erino","emwalo","eno","esicharani","esikombe","esirato","esiminyu","esisa","esitabo","esitambaya","esitanda","esiduyu","eya","akati","alala","kuka","ebeka","ebuyu","ekesa","ekina","esikinga","efiro","efumo","ekada","ekongolo","ekunda","eloba","eyoni","enyinya","etemwa","ehene","ehere","etangi","edete","eyoni","eyoyo","lula","mani","mulamwa","ne","obuheeri","obumanani","olubango","olukaka","olundi","esaaka","olwanda","omubasu","omubacha","omufu","omukafwiri","omukono","omukoye","omukutu","omulosi","omunaabi","omundu","omutende","omutere","omutuki","omwami","omwayi","omwene","omwimani","omwoyo","ta","te","senge","sikira","ti","siyira","yala","yala"};
		
		HashSet<String> correct1 = new HashSet<String>();
		HashSet<String> correct2 = new HashSet<String>();
		for(String c : correctWords1) {
			correct1.add(c);
		}
		for(String c : correctWords2) {
			correct2.add(c);
		}
		

		String inputFile1 = "/Users/gwprice/Desktop/project_data/final_data/Isukha-Idakho.txt";
		String inputFile2 = "/Users/gwprice/Desktop/project_data/final_data/Samia-Lugwe.txt";
		
		try {
			BufferedReader br1 = new BufferedReader(new FileReader(inputFile1));
			BufferedReader br2 = new BufferedReader(new FileReader(inputFile2));
			String line1 = "";
			String line2 = "";
			
			PrintWriter out1 = new PrintWriter(new FileWriter("/Users/gwprice/Desktop/project_data/final_data/Isukha-Idakho_short.txt"));
			PrintWriter out2 = new PrintWriter(new FileWriter("/Users/gwprice/Desktop/project_data/final_data/Samia-Lugwe_short.txt")); 
			
			int i = 0;
			while((line1 = br1.readLine()) != null){
				i++;
				String one = JaccardCharNgramsInCommonMeasurer.isolateLex(line1).toLowerCase();
				if(correct1.contains(one) || (i % 2 == 0)) {
					out1.println(line1);
				}
			}
			out1.close();
			
			i = 0;
			while((line2 = br2.readLine()) != null){
				i++;
				String two = JaccardCharNgramsInCommonMeasurer.isolateLex(line2).toLowerCase();
				if(correct2.contains(two) || (i % 2 == 0)) {
					out2.println(line2);
				}
			}
			out2.close();
			
			br1.close();
			br2.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
