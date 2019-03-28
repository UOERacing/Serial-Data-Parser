# Serial-Data-Parser
Program that takes the data incoming from PCB serial bus, writes, and graphs it.

Currently has two implementations: A NodeJS version which only reads and saves serial in data to CSV (graphing WIP), and a Python version which reads and graphs data (CSV save WIP). 

## Features
- Reads data (amperage) from serial bus on 9600 baud rate
- Logs time and saves data to CSV file 
- Graphs data on multiple line graph using MatPlotLib for Python