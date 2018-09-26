# cucumber-kotlin-skeleton

Sample Kotlin test project with Gradle, Cucumber, testNG, Allure, Selenium


## How to run

```
$ gradle clean test
```


## How to build report

```
$ gradle allureReport allureServe
```

## run with dockerized chrome
PREREQUISITES: 
* docker installed [see instruction](https://docs.docker.com/engine/installation/linux/docker-ce/ubuntu/)
* docker-compose installed [see instruction](https://docs.docker.com/compose/install/)
* vnc client installed (e.g Vinagre or [VNC® Viewer for Google Chrome™](https://chrome.google.com/webstore/detail/vnc%C2%AE-viewer-for-google-ch/iabmpiboiopbgfabjmgeedhcmjenhbla?hl=en)

in terminal window #1
```
$ docker-compose up
```
Open VNC client, connect with pass: **`secret`** 

**`localhost:15900`** (chrome node) 

**`localhost:25900`** (firefox node)

in terminal window #2
```
$ KOTLIN_POC_USE_SELENIUM_GRID=true KOTLIN_POC_SELENIUM_GRID_URL=http://localhost:14444/wd/hub gradle clean test
```

when tests finished in terminal window #1
```
^C
$ docker-compose down
```