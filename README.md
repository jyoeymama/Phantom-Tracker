# Phantom-Tracker

Welcome to Phantom Tracker, A sophisticated mod that uses packets from phantoms to be sent to a players client with in game coordinates attached.
TODO: Add files to the correct location (gradle is not compiling because i need everything sorted like the tree below for it to work)

Phantom-Tracker-main/

├── build.gradle

├── gradle.properties

├── gradlew.bat

├── gradle/

│   └── wrapper/

│       ├── gradle-wrapper.jar

│       └── gradle-wrapper.properties

└── src/

    └── main/
    
        ├── java/
        
        │   └── com/
        
        │       └── phantomtracker/
        
        │           ├── PhantomTrackerClient.java
        
        │           └── mixin/
        │               └── ClientPlayNetworkHandlerMixin.java
        └── resources/
            ├── fabric.mod.json
            └── phantomtracker.mixins.json
