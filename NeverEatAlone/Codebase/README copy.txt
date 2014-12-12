@AUTHOR TEJASVAM SINGH


TO SET UP YOU WILL NEED :

1. The rabbitmq server : https://www.rabbitmq.com/download.html
2. Apache TomEE (Link in setup instructions -- SetupServer)

You will need to enable guest access to rabbitmq. Please see : https://www.rabbitmq.com/access-control.html


PLEASE REFER TO THE SETUP INSTRUCTIonS FILE,
PERFORM THE FIRST FEW STEPS AND THEN PERFORM THE STEPS IN SetupClient
AND SetupServer FILES. FINALLY, COME BACK TO SETUP INSTRUCTIonS AND PERFORM THE REMAINING STEPS.


**NOTE: 

In order to run the projects, you will need to updae the IP to your IP in 2 locations :
edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.configuration/config.json
for the client project
AND
edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.configuration.services
for the server project.

DO NOT USE localhost. The localhost usually refers to the android emulator. So you won't be able to reach the server.

TESTS:

I've just updated the JSon request/response architecture and so the tests need an overhaul before they can be run.
As they are now, best case they fail because the string returned is now a JSon string.



KNOWN ISSUES :

.

PROBLEM :
AndroidManifest.xml is missing !
FIX:
Clean and Rebuild the project


PROBLEM:
Unable to instantiate...
FIX :
Right click the NeverEatAloneClient project -> properties -> Build Path
Under the src tab, delete all folders and then add gen,src,res.
Clean and rebuild


PROBLEM:
Could not find com.google.gson OR apache.httcomponents etc..
FIX:
Right click the NeverEatAloneClient project --> properties --> Build Path
Click the order and export tab 
Select "Maven Dependencies"

Do so for the NeverEatAloneServer project as well.

Clean and rebuild.


NOTE : Everytime you update the project by right click --> maven --> update, the maven dependencies get unchecked so you need to do steps above.



ADDITIonAL NOTES:

Most warnings were fixed (over 200). 
There are some 8 remaining warnings, some of them are to do with the Android SDK and some are due to the use of deprecated functionality.
Some of them are due to XML validation that Eclipse does. It can be turned off as it is not relevant to our project.


They are top priority and will be taken care of first thing at the beginning of the next iteration.



BUG NOTE :

Sometimes, you may get compile errors even though there are none. The most common source of bugs is the m2eclipse plugin which seems to contradict (sometimes) how eclipse wants to handle the build path.
Right click --> maven --> update.
Selecting maven dependencies
Removing and re-adding src,gen,res
And project clean and rebuild should fix this.

If not, you may have to restart eclipse.





FINALLY :

If everything works (including rabbitmq guest access), Please create two accounts :

one with username "a"
one with username "Tejas"

Log into "a" click "create", fill the fields and click the next button.

Go and log in as "Tejas"

You should see the Meal properties being posted to screen showing successful functioning of the notification framework.


IN THIS ITERATIon, ALL THE SUPPORT FRAMEWORKS REQUIRED FOR THE FUNCTIonALITY THAT THE APP HAS TO OFFER HAVE BEEN CREATED.
However, there is still some considerable tweaking to do. 
The problem is that without the corresponding  GUI pages, it  is hard (if not impossible) to demonstrate all the features of the app that are currently working.



















