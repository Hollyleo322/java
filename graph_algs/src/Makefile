.PHONY: all clean test s21_graph interface

all: test interface

clean: s21_graph_clean s21_graph_algorithms_clean

test: s21_graph_test s21_graph_algorithms_test

s21_graph_clean:
	gradle -p s21_graph clean
s21_graph_test: s21_graph
	gradle -p s21_graph jacocoTestReport
s21_graph:
	gradle -p s21_graph jar
s21_graph_algorithms: s21_graph
	./makeJavaWrapper.sh
	./moveGraphToAlg.sh
	./moveAlgsToInterface.sh
	gradle -p s21_graph_algorithms jar
s21_graph_algorithms_test: s21_graph_algorithms
	gradle -p s21_graph_algorithms jacocoTestReport
s21_graph_algorithms_clean:
	gradle -p s21_graph_algorithms clean
interface:
	gradle -p interface bootJar
	java -jar interface/build/libs/interface-0.0.1-SNAPSHOT.jar
