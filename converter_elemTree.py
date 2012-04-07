import elementtree.ElementTree as ET
import sys

# load command line argument (xml file name)
xmlfile = sys.argv[1]

# parse the xml file
crimeTree = ET.parse(xmlfile)

# print json opening array char
print '['
print '{'

# loop through each crime data node
for crimeNode in crimeTree.findall('class'):

		
		#now get the inner node
		# print crimeNode.attrib['name'] ,':', ' serg'
		
		# all the crime info
		crimeData = list(crimeNode.getiterator())
		
		for crimeElement in crimeData:
			
			if crimeElement.tag == 'class':
				if crimeElement.attrib['name'] == 'ciras.db.Crime':
					print '},'
					print '{'
				
				#else
				continue
						
			
			
			print crimeElement.attrib['name'], ':' , crimeElement.text
			# print crimeElement.tag
print ']'

