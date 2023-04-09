# ArnoldShortenator

ArnoldShortenator is a URL shortener meant for companies.  

## How a URL shortener works

Maps a short address, using a "slug", to any external URL.  For example, you might have a company Predator, Inc. 
whose address is  http://www.predatorinc.com.  Your staff has the line `search predatorinc.com` in all of their 
/etc/resolv.conf files (or whatever it is on Windows). 

You bring up a URL shortener on the address https://choppa.predatorinc.com. People can access "choppa" in their 
browser.  Then, you allow people to map a "slug", which is the first part of the path, to any address.  So, entering 
"choppa/jobs" might bring you to "https://www.predatorinc.com/jobs".

## Use Case
This is extremely useful for companies.  For example, many companies use Google Docs.  A link to a set of slides 
might be "https://docs.google.com/document/d/1_JNKQp26Vkd4PmloFxfyXdPl4jDW9V1NHtskSWtdGiI/edit". Imagine this is 
your company holidays.  You can map this to "choppa/holidays" and make it much easier to remember.

Other examples include 
* Company forms, like "choppa/401k"
* Personal home pages, like "choppa/drudder" mapping to my Confluence page
* PRDs, like "choppa/skynet-proposal"

## Running ArnoldShortenator

It's a Spring Boot app.  Clone it, configure your mysql server in src/main/resources/application.properties, and run 
`mvn spring-boot:run`.  Then, hit "http://localhost:8080"




