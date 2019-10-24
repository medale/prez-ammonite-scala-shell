//JAVA_OPTS=-Xmx20g; amm

//poi
//https://catalog.data.gov/dataset?res_format=CSV&page=2
//crashes.csv full 895917
//small-crashes.csv subset
//http://poi.apache.org/components/spreadsheet/
import $ivy.`org.apache.poi:poi:4.1.1`
import $ivy.`org.apache.poi:poi-ooxml:4.1.1`
import $ivy.`com.univocity:univocity-parsers:2.8.3`

//CsvParser<Tab>

import com.univocity.parsers.csv._
import java.nio.file._
import scala.collection.JavaConverters._
import java.nio.charset.StandardCharsets

val crashes = 'input / "small-crashes.csv"
val in = Files.newInputStream(crashes.toNIO)
val parser = new CsvParser(new CsvParserSettings)
val linesWithEntries = parser.parseAll(in, StandardCharsets.UTF_8).asScala.toList
in.close

import org.apache.poi.xssf.usermodel._
import org.apache.poi.ss.usermodel._

val wb = new XSSFWorkbook()
val sheet1 = wb.createSheet("Accidents")

val style = wb.createCellStyle
style.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex())
style.setFillPattern(FillPatternType.FINE_DOTS)

linesWithEntries.zipWithIndex.foreach { case(entries,i) =>
   val row = sheet1.createRow(i)
   entries.zipWithIndex.foreach { case (e,j) =>
      val cell =  row.createCell(j)
      cell.setCellValue(e)
      if (i % 2 == 0) {
        cell.setCellStyle(style)
      }
    }
}

val out = new java.io.FileOutputStream("workbook.xslx")
wb.write(out)
out.close
