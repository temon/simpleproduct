resolvers += Resolver.typesafeRepo("releases")

resolvers += "CB Central Mirror" at "http://repo.cloudbees.com/content/groups/public"

resolvers += "Java.net Maven2 Repository" at "http://download.java.net/maven/2/"

resolvers += "Sonatype snapshots" at "https://oss.sonatype.org/content/repositories/snapshots/"

addSbtPlugin("com.github.mpeltonen" % "sbt-idea" % "1.7.0-SNAPSHOT")

addSbtPlugin("com.typesafe.sbt" % "sbt-less" % "1.0.6")

addSbtPlugin("com.typesafe.sbt" % "sbt-uglify" % "1.0.3")

addSbtPlugin("com.typesafe.sbt" % "sbt-jshint" % "1.0.3")

addSbtPlugin("net.ground5hark.sbt" % "sbt-concat" % "0.1.8")

addSbtPlugin("com.earldouglas" % "xsbt-web-plugin" % "0.9.0")
