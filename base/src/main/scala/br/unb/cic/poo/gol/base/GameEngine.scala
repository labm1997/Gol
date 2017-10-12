package br.unb.cic.poo.gol.base

import scala.collection.mutable.ListBuffer
import scala.util.control.TailCalls.TailRec
import scala.annotation.tailrec

import scala.io.Source

/**
 * Representa a Game Engine do GoL 
 * 
 * @author Breno Xavier (baseado na implementacao Java de rbonifacio@unb.br
 */
object GameEngine {
  
  val height = GameView.obterAltura
  val width = GameView.obterLargura
  
  /* Instanciando as células na matriz */
  var cells = Array.fill(height, width){new Cell}
  
  /* Padrão de projeto Memento */
  def createMemento: Memento = {
    val m = new Memento
    m.setState(cells)
    return m
  }
  
  def setMemento(m: Memento) = {
    m.getState.zipWithIndex.foreach(lgroup => {
      val (l,i) = lgroup
      l.zipWithIndex.foreach(rgroup => {
        val (r,j) = rgroup
        if(r.isAlive) cells(i)(j).revive
        else cells(i)(j).kill
      })
    })
  }
  
  Source.fromResource("configuracao.txt").getLines.zipWithIndex.foreach(lgroup => {
    val (line,i) = lgroup
    line.split(" ").zipWithIndex.foreach(rgroup => {
      val (row,j) = rgroup
      if(row.toInt == 1 && validPosition(i,j)) cells(i)(j).revive;
    })
  })
  
  /**
	 * Calcula uma nova geracao do ambiente. Essa implementacao utiliza o
	 * algoritmo do Conway, ou seja:
	 * 
	 * a) uma celula morta com exatamente tres celulas vizinhas vivas se torna
	 * uma celula viva.
	 * 
	 * b) uma celula viva com duas ou tres celulas vizinhas vivas permanece
	 * viva.
	 * 
	 * c) em todos os outros casos a celula morre ou continua morta.
	 */
	   
  def nextGeneration {
    
    val mustRevive = new ListBuffer[Cell]
    val mustKill = new ListBuffer[Cell]

    
    for(i <- (0 until height)) {
      for(j <- (0 until width)) {
        if(shouldRevive(i, j)) {
          mustRevive += cells(i)(j)
        }
        else if((!shouldKeepAlive(i, j)) && cells(i)(j).isAlive) {
          mustKill += cells(i)(j)
        }
      }
    }
    
    
    for(cell <- mustRevive) {
      cell.revive
      Statistics.recordRevive
    }
    
    for(cell <- mustKill) {
      cell.kill
      Statistics.recordKill
    }
    
    
  }

  /*
	 * Verifica se uma posicao (a, b) referencia uma celula valida no tabuleiro.
	 */
  private def validPosition(i: Int, j: Int) = 
    i >= 0 && i < height && j >= 0 && j < width;
  
  
  /**
	 * Torna a celula de posicao (i, j) viva
	 * 
	 * @param i posicao vertical da celula
	 * @param j posicao horizontal da celula
	 * 
	 * @throws InvalidParameterException caso a posicao (i, j) nao seja valida.
	 */
  @throws(classOf[IllegalArgumentException])
  def makeCellAlive(i: Int, j: Int) = {
    if(validPosition(i, j)){
      cells(i)(j).revive
      Statistics.recordRevive
    } else {
      throw new IllegalArgumentException
    }
  }
  
  /**
	 * Verifica se uma celula na posicao (i, j) estah viva.
	 * 
	 * @param i Posicao vertical da celula
	 * @param j Posicao horizontal da celula
	 * @return Verdadeiro caso a celula de posicao (i,j) esteja viva.
	 * 
	 * @throws InvalidParameterException caso a posicao (i,j) nao seja valida. 
	 */
  @throws(classOf[IllegalArgumentException])
  def isCellAlive(i: Int, j: Int): Boolean = {
    if(validPosition(i, j)) {
      cells(i)(j).isAlive
    } else {
      throw new IllegalArgumentException
    }
  }
  
  
  /**
	 * Retorna o numero de celulas vivas no ambiente. 
	 * Esse metodo eh particularmente util para o calculo de 
	 * estatisticas e para melhorar a testabilidade.
	 * 
	 * @return  numero de celulas vivas.
	 */
  def numberOfAliveCells {
    var aliveCells = 0
    for(i <- (0 until height)) {
      for(j <- (0 until width)) {
        if(isCellAlive(i, j)) aliveCells += 1
      }
    }
  }
  
  
  /* Regra atual */
  private var regra: Regra = null;
  
  /* Define a regra atual */
  def definirRegra(novaRegra: Regra) = regra = novaRegra
  
  /* verifica se uma celula deve ser mantida viva */
  private def shouldKeepAlive(i: Int, j: Int): Boolean = regra.shouldKeepAlive(i,j)
  
  /* verifica se uma celula deve (re)nascer */
  private def shouldRevive(i: Int, j: Int): Boolean = regra.shouldRevive(i,j)
  
  /* Calcula a posição caso haja overflow ou underflow */
  private def posicao(i: Int, limite: Int): Int = (i%limite+limite)%limite
  
  /*
	 * Computa o numero de celulas vizinhas vivas, dada uma posicao no ambiente
	 * de referencia identificada pelos argumentos (i,j).
	 */
  def numberOfNeighborhoodAliveCells(i: Int, j: Int): Int = {
    var alive = 0
    for(a <- (i - 1 to i + 1)) {
      for(b <- (j - 1 to j + 1)) {
        if ((!(posicao(a,height)==i && posicao(b,width) == j)) && cells(posicao(a,height))(posicao(b,width)).isAlive) {
					alive += 1
				}
      }
    }
    alive
  }

}
