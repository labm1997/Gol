package br.unb.cic.poo.gol.base

class HighLife extends Regra {
  var nome = "HighLife"
  /* verifica se uma celula deve ser mantida viva */
  def shouldKeepAlive(i: Int, j: Int): Boolean = {
    (GameEngine.cells(i)(j).isAlive) &&
      (GameEngine.numberOfNeighborhoodAliveCells(i, j) == 2 || GameEngine.numberOfNeighborhoodAliveCells(i, j) == 3)
  }
  
  /* verifica se uma celula deve (re)nascer */
  def shouldRevive(i: Int, j: Int): Boolean = {
    (!GameEngine.cells(i)(j).isAlive) && 
      (GameEngine.numberOfNeighborhoodAliveCells(i, j) == 3 || GameEngine.numberOfNeighborhoodAliveCells(i, j) == 6)
  }
}
