import sys
import os
import errno
from itertools import izip

filePath = "/Users/gwprice/Desktop/project_data/hand_aligned/II_SL_shuffled.txt"
outFile = "/Users/gwprice/Desktop/project_data/hand_aligned/IsukhaIdakho_SamiaLugwe"
englishSuffix = ".langOne"
foreignSuffix = ".langTwo"


def make_sure_path_exists(path):
    try:
        os.makedirs(path)
    except OSError as exception:
        if exception.errno != errno.EEXIST:
            raise

def splitWordIntoNgrams(word,n=1) :
	if(len(word) <= n) :
		return word + ' \n'
	allNgrams = []
	chars = list(word)
	for a in range(len(chars) - n + 1) :
		ngram = ''
		z = a + n
		for c in range(a,z) :
			character = chars[c]
			ngram = ngram + character
		allNgrams.append(ngram)

	returnString = ''
	for gram in allNgrams :
		returnString = returnString + gram + ' '
	returnString = returnString + '\n'
	return returnString

n = int(sys.argv[1])

tenthLower = -8
tenthUpper = 0
for section in range(10) :
	make_sure_path_exists(outFile + str(section) + "_10")
	make_sure_path_exists(outFile + str(section) + "_90")
	make_sure_path_exists(outFile + str(section) + "_90/output")
	tenLangOneF = open(outFile + str(section) + "_10/data" + englishSuffix, "w")
	tenLangTwoF = open(outFile + str(section) + "_10/data" + foreignSuffix, "w")
	ninetyLangOneF = open(outFile + str(section) + "_90/data" + englishSuffix, "w")
	ninetyLangTwoF = open(outFile + str(section) + "_90/data" + foreignSuffix, "w")
	tenthLower = tenthUpper
	if section in range(4) :
		tenthUpper = tenthUpper + 8
	else :
		tenthUpper = tenthUpper + 7
	i = 0
	dataFile = open(filePath, "r")
	for l in dataFile :
		words = l.split("\t\t\t\t\t")
		wordOne = splitWordIntoNgrams(words[0],n)
		wordTwo = splitWordIntoNgrams(words[1][:-1],n)
		if (i in range(tenthLower,tenthUpper)) :
			tenLangOneF.write(wordOne)
			tenLangTwoF.write(wordTwo)
		else :
			ninetyLangOneF.write(wordOne)
			ninetyLangTwoF.write(wordTwo)
		i = i+1
	with open("/Users/gwprice/Documents/workspace/CognateAlignmentThesis/python_scripts/allngrams/all"+str(n)+"grams.txt","r") as gramsFile :
		for l in gramsFile :
			ninetyLangOneF.write(l)
			ninetyLangTwoF.write(l)

	tenLangOneF.close()
	tenLangTwoF.close()
	ninetyLangOneF.close()
	ninetyLangTwoF.close()