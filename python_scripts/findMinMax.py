outFile = '/Users/gwprice/Desktop/project_data/berkeley_lookup/length_normalized_mins_and_maxes.txt'

with open(outFile,'w+') as outF :
	for s in range(0,10) :
		for n in range(1,3) :
			print str(s) + ':' + str(n)
			current_min = float("inf")
			current_max = float("-inf")

			inFile = '/Users/gwprice/Desktop/project_data/berkeley_lookup/section_'+str(s)+'ii-sl'+str(n)+'gram.txt'
			inF = open(inFile,'r')
			for line in inF :
				parts = line.split('		')
				if(len(parts) != 3) :
					print "THERE WAS A HUGE PROBLEM WITH "
					print parts[0]
					print parts[1]
					print parts[2]
				avg = (float(len(parts[0])) + float(len(parts[1])))/2
				score = float(parts[2])
				normalized_score = score / avg
				if (normalized_score > current_max) :
					current_max = normalized_score
				elif (normalized_score < current_min) :
					current_min = normalized_score
			inF.close()
			outF.write("section " + str(s) + ", " + str(n) + "grams: MIN: " + str(current_min) + ", MAX: " + str(current_max) + "\n")