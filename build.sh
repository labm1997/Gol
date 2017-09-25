normal="\x1B[0m"
vermelho="\x1B[31m"
amarelo="\x1B[33m"
verde="\x1B[32m"

if [ $1 == "limpar" ]; then
	echo -e "[ "$verde"EXEC"$normal" ] Limpando arquivos de compilação"
	if [ -d "bin" ]; then
		rm bin -rf
	else
		echo -e "[ "$amarelo"NOTF"$normal" ] Não há bin/,  pulando"
	fi
	if [ -f "MANIFEST.MF" ]; then
		rm MANIFEST.MF
	fi
	if [ -f "Gol.jar" ]; then
		rm Gol.jar
	fi
	exit
fi

if [ ! -f "scala-library.jar" ]; then
	if [ $# == 0 ]; then
		echo -e "[ "$vermelho"ERRO"$normal" ] NÃO VAI DAR NÃO"
		echo -e "[ "$amarelo"DICA"$normal" ] Preciso de um arquivo para funcionar, normalmente esse arquivo está em /usr/share/java/scala-library.jar"
		echo -e "[ "$amarelo"DICA"$normal" ] Me diga onde está \"scala-library.jar\" passando como argumento:"
		echo -e "[ "$amarelo"DICA"$normal" ] $ ./build.sh CAMINHO"
		exit
	elif [ ! -f $1 ]; then
		echo -e "[ "$vermelho"ERRO"$normal" ] NÃO VAI DAR NÃO"
		echo -e "[ "$vermelho"ERRO"$normal" ] NÃO ACHEI "$1
		exit
	else
		if [ -f "MANIFEST.MF" ]; then
			cp MANIFEST.MF MANIFEST.MF.old
		fi
		echo -e "Main-Class: br.unb.cic.poo.gol.Main\nClass-Path: "$1 > MANIFEST.MF
	fi
fi
if [ ! -d "bin" ]; then
	mkdir bin
fi
scalac -sourcepath src -d bin src/br/unb/cic/poo/gol/Main.scala
cd bin
jar -cfm ../Gol.jar ../MANIFEST.MF *
cd ..
