lazy val root = project
  .in(file("."))
  .settings(
    name := "scala3-lilgrad",
    description := "A little autograd engine in scala",
    version := "0.1.0",
    scalaVersion := "3.0.1",
    libraryDependencies ++= Seq(
        "com.novocode" % "junit-interface" % "0.11" % "test",
        ("org.platanios" %% "tensorflow" % "0.6.4").cross(CrossVersion.for3Use2_13)
    )
    //libraryDependencies += "com.github.tototoshi" % "scala-csv" % "1.3.8"
    // "org.tensorflow" % "tensorflow-core-platform" % "0.3.1",
)
