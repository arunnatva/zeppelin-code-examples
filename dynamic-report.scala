
def getSite(): Array[(String,String)] = {
    sqlContext.sql("select siteid,sitename from testdb.sites").collect.map(x => (x(0).asInstanceOf[String],x(1).asInstanceOf[String]))
}

def getTurbines(siteId : String): Array[(String,String)] = {
    var turbinesQuery = "select distinct turbineid from testdb.power_wind where siteid = "
    turbinesQuery = turbinesQuery + "'" +siteId+"'"
    sqlContext.sql(turbinesQuery).collect.map(x => (x(0).asInstanceOf[String],x(0).asInstanceOf[String]))
}

val selectedSite = z.select("Wind Sites",getSite()).asInstanceOf[String]

val turbineId = z.select("Turbines",getTurbines(selectedSite))

var query = "select wspeed,pkwh from testdb.power_wind where siteid = "+"'"+ selectedSite +"'" +" and turbineid = "+"'" + turbineId + "'"

val df = sqlContext.sql(query)
println("%table "+df.columns.mkString("\t"))
println(df.map(x => x.mkString("\t")).collect().mkString("\n"))
