outFile3 = "/Users/gwprice/Documents/workspace/CognateAlignmentThesis/python_scripts/allngrams/all3grams.txt"
outFile2 = "/Users/gwprice/Documents/workspace/CognateAlignmentThesis/python_scripts/allngrams/all2grams.txt"
outFile1 = "/Users/gwprice/Documents/workspace/CognateAlignmentThesis/python_scripts/allngrams/all1grams.txt"
out1 = open(outFile1, "w")
out2 = open(outFile2, "w")
out3 = open(outFile3, "w")


letters = ['a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z']
for l in letters :
	out1.write(l + '\n')
	for m in letters :
		out2.write(l + m + '\n')
		for n in letters :
			out3.write(l + m + n + '\n')
out1.close()
out2.close()
out3.close()