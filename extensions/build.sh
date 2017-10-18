# Parâmetros
main="br.unb.cic.poo.gol.app.Main"
saida="app.jar"
dependencias="dependencias/base.jar"

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
	if [ -f $saida ]; then
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
fi

# Erro por não achar o arquivo
if [ ! -f $1 ]; then
	echo -e "[ "$vermelho"ERRO"$normal" ] NÃO VAI DAR NÃO"
	echo -e "[ "$vermelho"ERRO"$normal" ] NÃO ACHEI "$1
	exit
fi

# Verifica se há o Main
if [ ! -f "src/"$(echo $main | sed "s/\./\//g")".scala" ]; then
	echo -e "[ "$vermelho"ERRO"$normal" ] NÃO VAI DAR NÃO"
	echo -e "[ "$vermelho"ERRO"$normal" ] NÃO ACHEI src/"$(echo $main | sed "s/\./\//g")".scala"
	exit
fi

# Gera a pasta de compilação
if [ ! -d "bin" ]; then
	mkdir bin
fi
if [ ! -d "bin/dependencias" ]; then
	mkdir bin/dependencias
fi

# Gera o MANIFEST
echo -e "Main-Class: "$main"\nClass-Path: ../"$dependencias":"$1 > MANIFEST.MF

# Compila
scalac -classpath $dependencias -sourcepath src -d bin src/$(echo $main | sed "s/\./\//g").scala

cd bin
jar -cfm ../$saida ../MANIFEST.MF *
cd ..
