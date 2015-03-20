import sys, re
from itertools import izip

filePath = "Isukha-Idakho.txt"
outFile = "IsukhaIdakhoOut.txt"
oF = open(outFile, "w")

god = re.compile("\t\t[^ ]*\.[^ ]* ")
with open(filePath, "r") as dataFile :
	priorFirst = ''
	for l in dataFile :
		# print priorFirst and " AND " + first
		if(not god.search(l) is None) :
			splits = god.split(l)
			pos = god.search(l).group()
			oF.write(splits[0] + '		' + splits[1][:-1] + pos + '\n')