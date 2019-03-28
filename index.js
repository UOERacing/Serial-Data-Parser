//Global Variables
var isListening = false; 
var state = 0; //0 = Home, 1 = Reading State, 2 = Graphing state
var start, end;



//CSV Writer requirement
var csvWriter = require('csv-write-stream')
var writer = csvWriter({sendHeaders: false})

//File Stream requirement
var fs = require('fs');

//Serial Port requirement
const SerialPort = require('serialport')
const Readline = require('@serialport/parser-readline')
const port = new SerialPort('COM4', { baudRate: 9600 }) //Specify port to listen to and serial rate.

//Initialize read stream for serial port
const parser = new Readline()
port.pipe(parser)

parser.on('data', line => { //Acquires data from incoming stream
  if(isListening){
    if(line.substring(0,2) == "!!"){
      end = new Date() - start;
      //console.clear();
      console.log("Press C to exit. \nAmps: " + line.substring(2)) 
      writer.pipe(fs.createWriteStream('out.csv'));
      writer.write({Milliseconds: end, Amps: line.substring(2)}); // Header: Time (ms), Amps
    }
  }
})

process.on('SIGINT', a => writer.end());

//Program controls via keyboard keystrokes
const readline = require('readline');
readline.emitKeypressEvents(process.stdin);
process.stdin.setRawMode(true);
process.stdin.on('keypress', (str, key) => {
  if (key.name === 'c' && state == 1) { //End program and log data when the 'C' key is pressed.
    isListening = false;
    writer.end();
    console.clear();
    console.log("Successfully written data to CSV file.");
    home();
  }
  if(key.name === '1' && state == 0){ //Go to reader state
    console.clear();
    state = 1;
    fs.truncate('out.csv', 0, function(){});
    start = new Date();
    isListening = true;
  }
  if(key.name === '2' && state == 0){

  }
  if(key.name === 'escape'){ //Exit program all together
    console.clear();
    console.log("Exiting...");
    process.exit();
  }
});


function home(){
  state = 0;
  console.log("\n\nHome\n----------\n1. Data Reader (appends to previous data file)"
  +"\n2. Graph data output");
}

home();

var http = require('http');
var fs = require('fs');

const PORT=8080; 

fs.readFile('./graph.html', function (err, html) {

    if (err) throw err;    

    http.createServer(function(request, response) {  
        response.writeHeader(200, {"Content-Type": "text/html"});  
        response.write(html);  
        response.end();  
    }).listen(PORT);
});