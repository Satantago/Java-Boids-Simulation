# Boids simulation
# ============================
# This Makefile allows to compile tests of IHM via command lines
# Organisation:
#  1) Source code (*.java) belong to the src directory
#  2) Bytecode (*.class) belong to the bin directory
#
# Compilation:
#  Options of javac:
#   -d : directory in which compiled .class belong
#   -classpath : directory in which belong the .class already compiled
#   -sourcepath : directory in which .java are looked for (dependencies)


all: testGUI testBoids 

testGUI:
	javac -d bin -classpath bin/gui.jar -sourcepath src src/test/TestGUI.java

testBoids:
	javac -d bin -classpath bin/gui.jar -sourcepath src src/test/TestBoidsSimulator.java


doc:
	javadoc -classpath bin/gui.jar -encoding "UTF-8" -docencoding "UTF-8" -charset "UTF-8" -d ./doc -sourcepath ./src -subpackages element event group simulator test utility

# Execution:
# we can either directly write the command line:
#   > java -classpath bin TestGUI
# or start execution by using this Makefile :
#   > make exeIHM
exeGUI: testGUI
	java -classpath bin:bin/gui.jar test/TestGUI

exeBoids: testBoids
	java -classpath bin:bin/gui.jar test/TestBoidsSimulator
clean:
	rm -f bin/*/*.class

run: exePred


