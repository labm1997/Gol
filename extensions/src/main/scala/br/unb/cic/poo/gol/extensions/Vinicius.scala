package br.unb.cic.poo.gol.extensions

import br.unb.cic.poo.gol.base.Regra
import br.unb.cic.poo.gol.base.GameEngine

class Vinicius extends Regra {
  var nome = "Vinicius"
  /* verifica se uma celula deve ser mantida viva */
  def shouldKeepAlive(i: Int, j: Int): Boolean = {
    (GameEngine.cells(i)(j).isAlive) &&
      (GameEngine.numberOfNeighborhoodAliveCells(i, j) == 1 || GameEngine.numberOfNeighborhoodAliveCells(i, j) == 2)
  }
  
  /* verifica se uma celula deve (re)nascer */
  def shouldRevive(i: Int, j: Int): Boolean = {
    (!GameEngine.cells(i)(j).isAlive) && 
      (GameEngine.numberOfNeighborhoodAliveCells(i, j) == 8)
  }
}
