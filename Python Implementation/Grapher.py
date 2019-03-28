from matplotlib import pyplot as plt
from matplotlib import style
import numpy as np
import time
import serial
import keyboard

ser = serial.Serial()
ser.baudrate = 9600
ser.port = 'COM5'
ser.timeout = 0.1
ser.open()

start = time.time()

x=[]
y=[]
z=[]


while True:
    if keyboard.is_pressed('q'):
        break
    time.sleep(0.1)
    
    line = ser.readline()
    print(line)
    end = time.time()-start
    print(line[2:-2])
    print(round(end,3))
    try:
        x.append(end)
        y.append(int(line[2:-2]))
        #z.append(int(line[2:-2])*2)
    except:
        print("Error handled")

plt.title('Data Chart')
plt.ylabel('Amps')
plt.xlabel('Time (s)')
plt.plot(x,y,marker='o', markerfacecolor='red', markersize=4, color='salmon', linewidth=1)
#plt.plot(x,z,marker='s', markerfacecolor='blue', markersize=4, color='skyblue', linewidth=1)
plt.legend()
plt.show()

ser.close()
    
        
        
#style.use('ggplot')

#x,y = np.loadtxt('out.csv',unpack=True,delimiter=',')

