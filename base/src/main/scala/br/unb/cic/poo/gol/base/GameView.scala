package br.unb.cic.poo.gol.base

import scala.io.StdIn.{readInt, readLine}
import scala.io.Source

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
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import scala.collection.mutable.ListBuffer;
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton; 
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener
import com.badlogic.gdx.scenes.scene2d.Event
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle
import com.badlogic.gdx.graphics.Texture

class Coordinates(var x: Int, var y: Int)

class botao(var nome: String, var px: Int, var py: Int, var size: Coordinates)

class GolScreen(var width: Int, var height: Int, cellSize: Coordinates) extends ApplicationListener {
	private var batch: SpriteBatch = null
	private var shapeRenderer: ShapeRenderer = null
	private var camera: OrthographicCamera = null
    private var touchPos: Vector3 = null
    private var texture: Texture = null
    
	override def create() {
		batch = new SpriteBatch
		shapeRenderer = new ShapeRenderer
		camera = new OrthographicCamera
        camera.setToOrtho(false, 760, 400);
        texture = new Texture(Gdx.files.internal("prox.png"))
	}
	
	override def dispose() {
	  batch.dispose
	}

	override def render() {
	  Gdx.gl.glClearColor(0,0,0, 1)
	  Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
	  camera.update
		batch.begin()
		  for(i <- (0 until GameEngine.height)) {
		    for(j <- (0 until GameEngine.width)) {
		      shapeRenderer.begin(ShapeType.Filled);
		        if(GameEngine.isCellAlive(i, j)){
                    shapeRenderer.setColor(0.11f, 0.32f, 0.47f, 1);
                    shapeRenderer.rect((j)*cellSize.x, (GameEngine.height-1-i)*cellSize.y+(height-cellSize.y*GameEngine.height), cellSize.x, cellSize.y);
                }
              shapeRenderer.end();
            }
          }
          // Área dos botões
          shapeRenderer.begin(ShapeType.Filled);
          shapeRenderer.setColor(0,0.2f,0.1f,1);
          shapeRenderer.rect(0, 0, cellSize.x*GameEngine.width, GameView.padding)
          shapeRenderer.end();
		  
          GameView.botoes.foreach(b => {
            shapeRenderer.begin(ShapeType.Filled);
            shapeRenderer.setColor(0,0.4f,0.1f,1);
            shapeRenderer.rect(b.px, b.py, b.size.x, b.size.y); 
            shapeRenderer.end();
          });
		batch.end()
		
		/*if(Gdx.input.isTouched) {
         touchPos = new Vector3
         touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0)
         camera.unproject(touchPos)
         if(touchPos.x > 400 && touchPos.x < 700){
            if (touchPos.y > 100 && touchPos.y < 200){
                GameController.setRule(rules(0))
            }
         }
      }*/
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
    if(character == 'a' && !auto) {
      GameController.automatico
      auto = true
    }
    if(character == 's' && auto) {
      GameController.updateTask.cancel()
      auto = false
    }
    if(character == 'm') GameView.showRules(GameEngine.getRules.toList)
    if(character == 'q') GameController.halt
    return false
   }
   def touchDown (x: Int, y: Int, pointer: Int, button: Int): Boolean = false
   def touchUp (x: Int, y: Int, pointer: Int, button: Int): Boolean = false
   def mouseMoved (x: Int, y: Int): Boolean = false
   def scrolled (amount: Int): Boolean = false
   def touchDragged (x: Int, y: Int, pointer: Int): Boolean = {
        // Verifica se a posição é válida
        val ya = (y/GameView.cellSize.y).toInt;
        val xa = (x/GameView.cellSize.x).toInt;
        if(ya >= 0 && ya < GameEngine.height && xa >= 0 && xa < GameEngine.width) GameEngine.makeCellAlive(ya,xa)
    return false;
   }
}

object GameView{
    var cellSize = new Coordinates(20,20)
    var padding = 0
    var botaosize = new Coordinates(100,20)
    var botoes = new ListBuffer[botao];
    //var regras = encontrarRegras("regras.txt")
    
    def obterLargura = GameEngine.width
	def obterAltura = GameEngine.height
	var inputProcessor: MyInputProcessor = null
	
    def update {
		//printOptions
	}
	
	def intro {
        println("")
        println("BEM VINDO(A) AO JOGO DA VIDA!")
        println("Comandos:")
        println("A: Automático")
        println("S: Parar")
        println("Q: Sair")
        println("M: Mudar modo de jogo")
        println("->: Próxima geração")
        println("<-: Geração anterior")
        println("")
	}
    
    def Tela{

        val config: LwjglApplicationConfiguration = new LwjglApplicationConfiguration
        config.title = "Game of Life"
        config.width = GameEngine.width*cellSize.y
        config.height = GameEngine.height*cellSize.x + padding
        config.useGL30 = true;
        
        
        // Gerar botões (TODO)
        /*GameEngine.getRules.zipWithIndex.foreach(rg => {
            val (r,i) = rg
            botoes += new botao(r.nome,10+(5+botaosize.x)*i,padding/2-botaosize.y/2,botaosize)
        })
        */
        new LwjglApplication(new GolScreen(config.width,config.height,cellSize), config)
        inputProcessor = new MyInputProcessor();
        Gdx.input.setInputProcessor(inputProcessor);
	}
	
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
