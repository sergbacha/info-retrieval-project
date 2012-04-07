from xml.dom.minidom import parse, parseString
import sys




# ======================================================
# = Node is a node object that contains a Crime Object =
# ======================================================
def parseCrimeClass(node):
		print node.childNodes.length
		
		for crimeChildNode in node.childNodes:
			print node.childNodes.length
			print node.nodeType
			print node.attributes.item(0).name
			print node.attributes.length

















xmlfile = sys.argv[1]

xmlDOM = parse(xmlfile)

count = 0

for node in xmlDOM.documentElement.childNodes:
	count+=1
	
	if (node.nodeType == node.ELEMENT_NODE):
			# print node.nodeType
			# print node.nodeName
			# print node.attributes.item(0).name
			# print node.attributes.item(0).value
			# 
			if(node.attributes.item(0).value == "ciras.db.Crime"):
				parseCrimeClass(node)
		
	# name = node.getAttribute('name')
	# print name
   #print xmlDOM.documentElement.tagName

print count