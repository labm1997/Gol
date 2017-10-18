package br.unb.cic.poo.gol.app

import br.unb.cic.poo.gol.base.GameEngine
import br.unb.cic.poo.gol.base.GameController
import br.unb.cic.poo.gol.base.Regra
import br.unb.cic.poo.gol.base.GameView
import br.unb.cic.poo.gol.base.Conway

import scala.io.Source
import scala.io.StdIn
import scala.collection.mutable.MutableList


/**
 * Programa principal do GoL.
 * @author Breno Xavier (baseado na implementacao Java de rbonifacio@unb.br
 */

object Main {
    
  def main(args: Array[String]){
    val regras = encontrarRegras("regras.txt")
    //GameView.Tela
    GameController.setRule(regras(0))
    GameController.start(regras)
  }
  

/*
 * Retorna uma lista de regras de um arquivo texto
 */
  private def encontrarRegras(arquivo: String = "regras.txt") = 
    Source.fromResource(arquivo).getLines().map[Regra]( (a:String) => Class.forName(a).newInstance.asInstanceOf[Regra]).toList

}
