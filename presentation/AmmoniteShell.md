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

# Shell Scripting


# Bash Syntax

# Bash vs. Scala

# Bash vs. Ammonite

# Scala Shell vs. Ammonite Shell

# Installing Ammonite
* Prerequisite: Java 8 JDK, e.g. [Azul Zulu Community Java 8](https://www.azul.com/downloads/zulu-community)
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

# Configuring the shell ~/.ammonite/predef.sc
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
