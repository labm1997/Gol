package br.unb.cic.poo.gol.base

import scala.collection.mutable.Stack

import java.util.TimerTask
import java.util.Timer

/**
 * Relaciona o componente View com o componente Model. 
 * 
 * @author Breno Xavier (baseado na implementacao Java de rbonifacio@unb.br
 */
object GameController {

  val cellsList = new Stack[Memento]()
  val timer = new Timer()
  
  def start {
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
    cellsList.push(GameEngine.createMemento)
    GameEngine.nextGeneration
    GameView.update
  }
  
  def backGeneration {
    try {
      GameEngine.setMemento(cellsList.pop)
    }
    catch {
      case ex: NoSuchElementException => {
        println("Sem passado")
      }
    }
    GameView.update
  }
  
  def automatico {
    val updateTask = new TimerTask {
      def run = {
        cellsList.push(GameEngine.createMemento)
        GameEngine.nextGeneration
        GameView.printBoard
      }
    }
    timer.schedule(updateTask, 1000L, 1000L)
    readLine
    updateTask.cancel()
    GameView.update
  }
  
  def setRule(rule: Regra) {
    GameEngine.definirRegra(rule)
  }
  
}
