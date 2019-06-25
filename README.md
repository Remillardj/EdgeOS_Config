# My EdgeOS 4 Router Configuration
### Obviously slightly modified :)

## Why?
I looked around and had a really tough time finding a solution to resolve local hostnames with a local domain, especially using a mesh network like eero where that is not built in. However, I found a solution that is reliable as heck and beautiful.
This will resolve all local hostnames appended in the config with `mydomain.net`. You can set the mesh network router to bridged so WiFi will work.

## DHCP/DNS
It uses dnsmasq for DNS resolution and DHCP assignment, on the subnet of 192.168.50.2 - 192.168.50.254

## Usage
Download the tar gunzip file and upload it to your Edge Router 4. **BUT*** __make sure__ you make a basic of the config prior to upload mine. You never know with these things!

## Conclusion
Hopefully this helps someone out there, that needed some sort of network infrastructure and resolve hostnames instead of IP's.
