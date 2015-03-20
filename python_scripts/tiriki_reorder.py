import sys, re
from itertools import izip

filePath = "../final_data/TirikiDictionary-sfm_george.txt"
outFile = "../final_data/Tiriki.txt"
oF = open(outFile, "w")

god = re.compile("\n")
with open(filePath, "r") as dataFile :
	lx = ''
	ps = ''
	de = ''
	ps = ''
	for l in dataFile :
		if l.startswith("\lx") : 
			lx = l
		elif l.startswith("\ps") :
			ps = l
		elif l.startswith("\de") :
			de = l
			oF.write(lx[4:-2] + '\t\t' + de[4:-2] + '\t\t' + ps[4:-2] + '\t\t\n')
		elif god.search(l) :
			pass
		else :
			print "ABORT!!!! " + l