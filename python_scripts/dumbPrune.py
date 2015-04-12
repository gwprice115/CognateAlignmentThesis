inFile = '/Users/gwprice/Desktop/project_data/final_data/Isukha-Idakho_X_Samia-Lugwe.txt'
outFile = '/Users/gwprice/Desktop/project_data/final_data/Isukha-Idakho_X_Samia-Lugwe_dumbPruned.txt'

with open(inFile, 'r') as inF :
	with open(outFile,'w') as outF :
			for line in inF :
				parts = line.split('		')
				if(len(parts) != 2) :
					print "THERE WAS A HUGE PROBLEM WITH "
					print parts[0]
					print parts[1]
				word1 = list(parts[0])
				word2 = list(parts[1])
				if(len(list(set(word1) & set(word2))) > 0) :
					outF.write(line)
			