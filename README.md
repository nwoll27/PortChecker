PortChecker
===========

A simple TCP Port Checker utility. Accepts a .csv containing a list of IP's and TCP ports and attempts to open a new socket in each

TODO: This project needs to be completely refactored and rewritten. A list of some changes:
-Deletion of bizarre "ArrayList2D" class in favor of a normal data structure and objects
-Introduction of multi-threading to speed up process time for larger lists of ports
-More intuitive and easier to maintain method of input
-More robust handling of input values (parsing of ranges)
-Improvement of output report to be more readable

