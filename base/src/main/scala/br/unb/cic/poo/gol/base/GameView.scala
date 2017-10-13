package br.unb.cic.poo.gol.base

import scala.io.StdIn.{readInt, readLine}

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

/**
 * Representa o componente View do GoL
 * 
 * @author Breno Xavier (baseado na implementacao Java de rbonifacio@unb.br
 */
 
class Coordinates(var x: Int, var y: Int)
 
/*
 * Tela padrão
 */
 
class GolScreen(var width: Int, var height: Int, cellSize: Coordinates) extends ApplicationListener {
	private var batch: SpriteBatch = null
	private var shapeRenderer: ShapeRenderer = null

	override def create() {
		batch = new SpriteBatch
		shapeRenderer = new ShapeRenderer
	}
	
	override def dispose() {
	  batch.dispose
	}

	override def render() {
	  Gdx.gl.glClearColor(0,0,0, 1)
	  Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
		batch.begin()
		  for(i <- (0 until height)) {
		    for(j <- (0 until width)) {
		      shapeRenderer.begin(ShapeType.Filled);
		        if(GameEngine.isCellAlive(i, j)){
              shapeRenderer.setColor(0.11f, 0.32f, 0.47f, 1);
              shapeRenderer.rect((j)*cellSize.x, (height-1-i)*cellSize.y, cellSize.x, cellSize.y);
            }
          shapeRenderer.end();
        }
      }
		batch.end()
	}
	
	override def resize(width: Int, height: Int) {
	  
	  
	}
	
	override def pause() {
	
	}
	
	override def resume() {
	
	}
}

/*
 * Processador de entradas
 */
 
class MyInputProcessor extends InputProcessor {
   var auto: Boolean = false
   def keyDown (keycode: Int): Boolean = false
   def keyUp (keycode: Int): Boolean = {
    if(keycode == 22) GameController.nextGeneration
    if(keycode == 21) GameController.backGeneration
    return false
   }
   def keyTyped (character: Char): Boolean = {
    println(character)
    if(character == 'a' && !auto) {
      GameController.automatico
      auto = true
    }
    if(character == 's' && auto) {
      GameController.updateTask.cancel()
      auto = false
    }
    if(character == 'q') GameController.halt
    return false
   }
   def touchDown (x: Int, y: Int, pointer: Int, button: Int): Boolean = false
   def touchUp (x: Int, y: Int, pointer: Int, button: Int): Boolean = false
   def mouseMoved (x: Int, y: Int): Boolean = false
   def scrolled (amount: Int): Boolean = false
   def touchDragged (x: Int, y: Int, pointer: Int): Boolean = {
        GameEngine.makeCellAlive(
          GameEngine.posicao((y/GameView.cellSize.y).toInt, GameEngine.height), 
          GameEngine.posicao((x/GameView.cellSize.x).toInt, GameEngine.width)
        )
    return false;
   }
}

 
object GameView {
  
	private final val LINE = "+-----+"
	private final val DEAD_CELL = "|     |"
	private final val ALIVE_CELL = "|  o  |"
	
	private final val INVALID_OPTION = 0
	private final val MAKE_CELL_ALIVE = 1
	private final val NEXT_GENERATION = 2
	private final val HALT = 3
	private final val BACK_GENERATION = 4
	private final val AUTOMATICO = 5
		
	var cellSize = new Coordinates(20,20)
	
	def obterLargura = GameEngine.width
	def obterAltura = GameEngine.height
	
	var inputProcessor: MyInputProcessor = null
  
  /**
	 * Atualiza o componente view (representado pela classe GameBoard),
	 * possivelmente como uma resposta a uma atualiza��o do jogo.
	 */
	def createBoard {
  
    val config: LwjglApplicationConfiguration = new LwjglApplicationConfiguration
    config.title = "Oiiiiiiii"
    config.width = GameEngine.width*cellSize.x
    config.height = GameEngine.height*cellSize.y
    config.useGL30 = true;
    
    new LwjglApplication(new GolScreen(GameEngine.width,GameEngine.height,cellSize), config)
	
    inputProcessor = new MyInputProcessor();
    Gdx.input.setInputProcessor(inputProcessor);
    
    /*
		printFirstRow
		printLine
		
		for(i <- (0 until GameEngine.height)) {
		  for(j <- (0 until GameEngine.width)) {
		    print(if (GameEngine.isCellAlive(i, j))  ALIVE_CELL else DEAD_CELL);
		  }
		  println("   " + i)
		  printLine
		}*/
		
	}
	
	def update {
		//printOptions
	}
  
 /* private def printOptions {
	  
	  var option = 0
	  println("\n\n")
	  
	  do{
	    println("Select one of the options: \n \n"); 
			println("[1] Make a cell alive");
			println("[2] Next generation");
			println("[3] Halt");
			println("[4] Back generation");
			println("[5] Automatico");
		
			print("\n \n Option: ");
			
			option = parseOption(readLine)
	  }while(option == 0)
	  
	  option match {
      case MAKE_CELL_ALIVE => makeCellAlive
      case NEXT_GENERATION => nextGeneration
      case HALT => halt
      case BACK_GENERATION => backGeneration
      case AUTOMATICO => automatico
    }
	}*/
  
  /*private def makeCellAlive {
	  
	  var i = 0
	  var j = 0
	  
	  do {
      print("\n Inform the row number (0 - " + (GameEngine.height - 1) + "): ")
      i = parseRowandColumn(readLine).getOrElse(-1)
      print("\n Inform the column number (0 - " + (GameEngine.width - 1) + "): ")
      j = parseRowandColumn(readLine).getOrElse(-1)
    } while(!validPosition(i,j))
      
    GameController.makeCellAlive(i, j)
	}*/

  /*private def nextGeneration = GameController.nextGeneration
  private def backGeneration = GameController.backGeneration
  private def automatico = GameController.automatico
  private def halt = GameController.halt*/
	
  private def validPosition(i: Int, j: Int): Boolean = {
		i >= 0 && i < GameEngine.height && j >= 0 && j < GameEngine.width
	}
  
	/*def parseOption(option: String): Int = option match {
    case "1" => MAKE_CELL_ALIVE
    case "2" => NEXT_GENERATION
    case "3" => HALT
    case "4" => BACK_GENERATION
    case "5" => AUTOMATICO
    case _ => INVALID_OPTION
  }*/
	
  
  /* Imprime uma linha usada como separador das linhas do tabuleiro */
	/*private def printLine() {
	  for(j <- (0 until GameEngine.width)) {
	    print(LINE)
	  }
	  println()
	}*/
  
  /*
	 * Imprime os identificadores das colunas na primeira linha do tabuleiro
	 */
	/*private def printFirstRow {
		println("\n \n");
		
		for(j <- (0 until GameEngine.width)) {
		  print("   " + j + "   ")
		}
		println()
	}*/
  

  def parseRowandColumn(x: String): Option[Int] = {
    try {
      Some(x.toInt)
    } catch {
      case e: Exception => None
    }
  }
  
  def showRules(rules: List[Regra]) {
  
    var i = 1
    
    println("Selecione o modo de jogo:")
    rules.foreach( a => {println("["+i+"] " + a.nome); i+=1})
    
    val valor = parseRowandColumn(readLine).getOrElse(0)
      
    if(valor > 0 && valor <= rules.length) GameController.setRule(rules(valor-1))
    else {println("Opção inválida"); showRules(rules)}
    
  }
    
}
