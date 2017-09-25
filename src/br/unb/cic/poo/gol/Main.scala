package br.unb.cic.poo.gol

import scala.collection.mutable.ListBuffer

/**
 * Programa principal do GoL.
 * @author Breno Xavier (baseado na implementacao Java de rbonifacio@unb.br
 */
object Main {
  
  val height = 10
  val width = 10
  GameEngine.definirRegra(Conway)
  
  def main(args: Array[String]){
    GameController.start
  }
}
