package br.unb.cic.poo.gol.app

import br.unb.cic.poo.gol.base.GameEngine
import br.unb.cic.poo.gol.base.GameController
import br.unb.cic.poo.gol.base.Conway

/**
 * Programa principal do GoL.
 * @author Breno Xavier (baseado na implementacao Java de rbonifacio@unb.br
 */

object Main {
  
  GameEngine.definirRegra(Conway)
  
  def main(args: Array[String]){
    GameController.start
  }
}
