lazy val root = project
  .in(file("."))
  .settings(
    name := "scala3-lilgrad",
    description := "A little autograd engine in scala",
    version := "0.1.0",
    scalaVersion := "3.0.1",
    //libraryDependencies += "com.github.tototoshi" % "scala-csv" % "1.3.8"
    //libraryDependencies += "com.novocode" % "junit-interface" % "0.11" % "test"
  )
