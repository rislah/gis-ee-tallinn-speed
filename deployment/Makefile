docker: image push
image:
	docker build --build-arg JAR_FILE=build/libs/gis-0.0.1-SNAPSHOT.jar -f ./Dockerfile ../ -t risla8/gis
push:
	docker push risla8/gis