worker: sh target/bin/GorohSlot
web: java $JAVA_OPTS -cp target/classes:target/dependency/* com.urfu.GorohSlot.Main
web: java -jar $JAVA_OPTS -Dserver.port=$PORT target/GorohSlot-1.0-SNAPSHOT.jar