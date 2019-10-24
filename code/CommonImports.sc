import java.nio.file._
import java.nio.charset.StandardCharsets
import scala.util.Try
import scala.util.Success
import scala.util.Failure

def printWithNewlines(s: Seq[Any]): Unit = {
   println(s.mkString("\n"))
}

def printCwd: Unit = {
   val workingDir = Paths.get(sys.props("user.dir"))
   println(s"Working dir is ${workingDir}.")
}

