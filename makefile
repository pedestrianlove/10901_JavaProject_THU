build:
	mvn clean install assembly:single
	java -cp target/JavaProjectTHU-1.0-SNAPSHOT-jar-with-dependencies.jar tw.jsl.JavaProjectTHU.App
