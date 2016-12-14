import firebase as fb

url = "https://smartbin-16031.firebaseio.com/" 
token = "AIzaSyDzhWTMnEEo2uFH9WxTVY7t-hZ0DaG-u2w"
firebase = fb.FirebaseApplication(url, token)

firebaseData = firebase.get("/")
print firebaseData

locationList = firebaseData.keys()
print locationList # This is a list of keys

locationMap = {}

for location in locationList:
	locationMap[location] = None

print locationMap

counter = 0
for location in firebaseData:
	print "location is " + location
	print firebaseData[location]
	if locationMap[locationList[counter]] == None:
		locationMap[locationList[counter]] = firebaseData[location]["lastTimeStamp"]

	if locationMap[locationList[counter]] != firebaseData[location]["lastTimeStamp"]:
		
		locationMap[locationList[counter]] = firebaseData[location]["lastTimeStamp"]
		s2 = locationMap["sonar2"]
		s3 = locationMap["sonar3"]
		s4 = locationMap["sonar4"]
		s5 = locationMap["sonar5"]
		s6 = locationMap["sonar6"]
		weight = locationMap["weight"]
		toAdd = "%d,%d,%d,%d,%d,%d,%d"%(s1,s2,s3,s4,s5,s6,weight)
		print "\nto Add this to file\n"+toAdd

	print "\nno change, not doing anything "

for location in firebaseData:
	print "location is " + location
	print firebaseData[location]
	if locationMap[locationList[counter]] == None:
		locationMap[locationList[counter]] = firebaseData[location]["lastTimeStamp"]

	if locationMap[locationList[counter]] != firebaseData[location]["lastTimeStamp"]:
		
		locationMap[locationList[counter]] = firebaseData[location]["lastTimeStamp"]
		s2 = locationMap["sonar2"]
		s3 = locationMap["sonar3"]
		s4 = locationMap["sonar4"]
		s5 = locationMap["sonar5"]
		s6 = locationMap["sonar6"]
		weight = locationMap["weight"]
		toAdd = "%d,%d,%d,%d,%d,%d,%d"%(s1,s2,s3,s4,s5,s6,weight)
		print "\nto Add this to file\n"+toAdd

