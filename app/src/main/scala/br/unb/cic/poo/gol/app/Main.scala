package br.unb.cic.poo.gol.app

import br.unb.cic.poo.gol.base.GameEngine
import br.unb.cic.poo.gol.base.GameController
import br.unb.cic.poo.gol.base.Conway
import br.unb.cic.poo.gol.base.Regra

import scala.io.Source
import scala.collection.mutable.MutableList

/**
 * Programa principal do GoL.
 * @author Breno Xavier (baseado na implementacao Java de rbonifacio@unb.br
 */

object Main {
  
  GameEngine.definirRegra(Conway)
  
  def main(args: Array[String]){
    GameController.start
  }

/*
 * Retorna uma lista de regras de um arquivo texto
 */
  private def encontrarRegras(arquivo: String = "regras.txt") = 
    Source.fromResource(arquivo).getLines().map( (a:String) => Class.forName(a).newInstance.asInstanceOf[Regra])

}

