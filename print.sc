@main
def message(helloee: String = "Ammonite World", repeat: Int = 1): Unit = {
   val out = s"Hello, ${helloee}!\n" * repeat
   println(out)
}

