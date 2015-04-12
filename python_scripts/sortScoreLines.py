import collections

inFilePath = '/Users/gwprice/Desktop/project_data/idealThresholdsAndF1s_dummy.txt'

d = {}
with open(inFilePath,'r') as inFile :
	for line in inFile :
		words = line.split()
		score = float(words[3])
		d[score] = line[:-1]
	od = collections.OrderedDict(sorted(d.items()))

	reversed = []
	for k,v in od.iteritems():
		reversed.append(v)
	while(len(reversed) > 0) :
		print reversed.pop()
