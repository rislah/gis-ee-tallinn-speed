docker: image push
image:
	docker build --build-arg JAR_FILE=build/libs/demo-0.0.1-SNAPSHOT.jar -f ./Dockerfile ../ -t risla8/demo
push:
	docker push risla8/demo