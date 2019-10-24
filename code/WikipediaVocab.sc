//https://github.com/lihaoyi/requests-scala
val r = requests.get("https://en.wikipedia.org/wiki/Marine_debris")

browse(r)
r.cookies
r.text

write("out.txt", r.text)

//show HTML source
//search for: de.wikipedia

//import $ivy.`org.jsoup:<TAB>`
import $ivy.`org.jsoup:jsoup:1.9.2`
//Jsoup<Tab>

import org.jsoup._
import scala.collection.JavaConverters._

val doc = Jsoup.parse(r.text)
val links = doc.select("a")
val allLinks = links.iterator.asScala.toList
val aLink = allLinks(10)
aLink.<TAB>

source(aLink.attr("href"))

allLinks.find(_.attr("abs:href").startsWith("https://de.wiki"))
//<a href="https://de.wikipedia.org/wiki/Treibgut" title="Treibgut – German"
//lang="de" hreflang="de" class="interlanguage-link-target">Deutsch</a>

browse(allLinks)

val nonLinkRegex = "/wiki/(.*?):(.*?)".r
val discards = List("Main_Page","International_Standard","JSTOR","Digital_object","PubMed")

def isGoodLink(href: String): Boolean = {
  val isNonLink = href match {
    case nonLinkRegex(special,value) => {
      println(s"$special:$value")
      true
    }
    case _ => false
  }
  if (!isNonLink) {
    !discards.exists(d => href.contains(d))
  } else {
    false
  }
}
val retrieveLinks = allLinks.filter { l =>
  val href = l.attr("href")
  href.startsWith("/wiki") && isGoodLink(href)
}

val suffix = " – German"
import java.io.ByteArrayInputStream
val baseUrl = "https://en.wikipedia.org"
val enDeTuples = retrieveLinks.take(20).flatMap { en =>
             val href = en.attr("href")
             val url = s"$baseUrl$href"
             val r = requests.get(url)
             val in = new ByteArrayInputStream(r.text.getBytes)
             val doc = Jsoup.parse(in, "UTF-8", baseUrl)
             val links = doc.select("a")
             val deOpt = links.iterator.asScala.find(l => l.attr("href").startsWith("https://de.wikipedia"))
             deOpt.map { de => (en.text, de.attr("title").dropRight(suffix.size)) }
             }

val enDeLines = enDeTuples.map { case (en,de) => s"* ${en}: ${de}" }
val deEnLines = enDeTuples.map { case (en,de) => s"* ${de}: ${en}" }

val markdown = s"""# Vocabulary around Marine Debris
   |
   |# English-German
   |${enDeLines.mkString("\n")}
   |
   |# German-English
   |${deEnLines.mkString("\n")}
   |""".stripMargin

write("marine_debris.md", markdown)  //write.over

%pandoc("-s","marine_debris.md","-o","marine_debris.html","--metadata","pagetitle=\"Marine Debris Vocab\"")
//requires Tex install with pdflatex
%pandoc("-s","marine_debris.md","-o","marine_debris.pdf")
