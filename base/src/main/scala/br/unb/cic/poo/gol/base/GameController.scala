package br.unb.cic.poo.gol.base

import scala.collection.mutable.ListBuffer

import java.util.TimerTask
import java.util.Timer

/**
 * Relaciona o componente View com o componente Model. 
 * 
 * @author Breno Xavier (baseado na implementacao Java de rbonifacio@unb.br
 */
object GameController {

  val cellsList = new ListBuffer[Memento]
  val timer = new Timer()
  var updateTask: TimerTask = null
  
  def start(rules: List[Regra]) {
    rules.foreach(r => GameEngine.addRule(r))
    GameView.intro
    GameView.showRules(rules)
    GameView.Tela
    GameView.update
  }
  
  def halt() {
    //oops, nao muito legal fazer sysout na classe Controller
    println("\n \n")
    Statistics.display
    System.exit(0)
  }

  def makeCellAlive(i: Int, j: Int) {
    try {
			GameEngine.makeCellAlive(i, j)
			GameView.update
		}
		catch {
		  case ex: IllegalArgumentException => {
		    println(ex.getMessage)
		  }
		}
  }
  
  def nextGeneration {
    if(cellsList.size > 10) cellsList.remove(0) // Remove-se o mais antigo
    cellsList += GameEngine.createMemento
    GameEngine.nextGeneration
    GameView.update
  }
  
  def backGeneration {
    try {
      GameEngine.setMemento(cellsList.remove(cellsList.size-1))
    }
    catch {
      case ex: IndexOutOfBoundsException => {
        println("Sem passado")
      }
    }
    GameView.update
  }
  
  def automatico {
    updateTask = new TimerTask {
      def run = {
        if(cellsList.size > 10) cellsList.remove(0) // Remove-se o mais antigo
        cellsList += GameEngine.createMemento
        GameEngine.nextGeneration
      }
    }
    timer.schedule(updateTask, 17L, 17L)
    GameView.update
  }
  
  def setRule(rule: Regra) {
    GameEngine.definirRegra(rule)
  }
  
}
