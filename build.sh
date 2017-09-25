# Parâmetros
main="br.unb.cic.poo.gol.Main"
saida="Gol.jar"

# Cores
normal="\x1B[0m"
vermelho="\x1B[31m"
amarelo="\x1B[33m"
verde="\x1B[32m"

# Faz a limpeza
if [ "$1" == "limpar" ]; then
	echo -e "[ "$verde"EXEC"$normal" ] Limpando arquivos de compilação"
	if [ -d "bin" ]; then
		rm bin -rf
	else
		echo -e "[ "$amarelo"NOTF"$normal" ] Não há bin/,  pulando"
	fi
	if [ -f "MANIFEST.MF" ]; then
		rm MANIFEST.MF
	fi
	if [ -f "MANIFEST.MF.old" ]; then
		rm MANIFEST.MF.old
	fi
	if [ -f "Gol.jar" ]; then
		rm $saida
	fi
	exit
fi

# Erro por não passar o arquivo
if [ $# == 0 ]; then
	echo -e "[ "$vermelho"ERRO"$normal" ] NÃO VAI DAR NÃO"
	echo -e "[ "$amarelo"DICA"$normal" ] Preciso de um arquivo para funcionar, normalmente esse arquivo está em /usr/share/java/scala-library.jar"
	echo -e "[ "$amarelo"DICA"$normal" ] Me diga onde está \"scala-library.jar\" passando como argumento:"
	echo -e "[ "$amarelo"DICA"$normal" ] $ ./build.sh CAMINHO"
	exit

# Erro por não achar o arquivo
elif [ ! -f $1 ]; then
	echo -e "[ "$vermelho"ERRO"$normal" ] NÃO VAI DAR NÃO"
	echo -e "[ "$vermelho"ERRO"$normal" ] NÃO ACHEI "$1
	exit

# Gera o MANIFEST
else
	if [ -f "MANIFEST.MF" ]; then
		cp MANIFEST.MF MANIFEST.MF.old
	fi
	echo -e "Main-Class: "$main"\nClass-Path: "$1 > MANIFEST.MF
fi

# Verifica se há o Main
if [ ! -f "src/"$(echo $main | sed "s/\./\//g")".scala" ]; then
	echo -e "[ "$vermelho"ERRO"$normal" ] NÃO VAI DAR NÃO"
	echo -e "[ "$vermelho"ERRO"$normal" ] NÃO ACHEI src/"$(echo $main | sed "s/\./\//g")".scala"
	exit
fi

# Gera a pasta de compilação, compila e gera o Java Archive
if [ ! -d "bin" ]; then
	mkdir bin
fi

scalac -sourcepath src -d bin src/$(echo $main | sed "s/\./\//g").scala
cd bin
jar -cfm ../$saida ../MANIFEST.MF *
cd ..
