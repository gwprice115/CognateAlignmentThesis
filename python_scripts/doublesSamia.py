import sys
from itertools import izip

filePath = "Samia-Lugwe_dictionary_1-143_OCRed_-2.txt"
outFile = "Samia-Lugwe_out.txt"
oF = open(outFile, "w")

with open(filePath, "r") as dataFile :
	priorFirst = ''
	i  = 0
	for l in dataFile :
		oF.write(l)
		# print "l" + l
		# print priorFirst and " AND " + first
		first = l[:2]
		rest = l[3:]
		# print "f"+first
		# print "r"+rest
		# print "--"
		if(first in rest) :
			print l
			i = i+1
	print i