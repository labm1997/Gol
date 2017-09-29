# Gol
Há dois módulos: base e app. Esses módulos formam um framework, o módulo base tem a implementação da Engine do Jogo da Vida e as regras Conway e HighLife, o módulo app é responsável por carregar os módulos usando reflection a partir de um arquivo texto regras.txt
# Compilação
O módulo app depende do módulo base, portanto deve-se compilar o módulo base primeiro. Para compilar o módulo base use o sbt na pasta base:
```
$ sbt
sbt:user> assembly
```
Isso vai gerar o arquivo base/target/scala-2.12/base-assembly-1.0.jar. Copie esse arquivo para app/lib para colocá-lo como dependência de app. Agora na pasta app, compile app com o mesmo comando acima.
# Requisitos:
  * scala - Scala programming language
  * sbt
  * sbt-assembly - Plug-in sbt assembly para o sbt
# Executar
Para executar faça:
```
java -jar ARQUIVO 
```
