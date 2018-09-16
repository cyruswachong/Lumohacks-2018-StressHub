int tempPin = 3;  
int value;
float temperature;

void setup() 
{
  Serial.begin(9600);
  tempPin = 0;  
  pinMode(tempPin, INPUT);
}

void loop()
{
  temperature = analogRead(A0);
  temperature = temperature/24;
  Serial.print(temperature);
  Serial.print("\n");
  delay(100);
}
