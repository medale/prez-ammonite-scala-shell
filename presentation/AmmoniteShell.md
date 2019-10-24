---
header-includes:
 - \usepackage{fvextra}
 - \DefineVerbatimEnvironment{Highlighting}{Verbatim}{breaklines,commandchars=\\\{\}}
 - \usepackage{fontspec}
 - \usepackage{setspace}
title: "Ammonite: Succinct Scala Shell Scripting"
author: Markus Dale, medale@asymmetrik.com
date: October 2019
---

# Intro, Slides And Code
* Slides: https://github.com/medale/prez-ammonite-scala-shell/blob/master/presentation/AmmoniteShell.pdf
* Code Examples: https://github.com/medale/prez-ammonite-scala-shell/code

# Examples of Shell Scripting
* Automate a small but labor-intensive task
* Run clean-up jobs with cron
* Refresh all git repositories (git pull)
* build and publish all projects under a common dir

# That Bash Syntax

```bash
# 3041 upvotes for question, 4259 for answer
if [ ! -f CommonImports.sc ]; then
    echo "File not found!"
fi
# elif?


find . -name *.foo -exec rm -f {} \;
```

# Scala: file exists?
```scala
import java.nio.file._

val f = Paths.get("CommonImports.sc")
if (!Files.exists(f)) {
  println("File not found!")
}
```

# Scala: find and delete
```scala
import java.nio.file._
import scala.collection.JavaConverters._

val cwd = Paths.get(sys.props("user.dir"))
val filesStream = Files.walk(cwd)
val files = filesStream.iterator.asScala.toList
val fooFiles = files.filter(_.toString.endsWith(".foo"))
fooFiles.foreach(Files.delete(_))

filesStream.close
```

# Ammonite: file exists?
```scala
if (!exists("CommonImports.sc")) {
   println("File not found!")
}

//also: cd!, ls!, rm!, mv!...
```

# Installing Ammonite
* Prerequisite: Java 8 JDK in your path, e.g. from [Azul Zulu Community Java 8](https://www.azul.com/downloads/zulu-community)
* Download 1.7.4 (2.12 or 2.13) binary from https://github.com/lihaoyi/Ammonite/releases
* Add as amm to your path
     * 1.6.7. last 2.11 version (if you are running older version of Spark...)
     * Can install multiple versions locally

```
cd ~/bin
ln -s amm212 amm
ls -l
amm -> amm212
amm211
amm212
```

# Other ways of installing Ammonite
* From ammonite.io via curl

\tiny

```bash
sudo sh -c '(echo "#!/usr/bin/env sh" && curl -L \ https://github.com/lihaoyi/Ammonite/releases/download/1.7.4/2.13-1.7.4) > \
/usr/local/bin/amm && chmod +x /usr/local/bin/amm' && amm
```
\normalsize

* On Mac: `brew install ammonite-repl`
* Windows: `https://github.com/lihaoyi/Ammonite/issues/119`

# Configure the shell ~/.ammonite/predef.sc
* Ammonite source: https://github.com/lihaoyi/Ammonite
* \small `shell/src/main/resources/ammonite/shell/example-predef.sc`

\scriptsize

```scala
interp.load.ivy(
  "com.lihaoyi" %
  s"ammonite-shell_${scala.util.Properties.versionNumberString}" %
  ammonite.Constants.version
)
@
val shellSession = ammonite.shell.ShellSession()
import shellSession._
import ammonite.ops._
import ammonite.shell._
ammonite.shell.Configure(interp, repl, wd)
```
\normalsize

# Major improvements over Scala REPL
* input and output code syntax highlighting
* output valid Scala code
* multi-line editing - move one statement at a time
* statement history search (by word/prefix + up arrow)
* Classpath search

# Importing scripts
*  vals, function defs, classes, objects or traits (not import statements)

```scala
import $file.CommonImports  //in current dir

import $file.scripts.Utils  //in subdir scripts

import $file.^.print        //in parent dir
```

# exec - bring in the defs and imports
*  all definitions AND import statements

```scala
//contains: import java.nio.file._

import $exec.CommonImports
Paths.get("foo")
import CommonImports._
printWithNewlines(List(1,2,3))
```

# Directories
```scala
val wd = pwd
cd! wd / up
cd(wd/up)

```

# And now for something completely different: Colon Cancer
* Screening saves lives! ![](graphics/Chemo.png){width=100px}
     * Colonoscopy - talk to your doc
     * [Dave Barry: A journey into my colon — and yours](https://www.miamiherald.com/living/liv-columns-blogs/dave-barry/article1928847.html)
* [Colorectal Cancer Alliance](https://www.ccalliance.org/)
* [Ora Lee Smith Cancer Research Foundation](https://oralee.org)

# Running Ammonite with extra memory

```bash
JAVA_OPTS=-Xmx10g; amm211
```

# A better Scala shell - Pretty printing

```

```

# Loading libraries

```
import $ivy.`org.jsoup:jsoup:1.12.1`
import org.jsoup._

val doc = Jsoup.connect("https://en.wikipedia.org/wiki/Marine_debris").get
doc.select(".mw-headline")

show(doc.select(".mw-headline"))
```
# Questions?

![](graphics/Farley.png){width=200px}

* medale@asymmetrik.com
* https://github.com/medale/prez-ammonite-scala-shell/blob/master/presentation/AmmoniteShell.pdf

# Resources
* [Ammonite Shell](https://ammonite.io)
* [Vector graphics for this presentation](https://publicdomainvectors.org/)
