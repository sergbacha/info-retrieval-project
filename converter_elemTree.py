import elementtree.ElementTree as ET
import sys
import hashlib

# load command line argument (xml file name)
xmlfile = sys.argv[1]

# load the object that will hash things


# parse the xml file
crimeTree = ET.parse(xmlfile)

# regular expression to use for each json field
jsonField = '\"%s\" : \"%s\" %s\n'

# print json opening array char
print '['
print '{'

commaChar = ','

# loop through each crime data node
for crimeNode in crimeTree.findall('class'):

		
		#now get the inner node
		# print crimeNode.attrib['name'] ,':', ' serg'
		
		# all the crime info
		crimeData = list(crimeNode.getiterator())
		
		# loop through every child in the xml. deep traversal
		for crimeElement in crimeData:
			
			# if we encounter a class tag
			if crimeElement.tag == 'class':
				
				# we are in a new crime entry, start new json entry
				if crimeElement.attrib['name'] == 'ciras.db.Crime':
					print '},'
					print '{'
				
				#else, its probably an address or something else
				continue

			# LonY is last element of entries. If we encounter it,
			# don't put a comma. its the last json entry
			if crimeElement.attrib['name'] != 'LonY':
				commaChar = ','
			else:
				commaChar = ''
			
			
			# create the id hash from the crimeNumber
			if crimeElement.attrib['name'] == 'CrimeNumber':
				sys.stdout.write(jsonField % ('id', hashlib.md5(crimeElement.text).hexdigest(), commaChar))
			# ignore SRID
			elif crimeElement.attrib['name'] == 'SRID':
				continue
			# create a date from the File field
			elif crimeElement.attrib['name'] == 'File':
				unformattedDate = crimeElement.text
				sys.stdout.write(jsonField % ('crimeDate', unformattedDate.replace('.txt',''), commaChar))
			# normal field add it
			else: 
				sys.stdout.write(jsonField % (crimeElement.attrib['name'].lower(),  crimeElement.text, commaChar))
				
			# print crimeElement.tag
print '}'
print ']'

