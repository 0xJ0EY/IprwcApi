# IprwcApi
REST API For the IPRWC webshop  
[![Build Status](https://travis-ci.com/0xJ0EY/IprwcApi.svg?token=NHZZE7EcX4WkL29ZP9TB&branch=master)](https://travis-ci.com/0xJ0EY/IprwcApi)

## Installation
You can install the project via git    
```bash
git clone https://github.com/0xJ0EY/IprwcApi.git
cd IprwcApi
```

If you want to run it locally on https you'll need to generate a selfsigned certificate.  
This is possible by executing the following command:
```bash
keytool -genkey -keyalg RSA -alias ipwrc -keystore ipwrc.jks -validity 90 -keysize 2048
```
After you've generated a cert, it needs to be registered in the config for more info check 
[Dropwizard config](https://www.dropwizard.io/1.0.6/docs/manual/configuration.html#default)  


## Running the app
By default the application will take the config file located in /src/main/config/application.yml  

Running the application on MacOS / Linux distro's
```bash
./gradlew run
```

Running the application on Windows  
```batch
gradlew.bat run
```

## Testing
Testing on macOS/Linux systems:  
```bash
./gradlew check
```

Testing on Windows systems:  
```batch
gradlew.bat test
```
