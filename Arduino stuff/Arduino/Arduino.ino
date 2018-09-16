int tempPin = 3;  
int value;
float temperature;
float add = 0;
int score;

void setup() 
{
  Serial.begin(9600);
  tempPin = 0;  
  pinMode(tempPin, INPUT);
}

void loop()
{
  for (int i = 0; i < 15; i++)
  {
    temperature = analogRead(A0);
    temperature = temperature/12-15;
    add = add + temperature;
    delay(1000);
  }
  add = add/15;
  score = 2*add;
  
  Serial.print(score);
  Serial.print('\n');
}
