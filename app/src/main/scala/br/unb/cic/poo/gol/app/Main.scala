package br.unb.cic.poo.gol.app

import br.unb.cic.poo.gol.base.GameEngine
import br.unb.cic.poo.gol.base.GameController
import br.unb.cic.poo.gol.base.Regra
import br.unb.cic.poo.gol.base.GameView

import scala.io.Source
import scala.io.StdIn
import scala.collection.mutable.MutableList


/**
 * Programa principal do GoL.
 * @author Breno Xavier (baseado na implementacao Java de rbonifacio@unb.br
 */

/*object Main {
  def main(args: Array[String]): Unit = {
    val config: LwjglApplicationConfiguration = new LwjglApplicationConfiguration
    new LwjglApplication(new GameCore, config)
  }
}*/


object Main {
    
  def main(args: Array[String]){
    val regras = encontrarRegras("regras.txt")
    mostrarSelecao(regras)
    GameController.start
  }
  
/*
 * Mostra a tela de seleção do modo de jogo
 */ 
  private def mostrarSelecao(regras: List[Regra]) {
    var i = 1
    
    println("Selecione o modo de jogo:")
    regras.foreach( a => {println("["+i+"] " + a.nome); i+=1})
    
    val valor = GameView.parseRowandColumn(readLine).getOrElse(0)
      
    if(valor > 0 && valor <= regras.length) GameEngine.definirRegra(regras(valor-1))
    else {println("Opção inválida"); mostrarSelecao(regras)}
  }
 

/*
 * Retorna uma lista de regras de um arquivo texto
 */
  private def encontrarRegras(arquivo: String = "regras.txt") = 
    Source.fromResource(arquivo).getLines().map[Regra]( (a:String) => Class.forName(a).newInstance.asInstanceOf[Regra]).toList

}

