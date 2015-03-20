import sys, re
from itertools import izip

filePath = "../final_data/Samia-Lugwe.txt"

god = re.compile("[^\t]*\t\t[^\t]*\t\t[^\t]*")
with open(filePath, "r") as dataFile :
	priorFirst = ''
	for l in dataFile :
		if(god.match(l) is None) :
			print l