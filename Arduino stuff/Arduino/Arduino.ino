int tempPin = 2;  
int value;
int temperature;

void setup() 
{
  tempPin = 2;
  pinMode(tempPin, INPUT);
}

void loop()
{
  temperature = analogRead(tempPin);
  Serial.print(temperature);
  delay(1000);
}
