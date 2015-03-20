import sys
from itertools import izip

filePath = "Samia-Lugwe_dictionary_1-143_OCRed_-2.txt"
outFile = "anomalyOut.txt"
oF = open(outFile, "w")

with open(filePath, "r") as dataFile :
	twoBack = '		'
	oneBack = '		'
	k  = 0
	for thisLine in dataFile :
		k = k + 1
		trueSoFar = True
		i = 0
		while(i < twoBack.index("\t\t") and trueSoFar) :
			twoBackStart = twoBack[:i]
			thisStart = thisLine[:i]
			if(twoBackStart == thisStart) :
				if(not oneBack.startswith(thisStart)) :
					trueSoFar = False
		if(trueSoFar) :#got to the end of the word with no difference
			print '--------------'
			print twoBack
			print oneBack
			print twoBack[:i]
			print thisLine
			print '--------------'
		twoBack = oneBack
		oneBack = thisLine
		print k