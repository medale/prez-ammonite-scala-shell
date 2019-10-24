def du(dir: Path = pwd): Long = {
   val files = ls.rec(dir).filter(f => f.isFile)
   files.map(f => f.size).sum
}
