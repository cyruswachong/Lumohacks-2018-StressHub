import serial
import time
import json
import pyrebase

config = {
 "apiKey": "AIzaSyAiYfR4oA1TqeScxK3MuOWfUw6fcMkndl8",
 "authDomain": "https://lumohacks-project-d9ce0.firebaseapp.com",
 "databaseURL": "https://lumohacks-project-d9ce0.firebaseio.com/",
 "storageBucket": "https://lumohacks-project-d9ce0.appspot.com"
}
firebase = pyrebase.initialize_app(config)
database = firebase.database()

serialport = serial.Serial('COM6', 9600, timeout=0.5)

while True:

    command = serialport.readline()
    time.sleep(0.5)
    serialport.flushInput()
    serialport.flushOutput()
    time.sleep(0.5)
    if(len(str(command)) == 10):
        print ((str(command))[2:7])
        database.update({'unprocessed':{'score':(str(command))[2:7]}})

        
ser.close()
