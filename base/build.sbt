name := "base"

organization := "br.unb.cic.poo.gol"

version := "1.0"

scalaVersion := "2.12.3"

publishMavenStyle := true

libraryDependencies += "org.scala-lang" % "scala-swing" % "2.10+"
libraryDependencies += "com.badlogicgames.gdx" % "gdx-backend-lwjgl" % "1.9.1"
libraryDependencies += "com.badlogicgames.gdx" % "gdx-platform" % "1.9.1" classifier "natives-desktop"
libraryDependencies += "com.badlogicgames.gdx" % "gdx-box2d-platform" % "1.9.1" classifier "natives-desktop"
