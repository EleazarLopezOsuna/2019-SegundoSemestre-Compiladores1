SET JAVA_HOME = "C:\Program Files\Java\jdk-11.0.2\bin";
SET PATH =% JAVA_HOME%;%PATH%;
SET CLASSPATH = %JAVA_HOME%;
cd C:\Users\USER\Documents\NetBeansProjects\Proyecto1_Compiladores_SegundoSemestre_2019\src\Analizadores\HTML
java -jar C:\Users\USER\Desktop\Analizadores\java-cup-11b.jar -parser Analisis_Sintactico -symbols Simbolos Sintactico.cup