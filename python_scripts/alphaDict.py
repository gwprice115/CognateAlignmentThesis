import sys
from itertools import izip

filePath = "TirikiDictionary_2013_draft_I_think_-3.txt"
outFile = "TirikiOut.txt"
oF = open(outFile, "w")

with open(filePath, "r") as dataFile :
	priorFirst = ''
	for l in dataFile :
		# print priorFirst and " AND " + first
		first = l[:2]
		if(priorFirst != first and l != '\n' and priorFirst != '\n') :
			oF.write("BANANASARESOGOOD" + l)
		else :
			print 'failed'
			oF.write(l)
			priorFirst = first