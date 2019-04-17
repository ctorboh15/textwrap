# Read Me


## To run this program 
### Option 1.
    1.) run `mvn clean install` from cmd line
    2.) run the generated jar file using java -jar {jarName}
    
### Option 2.
    1.) run `mvn spring-boot:run` from cmd line
    
    
## Notes
My thoughts about this project that it got you thinking a bit about edge case scenarios and how space's are respected.
For this project I treated spaces as part of the character count so it was factored into the 13 character limit.

Also for more robustness I allowed the user to input the max character limit so the program could be ran without having to do
any code changes. 

Finally just for fun ... I created a second version of the daParsing method that runs the program continuously until it sent the kill command; I did not use it as it was not part of the 
scope for this challenge but it is there.