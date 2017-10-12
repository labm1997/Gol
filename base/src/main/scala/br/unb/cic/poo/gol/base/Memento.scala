package br.unb.cic.poo.gol.base

class Memento() {
  var cells = Array.fill(GameView.obterAltura, GameView.obterLargura){new Cell}
  
  def getState = cells
  def setState(state: Array[Array[Cell]]) = {
  
    state.zipWithIndex.foreach(lgroup => {
      val (l,i) = lgroup
      l.zipWithIndex.foreach(rgroup => {
        val (r,j) = rgroup
        if(r.isAlive) cells(i)(j).revive
      })
    })
    
  }
}
