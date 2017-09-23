scalac -sourcepath src -d bin src/br/unb/cic/poo/gol/Main.scala
cd bin
jar -cfm ../Gol.jar ../MANIFEST.MF *
cd ..
