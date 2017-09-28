# Gol
Para compilar um .jar do app é preciso gerar um do base e colocar em app/lib
A compilação se faz pelo sbt:
```
$ sbt
sbt:user> assembly
```
# Requisitos:
  * scala - Scala programming language
  * sbt
  * sbt-assembly - Plug-in sbt assembly para o sbt
# Executar
Para executar faça:
```
java -jar ARQUIVO
```
