# Autores
André Filipe de Sousa Silva 16/0079926
Ivan Bastos Lancellotti 16/0009057
Luiz Antônio Borges Martins 16/0013615
Hélio Adson Oliveira Bernardo 16/0030081
Vinicius Campos Silva 16/0041465

# Boas Vindas
Bem vindo a nossa versão do jogo Game of Life, obrigado por ter feito o download.
Nosso jogo foi desenvolvido como um framework e com uma licensa de software livre, para que todos possam joga-lo da maneira que bem entenderem, inclusive adicionando suas próprias regras!
Esperamos que sua experiência seja divertida!

# Gol
Há dois módulos: base e app. Esses módulos formam um framework, o módulo base tem a implementação da Engine do Jogo da Vida e as regras Conway e HighLife, o módulo app é responsável por carregar os módulos usando reflection a partir de um arquivo texto regras.txt


# Compilação
Ao fazer o download, o programa estará dentro de uma pasta chamada 'Gol', e dentro dela estão outras duas pastas que devem ser compiladas, sendo elas 'base' e 'app'.
O módulo app depende do módulo base, portanto deve-se compilar o módulo base primeiro. Para compilar o módulo base use o sbt na pasta Gol/base:
```
$ sbt
sbt:user> assembly
```
Isso vai gerar o arquivo base/target/scala-2.12/base-assembly-1.0.jar. Copie esse arquivo e vá para 
Gol/app, onde deve existir uma pasta chamada 'lib', caso essa pasta não exista, ela deve ser criada, depois adicione o arquivo que estava copiado nessa pasta, Gol/app/lib, para colocá-lo como dependência de app. Agora na pasta app, compile app com o mesmo comando acima.

# Requisitos:
  * scala - Scala programming language
  * sbt
  * sbt-assembly - Plug-in sbt assembly para o sbt

#Orientações para adicionar novas regras
Caso deseje adicionar uma nova regra para o jogo, é necessário que o código fonte da regra seja escrito na linguagem scala. O arquivo da regra deve ser escrito da seguinte maneira e gerado com Nome.scala, onde Nome representa o nome da nova regra:
```
class Nome extends Regra
```
Após a criação desse arquivo ele deve ser colocado na seguinte pasta: 
Gol/base/src/main/scala/br/unb/cic/poo/gol/base

E para concluir deve ser feita uma alteração no arquivo regras.txt, localizado em:
Gol/app/src/main/resources

deve ser adicionado o seguinte texto nesse arquivo:
```
br.unb.cic.poo.gol.base.Nome
```

# Executar
Para executar faça:
```
java -jar ARQUIVO 
```
