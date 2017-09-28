package br.unb.cic.poo.gol.base

trait Regra {
  def shouldKeepAlive(i: Int, j: Int): Boolean
  def shouldRevive(i: Int, j: Int): Boolean
  var nome: String
}

